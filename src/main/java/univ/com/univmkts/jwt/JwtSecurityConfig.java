package univ.com.univmkts.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;
import univ.com.univmkts.customAuth.CustomAuthenticationProvider;
import univ.com.univmkts.users.srv.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig
{

    private final CustomAuthenticationProvider customAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {

        // @formatter:off
        // h2-console is a servlet
        // https://github.com/spring-projects/spring-security/issues/12310
        /*
         * In this examplw we would be using Oauth2 JWT based Asymmetric Encrption RSA256 based authentication
         * WE would be configuring 2 Authorization providers to illustrate multiple Authorization Sources
         *  to authorize Security Filter Chain
         *  - DAO based MySql Auth provider
         *  - Custom Auth provider 
         * - Authorize /authorize url is exposed to generate the bearer token(s) which could be used in subsequent REST Calls 
         */
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                                               .requestMatchers(HttpMethod.POST,"/authenticate").permitAll()
                                               //.requestMatchers(HttpMethod.POST,"/auth/addNewUser").permitAll() //- Started using Pre-Registered Users

                                               //Add New User Endpoint - ADMIN Only  
                                               .requestMatchers(HttpMethod.POST,"/auth/addNewUser").permitAll()
                                               // Screener APIs – ADMIN only
                                               .requestMatchers("/screener/**").hasAuthority("SCOPE_ADMIN")
                                              
                                               // Watchlist APIs – ADMIN and GENERAL_USER
                                                .requestMatchers(HttpMethod.GET, "/watchlist/**")
                                                    .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_GENERAL_USER")

                                                .requestMatchers(HttpMethod.POST, "/watchlist/**")
                                                   .hasAuthority("SCOPE_ADMIN")
                                                
                                                // Public endpoint
                                                .requestMatchers("/hello").permitAll()   
                                                // CORS preflight
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // Everything else blocked
                                                .anyRequest().denyAll())
                       
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .headers(header ->
                {
                    header.frameOptions((frameOptions) -> frameOptions.sameOrigin());
                })
                .build();

                // @formatter:on
    }

    // User Creation
    @Bean
    @Primary
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsServiceImpl();
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /*
     * Adding Multiple Authentication Provider(s) to the Authentication Manager -
     * Each Auth Provider is respected for Token Generation and Subsequent Usage
     */
    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> myAuthenticationProviders)
    {
        return new ProviderManager(authenticationProvider(), customAuthProvider);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource()
    {
        JWKSet jwkSet = new JWKSet(rsaKey());
        return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource)
    {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException
    {
        return NimbusJwtDecoder.withPublicKey(rsaKey().toRSAPublicKey()).build();
    }

    @Bean
    public RSAKey rsaKey()
    {

        KeyPair keyPair = keyPair();

        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString()).build();
    }

    @Bean
    public KeyPair keyPair()
    {
        try
        {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to generate an RSA Key Pair", e);
        }
    }

}

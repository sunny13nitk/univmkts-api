package univ.com.univmkts.users.rest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import univ.com.univmkts.users.model.User;
import univ.com.univmkts.users.srv.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserProvisioningController
{

    private final UserDetailsServiceImpl service;

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User userInfo)
    {
        return service.addUser(userInfo);
    }

}
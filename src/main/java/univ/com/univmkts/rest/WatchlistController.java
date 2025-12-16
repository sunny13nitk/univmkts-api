package univ.com.univmkts.rest;

import java.time.Instant;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import univ.com.univmkts.model.EN_WatchlistPublic;
import univ.com.univmkts.repos.RepoWatchlist;
import univ.com.univmkts.watchlist.pojos.TY_WLDB;
import univ.com.univmkts.watchlist.srv.intf.IF_WLUploadConvSrv;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
@Slf4j
public class WatchlistController
{

    private final MessageSource msgSrc;
    private final IF_WLUploadConvSrv wlUploadConvSrv;
    private final RepoWatchlist repoWl;

    @PostMapping("/")
    @Transactional
    public ResponseEntity<List<EN_WatchlistPublic>> uploadWatchlist(@RequestBody List<TY_WLDB> watchlist)
    {
        List<EN_WatchlistPublic> entWl = null;
        if (watchlist != null && !watchlist.isEmpty())
        {
            entWl = wlUploadConvSrv.convertAndUploadWatchlist(watchlist);
            log.info("Watchlist uploaded with : " + entWl.size() + " scrips at -  " + Instant.now());
            repoWl.saveAll(entWl);
            return ResponseEntity.ok(entWl);

        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<EN_WatchlistPublic>> loadWatchList()
    {
        List<EN_WatchlistPublic> entWl = null;
        if (repoWl.count() > 0)
        {
            entWl = repoWl.findAll();
            log.info("Watchlist loaded with : " + entWl.size() + " scrips at -  " + Instant.now());
            return ResponseEntity.ok(entWl);

        }

        return ResponseEntity.badRequest().build();
    }

}

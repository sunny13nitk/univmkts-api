package univ.com.univmkts.rest;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import univ.com.univmkts.model.EN_WatchlistPublic;
import univ.com.univmkts.watchlist.pojos.TY_WLDB;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
@Slf4j
public class WatchlistController
{

    private final MessageSource msgSrc;

    @PostMapping("/")
    public ResponseEntity<List<EN_WatchlistPublic>> uploadWatchlist(@RequestBody List<TY_WLDB> watchlist)
    {
        List<EN_WatchlistPublic> entWl = null;
        if (watchlist != null && !watchlist.isEmpty())
        {

        }

        return ResponseEntity.badRequest().build();
    }

}

package univ.com.univmkts.watchlist.srv.intf;

import java.util.List;

import univ.com.univmkts.model.EN_WatchlistPublic;
import univ.com.univmkts.watchlist.pojos.TY_WLDB;

public interface IF_WLUploadConvSrv
{
    public List<EN_WatchlistPublic> convertAndUploadWatchlist(List<TY_WLDB> watchlist);
}

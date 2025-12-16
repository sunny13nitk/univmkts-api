package univ.com.univmkts.watchlist.srv.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import univ.com.univmkts.enums.EnumGrowth;
import univ.com.univmkts.enums.EnumMCapClass;
import univ.com.univmkts.model.EN_WatchlistPublic;
import univ.com.univmkts.watchlist.pojos.TY_WLDB;
import univ.com.univmkts.watchlist.srv.intf.IF_WLUploadConvSrv;

@Service
public class CL_WLUploadConvSrv implements IF_WLUploadConvSrv
{

    @Override
    public List<EN_WatchlistPublic> convertAndUploadWatchlist(List<TY_WLDB> watchlist)
    {
        List<EN_WatchlistPublic> entWl = null;
        if (watchlist != null)
        {
            if (CollectionUtils.isNotEmpty(watchlist))
            {
                entWl = watchlist.stream().map(wl ->
                {
                    EN_WatchlistPublic ent = new EN_WatchlistPublic();
                    ent.setScrip(wl.getScrip());
                    ent.setMcapCr(wl.getMcapCr());
                    if (wl.getMcapCr() >= 100000)
                    {
                        ent.setMcapClass(EnumMCapClass.LargeCap);
                    }
                    else if (wl.getMcapCr() < 10000)
                    {
                        ent.setMcapClass(EnumMCapClass.MicroCap);
                    }
                    else if (wl.getMcapCr() >= 10000 && wl.getMcapCr() < 200000)
                    {
                        ent.setMcapClass(EnumMCapClass.SmallCap);
                    }
                    else if (wl.getMcapCr() >= 20000 && wl.getMcapCr() < 100000)
                    {
                        ent.setMcapClass(EnumMCapClass.MidCap);
                    }

                    ent.setGrowth(wl.getAvgReturns());
                    if (wl.getAvgReturns() >= 25)
                    {
                        ent.setGrowthClass(EnumGrowth.VeryHigh);
                    }
                    else if (wl.getAvgReturns() > 20 && wl.getAvgReturns() < 25)
                    {
                        ent.setGrowthClass(EnumGrowth.High);
                    }
                    else if (wl.getAvgReturns() >= 15 && wl.getAvgReturns() < 20)
                    {
                        ent.setGrowthClass(EnumGrowth.Moderate);
                    }
                    else if (wl.getAvgReturns() < 15)
                    {
                        ent.setGrowthClass(EnumGrowth.Low);
                    }

                    ent.setIdx52Wk(wl.getIdx52Wk());
                    ent.setLongevitygr(wl.getLongEPSCAGR());
                    ent.setPeg(wl.getPeg());

                    return ent;
                }).toList();
            }

        }
        return entWl;
    }

}

package univ.com.univmkts.watchlist.pojos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TY_WLDB
{
    private String scrip;
    private String rating; // EnumWLRating
    private String scriptype; // EnumScripType
    private double avgReturns;
    private double err;
    private double mcapCr;
    private int longEPSCAGR;
    private String dailyPL;
    private double price2StrongBuy;
    private double cmp;
    private double deltaStrongBuyCMP;
    private double currpe;
    private double pe5y;
    private double mcap2sales;
    private double mcap2sales3y;
    private double epsttm;
    private double revgr3y;
    private double patgr3y;
    private double ttmpatgr;
    private double baseCaseEPSCAGR;
    private double baseCaseTermPE;
    private double baseCaseTP;
    private double baseCaseRetCAGR;
    private double bullCaseEPSCAGR;
    private double bullCaseTermPE;
    private double bullCaseTP;
    private double bullCaseRetCAGR;
    private double bullwtRatio;
    private double peg;
    private int size;
    private double idx52Wk;
    private double ocfPAT5Y; // Operating cash flow to PAT - last 5 years cumulative

    private List<TY_KeyValueDouble> exitKeyVals = new ArrayList<TY_KeyValueDouble>();
    private String notesLink;

}

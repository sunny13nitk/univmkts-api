package univ.com.univmkts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import univ.com.univmkts.enums.EnumGrowth;
import univ.com.univmkts.enums.EnumMCapClass;

@Entity
@Table(name = "watchlist")
@AllArgsConstructor
@NoArgsConstructor
public class EN_WatchlistPublic
{
    @Id
    private String scrip;
    private double mcapCr;
    @Enumerated(EnumType.STRING)
    private EnumMCapClass mcapClass;
    private double growth;
    @Enumerated(EnumType.STRING)
    private EnumGrowth growthClass;
    private int longevitygr;
    private double idx52Wk;
    private double peg;

}
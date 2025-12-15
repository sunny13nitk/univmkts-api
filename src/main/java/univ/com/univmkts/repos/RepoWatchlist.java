package univ.com.univmkts.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import univ.com.univmkts.model.EN_WatchlistPublic;

@Repository
public interface RepoWatchlist extends JpaRepository<EN_WatchlistPublic, String>
{

}

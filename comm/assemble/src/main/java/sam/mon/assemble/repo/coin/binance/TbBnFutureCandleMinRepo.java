package sam.mon.assemble.repo.coin.binance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMin;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMinId;


@Repository
public interface TbBnFutureCandleMinRepo extends JpaRepository<TbBnFutureCandleMin, TbBnFutureCandleMinId>{
	
	 @Query(value =
		        "SELECT "+
		            " rp.year AS year " +
		            ", SUM(rp.loan_small) AS smallSum " +
		            ", SUM(rp.loan_major) AS majorSum " +
		            ", SUM(rp.loan_total) AS totalSum " +
		            "FROM report rp " +
		            "GROUP BY rp.year"
		        , nativeQuery = true
		    )
		    List<TbBnFutureCandleMin> findMaxOpenTimeBySymbol();

}

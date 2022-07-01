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
		            " cm.symbol AS symbol " +
		            ", SUM(rp.time_open) AS time_open " +
		            "FROM tb_bn_future_candle_min cm " +
		            "GROUP BY cm.symbol"
		        , nativeQuery = true
		    )
		    List<TbBnFutureCandleMin> findMaxOpenTimeBySymbol();

}

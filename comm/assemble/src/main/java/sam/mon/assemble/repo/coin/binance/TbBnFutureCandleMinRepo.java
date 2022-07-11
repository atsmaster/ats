package sam.mon.assemble.repo.coin.binance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMin;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMinId;


@Repository
public interface TbBnFutureCandleMinRepo extends JpaRepository<TbBnFutureCandleMin, TbBnFutureCandleMinId>{
	
//	 @Query(value = "SELECT ie.symbol as symbol"
//	 		+ "     , IF(cm.time_open IS NOT NULL, cm.time_open, ie.onboard_date) AS time_open"
//	 		+ "  FROM tb_bn_future_exchang_info_entry ie "
//	 		+ "  LEFT JOIN ("
//	 		+ " 	    SELECT cm.symbol AS symbol, MAX(cm.time_open) AS time_open "
//	 		+ "		FROM   tb_bn_future_candle_min cm GROUP BY cm.symbol "
//	 		+ "	   ) cm"
//	 		+ "	on ie.symbol = cm.symbol"
//	 		+ " WHERE ie.list_yn = 'Y'"
//		        , nativeQuery = true
//		    )
	
//	@Query(value = "SELECT ie FROM tb_bn_future_exchang_info_entry ie "
//			+ "JOIN FETCH ie.(SELECT cm.symbol AS symbol, MAX(cm.time_open) AS time_open FROM tb_bn_future_candle_min cm GROUP BY cm.symbol)"
//			,nativeQuery = true)
	@Query(value = "SELECT ie FROM tb_bn_future_exchang_info_entry ie "
			+ "JOIN FETCH ie.tb_bn_future_candle_min"
			,nativeQuery = true)
	 List<Object> findAllMaxOpenTime();

}

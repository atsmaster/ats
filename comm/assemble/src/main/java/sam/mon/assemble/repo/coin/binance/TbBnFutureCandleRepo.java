package sam.mon.assemble.repo.coin.binance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.MaxTimeOpenOfSymbol;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleId;


@Repository
public interface TbBnFutureCandleRepo extends JpaRepository<TbBnFutureCandle, TbBnFutureCandleId>{
    

    
 
    @Query( nativeQuery = true, value =
            "SELECT "+
            "  c.symbol AS symbol, " +
            "  c.time_enum_tp AS timeEnumTp, " +
            "  MAX(c.time_open) AS timeOpen " +
            "FROM " + 
            "  tb_bn_future_candle c " +
            "GROUP BY "+
            "  c.symbol, c.time_enum_tp"
            
        )
        List<MaxTimeOpenOfSymbol> findMaxOpenTimeOfSymbol();
    
}

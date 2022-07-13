package sam.mon.assemble.repo.coin.binance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.TbBnFutureCandle;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleId;


@Repository
public interface TbBnFutureCandleRepo extends JpaRepository<TbBnFutureCandle, TbBnFutureCandleId>{
	

}

package sam.mon.assemble.repo.coin.binance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMin;
import sam.mon.assemble.model.coin.binance.TbBnFutureCandleMinId;


@Repository
public interface TbBnFutureCandleMinRepo extends JpaRepository<TbBnFutureCandleMin, TbBnFutureCandleMinId>{

}

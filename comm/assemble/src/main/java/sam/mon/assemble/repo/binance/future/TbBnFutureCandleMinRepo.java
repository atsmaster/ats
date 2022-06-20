package sam.mon.assemble.repo.binance.future;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.binance.future.TbBnFutureCandleMin;
import sam.mon.assemble.model.binance.future.TbBnFutureCandleMinId;

@Repository
public interface TbBnFutureCandleMinRepo extends JpaRepository<TbBnFutureCandleMin, TbBnFutureCandleMinId>{

}

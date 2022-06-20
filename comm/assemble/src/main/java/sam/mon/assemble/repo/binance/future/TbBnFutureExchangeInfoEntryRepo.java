package sam.mon.assemble.repo.binance.future;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.binance.future.TbBnFutureExchangeInfoEntry;

@Repository
public interface TbBnFutureExchangeInfoEntryRepo extends JpaRepository<TbBnFutureExchangeInfoEntry, String>{

}

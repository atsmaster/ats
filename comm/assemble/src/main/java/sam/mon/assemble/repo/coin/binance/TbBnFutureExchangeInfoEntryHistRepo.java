package sam.mon.assemble.repo.coin.binance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntryHist;

@Repository
public interface TbBnFutureExchangeInfoEntryHistRepo extends JpaRepository<TbBnFutureExchangeInfoEntryHist, String>{

}

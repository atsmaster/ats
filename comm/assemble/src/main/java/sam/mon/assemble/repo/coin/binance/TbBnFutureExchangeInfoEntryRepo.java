package sam.mon.assemble.repo.coin.binance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.coin.binance.TbBnFutureExchangeInfoEntry;

@Repository
public interface TbBnFutureExchangeInfoEntryRepo extends JpaRepository<TbBnFutureExchangeInfoEntry, String>{
	
	List<TbBnFutureExchangeInfoEntry> findByListYn(Boolean listYn);

}

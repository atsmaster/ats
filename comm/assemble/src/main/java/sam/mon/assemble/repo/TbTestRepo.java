package sam.mon.assemble.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sam.mon.assemble.model.TbTest;


@Repository
public interface TbTestRepo extends JpaRepository<TbTest, Long>{

}

package sam.mon.assemble.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_test")
@EqualsAndHashCode
public class TbTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB.Column에 auto_increment 옵션 있어야함
	private Long tbTestNo;

	private String tbTestStr;

	@EqualsAndHashCode.Exclude
	private BigDecimal tbTestBd;


    @EqualsAndHashCode.Include
    private BigDecimal tbTestBdForEq() {
        return tbTestBd.stripTrailingZeros();    
    }
	
}

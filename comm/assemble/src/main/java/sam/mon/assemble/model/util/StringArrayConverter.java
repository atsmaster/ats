package sam.mon.assemble.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;

public class StringArrayConverter implements AttributeConverter<List<String>, String> {
	 private static final String SPLIT_CHAR = ",";

	  @Override
	  public String convertToDatabaseColumn(List<String> attribute) {
	    return attribute.stream().collect(Collectors.joining(SPLIT_CHAR));
	  }

	  @Override
	  public List<String> convertToEntityAttribute(String dbData) {
	    return Arrays.stream(dbData.split(SPLIT_CHAR))
	        .collect(Collectors.toList());

	  }
}

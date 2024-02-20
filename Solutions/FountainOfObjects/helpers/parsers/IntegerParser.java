package helpers.parsers;

import java.util.Optional;

public class IntegerParser {
	private String numberString;
	
	private static final String MAX_VALUE_STRING = Integer.toString(Integer.MAX_VALUE);
	private static final String MIN_VALUE_STRING = Integer.toString(Integer.MIN_VALUE).substring(1);
	
	private static final char NEGATIVE_SIGN = '-';
	
	private IntegerParser(String numberString) {
		if(numberString == null){
			throw new NullPointerException();
		}
		
		this.numberString = numberString;
	}
	
	public static Optional<Integer> tryParse(String numberString){
		IntegerParser parser = new IntegerParser(numberString);
		
		return parser.parseIfValid();
	}
	
	private Optional<Integer> parseIfValid(){
		if(numberString.length() == 0){
			return emptyResult();
		}
		
		boolean isNegative = numberString.charAt(0) == NEGATIVE_SIGN;
		
		String valueLimitString = MAX_VALUE_STRING;
		
		if(isNegative){
			numberString = numberString.substring(1);
			
			if(numberString.length() == 0){
				return emptyResult();
			}
			
			valueLimitString = MIN_VALUE_STRING;
		}
		
		boolean potentiallyOutOfRange;
		
		switch(Integer.signum(Integer.compare(numberString.length(), valueLimitString.length()))){
			case 0:
				potentiallyOutOfRange = true;
				break;
			
			case -1:
				potentiallyOutOfRange = false;
				break;
			
			case 1:
				return emptyResult();
				
			default: throw new RuntimeException("should not reach");
		}
		
		for(int i = 0; i < numberString.length(); i++){
			char currentDigit = numberString.charAt(i);
			
			if(!Character.isDigit(currentDigit)){
				return emptyResult();
			}
			
			if(potentiallyOutOfRange){
				switch(Integer.signum(Character.compare(currentDigit, valueLimitString.charAt(i)))){
					case 0:
						potentiallyOutOfRange = true;
						break;
					
					case -1:
						potentiallyOutOfRange = false;
						break;
					
					case 1:
						return emptyResult();
						
					default: throw new RuntimeException("should not reach");
				}
			}
		}
		
		if(isNegative){
			numberString = "-" + numberString;
		}
		
		return valueResult(Integer.parseInt(numberString));
	}
	
	private static Optional<Integer> emptyResult(){
		return Optional.empty();
	}
	
	private static Optional<Integer> valueResult(Integer value){
		return Optional.of(value);
	}
}
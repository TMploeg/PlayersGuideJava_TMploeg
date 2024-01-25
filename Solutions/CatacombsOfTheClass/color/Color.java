package color;

public class Color{
	private int red;
	private int green;
	private int blue;
	
	public static final Color WHITE = new Color(255,255,255);
	public static final Color BLACK = new Color(0,0,0);
	public static final Color RED = new Color(255,0,0);
	public static final Color ORANGE = new Color(255,165,0);
	public static final Color YELLOW = new Color(255,255,0);
	public static final Color GREEN = new Color(0,128,0);
	public static final Color BLUE = new Color(0,0,255);
	public static final Color PURPLE = new Color(128,0,128);
	
	public Color(int red, int green, int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public Color(String hexCode){
		int[] hexValues = convertHexString(hexCode);
		if(hexValues.length != 3){
			throw new RuntimeException("method should have returned 3 values");
		}
		
		red = hexValues[0];
		green = hexValues[1];
		blue = hexValues[2];
	}
	
	private int[] convertHexString(String hexCode){
		if(hexCode == null || hexCode.length() == 0){
			throw new RuntimeException("must provide hex string");
		}
		
		boolean startsWithHashtag = hexCode.charAt(0) == '#';
		
		if(hexCode.length() != (startsWithHashtag ? 7 : 6)){
			throw new RuntimeException("string is in incorrect format");
		}
		
		String[] splitHexCodes = startsWithHashtag 
			? new String[] {
				hexCode.substring(1,3),
				hexCode.substring(3,5),
				hexCode.substring(5)
			}
			: new String[] {
				hexCode.substring(0,2),
				hexCode.substring(2,4),
				hexCode.substring(4)
			};
		
		String hexChars = "0123456789abcdef";
		
		int[] hexValues = new int[splitHexCodes.length];
		
		for(int hexCodeIndex = 0; hexCodeIndex < splitHexCodes.length; hexCodeIndex++){
			String partialHexCode = splitHexCodes[hexCodeIndex];
			
			for(int hexCodeCharIndex = 0; hexCodeCharIndex < partialHexCode.length(); hexCodeCharIndex++){
				int hexCharValue = hexChars.indexOf(partialHexCode.charAt(hexCodeCharIndex));
				int digitScale = partialHexCode.length() - 1 - hexCodeCharIndex;
				
				if(hexCharValue == -1){
					throw new RuntimeException("not a valid hex code");
				}
				
				hexValues[hexCodeIndex] += ((int)Math.pow(16, digitScale)) * hexCharValue;
			}
		}
		
		return hexValues;
	}
	
	@Override
	public String toString(){
		return "(" + red + ", " + green + ", " + blue + ")";
	}
}
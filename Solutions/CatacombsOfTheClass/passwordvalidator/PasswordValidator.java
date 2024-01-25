package passwordvalidator;

public class PasswordValidator{
	private int minPasswordLength = 6;
	private int maxPasswordLength = 13;
	
	private char[] illegalCharacters = {'T','&'};
	
	public boolean isValidPassword(String password){
		if(password == null || password.length() < minPasswordLength || password.length() > maxPasswordLength){
			return false;
		}
		
		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		boolean hasNumber = false;
		boolean hasIllegalCharacter = false;
		
		for(char c : password.toCharArray()){
			if(Character.isUpperCase(c)){
				hasUpperCase = true;
			}
			
			if(Character.isLowerCase(c)){
				hasLowerCase = true;
			}
			
			if(Character.isDigit(c)){
				hasNumber = true;
			}
			
			for(char illegalCharacter : illegalCharacters){
				if(illegalCharacter == c){
					hasIllegalCharacter = true;
					break;
				}
			}
		}
		
		return hasUpperCase && hasLowerCase && hasNumber && !hasIllegalCharacter;
	}
}
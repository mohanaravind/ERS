/**
 * 
 */
package com.mohanaravind.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Use this to create an 4 digit token with DeviceID, Phone number,
 * Seed and a passphrase 
 *
 * @author Aravind Mohan
 *
 */
public class TokenFactory {
	private final Integer primaryKey = 926543;
	private final Integer secondaryKey = 265732;
	private final Integer tertiaryKey = 346925;
	
	private final Integer masterKey = 482695;
	
	private Integer gmt = 0;	
	private Integer salt = 0;
	private Integer phoneHash = 0;
	private Integer deviceHash = 0;
	private Integer token = 0;
	
	
	private Integer seed = 0;
	private String passphrase = "";
	private String phoneNumber = "";
	private String deviceId = "";
	
		
	
	/**
	 * Constructor
	 * @param phoneNumber
	 * @param deviceId
	 * @param passphrase
	 * @param seed
	 */
	public TokenFactory(String phoneNumber, String deviceId, String passphrase, Integer seed){
		this.phoneNumber = phoneNumber;
		this.deviceId = deviceId;
		this.passphrase = passphrase;
		this.seed = seed;
	}
	

	
	/**
	 * Creates the token
	 * @return
	 */
	public Integer generateToken(){
		try {
			//Generate GMT
			generateGMT();
			
			//Salt generation
			generateSalt();
			
			//Generate the phone's hash
			generatePhoneHash();
			
			//Generate the device's hash
			generateDeviceHash();
			
			//Build the token
			buildToken();
			
			//Ensure the token is the right size
			ensureTokenIntegrity();
		} catch (Exception e) {
			token = 6714;			
		}
		
		return token;
	}
	
	
	/**
	 * Returns the GMT date time Hash code
	 * @return
	 */
	private void generateGMT(){
		try {
			String gmtTime = "GMT Time";

			final Date currentTime = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm a z");

			 // Give it to me in GMT time.
			 sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			 
			 //Get the date time
			 gmtTime = sdf.format(currentTime);
			 
			 //Set the GMT date time hash code
			 gmt = gmtTime.hashCode();
		} catch (Exception e) {
			gmt = 512434897;
		}
	}

	/**
	 * Generates the salt for encryption
	 * @return
	 */
	private void generateSalt(){				
	    try {
			salt = gmt + passphrase.hashCode() - primaryKey * seed.hashCode();
		} catch (Exception e) {
			salt = -121594;
		}	    	    
	}
	
	/**
	 * Generate the phone hash
	 */
	private void generatePhoneHash(){
		try {
			phoneHash = seed.hashCode() - secondaryKey + salt.hashCode() * phoneNumber.hashCode();
		} catch (Exception e) {
			phoneHash = 1879348;
		}
	}
	
	/**
	 * Generate the device hash
	 */
	private void generateDeviceHash(){
		try {
			deviceHash = tertiaryKey*deviceId.hashCode() + salt.hashCode() + phoneHash.hashCode();
		} catch (Exception e) {
			deviceHash = 1181893;
		}
	}
	
	/**
	 * Build the token
	 */
	private void buildToken(){
		try {
			Integer tokenSeed = deviceHash.hashCode() - masterKey.hashCode() + gmt.hashCode();
			
			//Get the absolute value					
			tokenSeed = Math.abs(tokenSeed.hashCode()) + Math.abs(gmt.hashCode()) + 1000;
			
			String tokenized = String.valueOf(tokenSeed);
			
			//Get the length
			Integer length = tokenized.length();
			
			String tokenString = tokenized.substring(length - 4, length);
			
			//Get the token
			token = Integer.valueOf(tokenString);
		} catch (NumberFormatException e) {
			token = 6714;
		}
	}
	
	/**
	 * To make sure the token is in the right size
	 */
	private void ensureTokenIntegrity(){
		String tokenized = String.valueOf(token);
		
		while (tokenized.length() < 4 ) {
			tokenized = tokenized.concat("7");			
		}
		
		//Set the token value
		token = Integer.valueOf(tokenized);
	}
}

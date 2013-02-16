/**
 * 
 */
package com.mohanaravind.utility;

import java.util.Random;


/**
 * @author Aravind
 *
 * 
 * Use this to authenticate using SMS
 * 
 * Requires SMSProvider and SMSReceiver utility
 */
public class SMSVerifier implements Runnable {

	public enum Status{
		Idle, Started, Verified, Failed, Abandoned 
	}
		
	//The waiting time between successive checks
	private final long mSleepTime = 2000;
	
	//The seed value used to generate the random verification code
	private int mRandomSeed = 12345;
	
	//Waiting time in seconds before declaring it as failed
	private int mWaitingTime = 10;
	
	private int mCount = 0;
	
	//The execution status of the verifier engine
	private Status mStatus = Status.Idle;
	
	//Random generated verification code
	private String mVerificationCode = "";
	
	//SMS Verifier listener
	private static SMSVerifierListener mSMSVerifierListener; 
		
	//The device phone number
	private String mPhoneNumber = "";
	
	private static SMSVerifier mSMSAuthenticator;
	
	/**
	 * Default Constructor
	 */
	private SMSVerifier(){
		
	}

	/**
	 * Returns the instance of SMS Authenticator
	 * @param phoneNumber of the device on which this verification is run
	 * @return
	 */
	public static SMSVerifier getSMSVerifier(SMSVerifierListener smsVerifierListener){
		if(mSMSAuthenticator == null)
			mSMSAuthenticator = new SMSVerifier();
		
		//Set the listener
		mSMSVerifierListener = smsVerifierListener;
		
		return mSMSAuthenticator;
	}
	

	/**
	 * Abandons the verification process
	 */
	public void abandonVerification(){
		this.mStatus = Status.Abandoned;
	}
		
	
	
	@Override
	public void run() {
		//Clear up the data
		clearData();
				
		//Start authentication
		authenticate();		
	}
	
	
	/**
	 * Clears the verifier data
	 */
	private void clearData(){
		this.mStatus = Status.Idle;
		this.mCount = 0;
	}
	
	
	
	/**
	 * Authenticates the user's phone number by sending and receiving the SMS
	 */
	private void authenticate(){
		try {			

			//Set the status as Started
			this.mStatus = Status.Started;
						
			//Create a new random generator instance
			Random randomGenerator = new Random();
			
			//Set the verification code
			mVerificationCode = String.valueOf(randomGenerator.nextInt(mRandomSeed));
													
			//Set the abort broad cast to true to stop the verification code being displayed to user
			SMSReceiver.setAbortBroadcast(true);
			
			//Get the SMS provider
			SMSProvider smsProvider = SMSProvider.getSMSProvider();
									
			//Send the SMS verification code		
			smsProvider.sendSMS(mPhoneNumber, mVerificationCode);
			
			//Wait while there is waiting time
			while(this.mCount < mWaitingTime){			
								
				//Check whether the SMS has been received
				if(SMSReceiver.getStatus() == SMSReceiver.Status.Received){											
					//If the verificatoin code matches
					if(SMSReceiver.getMessage().contentEquals(this.mVerificationCode))
						//Set the success flag
						this.mStatus = Status.Verified;					
					else
						this.mStatus = Status.Failed;
						
					//Clear the data
					SMSReceiver.clearData();					
					
					break;					
				}
				else if(SMSReceiver.getStatus() == SMSReceiver.Status.Failed)
				{
					this.mStatus = Status.Failed;
					break;						
				}
				
				//If verification was abandoned
				if(this.mStatus == Status.Abandoned)
					break;												
			
				//Sleep the thread
				Thread.sleep(mSleepTime);
				
				//Increment the count
				this.mCount++;		
				
				//Trigger the event
				mSMSVerifierListener.awaitingSMS(mCount);
			}
			
			//If still no status
			if(mStatus == Status.Idle)
				this.mStatus = Status.Failed;
					
		} catch (Exception e) {
			//Set the status as failed
			this.mStatus = Status.Failed;			
		} finally{ 
			//Trigger the completed verification event
			mSMSVerifierListener.completedVerification(mStatus);
		}
				
	}

	
	
	/**
	 *             Properties
	 */
	
	/**
	 * Use this method to set the seed value for 
	 * Random generator function which will be used to 
	 * generate the verification code that would be sent
	 * through SMS
	 * Default is 12345
	 * @author Aravind
	 * @param seed
	 */
	public void setSeed(int seed){
		this.mRandomSeed = seed;
	}
	

	/**
	 * Set the waiting time for the verifier for SMS 
	 * before declaring it as failed
	 * Default is 20 seconds
	 * @param time
	 */
	public void setWaitingTime(int time){
		this.mRandomSeed = time;
	}
	
	
	/**
	 * Returns the execution status of the verifier engine
	 * @return
	 */
	public Status getStatus(){
		return mStatus;
	}
	
	
	/**
	 * Sets the device's phone number
	 */
	public void setPhoneNumber(String phoneNumber){
		this.mPhoneNumber = phoneNumber;
	}
	
			
	
	/**
	 * Implement this to listen to SMS Verifier events
	 * @author Aravind
	 *
	 */
	public interface SMSVerifierListener
	{
		/**
		 * Gets triggered after a waiting time gets elapsed
		 * @param elapsedTime
		 */
		public void awaitingSMS(int elapsedTime);
		
		/**
		 * Gets triggered once the verification process has been done
		 */
		public void completedVerification(SMSVerifier.Status status );
		
		
	}

	
		
}






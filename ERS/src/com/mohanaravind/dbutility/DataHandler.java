/**
 * 
 */
package com.mohanaravind.dbutility;

import java.util.List;

import com.mohanaravind.entity.History;

import android.content.Context;

/**
 * @author Aravind
 *
 */
public class DataHandler {
	
	Context _objContext = null;
	
	public DataHandler(Context objContext){
		this._objContext = objContext;
	}
	
	
	/**
	 * @category Sets the History data which is being sent by the user
	 * @param objHistoryData
	 * @return
	 */
	public Boolean setHistoryData(History objHistoryData){
		//Declarations
		DBAdapter objDBAdapter = null;
		Boolean blnSuccessFlag = false;
		
		try {
			//Initialize
			objDBAdapter = new DBAdapter(_objContext);
			
			objDBAdapter.open();
			
			if(objDBAdapter.insertEntry(objHistoryData) > 0)
				blnSuccessFlag = true;
			
		} catch (Exception e) {
			//In case of any exception
			blnSuccessFlag = false;
		}
		finally{
			if(objDBAdapter != null)
				objDBAdapter.close();
		}
		
		//Return the flag
		return blnSuccessFlag;
	}
	
	/**
	 * @category Returns the Historys which is required to be displayed
	 * @param intHistorysRequired
	 * @return
	 */
	public List<History> getHistorys(Integer intHistorysRequired){
		//Declarations
		List<History> lstHistoryData = null;
		DBAdapter objDBAdapter = null;
		
		try {
			//Initialize
			objDBAdapter = new DBAdapter(_objContext);
			
			//Open the connection
			objDBAdapter.open();
			
			//Get the Historyss
			lstHistoryData = objDBAdapter.getHistorys(intHistorysRequired);			
			
		} catch (Exception e) {
			lstHistoryData = null;
		}finally{
			if(objDBAdapter != null)
				objDBAdapter.close();
		}
		
		return lstHistoryData;
	}

	
}

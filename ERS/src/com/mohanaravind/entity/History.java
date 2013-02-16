/**
 * 
 */
package com.mohanaravind.entity;

/**
 * @author Aravind
 *
 */
public class History  implements Comparable<History>{
	private String _strHistory;
	private String _strInformationType;
	private Integer _intID;

	/**
	 * @category Clones the History data object
	 * @param objHistoryData
	 * @return
	 */
	public History Clone(History objHistoryData){
		//Declarations
		History objClone;
		
		objClone = new History();
		objClone.setID(objHistoryData.getID());
		objClone.setInformationType(objHistoryData.getInformationType());
		objClone.setHistory(objHistoryData.getHistory());
		
		return objHistoryData;
		
	}
	
	
	/**
	 * @return the strHistory
	 */
	public String getHistory() {
		return _strHistory;
	}
	/**
	 * @param strHistory the strHistory to set
	 */
	public void setHistory(String strHistory) {
		this._strHistory = strHistory;
	}
	/**
	 * @return the strInformationType
	 */
	public String getInformationType() {
		return _strInformationType;
	}
	/**
	 * @param strInformationType the strInformationType to set
	 */
	public void setInformationType(String strInformationType) {
		this._strInformationType = strInformationType;
	}
	/**
	 * @return the _intID
	 */
	public Integer getID() {
		return _intID;
	}
	/**
	 * @param _intID the _intID to set
	 */
	public void setID(Integer intID) {
		this._intID = intID;
	}
	@Override
	public int compareTo(History another) {
		//Declarations
		int intReturn = 0;
		
		if(this._intID > another.getID()){
			intReturn = -1;
		}else if(this._intID < another.getID()){
			intReturn = 1;
		}
			
		
		return intReturn;
	}
}

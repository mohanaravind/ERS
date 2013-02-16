/**
 * 
 */
package com.mohanaravind.dbutility;

import java.util.ArrayList;
import java.util.List;

import com.mohanaravind.entity.History;

import android.content.ContentValues;
import android.content.Context;

import android.database.*;

import android.database.sqlite.*;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

import android.util.Log;

class DBAdapter {

	private static final String DATABASE_NAME = "ECS.db";

	private static final String DATABASE_TABLE = "messages";

	private static final int DATABASE_VERSION = 1;

	// The index (key) column name for use in where clauses.

	public static final String KEY_ID="_id";

	// The name and column index of each column in your database.

	public static final String KEY_HISTORY="history";
	public static final int HISTORY_COLUMN = 1;

	public static final String KEY_INFORMATIONTYPE="InformationType";
	public static final int INFORMATIONTYPE_COLUMN = 2;

	// SQL Statement to create a new database.

	private static final String DATABASE_CREATE = "create table " +

			DATABASE_TABLE + " (" + KEY_ID +
			
			" integer primary key autoincrement, " +
			
			KEY_HISTORY + " text not null, " + KEY_INFORMATIONTYPE + " text not null" + ");";

	// Variable to hold the database instance

	private SQLiteDatabase db;

	// Context of the application using the database.

	private final Context context;

	// Database open/upgrade helper

	private myDbHelper dbHelper;

	public DBAdapter(Context _context) {

		context = _context;

		dbHelper = new myDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public DBAdapter open() throws SQLException {

		db = dbHelper.getWritableDatabase();

		return this;

	}

	public void close() {

		db.close();

	}

	/***
	 * @category Inserts a row of data
	 * @param History
	 * @return 
	 */
	public long insertEntry(History objHistory) {

		ContentValues objContentValues = new ContentValues();

		//Fill in ContentValues to represent the new row

		//Set the message details
		objContentValues.put(KEY_HISTORY, objHistory.getHistory());
		objContentValues.put(KEY_INFORMATIONTYPE, objHistory.getInformationType());

				
		return db.insert(DATABASE_TABLE, null, objContentValues);

	}

	/**
	 * @category Removes an entry from the database
	 * @param _rowIndex
	 * @return
	 */
	public boolean removeEntry(long _rowIndex) {

		return db.delete(DATABASE_TABLE, KEY_ID +

				"=" + _rowIndex, null) > 0;

	}


	public Cursor getAllEntries () {

		return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_HISTORY, KEY_INFORMATIONTYPE},

				null, null, null, null, null);

	}


	/**
	 * @category Gets the list of history
	 * @param intMessageLimit
	 * @return
	 */
	public List<History> getHistorys(int intMessageLimit) {
		//Declarations
		Cursor objCursor;
		List<History> lstHistory = new ArrayList<History>();
		History objHistory;
		Integer intIndex = 0;

		//Initialize
		objCursor = getAllEntries();
		objHistory = new History();


		//Get the last message
		if (objCursor.moveToLast()) {
			do {
				//Increment the index 
				intIndex++;
				
				//Create a new message data instance
				objHistory = new History();
				
				objHistory.setID(intIndex);
				objHistory.setHistory(objCursor.getString(HISTORY_COLUMN));
				objHistory.setInformationType(objCursor.getString(INFORMATIONTYPE_COLUMN));
				
				//Add the data to the list
				lstHistory.add(objHistory);
				
			} while(objCursor.moveToPrevious() && intIndex < intMessageLimit);

		}

		return lstHistory;
	}

	public int updateEntry(long _rowIndex, History _myObject) {

		String where = KEY_ID + "=" + _rowIndex;

		ContentValues contentValues = new ContentValues();

		// TODO fill in the ContentValue based on the new object

		return db.update(DATABASE_TABLE, contentValues, where, null);

	}

	private static class myDbHelper extends SQLiteOpenHelper {


		// Called when no database exists in

		// disk and the helper class needs

		// to create a new one.

		public myDbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);			
		}

		@Override

		public void onCreate(SQLiteDatabase _db) {

			_db.execSQL(DATABASE_CREATE);

		}

		// Called when there is a database version mismatch meaning that

		// the version of the database on disk needs to be upgraded to

		// the current version.

		@Override

		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,

				int _newVersion) {

			// Log the version upgrade.

			Log.w("TaskDBAdapter", "Upgrading from version " +

					_oldVersion + " to " +

					_newVersion +

					", which will destroy all old data");

			// Upgrade the existing database to conform to the new version.

			// Multiple previous versions can be handled by comparing

			// _oldVersion and _newVersion values.

			// The simplest case is to drop the old table and create a

			// new one.

			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

			// Create a new one.

			onCreate(_db);

		}

	}

}
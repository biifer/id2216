package app.example.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_UPLOADER = "uploader";
	public static final String KEY_ROUTE_NAME = "routename";
	public static final String KEY_DISTANCE = "distance";
	public static final String KEY_RADIUS = "radius";
	public static final String KEY_FLAGS = "flags";
	public static final String KEY_TOTAL_TIME = "totaltime";
	public static final String KEY_AVERAGE_SPEED = "averagespeed";
	private static final String TAG = "DataBaseAdapter";

	private static final String DATABASE_NAME = "runners";
	private static final String DATABASE_TABLE = "routes";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE =
	"create table routes (_id integer primary key autoincrement, "
	+ "uploader text not null, routename text not null, "
	+ "distance text not null, radius text not null, "
	+ "flags text not null, totaltime text not null, "
	+ "averagespeed text not null);";

	private final Context context;

	private DatabaseHelper dataBaseHelper;
	private SQLiteDatabase db;

	public DataBaseAdapter(Context ctx)
	{
	this.context = ctx;
	dataBaseHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper
	{
	DatabaseHelper(Context context)
	{
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
	db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
	int newVersion)
	{
	Log.w(TAG, "Upgrading database from version " + oldVersion
	+ " to "
	+ newVersion + ", which will destroy all old data");
	db.execSQL("DROP TABLE IF EXISTS titles");
	onCreate(db);
	}
	}

	//öppnar databasen
	public DataBaseAdapter open() throws SQLException
	{
	db = dataBaseHelper.getWritableDatabase();
	return this;
	}

	//Stänger databasen
	public void close()
	{
	dataBaseHelper.close();
	}

	//Lägger till en route i databasen
	public long insertRoute(String uploader, String routeName, String distance, String radius, String flags, String totalTime, String averageSpeed)
	{
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_UPLOADER, uploader);
	initialValues.put(KEY_ROUTE_NAME, routeName);
	initialValues.put(KEY_DISTANCE, distance);
	initialValues.put(KEY_RADIUS, radius);
	initialValues.put(KEY_FLAGS, flags);
	initialValues.put(KEY_TOTAL_TIME, totalTime);
	initialValues.put(KEY_AVERAGE_SPEED, averageSpeed);
	return db.insert(DATABASE_TABLE, null, initialValues);
	}

	//Tar bort en viss route
	public boolean deleteRoute(long rowId)
	{
	return db.delete(DATABASE_TABLE, KEY_ROWID +
	"=" + rowId, null) > 0;
	}

	//Hämtar alla routes
	public Cursor getAllRoutes()
	{
	return db.query(DATABASE_TABLE, new String[] {
	KEY_ROWID,
	KEY_UPLOADER,
	KEY_ROUTE_NAME,
	KEY_DISTANCE,
	KEY_RADIUS,
	KEY_FLAGS,
	KEY_TOTAL_TIME,
	KEY_AVERAGE_SPEED},
	null,null,null,null,null);
	}

	//Hämtar en viss route
	public Cursor getRoute(long rowId) throws SQLException
	{
	Cursor mCursor =
	db.query(true, DATABASE_TABLE, new String[] {
	KEY_ROWID,
	KEY_UPLOADER,
	KEY_ROUTE_NAME,
	KEY_DISTANCE,
	KEY_RADIUS,
	KEY_FLAGS,
	KEY_TOTAL_TIME,
	KEY_AVERAGE_SPEED
	},
	KEY_ROWID + "=" + rowId,
	null,null,null,null,null);
	if (mCursor != null) {
	mCursor.moveToFirst();
	}
	return mCursor;
	}

	//Uppdaterar en route
	public boolean updateRoute(long rowId, String uploader, String routeName, String distance, String radius, String flags, String totalTime, String averageSpeed)
	{
	ContentValues args = new ContentValues();
	args.put(KEY_UPLOADER, uploader);
	args.put(KEY_ROUTE_NAME, routeName);
	args.put(KEY_DISTANCE, distance);
	args.put(KEY_RADIUS, radius);
	args.put(KEY_FLAGS, flags);
	args.put(KEY_TOTAL_TIME, totalTime);
	args.put(KEY_AVERAGE_SPEED, averageSpeed);
	return db.update(DATABASE_TABLE, args,
	KEY_ROWID + "=" + rowId, null) > 0;
	}

	
}

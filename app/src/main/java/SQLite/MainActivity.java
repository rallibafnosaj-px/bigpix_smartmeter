package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends SQLiteOpenHelper {

    private static final String databaseName = "BigpixSmartmeter";
    public static final String USERS_TBL = "users_tbl";
    private static final String LOCATION_TBL = "location_tbl" ;
    private static final String IP_TBL = "ip_tbl";


    Cursor cursor;
    SQLiteDatabase db;
    String query;

    @Override
    public void onCreate(SQLiteDatabase db) {

        AddTables(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public MainActivity(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    public void AddTables(SQLiteDatabase db)
    {
        String createTableLogin = "CREATE TABLE IF NOT EXISTS " + USERS_TBL + " (ID integer primary key autoincrement, users_username varchar(250), users_password varchar(250)) ";
        String createTableLocation = "CREATE TABLE IF NOT EXISTS " + LOCATION_TBL + "(ID integer primary key autoincrement, location_username varchar(250), location_date varchar(250), location_address varchar(250), location_longtitude varchar(250), location_latitude varchar(250)) ";
        String createTableIP = "CREATE TABLE IF NOT EXISTS " + IP_TBL + " (ip_ipaddress varchar(250)) ";


        db.execSQL(createTableLogin);
        db.execSQL(createTableLocation);
        db.execSQL(createTableIP);
    }
    public String RetrieveIPAddress()
    {
        String ipAddress = "";
        query = "Select ip_ipaddress from ip_tbl";

        db = getWritableDatabase();

        cursor = db.rawQuery(query, null);

        if(cursor.getCount() > 0)
        {
            if(cursor.moveToFirst())
            {
                ipAddress = cursor.getString(0);
            }
        }

        cursor.close();
        return ipAddress;

    }
    public void InsertIPAddress(String ipAddress)
    {
        ContentValues cv = new ContentValues();
        cv.put("ip_ipaddress", ipAddress + ":8080");

        db = getWritableDatabase();

        db.insert(IP_TBL,null,cv);


    }
    public void UpdateIPAddress(String ipAddress)
    {
        query = "Update " + IP_TBL + " set ip_ipaddress = " + "'" + ipAddress + "'";

        db = getWritableDatabase();

        db.execSQL(query);


    }


}

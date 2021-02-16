package SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends SQLiteOpenHelper {

    private static final String databaseName = "BigpixSmartmeter";
    public static final String USERS_TBL = "users_tbl";
    private static final String LOCATION_TBL = "location_tbl" ;
    private static final String IP_TBL = "ip_tbl";

    Context context;

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
        String createTableLogin = "CREATE TABLE IF NOT EXISTS " + USERS_TBL + "l (ID integer primary key autoincrement, users_username varchar(250), users_password varchar(250)) ";
        String createTableLocation = "CREATE TABLE IF NOT EXISTS " + LOCATION_TBL + "(ID integer primary key autoincrement, location_username varchar(250), location_date varchar(250), location_address varchar(250), location_longtitude varchar(250), location_latitude varchar(250)) ";
        String createTableIP = "CREATE TABLE IF NOT EXISTS " + IP_TBL + " (ip_ipaddress varchar(250)) ";


        db.execSQL(createTableLogin);
        db.execSQL(createTableLocation);
        db.execSQL(createTableIP);
    }


}

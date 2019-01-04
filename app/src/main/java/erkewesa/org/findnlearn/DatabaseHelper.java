package erkewesa.org.findnlearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fal.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "fal_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STUGA = "stuga";

    public static final String SQL_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text not null, " +
                    COLUMN_STUGA + " text)";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String name, String stuga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_STUGA, stuga);
        long result = db.insert(DB_NAME, null, contentValues);
        if (result == 1){
            return true;
        } else if (result == -1){
            return false;
        }
        return false;
    }


}

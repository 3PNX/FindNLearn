package erkewesa.org.findnlearn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fal.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_FAL = "fal";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STUGA = "stuga";

    public static final String SQL_CREATE =
            "create table " + TABLE_FAL + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text not null, " +
                    COLUMN_STUGA + " text);";

    public static final String SQL_INSERT =
            "insert into " + TABLE_FAL + "(" +
                    COLUMN_NAME  + ", " +
                    COLUMN_STUGA + ");";

    public static final String SQL_SELECT_CHECK =
            "select id from " + TABLE_FAL + " where " +
                    COLUMN_NAME  + " = " +
                    COLUMN_STUGA + ");";

    public Databasehelper(Context context) {
        super(context, COLUMN_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);

    }

    public void onInsert (SQLiteDatabase db) {
        db.execSQL(SQL_INSERT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

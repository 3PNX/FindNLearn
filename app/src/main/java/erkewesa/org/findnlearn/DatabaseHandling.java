package erkewesa.org.findnlearn;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseHandling {

    public static final String DB_NAME = "fal.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_FAL = "fal";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STUGA = "stuga";


    public static final String SQL_INSERT =
            "insert into " + TABLE_FAL + "(" +
                    COLUMN_NAME  + ", " +
                    COLUMN_STUGA + ") values (context);";

    public static final String SQL_SELECT_CHECK =
            "select id from " + TABLE_FAL + " where " +
                    COLUMN_NAME  + " = " +
                    COLUMN_STUGA + ");";


    public void onInsert (SQLiteDatabase db, String context) {
        db.execSQL(SQL_INSERT);
    }

}

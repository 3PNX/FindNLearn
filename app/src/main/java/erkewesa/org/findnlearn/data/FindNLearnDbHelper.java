package erkewesa.org.findnlearn.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class FindNLearnDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="findnlearn.db";

    private static final String TEXT_TYPE=" TEXT";
    private static final String NUMVER_TYPE="INTEGER";
    private static final String COMMA_SEP=",";
    private static final String SQL_CREATE_STUDIENGANG_TABLE_ENTRIES="CREATE TABLE " +StudiengangContract.StudiengangEntry.TABLE_NAME + " (" +
            StudiengangContract.StudiengangEntry._ID + " INTEGER PRIMARY KEY," +
            StudiengangContract.StudiengangEntry.COLUMN_STUDIENGANG + TEXT_TYPE + COMMA_SEP +
            StudiengangContract.StudiengangEntry.COLUMN_SEMESTER + NUMVER_TYPE  +
            " )";

    private final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ StudiengangContract.StudiengangEntry.TABLE_NAME;



    public FindNLearnDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STUDIENGANG_TABLE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        // Create tables again
        onCreate(db);
    }


}

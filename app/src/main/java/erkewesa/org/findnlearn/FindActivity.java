package erkewesa.org.findnlearn;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;

import erkewesa.org.findnlearn.data.FindNLearnDbHelper;
import erkewesa.org.findnlearn.data.StudiengangContract;

public class FindActivity extends AppCompatActivity{

    Spinner studgSp;
    Button btnGo;
    private FindNLearnDbHelper myDbHelper;
    private ArrayList<String> studiengaenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        btnGo=findViewById(R.id.searchBtn);
        studgSp=findViewById(R.id.studgSp);
        myDbHelper  = new FindNLearnDbHelper(this);
        studiengaenge =new ArrayList<>();
        studiengaenge =getStudiengangListe();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,studiengaenge);
        studgSp.setAdapter(adapter);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudiengang();
                displayDatabaseInfo();

            }
        });




        displayDatabaseInfo();

    }

    private ArrayList<String> getStudiengangListe(){
        SQLiteDatabase db=myDbHelper.getReadableDatabase();
        ArrayList<String> result= new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT "+StudiengangContract.StudiengangEntry.COLUMN_STUDIENGANG +
                " FROM "+StudiengangContract.StudiengangEntry.TABLE_NAME,null);
        try{
            while(cursor.moveToNext()){
                result.add(cursor.getString(0));
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    private void deleteTable(){
        SQLiteDatabase db=myDbHelper.getWritableDatabase();

        db.execSQL("Delete from "+StudiengangContract.StudiengangEntry.TABLE_NAME);
    }

    private void displayDatabaseInfo(){



        SQLiteDatabase db=myDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ StudiengangContract.StudiengangEntry.TABLE_NAME,null);
        try{
            TextView displayView=(TextView) findViewById(R.id.dbTextView);
            displayView.setText("Anzahl der Reihen in der Datenbank: " +cursor.getCount());
        } finally {
            cursor.close();
        }
    }

    private void insertStudiengang(){
        SQLiteDatabase db= myDbHelper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StudiengangContract.StudiengangEntry.COLUMN_SEMESTER,2);
        values.put(StudiengangContract.StudiengangEntry.COLUMN_STUDIENGANG,"Recht");
        long newRowId=db.insert(StudiengangContract.StudiengangEntry.TABLE_NAME,null,values);

        ContentValues values2=new ContentValues();
        values2.put(StudiengangContract.StudiengangEntry.COLUMN_SEMESTER,1);
        values2.put(StudiengangContract.StudiengangEntry.COLUMN_STUDIENGANG,"Mathematik 1");
        long RowId=db.insert(StudiengangContract.StudiengangEntry.TABLE_NAME,null,values2);

        ContentValues values3=new ContentValues();
        values2.put(StudiengangContract.StudiengangEntry.COLUMN_SEMESTER,1);
        values2.put(StudiengangContract.StudiengangEntry.COLUMN_STUDIENGANG,"Mobile Anwendungen");
        long nRowId=db.insert(StudiengangContract.StudiengangEntry.TABLE_NAME,null,values3);
    }



}

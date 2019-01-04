package erkewesa.org.findnlearn;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.service.autofill.FillEventHistory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import erkewesa.org.findnlearn.data.FindNLearnDbHelper;
import erkewesa.org.findnlearn.data.StudiengangContract;

public class MainActivity extends AppCompatActivity {

   private FindNLearnDbHelper myDbHelper;
   private SQLiteDatabase db;
   private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDbHelper=new FindNLearnDbHelper(this);
        db=myDbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT "+StudiengangContract.StudiengangEntry.COLUMN_USERNAME+" FROM "+StudiengangContract.StudiengangEntry.TABLE_NAME,null);
        try{
            while(cursor.moveToNext()){
                user=cursor.getString(0);
            }
        }catch (Exception e){
            user=null;
        } finally {
            cursor.close();
        }

        if(user==null){
            Intent createUserIntent=new Intent(getApplicationContext(),CreateUserActivity.class);
            startActivity(createUserIntent);
        }

        setContentView(R.layout.activity_main);

        //Buttons / ClickListener
        Button findBtn=findViewById(R.id.findBtn);
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findActivityIntent=new Intent(getApplicationContext(),FindActivity.class);
                startActivity(findActivityIntent);
            }
        });

        Button calendarBtn=findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarActivityIntent=new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(calendarActivityIntent);
            }
        });

        Button viewGroupBtn=findViewById(R.id.viewGroupBtn);
        viewGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewGroupActivityIntent=new Intent(getApplicationContext(),ViewGroupActivity.class);
                startActivity(viewGroupActivityIntent);
            }
        });

        Button profileBtn=findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActivityIntent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(profileActivityIntent);
            }
        });

        Button createBtn=findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createActivityIntent=new Intent(getApplicationContext(),CreateActivity.class);
                startActivity(createActivityIntent);
            }
        });



    }
}

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
   private String rndmKey;
   private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDbHelper=new FindNLearnDbHelper(this);
        db=myDbHelper.getReadableDatabase();



        Cursor cursor = db.rawQuery("SELECT "+StudiengangContract.StudiengangEntry.COLUMN_USERNAME+","+StudiengangContract.StudiengangEntry.COLUMN_RANDOMKEY+" FROM "+StudiengangContract.StudiengangEntry.TABLE_NAME,null);
        try{
            while(cursor.moveToNext()){
                user=cursor.getString(0);
                rndmKey=cursor.getString(1);
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

        tvWelcome=findViewById(R.id.tvWelcomeMessage);
        tvWelcome.setText("Herzlich Willkommen "+user+"! ");

        //Buttons / ClickListener
        Button findBtn=findViewById(R.id.findBtn);
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findActivityIntent=new Intent(getApplicationContext(),FindActivity.class);
                findActivityIntent.putExtra("RandomKey",rndmKey);
                findActivityIntent.putExtra("username",user);
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
                viewGroupActivityIntent.putExtra("RandomKey",rndmKey);
                viewGroupActivityIntent.putExtra("username",user);
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
                createActivityIntent.putExtra("RandomKey",rndmKey);
                createActivityIntent.putExtra("username",user);
                startActivity(createActivityIntent);
            }
        });



    }




}

package erkewesa.org.findnlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button buttonTest;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTest=findViewById(R.id.buttonTest);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Modul_01").child("Semester").setValue(1);
            }
        });

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

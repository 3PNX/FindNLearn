package erkewesa.org.findnlearn;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import erkewesa.org.findnlearn.data.FindNLearnDbHelper;
import erkewesa.org.findnlearn.data.StudiengangContract;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView cv;
    private FindNLearnDbHelper myDbHelper;
    private SQLiteDatabase db;
    private String rndmKey;
    private DatabaseReference mDatabase;
    private ArrayList<String> mMeets=new ArrayList();
    private ArrayList<String> mDates=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        rndmKey=getIntent().getStringExtra("RandomKey");
        cv = findViewById(R.id.calendarView);


        mDatabase=FirebaseDatabase.getInstance().getReference().child("Kursteilnehmer");
        mDatabase.orderByChild("Teilnehmer").equalTo(rndmKey).addListenerForSingleValueEvent(new ValueEventListener() { //In Kursteinehmer nach gleichen Knoten suchen

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){
                        mMeets.add(adSnapshot.child("Meet").getValue(String.class)); // für jeden Treffer die MeetKeys in mMeets schreiben
                    }

                }else {
                    return;
                }

                if(mMeets.size()!=0) {
                    for (String k: mMeets){ // Über mMeets iterieren
                        mDatabase.orderByChild("meet").equalTo(k).addListenerForSingleValueEvent(new ValueEventListener() { // in meets nach gleichen meetKeys (DB und mMeets) suchen

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()!=null) {
                                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){ // für jeden Treffer den DatumWert in mDates schreiben
                                        mDates.add(adSnapshot.child("datum").getValue(String.class));
                                    }

                                }else {
                                    return;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }





        });

        for (String d: mDates){ // über die mDates iterieren und jeden Datumstring nach der Konvertierung nach Long in den Kalenderview schreiben
            String d_parts[] = d.split("/");
            int day = Integer.parseInt(d_parts[0]);
            int month = Integer.parseInt(d_parts[1]);
            int year = Integer.parseInt(d_parts[2]);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            long millitime = cal.getTimeInMillis();
            cv.setDate(millitime, true, true);

        }


    }
}

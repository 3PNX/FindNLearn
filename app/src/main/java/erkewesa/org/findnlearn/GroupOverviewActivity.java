package erkewesa.org.findnlearn;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import erkewesa.org.findnlearn.views.BoxInhalt;
import erkewesa.org.findnlearn.views.testView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GroupOverviewActivity extends AppCompatActivity {
    private String meetKey,modul,datum;
    private TextView tvTeilnehmer;
    private Long teilnehmer;
    private Meetings meeting;
    private ArrayList<String> arrDaten=new ArrayList<>();
    private ArrayList<String> arrVonZeit=new ArrayList<>();
    private ArrayList<String> arrBisZeit=new ArrayList<>();

    private TextView tvModul;
    private TextView tvTeilnehmerzahl;

    private ArrayList<Date> arrDates=new ArrayList<>();
    private ArrayList<BoxInhalt> arrBox=new ArrayList<>();
    ArrayList<testView> arrTV=new ArrayList<>();

    final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");





    private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);
        tvModul=findViewById(R.id.tvModul);
        tvTeilnehmerzahl=findViewById(R.id.tvTeilnehmerZahl);



        meetKey=getIntent().getStringExtra("meetKey");

        final LinearLayout scrollLin=findViewById(R.id.scrollLin);

        DatabaseReference mMeetingDb=mDatabase.child("meet").child(meetKey);

        //Get Modul und TeilnehmerZahl des Meetings
        mMeetingDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meeting=dataSnapshot.getValue(Meetings.class);
                modul=meeting.getModul();
                teilnehmer=meeting.getTeilnehmer();
                tvModul.setText(modul);
                tvTeilnehmerzahl.setText(""+teilnehmer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //GET Termindaten
        DatabaseReference mTerminDb=mDatabase.child("Termine");
        mTerminDb.orderByChild("Meet").equalTo(meetKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){
                        arrDaten.add(adSnapshot.child("Datum").getValue(String.class));
                    }

                    for(int i=0;i<arrDaten.size();i++){

                        try {
                            Date strDate = sdf.parse(arrDaten.get(i));

                            arrDates.add(strDate);
                        }catch(ParseException e){

                        }
                    }

                    Collections.sort(arrDates);
                    for(int i=0;i<arrDates.size();i++){
                        String temp=sdf.format(arrDates.get(i));
                        arrDaten.set(i,temp);
                    }

                    for(int i=0;i<arrDaten.size();i++){
                        arrBox.add(new BoxInhalt());
                        arrBox.get(i).setDatum(arrDaten.get(i));

                        arrTV.add(new testView(getApplicationContext()));
                        arrTV.get(i).setLayoutParams(new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.MATCH_PARENT,
                                ConstraintLayout.LayoutParams.MATCH_PARENT));

                        arrTV.get(i).setInhalt(arrBox.get(i));

                        scrollLin.addView(arrTV.get(i));




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}



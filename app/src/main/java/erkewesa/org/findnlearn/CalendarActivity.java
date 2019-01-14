package erkewesa.org.findnlearn;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import erkewesa.org.findnlearn.data.FindNLearnDbHelper;
import erkewesa.org.findnlearn.views.BoxInhalt;
import erkewesa.org.findnlearn.views.terminBoxView;

public class CalendarActivity extends AppCompatActivity {
    private String userKey;
    private ArrayList<String> mTerminKeys=new ArrayList();
    private ArrayList<Termine> arrTermine=new ArrayList();
    private ArrayList<String> arrModulKeys=new ArrayList<>();
    private ArrayList<String> arrMeetingsTxt=new ArrayList<>();
    private ArrayList<BoxInhalt> arrBox=new ArrayList<>();
    private ArrayList<terminBoxView> arrTV=new ArrayList<>();
    private TextView tvAnzZusagen;
    private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
    private int anzZusagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        tvAnzZusagen=findViewById(R.id.tvTeilnehmerZahl);

        userKey=getIntent().getStringExtra("user");

        final LinearLayout scrollLin=findViewById(R.id.calendarscrollLin);
        final DatabaseReference mTermineDb=mDatabase.child("Termine");
        final DatabaseReference mModulDb=mDatabase.child("meet");
        final DatabaseReference mZusagenDb=mDatabase.child("Zusagen");

        mZusagenDb.orderByChild("User").equalTo(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot adSnap:dataSnapshot.getChildren()){
                        mTerminKeys.add(adSnap.child("Termin").getValue(String.class));
                        anzZusagen=mTerminKeys.size();
                        tvAnzZusagen.setText(""+anzZusagen);
                    }
                }

                if(mTerminKeys.size()!=0) {
                    for (int i = 0; i < mTerminKeys.size(); i++) {
                        String terminKey = mTerminKeys.get(i);
                        final int finalI = i;
                        mTermineDb.child(terminKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                arrTermine.add(dataSnapshot.getValue(Termine.class));
                                arrModulKeys.add(dataSnapshot.child("Meet").getValue(String.class));
                                arrTermine.get(finalI).setDate();
                                Collections.sort(arrTermine);

                                if (arrModulKeys.size() != 0) {

                                        String meetKey = arrModulKeys.get(finalI);
                                        DatabaseReference ref = mModulDb.child(meetKey);

                                        ref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                arrMeetingsTxt.add(dataSnapshot.child("modul").getValue(String.class));

                                                if (arrMeetingsTxt.size() != 0) {

                                                        arrBox.add(new BoxInhalt());
                                                        arrBox.get(finalI).setDatum(arrTermine.get(finalI).getDatum());
                                                        arrBox.get(finalI).setBeschreibung(arrTermine.get(finalI).getBeschreibung());
                                                        arrBox.get(finalI).setZeitVon(arrTermine.get(finalI).getZeit_von());
                                                        arrBox.get(finalI).setZeitBis(arrTermine.get(finalI).getZeit_bis());
                                                        arrBox.get(finalI).setModul(arrMeetingsTxt.get(finalI));

                                                        arrTV.add(new terminBoxView(getApplicationContext()));
                                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT);
                                                        params.gravity = Gravity.CENTER;
                                                        arrTV.get(finalI).setLayoutParams(params);

                                                        View.OnClickListener onClick = new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                int id = v.getId();
                                                                if (id == R.id.tvClick) {
                                                                    Intent groupOverViewIntent = new Intent(getApplicationContext(), GroupOverviewActivity.class);
                                                                    groupOverViewIntent.putExtra("meetKey", arrModulKeys.get(finalI));
                                                                    groupOverViewIntent.putExtra("userKey", userKey);
                                                                    startActivity(groupOverViewIntent);
                                                                }
                                                            }
                                                        };
                                                        arrTV.get(finalI).setInhalt(arrBox.get(finalI), onClick);


                                                        scrollLin.addView(arrTV.get(finalI));



                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });






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



    }

}


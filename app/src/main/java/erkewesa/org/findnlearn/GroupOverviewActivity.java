package erkewesa.org.findnlearn;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import erkewesa.org.findnlearn.views.BoxInhalt;
import erkewesa.org.findnlearn.views.testView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GroupOverviewActivity extends AppCompatActivity {
    private String meetKey,modul,datum,userKey;
    private TextView tvTeilnehmer;
    private Long teilnehmer;
    private Meetings meeting;
    private ArrayList<String> arrDaten=new ArrayList<>();
    private ArrayList<String> arrVonZeit=new ArrayList<>();
    private ArrayList<String> arrBisZeit=new ArrayList<>();
    private ArrayList<String> arrBeschreibung=new ArrayList<>();

    private ArrayList<Termine> arrTermine=new ArrayList<>();

    private ArrayList<String> arrTerminKeys=new ArrayList<>();

    private TextView tvModul;
    private TextView tvTeilnehmerzahl;
    private Button btnAdd;

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
        btnAdd=findViewById(R.id.btnAdd);





        meetKey=getIntent().getStringExtra("meetKey");
        userKey=getIntent().getStringExtra("userKey");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreateTerminIntent=new Intent(getApplicationContext(),CreateTerminActivity.class);
                CreateTerminIntent.putExtra("meetKey",meetKey);
                startActivity(CreateTerminIntent);
            }
        });

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
                int j=0;
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){
                        arrTerminKeys.add(adSnapshot.getKey());
                        arrTermine.add(adSnapshot.getValue(Termine.class));
                        arrTermine.get(j).setDate();
                        j++;


                    }

                    Collections.sort(arrTermine);

                    for(int i=0;i<arrTermine.size();i++){
                        final int k=i;
                        arrBox.add(new BoxInhalt());
                        arrBox.get(i).setDatum(arrTermine.get(i).getDatum());
                        arrBox.get(i).setBeschreibung(arrTermine.get(i).getBeschreibung());
                        arrBox.get(i).setZeitVon(arrTermine.get(i).getZeit_von());
                        arrBox.get(i).setZeitBis(arrTermine.get(i).getZeit_bis());

                        arrTV.add(new testView(getApplicationContext()));
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.gravity=Gravity.CENTER;
                        arrTV.get(i).setLayoutParams(params);

                        View.OnClickListener onClick=new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id=v.getId();
                                if(id==R.id.tvClick) {
                                    showPopUp(arrTerminKeys.get(k), arrTermine.get(k).getBeschreibung());
                                }
                            }
                        };
                        arrTV.get(i).setInhalt(arrBox.get(i),arrTerminKeys.get(i),userKey,onClick);


                        scrollLin.addView(arrTV.get(i));




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }




    public void showPopUp(String termin, String beschreibung){

        final Dialog myDialog=new Dialog(this);
        myDialog.setContentView(R.layout.termin_popup);

        DatabaseReference mDbZusagen=FirebaseDatabase.getInstance().getReference().child("Zusagen");
        DatabaseReference mDbAbsagen=FirebaseDatabase.getInstance().getReference().child("Absagen");

        TextView tvBeschreibung=myDialog.findViewById(R.id.tvPopUpBeschreibung);
        final TextView tvZusagenZahl=myDialog.findViewById(R.id.tvPopUpZusagenZahl);
        final TextView tvAbsagenZahl=myDialog.findViewById(R.id.tvPopUpAbsagenZahl);
        final TextView txtClose=myDialog.findViewById(R.id.txtTerminClose);
        final ArrayList<String> arrZusagen=new ArrayList<>();
        final ArrayList<String> arrAbsagen=new ArrayList<>();



        tvBeschreibung.setText(beschreibung); //boxinhalt beschreibung

        mDbZusagen.orderByChild("Termin").equalTo(termin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot adSnap:dataSnapshot.getChildren()){
                        arrZusagen.add(adSnap.child("Termin").getValue(String.class));
                    }

                    int anzZusagen=arrZusagen.size();
                    tvZusagenZahl.setText(""+anzZusagen);
                } else{
                    tvZusagenZahl.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDbAbsagen.orderByChild("Termin").equalTo(termin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot adSnap:dataSnapshot.getChildren()){
                        arrAbsagen.add(adSnap.child("Termin").getValue(String.class));
                    }

                    int anzAbsagen=arrAbsagen.size();
                    tvAbsagenZahl.setText(""+anzAbsagen);
                } else{
                    tvAbsagenZahl.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();








    }
}



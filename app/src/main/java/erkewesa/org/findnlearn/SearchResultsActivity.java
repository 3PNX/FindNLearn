package erkewesa.org.findnlearn;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SearchResultsActivity extends AppCompatActivity {
    ListView resultLv;
    String selectedStudiengang;
    String selectedModul;
    Dialog myDialog;
    String rndmKey;
    String username;
    Long teilnehmer;
    private ArrayList<Meetings> mResults=new ArrayList<Meetings>();
    private ArrayList<String> mKeys =new ArrayList();



    ArrayAdapter<Meetings> arrayAdapter;



    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        resultLv=findViewById(R.id.resultLv);
        myDialog=new Dialog(this);

        arrayAdapter=new ArrayAdapter<Meetings>(this,android.R.layout.simple_selectable_list_item,mResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 300;
                view.setLayoutParams(params);

                return view;
            }
        };
        resultLv.setAdapter(arrayAdapter);
        mDatabase=FirebaseDatabase.getInstance().getReference().child("meet");



        selectedStudiengang=getIntent().getStringExtra("Studiengang");
        selectedModul=getIntent().getStringExtra("Modul");
        rndmKey=getIntent().getStringExtra("RandomKey");
        username=getIntent().getStringExtra("username");

        String studg_modul=selectedStudiengang+"_"+selectedModul;



        mDatabase.orderByChild("studg_modul").equalTo(studg_modul).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){
                        mResults.add(adSnapshot.getValue(Meetings.class));
                        arrayAdapter.notifyDataSetChanged();
                        String key=adSnapshot.getKey();
                        mKeys.add(key);
                    }

                } else{
                    ArrayList<String> keineEintraegeArr = new ArrayList<String>();
                    ArrayAdapter keineEintraegeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,keineEintraegeArr);
                    resultLv.setAdapter(keineEintraegeAdapter);
                    keineEintraegeArr.add("Keine Einträge gefunden...");
                    keineEintraegeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                ArrayList<String> keineEintraegeArr = new ArrayList<String>();
                ArrayAdapter keineEintraegeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,keineEintraegeArr);
                resultLv.setAdapter(keineEintraegeAdapter);
                keineEintraegeArr.add("Keine Einträge gefunden...");
                keineEintraegeAdapter.notifyDataSetChanged();

            }
        });

        resultLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meetings m=mResults.get(position);
                String key = mKeys.get(position);
                showPopUp(m,key);

            }
        });






    }

    public void showPopUp( Meetings m,final String key){

        myDialog.setContentView(R.layout.meetingpopup);
        TextView txtClose;
        TextView txtStudg;
        TextView txtSem;
        TextView txtMod;
        TextView txtTeilnehmer;
        final TextView txtNextDate;
        final Button btnBeitreten;
        final ArrayList<String> arrDates=new ArrayList<>();
        final ArrayList<Date> arrDatum=new ArrayList<>();
        final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");


        txtClose=myDialog.findViewById(R.id.txtClose);
        txtStudg=myDialog.findViewById(R.id.txtStudg);
        txtSem=myDialog.findViewById(R.id.txtSem);
        txtMod=myDialog.findViewById(R.id.txtMod);
        txtTeilnehmer=myDialog.findViewById(R.id.txtTeilnehmer);
        txtNextDate=myDialog.findViewById(R.id.txtNextDate);
        btnBeitreten=myDialog.findViewById(R.id.btnBeitreten);

        txtStudg.setText("Studiengang: "+m.getStudiengang());
        txtSem.setText("Semester: "+m.getSemester().toString());
        txtMod.setText("Modul: "+m.getModul());
        txtTeilnehmer.setText("Teilnehmer: "+m.getTeilnehmer().toString());

        DatabaseReference mDates=FirebaseDatabase.getInstance().getReference().child("Termine");

        mDates.orderByChild("Meet").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){
                        arrDates.add(adSnapshot.child("Datum").getValue(String.class));
                    }
                        for(int i=0;i<arrDates.size();i++){

                        try {
                            Date strDate = sdf.parse(arrDates.get(i));

                            arrDatum.add(strDate);
                        }catch(ParseException e){

                        }
                        }

                    Collections.sort(arrDatum);
                    for(int i=0;i<arrDatum.size();i++){
                        Date t=arrDatum.get(i);
                        if(!new Date().after(t)){
                            String fmtDatum=sdf.format(t);
                            txtNextDate.setText("Nächstes Treffen: "+fmtDatum);
                            break;
                        } else{
                            txtNextDate.setText("Nächstes Treffen: Noch nicht festgelegt");
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        final DatabaseReference mTeilnehmer=FirebaseDatabase.getInstance().getReference().child("meet").child(key).child("teilnehmer");

        mTeilnehmer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teilnehmer=dataSnapshot.getValue(Long.class);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference mDbRef=FirebaseDatabase.getInstance().getReference().child("Kursteilnehmer");
        final ArrayList<String> arrKurse=new ArrayList<>();

        mDbRef.orderByChild("Teilnehmer").equalTo(rndmKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){

                        arrKurse.add(adSnapshot.child("Meet").getValue(String.class));
                    }
                    if(arrKurse.size()!=0) {
                        for (int i = 0; i < arrKurse.size(); i++) {
                            String meet = arrKurse.get(i);
                            if(meet.equals(key)){
                                btnBeitreten.setEnabled(false);
                                btnBeitreten.setText("Bereits Mitglied");
                                break;
                            }
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        btnBeitreten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Kursteilnehmer").push();
                mRef.child("Meet").setValue(key);
                mRef.child("Teilnehmer").setValue(rndmKey);
                mTeilnehmer.setValue(teilnehmer+1);





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

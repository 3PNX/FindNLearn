package erkewesa.org.findnlearn;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {
    private DatabaseReference mRef;

    Spinner cr_stuga;
    Spinner cr_se;
    Spinner cr_mod;
    TextView cr_datum;
    Button cr_create;

    // das Zeugs aus Find
    Spinner studgSp;
    Spinner semesterSp;
    Spinner modulSp;
    TextView tvStudiengang;
    TextView tvSemester;
    TextView tvModul;

    String selectedStudiengang;
    String selectedSemester;

    int anzSemester;
    private DatabaseReference mDataBase;
    private DatabaseReference dbSemester;
    private DatabaseReference dbModul;
    private ArrayList<String> arrStudiengaenge =new ArrayList<String>();
    private ArrayList<String> arrSemester=new ArrayList<String>();
    private ArrayList<String> arrModule=new ArrayList<String>();

    // bis hier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mRef = FirebaseDatabase.getInstance().getReference();

        //find jo
        mDataBase=FirebaseDatabase.getInstance().getReference();
        mDataBase=FirebaseDatabase.getInstance().getReference();
        studgSp= findViewById(R.id.cr_stuga);
        semesterSp=findViewById(R.id.cr_se);
        modulSp=findViewById(R.id.cr_mod);
        tvStudiengang=findViewById(R.id.cr_l_stuga);
        tvSemester=findViewById(R.id.cr_l_se);
        tvModul=findViewById(R.id.cr_l_mod);


        DatabaseReference dbStudiengänge=mDataBase.child("Studiengänge");
        final ArrayAdapter<String> studAdapter = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item, arrStudiengaenge);
        final ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item, arrSemester);
        final ArrayAdapter<String> modulAdapter = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item, arrModule);



        studgSp.setAdapter(studAdapter);
        semesterSp.setAdapter(semAdapter);
        modulSp.setAdapter(modulAdapter);

        tvSemester.setVisibility(View.INVISIBLE);
        semesterSp.setVisibility(View.INVISIBLE);
        tvModul.setVisibility(View.INVISIBLE);
        modulSp.setVisibility(View.INVISIBLE);



        dbStudiengänge.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String values=dataSnapshot.getValue(String.class);

                arrStudiengaenge.add(values);
                studAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        studgSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudiengang=studgSp.getSelectedItem().toString();
                studAdapter.notifyDataSetChanged();
                semesterSp.setSelection(0);


                try {
                    dbSemester= mDataBase.child("Semester").child(selectedStudiengang);
                    tvSemester.setVisibility(View.VISIBLE);
                    semesterSp.setVisibility(View.VISIBLE);
                }catch(DatabaseException ex){
                    dbSemester = mDataBase.child("Semester").child("Wahl");
                    tvSemester.setVisibility(View.INVISIBLE);
                    semesterSp.setVisibility(View.INVISIBLE);
                    tvModul.setVisibility(View.INVISIBLE);
                    modulSp.setVisibility(View.INVISIBLE);
                }

                dbSemester.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try {
                            anzSemester = dataSnapshot.getValue(Integer.class);
                            arrSemester.clear();
                            arrSemester.add("Bitte wählen...");

                            for (int i = 1; i <= anzSemester; i++) {
                                arrSemester.add("" + i);
                            }
                            semAdapter.notifyDataSetChanged();
                        } catch(Exception e){
                            String value =dataSnapshot.getValue(String.class);
                            arrSemester.add(value);
                            semAdapter.notifyDataSetChanged();

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                semesterSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        arrModule.clear();
                        selectedSemester=semesterSp.getItemAtPosition(position).toString();


                        if(!selectedStudiengang.equals("Bitte wählen...")||!selectedSemester.equals("Bitte wählen...")) {
                            try {
                                dbModul = mDataBase.child("Module").child(selectedStudiengang).child("Semester " + selectedSemester);
                                tvModul.setVisibility(View.VISIBLE);
                                modulSp.setVisibility(View.VISIBLE);


                                dbModul.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        String values = dataSnapshot.getValue(String.class);
                                        arrModule.add(values);
                                        modulAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }catch(DatabaseException e){
                                tvModul.setVisibility(View.INVISIBLE);
                                modulSp.setVisibility(View.INVISIBLE);
                                arrModule.clear();
                                arrModule.add("Bitte wählen...");
                                modulAdapter.notifyDataSetChanged();
                            }
                        } else{
                            arrModule.add("Bitte wählen...");
                            modulAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // end of find jo

        // meet erstellen



//        cr_stuga= findViewById(R.id.cr_stuga);
//        cr_se=findViewById(R.id.cr_se);
//        cr_mod=findViewById(R.id.cr_mod);
        cr_datum= findViewById(R.id.cr_datum);
        cr_create=(Button) findViewById(R.id.cr_create);

        cr_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mRefChild = mRef.child("meet");

                Meetings m = new Meetings();
                m.setDatum((String) cr_datum.getText());
                m.setModul((String) modulSp.getSelectedItem());
                m.setSemester((Long) semesterSp.getSelectedItem());
                m.setStudiengang((String) studgSp.getSelectedItem());
                m.setStudg_modul( studgSp.getSelectedItem().toString() + "_" + modulSp.getSelectedItem().toString());
                mRefChild.push().setValue(m);

            }
        });
        //bis hier
    }
}

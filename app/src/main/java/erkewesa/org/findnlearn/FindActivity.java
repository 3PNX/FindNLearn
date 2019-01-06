package erkewesa.org.findnlearn;

import android.content.Intent;
import android.media.tv.TvInputManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


import java.util.ArrayList;



public class FindActivity extends AppCompatActivity{

    Spinner studgSp;
    Spinner semesterSp;
    Spinner modulSp;
    Button searchBtn;
    TextView tvStudiengang;
    TextView tvSemester;
    TextView tvModul;
    String rndmKey;
    String username;

    String selectedStudiengang;
    String selectedSemester;

    int anzSemester;
    private DatabaseReference mDataBase;
    private DatabaseReference dbSemester;
    private DatabaseReference dbModul;
    private ArrayList<String> arrStudiengaenge =new ArrayList<String>();
    private ArrayList<String> arrSemester=new ArrayList<String>();
    private ArrayList<String> arrModule=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        mDataBase=FirebaseDatabase.getInstance().getReference();

        searchBtn=findViewById(R.id.searchBtn);
        studgSp= findViewById(R.id.studgSp);
        semesterSp=findViewById(R.id.semesterSp);
        modulSp=findViewById(R.id.modulSp);
        tvStudiengang=findViewById(R.id.studgTxV);
        tvSemester=findViewById(R.id.semesterTxV);
        tvModul=findViewById(R.id.modulTxV);

        searchBtn.setEnabled(false);

        rndmKey=getIntent().getStringExtra("RandomKey");
        username=getIntent().getStringExtra("username");


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
                                searchBtn.setEnabled(true);

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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchResultsActivityIntent=new Intent(getApplicationContext(),SearchResultsActivity.class);
                searchResultsActivityIntent.putExtra("Studiengang",studgSp.getSelectedItem().toString());
                searchResultsActivityIntent.putExtra("Modul",modulSp.getSelectedItem().toString());
                searchResultsActivityIntent.putExtra("RandomKey",rndmKey);
                searchResultsActivityIntent.putExtra("username",username);
                startActivity(searchResultsActivityIntent);
            }
        });




    }


}

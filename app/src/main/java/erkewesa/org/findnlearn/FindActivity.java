package erkewesa.org.findnlearn;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;



public class FindActivity extends AppCompatActivity{

    Spinner studgSp;
    Spinner semesterSp;
    Spinner modulSp;
    Button btnGo;

    int anzSemester;
    private DatabaseReference mDataBase;
    private ArrayList<String> arrStudiengaenge =new ArrayList<String>();
    private ArrayList<String> arrSemester=new ArrayList<String>();
    private ArrayList<String> arrModule=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        mDataBase=FirebaseDatabase.getInstance().getReference();

        btnGo=findViewById(R.id.searchBtn);
        studgSp=findViewById(R.id.studgSp);
        semesterSp=findViewById(R.id.semesterSp);
        modulSp=findViewById(R.id.modulSp);

        DatabaseReference dbStudiengänge=mDataBase.child("Studiengänge");
        final ArrayAdapter<String> studAdapter = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item, arrStudiengaenge);
        final ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item, arrSemester);
        final ArrayAdapter<String> modulAdapter = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item, arrModule);

        studgSp.setAdapter(studAdapter);
        semesterSp.setAdapter(semAdapter);
        modulSp.setAdapter(modulAdapter);



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
        //NOCH NULLPOINTER EXCEPTION AB HIER
        String selectedStudiengang=studgSp.getSelectedItem().toString();
        DatabaseReference dbSemester=mDataBase.child("Semester").child(selectedStudiengang);

        dbSemester.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                anzSemester = dataSnapshot.getValue(Integer.class);
                arrSemester.clear();

                for(int i=1;i<=anzSemester;i++){
                    arrSemester.add(""+i);
                }
                semAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }










}

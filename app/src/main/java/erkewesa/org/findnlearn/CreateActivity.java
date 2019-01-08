package erkewesa.org.findnlearn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import erkewesa.org.findnlearn.data.FindNLearnDbHelper;

public class CreateActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private static final String TAG = "CreateActivity";
    Spinner cr_stuga;
    Spinner cr_se;
    Spinner cr_mod;
    // Dec new DatePicker and TimePicker
    private TextView cr_date;
    private TextView cr_timeb;
    private TextView cr_timee;
    private DatePickerDialog.OnDateSetListener cr_date_list;
    private TimePickerDialog cr_tp_timeb;
    private TimePickerDialog cr_tp_timee;

    private String zeit_von = "00:00";
    private String zeit_bis = "00:00";


    // end of Dec
    Button cr_create;
    TextView cr_popup;

    // das Zeugs aus Find
    Spinner studgSp;
    Spinner semesterSp;
    Spinner modulSp;
    TextView tvStudiengang;
    TextView tvSemester;
    TextView tvModul;
    TextView tvTag;

    String selectedStudiengang;
    String selectedSemester;
    private String rndmKey;


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
        studgSp= findViewById(R.id.cr_stuga);
        semesterSp=findViewById(R.id.cr_se);
        modulSp=findViewById(R.id.cr_mod);
        tvStudiengang=findViewById(R.id.cr_l_stuga);
        tvSemester=findViewById(R.id.cr_l_se);
        tvModul=findViewById(R.id.cr_l_mod);
        tvTag=findViewById(R.id.cr_l_tag);
        cr_stuga= findViewById(R.id.cr_stuga);
        cr_se=findViewById(R.id.cr_se);
        cr_mod=findViewById(R.id.cr_mod);
        // new Date init
        cr_date = findViewById(R.id.cr_tv_date);
        cr_timeb = findViewById(R.id.cr_tv_timeb);
        cr_timee = findViewById(R.id.cr_tv_timee);
        // new Date end
        cr_create=(Button) findViewById(R.id.cr_create);
        cr_popup = findViewById(R.id.cr_popup);
        // date pickers logic
        cr_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view ) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        cr_date_list,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        cr_date_list = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + day);
                String date = day + "/" + month + "/" + year;
                cr_date.setText(date);
            }
        };
        // end of date pickers logic
        // start of time pickers logic

        // begin-time
        cr_timeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                cr_tp_timeb = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minutes) {
                        String min = ""+minutes;
                        if  (min.equals("0")){
                            min = "00";
                        }
                        cr_timeb.setText(hourOfDay + ":" + min + " Uhr bis ");

                        zeit_von = hourOfDay + ":" + min;

                    }
                }, 0, 0, false);

                cr_tp_timeb.show();


            }
        });

        // end-time
        cr_timee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                cr_tp_timee = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minutes) {
                        String min = ""+minutes;
                        if  (min.equals("0")){
                            min = "00";
                        }
                        cr_timee.setText(hourOfDay + ":" + min + " Uhr");

                        zeit_bis = hourOfDay + ":" + min;
                    }
                }, 0, 0, false);

                cr_tp_timee.show();
            }
        });


        //end






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
        tvTag.setVisibility(View.INVISIBLE);
        cr_date.setVisibility(View.INVISIBLE);
        cr_popup.setVisibility(View.INVISIBLE);
        cr_create.setVisibility(View.INVISIBLE);
        cr_timeb.setVisibility(View.INVISIBLE);
        cr_timee.setVisibility(View.INVISIBLE);


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
                    cr_date.setVisibility(View.INVISIBLE);
                    cr_create.setVisibility(View.INVISIBLE);
                    cr_timeb.setVisibility(View.INVISIBLE);
                    cr_timee.setVisibility(View.INVISIBLE);
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
                                tvTag.setVisibility(View.VISIBLE);
                                cr_date.setVisibility(View.VISIBLE);
                                cr_create.setVisibility(View.VISIBLE);
                                cr_timeb.setVisibility(View.VISIBLE);
                                cr_timee.setVisibility(View.VISIBLE);

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





        cr_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mRefChild = mRef.child("meet");
                rndmKey=getIntent().getStringExtra("RandomKey");

                Meetings m = new Meetings();
                m.setModul((String) cr_mod.getSelectedItem());
                m.setTeilnehmer(Long.parseLong("1"));
                m.setSemester( Long.parseLong(cr_se.getSelectedItem().toString()));
                m.setStudiengang((String) cr_stuga.getSelectedItem());
                m.setStudg_modul((String) cr_stuga.getSelectedItem() + "_" + cr_mod.getSelectedItem());

                DatabaseReference mRefMeeting = mRefChild.push();
                String meetKey=mRefMeeting.getKey();
                mRefMeeting.setValue(m);

               DatabaseReference mRefTermine= mDataBase.child("Termine").push();
                mRefTermine.child("Meet").setValue(meetKey);
                mRefTermine.child("Datum").setValue(cr_date.getText().toString());
                mRefTermine.child("Zeit_bis").setValue(zeit_bis);
                mRefTermine.child("Zeit_von").setValue(zeit_von);

                DatabaseReference mRefTeilnehmer = mDataBase.child("Kursteilnehmer").push();
                mRefTeilnehmer.child("Meet").setValue(meetKey);
                mRefTeilnehmer.child("Teilnehmer").setValue(rndmKey);


                Intent HomeIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(HomeIntent);

                Toast.makeText(CreateActivity.this, "succeeded!", Toast.LENGTH_LONG).show();






            }
        });






    }



}

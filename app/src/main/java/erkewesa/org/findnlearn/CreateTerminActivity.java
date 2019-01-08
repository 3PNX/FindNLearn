package erkewesa.org.findnlearn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateTerminActivity extends AppCompatActivity {
    private static final String TAG = "CreateActivity";
    private String meetKey, userKey;
    private EditText edBeschreibung;
    private DatabaseReference mDataBase;

    private TextView crt_date;
    private TextView crt_timeb;
    private TextView crt_timee;
    private TextView crt_save;
    private DatePickerDialog.OnDateSetListener crt_date_list;
    private TimePickerDialog crt_tp_timeb;
    private TimePickerDialog crt_tp_timee;

    private String zeit_von = "00:00";
    private String zeit_bis = "00:00";

    Intent  GroupOverviewIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_termin);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        userKey=getIntent().getStringExtra("userKey");

        meetKey=getIntent().getStringExtra("meetKey");
        edBeschreibung = findViewById(R.id.edTerminBeschreibung);

        crt_date = findViewById(R.id.crt_tv_date);
        crt_timeb = findViewById(R.id.crt_tv_timeb);
        crt_timee = findViewById(R.id.crt_tv_timee);
        crt_save = findViewById(R.id.crt_save);

        crt_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view ) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateTerminActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        crt_date_list,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        crt_date_list = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + day);
                String date = day + "/" + month + "/" + year;
                crt_date.setText(date);
            }
        };
        // end of date pickers logic
        // start of time pickers logic

        // begin-time
        crt_timeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                crt_tp_timeb = new TimePickerDialog(CreateTerminActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minutes) {
                        String min = ""+minutes;
                        if  (min.equals("0")){
                            min = "00";
                        }
                        crt_timeb.setText(hourOfDay + ":" + min + " Uhr bis ");

                        zeit_von = hourOfDay + ":" + min;

                    }
                }, 0, 0, false);

                crt_tp_timeb.show();


            }
        });

        // end-time
        crt_timee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                crt_tp_timee = new TimePickerDialog(CreateTerminActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minutes) {
                        String min = ""+minutes;
                        if  (min.equals("0")){
                            min = "00";
                        }
                        crt_timee.setText(hourOfDay + ":" + min + " Uhr");

                        zeit_bis = hourOfDay + ":" + min;
                    }
                }, 0, 0, false);

                crt_tp_timee.show();
            }
        });

        GroupOverviewIntent =new Intent(getApplicationContext(),GroupOverviewActivity.class);
        GroupOverviewIntent.putExtra("meetKey",meetKey);
        GroupOverviewIntent.putExtra("userKey",userKey);

        crt_save.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  try {
                      DatabaseReference mRefTermine = mDataBase.child("Termine").push();
                      mRefTermine.child("Beschreibung").setValue(edBeschreibung.getText().toString());
                      mRefTermine.child("Datum").setValue(crt_date.getText());
                      mRefTermine.child("Meet").setValue(meetKey);
                      mRefTermine.child("Zeit_bis").setValue(zeit_bis);
                      mRefTermine.child("Zeit_von").setValue(zeit_von);

                      Toast.makeText(CreateTerminActivity.this, "succeeded!", Toast.LENGTH_LONG).show();



                  }catch (DatabaseException e){
                      Toast.makeText(CreateTerminActivity.this, "Problem with Connection", Toast.LENGTH_LONG).show();
                  }


                  startActivity(GroupOverviewIntent);



              }
          });




    }
    @Override
    public void onBackPressed(){
        this.startActivity(GroupOverviewIntent);
    }

}

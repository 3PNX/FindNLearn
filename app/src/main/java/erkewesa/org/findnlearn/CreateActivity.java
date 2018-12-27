package erkewesa.org.findnlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    Spinner cr_stuga;
    Spinner cr_se;
    Spinner cr_mod;
    Date cr_tag;
    Button cr_create;

    private DatabaseReference mDataBase;
    private ArrayList<String> arr_cr_stuga = new ArrayList<String>();
    private ArrayList<String> arr_cr_se = new ArrayList<String>();
    private ArrayList<String> arrModule = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mDataBase=FirebaseDatabase.getInstance().getReference();

        cr_stuga= findViewById(R.id.cr_stuga);
        cr_se=findViewById(R.id.cr_se);
        cr_mod=findViewById(R.id.cr_mod);
//        cr_tag=findViewById(R.id.cr_tag);

    }
}

package erkewesa.org.findnlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {
    private EditText pro_name, pro_stuga;
    private Button pro_save;
    private User user;
    private DatabaseReference mRef;
    private DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //abfragen ob User schon in DB ist: Lokale DB vergleichen mit Firebase jo
        // Verbindung zur loc DB aufbauen


        // wenn das nicht der Fall ist, dann das...
        pro_name = findViewById(R.id.pro_name);
        pro_stuga = findViewById(R.id.pro_course);
        pro_save = (Button) findViewById(R.id.pro_save);

        pro_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //loc DB
                    insertLocalData();
                // end
                // firebase
                user = new User(pro_name.getText().toString(), pro_stuga.getText().toString());

                try {
                    DatabaseReference mRefChild = mRef.child("user");
                    mRefChild.push().setValue(user);

                }catch (DatabaseException e){
                    e.printStackTrace();
                }
                //end



            }
        });

        // wenn doch, dann Vergleich ab hier:



        // bis hier
    }

    private void insertLocalData (){
        boolean result = dbh.insertData(pro_name.getText().toString(), pro_stuga.getText().toString());
        if (result){
            Toast.makeText(ProfileActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(ProfileActivity.this, "Data insertion failed", Toast.LENGTH_LONG).show();
        }
    }

}

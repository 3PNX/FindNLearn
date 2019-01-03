package erkewesa.org.findnlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {
    private String pro_name, pro_stuga;
    private Button pro_save;
    private User user;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //abfragen ob User schon in DB ist: Lokale DB vergleichen mit Firebase jo
        // Verbindung zur loc DB aufbauen


        // wenn das nicht der Fall ist, dann das...
        pro_name = findViewById(R.id.pro_name).toString();
        pro_stuga = findViewById(R.id.pro_course).toString();
        pro_save = (Button) findViewById(R.id.pro_save);

        pro_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(pro_name, pro_stuga);

                try {
                    DatabaseReference mRefChild = mRef.child("user");
                    mRefChild.push().setValue(user);

                }catch (DatabaseException e){
                    e.printStackTrace();
                }




            }
        });

        // wenn doch, dann Vergleich ab hier:



        // bis hier
    }
}

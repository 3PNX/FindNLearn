package erkewesa.org.findnlearn;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import erkewesa.org.findnlearn.data.FindNLearnDbHelper;
import erkewesa.org.findnlearn.data.StudiengangContract;

public class CreateUserActivity extends AppCompatActivity {

    private Button btnCreate;
    private DatabaseReference mFireDatabase;
    private EditText edUser;
    private FindNLearnDbHelper myDbHelper;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        btnCreate=findViewById(R.id.btnCreateUser);
        edUser=findViewById(R.id.edUser);



        myDbHelper=new FindNLearnDbHelper(this);
        final SQLiteDatabase db=myDbHelper.getWritableDatabase();

        mFireDatabase =FirebaseDatabase.getInstance().getReference().child("User");

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=edUser.getText().toString();
                DatabaseReference ref= mFireDatabase.push();
                key=ref.getKey();
                ref.child("Name").setValue(user);
                ContentValues cv= new ContentValues();
                cv.put(StudiengangContract.StudiengangEntry.COLUMN_RANDOMKEY,key);
                cv.put(StudiengangContract.StudiengangEntry.COLUMN_USERNAME,user);

                db.insert(StudiengangContract.StudiengangEntry.TABLE_NAME,null,cv);

                Intent usersignedIntent =new Intent(getApplicationContext(),MainActivity.class);
                usersignedIntent.putExtra("username",user);
                startActivity(usersignedIntent);


            }
        });

    }


    @Override
    public void onBackPressed(){
        this.finishAffinity();
    }

}

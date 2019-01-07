package erkewesa.org.findnlearn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import erkewesa.org.findnlearn.views.BoxInhalt;
import erkewesa.org.findnlearn.views.testView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupOverviewActivity extends AppCompatActivity {
    private String meetKey;
    private TextView tvTeilnehmer;
    private Long teilnehmer;




    private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_overview);

       testView tv=findViewById(R.id.testView);
       BoxInhalt bo=new BoxInhalt();
       bo.setTitle("Prog2");
       bo.setDatum("12/01/2019");




       tv.setInhalt(bo);



    }
}


// meetKey=getIntent().getStringExtra("meetKey");
//
//        mDatabase.child("meet").child(meetKey).child("Teilnehmer").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                teilnehmer=dataSnapshot.getValue(Long.class);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
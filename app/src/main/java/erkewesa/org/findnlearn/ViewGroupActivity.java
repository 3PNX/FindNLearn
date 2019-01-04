package erkewesa.org.findnlearn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewGroupActivity extends AppCompatActivity {

    private String rndmKey;
    private String username;
    ListView lvMyGroups;
    private ArrayList<Meetings> mResults=new ArrayList<Meetings>();

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        lvMyGroups=findViewById(R.id.LvMyGroups);
        rndmKey=getIntent().getStringExtra("RandomKey");
        username=getIntent().getStringExtra("username");



        final ArrayAdapter<Meetings> arrayAdapter=new ArrayAdapter<Meetings>(this,android.R.layout.simple_selectable_list_item,mResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 300;
                view.setLayoutParams(params);

                return view;
            }
        };
        lvMyGroups.setAdapter(arrayAdapter);

        mDatabase=FirebaseDatabase.getInstance().getReference().child("meet");

        mDatabase.orderByValue().equalTo(rndmKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){
                        mResults.add(adSnapshot.getValue(Meetings.class));
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else{
                    ArrayList<String> keineEintraegeArr = new ArrayList<String>();
                    ArrayAdapter keineEintraegeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,keineEintraegeArr);
                    lvMyGroups.setAdapter(keineEintraegeAdapter);
                    keineEintraegeArr.add("Keine Einträge gefunden...");
                    keineEintraegeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}

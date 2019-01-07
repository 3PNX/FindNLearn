package erkewesa.org.findnlearn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
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
    private ArrayList<String> mMeets=new ArrayList();

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

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Kursteilnehmer");

        mDatabase.orderByChild("Teilnehmer").equalTo(rndmKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    for(DataSnapshot adSnapshot:dataSnapshot.getChildren()){

                        mMeets.add(adSnapshot.child("Meet").getValue(String.class));

                    }
                } else{
                    ArrayList<String> keineEintraegeArr = new ArrayList<String>();
                    ArrayAdapter keineEintraegeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,keineEintraegeArr);
                    lvMyGroups.setAdapter(keineEintraegeAdapter);
                    keineEintraegeArr.add("Keine Einträge gefunden...");
                    keineEintraegeAdapter.notifyDataSetChanged();
                }

                if(mMeets.size()!=0) {
                    for (int i = 0; i < mMeets.size(); i++) {
                        String meeting = mMeets.get(i);
                        DatabaseReference dbMeets = FirebaseDatabase.getInstance().getReference().child("meet").child(meeting);

                        dbMeets.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mResults.add(dataSnapshot.getValue(Meetings.class));
                                arrayAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvMyGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mMeets.size()!=0) {
                    Intent groupOverViewIntent = new Intent(getApplicationContext(), GroupOverviewActivity.class);
                    groupOverViewIntent.putExtra("meetKey", mMeets.get(position));
                    groupOverViewIntent.putExtra("userKey",rndmKey);
                    startActivity(groupOverViewIntent);
                }
            }
        });




    }
}

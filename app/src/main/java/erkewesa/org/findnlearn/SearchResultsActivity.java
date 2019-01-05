package erkewesa.org.findnlearn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    ListView resultLv;
    String selectedStudiengang;
    String selectedModul;
    private ArrayList<Meetings> mResults=new ArrayList<Meetings>();

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        resultLv=findViewById(R.id.resultLv);

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
        resultLv.setAdapter(arrayAdapter);

        mDatabase=FirebaseDatabase.getInstance().getReference().child("meet");



        selectedStudiengang=getIntent().getStringExtra("Studiengang");
        selectedModul=getIntent().getStringExtra("Modul");
        String studg_modul=selectedStudiengang+"_"+selectedModul;

        mDatabase.orderByChild("studg_modul").equalTo(studg_modul).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    resultLv.setAdapter(keineEintraegeAdapter);
                    keineEintraegeArr.add("Keine Einträge gefunden...");
                    keineEintraegeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                ArrayList<String> keineEintraegeArr = new ArrayList<String>();
                ArrayAdapter keineEintraegeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,keineEintraegeArr);
                resultLv.setAdapter(keineEintraegeAdapter);
                keineEintraegeArr.add("Keine Einträge gefunden...");
                keineEintraegeAdapter.notifyDataSetChanged();

            }
        });






    }
}

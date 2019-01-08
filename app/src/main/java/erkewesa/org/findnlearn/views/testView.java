package erkewesa.org.findnlearn.views;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import erkewesa.org.findnlearn.GroupOverviewActivity;
import erkewesa.org.findnlearn.R;


public class testView extends LinearLayout {
    private TextView beschreibung,datum,zeitVon,zeitBis,tvClick;
    private Button btnZusagen,btnAbsagen;
    private BoxInhalt boxInhalt;
    private final int colorZugesagt=Color.parseColor("#ff99cc00");
    private final int colorAbgesagt=Color.parseColor("#ffff4444");
    private final int colorNichtZugesagt=Color.parseColor("#d1f2b3");
    private final int colorNichtAbgesagt=Color.parseColor("#f29785");

    private Dialog myDialog;
    private Context context;

    private ArrayList<String> arrZusagen=new ArrayList<>();
    private ArrayList<String> arrAbsagen=new ArrayList<>();

    private TextView txtClose;
    private TextView tvBeschreibung;
    private TextView tvZusagenZahl;
    private TextView tvAbsagenZahl;





    public testView(Context context) {
        super(context,null);
        init(context);
    }

    public testView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        init(context);
    }

    public testView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public testView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context){

        inflate(getContext(),R.layout.activity_overview,this);

        datum=findViewById(R.id.tvDatum);
        beschreibung=findViewById(R.id.tvBeschreibung);
        zeitVon=findViewById(R.id.tvVon);
        zeitBis=findViewById(R.id.tvBis);
        btnZusagen=findViewById(R.id.o_zusagen);
        btnAbsagen=findViewById(R.id.o_absagen);
        tvClick=findViewById(R.id.tvClick);
        myDialog=new Dialog(context.getApplicationContext());



        myDialog.setContentView(R.layout.termin_popup);

       txtClose=myDialog.findViewById(R.id.txtTerminClose);
       tvBeschreibung=myDialog.findViewById(R.id.tvPopUpBeschreibung);
       tvZusagenZahl=myDialog.findViewById(R.id.tvPopUpZusagenZahl);
       tvAbsagenZahl=myDialog.findViewById(R.id.tvPopUpAbsagenZahl);


    }
    public void setInhalt(BoxInhalt boxInhalt,String key,String user,OnClickListener onClick){
        this.boxInhalt =boxInhalt;
        setupView(key,user,onClick);
    }

    private void setupView(final String key,final String user,OnClickListener onClick){

        datum.setText(boxInhalt.getDatum());
        beschreibung.setText(boxInhalt.getBeschreibung());
        zeitVon.setText(boxInhalt.getZeitVon());
        zeitBis.setText(boxInhalt.getZeitBis());
        final String termin_user=key+"_"+user;

        final DatabaseReference absDB=FirebaseDatabase.getInstance().getReference().child("Absagen");
        final DatabaseReference zusDb=FirebaseDatabase.getInstance().getReference().child("Zusagen");


        absDB.orderByChild("Termin_User").equalTo(termin_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    setAbgesagt();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        zusDb.orderByChild("Termin_User").equalTo(termin_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    setZugesagt();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


                btnZusagen.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setZugesagt();
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Zusagen").push();
                        mDatabase.child("Termin").setValue(key);
                        mDatabase.child("User").setValue(user);

                        mDatabase.child("Termin_User").setValue(termin_user);


                        absDB.orderByChild("Termin_User").equalTo(termin_user).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                                    String child = adSnapshot.getKey();
                                    absDB.child(child).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
        btnAbsagen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                setAbgesagt();
                DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference().child("Absagen").push();
                mDatabase.child("Termin").setValue(key);
                mDatabase.child("User").setValue(user);
                mDatabase.child("Termin_User").setValue(termin_user);



                zusDb.orderByChild("Termin_User").equalTo(termin_user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot adSnapshot:dataSnapshot.getChildren()) {
                            String child = adSnapshot.getKey();
                            zusDb.child(child).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        tvClick.setOnClickListener(onClick);


    }
    public void setZugesagt(){
        btnZusagen.setText("ZUGESAGT");
        btnZusagen.setBackgroundColor(colorZugesagt);
        btnZusagen.setEnabled(false);
        btnAbsagen.setText("ABSAGEN");
        btnAbsagen.setBackgroundColor(colorNichtAbgesagt);
        btnAbsagen.setEnabled(true);
    }

    public void setAbgesagt(){
        btnZusagen.setText("ZUSAGEN");
        btnZusagen.setBackgroundColor(colorNichtZugesagt);
        btnAbsagen.setBackgroundColor(colorAbgesagt);
        btnAbsagen.setText("ABGESAGT");
        btnAbsagen.setEnabled(false);
        btnZusagen.setEnabled(true);
    }





}

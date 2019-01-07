package erkewesa.org.findnlearn.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import erkewesa.org.findnlearn.R;


public class testView extends LinearLayout {
    private TextView title,datum;
    private BoxInhalt s;




    public testView(Context context) {
        super(context,null);
        init();
    }

    public testView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        init();
    }

    public testView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public testView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){

        inflate(getContext(),R.layout.activity_overview,this);

        title=findViewById(R.id.tvBoxTitel);
        datum=findViewById(R.id.tvBoxDatum);


    }
    public void setInhalt(BoxInhalt s){
        this.s=s;
        setupView();
    }

    private void setupView(){
        title.setText(s.getTitle());
        datum.setText(s.getDatum());
    }


}

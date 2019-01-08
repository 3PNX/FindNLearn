package erkewesa.org.findnlearn.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import erkewesa.org.findnlearn.R;

public class terminBoxView extends LinearLayout {
    private TextView tvTerminBeschreibung, tvTerminVon, tvTerminBis, tvClick, tvTerminModul, tvTerminDatum;
    private BoxInhalt boxInhalt;


    public terminBoxView(Context context) {
        super(context);
        init(context);
    }

    public terminBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public terminBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public terminBoxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(getContext(), R.layout.terminoverview, this);

        tvTerminBeschreibung = findViewById(R.id.tvTerminBeschreibung);
        tvTerminVon = findViewById(R.id.tvTerminVon);
        tvTerminBis = findViewById(R.id.tvTerminBis);
        tvTerminModul = findViewById(R.id.tvTerminModul);
        tvTerminDatum = findViewById(R.id.tvTerminDatum);
        tvClick=findViewById(R.id.tvClick);

    }

    public void setInhalt(BoxInhalt boxInhalt,OnClickListener onClick) {
        this.boxInhalt = boxInhalt;
        setupView(onClick);
    }

    private void setupView(OnClickListener onClick) {

        tvTerminDatum.setText(boxInhalt.getDatum());
        tvTerminBeschreibung.setText(boxInhalt.getBeschreibung());
        tvTerminVon.setText(boxInhalt.getZeitVon());
        tvTerminBis.setText(boxInhalt.getZeitBis());
        tvTerminModul.setText(boxInhalt.getModul());
        tvClick.setOnClickListener(onClick);
    }
}
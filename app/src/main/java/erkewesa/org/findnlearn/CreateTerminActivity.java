package erkewesa.org.findnlearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateTerminActivity extends AppCompatActivity {
    private String meetKey;
    private EditText edBeschreibung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_termin);

        meetKey=getIntent().getStringExtra("meetKey");

    }
}

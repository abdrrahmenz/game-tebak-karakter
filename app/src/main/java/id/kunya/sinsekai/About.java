package id.kunya.sinsekai;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView developer = (TextView) findViewById(R.id.developer);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Steamy.otf");
        developer.setTypeface(custom_font);
    }
}

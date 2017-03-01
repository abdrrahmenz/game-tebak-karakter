package id.kunya.sinsekai;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DevelopedBy extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developed_by);

        final TextView developer = (TextView) findViewById(R.id.kunya);
        final View onlayout = findViewById(R.id.bdSponsor);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Steamy.otf");
        developer.setTypeface(custom_font);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                developer.setVisibility(View.GONE);
                onlayout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent i = new Intent(DevelopedBy.this, Home.class);
                        startActivity(i);
                    }
                }, SPLASH_TIME_OUT);
            }
        }, SPLASH_TIME_OUT);
    }
}

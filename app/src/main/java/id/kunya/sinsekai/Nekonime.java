package id.kunya.sinsekai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Nekonime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nekonime);
        Button closeiklan= (Button) findViewById(R.id.close_iklan);
        ImageView nekotoweb= (ImageView) findViewById(R.id.nekotoweb);
        TextView nekotowebt= (TextView) findViewById(R.id.nekotowebt);
        ImageView nekotowebl= (ImageView) findViewById(R.id.nekotowebl);

        closeiklan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nekotowebl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("http://nekonime.com/");
            }
        });

        nekotowebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("http://nekonime.com/");
            }
        });

        nekotoweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("http://nekonime.com/");
            }
        });
    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}

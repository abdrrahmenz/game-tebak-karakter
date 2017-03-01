package id.kunya.sinsekai;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Asker extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText ask_type, ask, answer, point, locked, answered,edtID,level;
    Button addAsk,getData,updateData,livePreview;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asker);
        myDb = new DatabaseHelper(this);
        edtID= (EditText) findViewById(R.id.id);
        ask_type= (EditText) findViewById(R.id.ask_type);
        ask= (EditText) findViewById(R.id.ask);
        answer= (EditText) findViewById(R.id.answer);
        point= (EditText) findViewById(R.id.point);
        answered= (EditText) findViewById(R.id.answered);
        locked= (EditText) findViewById(R.id.locked);
        level= (EditText) findViewById(R.id.level);
        addAsk= (Button) findViewById(R.id.addAsk);
        getData= (Button) findViewById(R.id.showData);
        updateData= (Button) findViewById(R.id.updateData);
        livePreview= (Button) findViewById(R.id.livePreview);
        final TextView buku= (TextView) findViewById(R.id.buku);
        addData();
        viewAll();
        updateData();
        livePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insf=new Intent(Asker.this, Characters.class);
                startActivity(insf);
            }
        });
        edtID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buku.setText("Buat Kuis");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                buku.setText("fahmi is typing...");
                if(edtID.getText().toString().equals("1")) {
                    Toast.makeText(getApplicationContext(),"1 asli", Toast.LENGTH_SHORT).show();
                }else if(edtID.getText().toString().equals("2")){
                    Toast.makeText(getApplicationContext(),"2 asli", Toast.LENGTH_SHORT).show();
                }else if(edtID.getText().toString().equals("3")){
                    Toast.makeText(getApplicationContext(),"3 asli", Toast.LENGTH_SHORT).show();
                }else if(edtID.getText().toString().equals("4")){
                    Toast.makeText(getApplicationContext(),"4 asli", Toast.LENGTH_SHORT).show();
                }else if(edtID.getText().toString().equals("5")){
                    Toast.makeText(getApplicationContext(),"5 asli", Toast.LENGTH_SHORT).show();
                }else if(edtID.getText().toString().equals("6")){
                    Toast.makeText(getApplicationContext(),"6 asli", Toast.LENGTH_SHORT).show();
                }else if(edtID.getText().toString().equals("7")){
                    Toast.makeText(getApplicationContext(),"7 asli", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),edtID.getText().toString(), Toast.LENGTH_SHORT).show();
                }
//                if(edtID.getText().toString().equals("focus")){
//                    Toast.makeText(getApplicationContext(),"on focus",Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void updateData(){
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(edtID.getText().toString(),
                        ask_type.getText().toString(),
                        ask.getText().toString(),
                        answer.getText().toString(),
                        point.getText().toString(),
                        answered.getText().toString(),
                        locked.getText().toString());

                if (isUpdated == true)
                    Toast.makeText(getApplicationContext(), "Kuis telah diupdate", Toast.LENGTH_SHORT).show();
                else

                    Toast.makeText(getApplicationContext(), "Gagal mengupdate kuis", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addData(){
        addAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(ask_type.getText().toString(),
                        ask.getText().toString(),
                        answer.getText().toString(),
                        point.getText().toString(),
                        answered.getText().toString(),
                        locked.getText().toString(),
                        level.getText().toString());
                if (isInserted == true)
                    Toast.makeText(getApplicationContext(), "Kuis telah ditambah", Toast.LENGTH_SHORT).show();
                else

                    Toast.makeText(getApplicationContext(), "Gagal menambahkan kuis", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void  viewAll(){
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getAllData();
                if (res.getCount()==0){
                    //show message
                   showMessage("Error","No Result");
                    return;
                }

                StringBuffer buffer =new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("id :"+res.getString(0)+"\n");
                    buffer.append("ask_type :"+res.getString(1)+"\n");
                    buffer.append("ask :"+res.getString(2)+"\n");
                    buffer.append("answer :"+res.getString(3)+"\n");
                    buffer.append("point :"+res.getString(4)+"\n");
                    buffer.append("is answered? :"+res.getString(5)+"\n");
                    buffer.append("is locked? :"+res.getString(6)+"\n");
                    buffer.append("level :"+res.getString(7)+"\n");
                }

                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }
    public  void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}

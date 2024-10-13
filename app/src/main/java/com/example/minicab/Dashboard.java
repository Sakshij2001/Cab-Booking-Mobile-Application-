package com.example.minicab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Dashboard extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button btn_next;
    EditText edt_date,edt_pickup,edt_destination;
    String date,address,add_destination,userId,mainLat,mainLon;
    Spinner sp_time;
    TextView textLat,textLong;
    String[] dropdownTime={"10 am","11 am","12 pm","1 pm"};

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        edt_date=findViewById(R.id.txtDate);
        edt_pickup=findViewById(R.id.txtPickup);
        edt_destination=findViewById(R.id.txtDestination);
        sp_time=findViewById(R.id.spTime);
        btn_next=findViewById(R.id.btnNext);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dbRef=database.getReference("location");
        userId=mAuth.getCurrentUser().getUid();
        DatabaseReference dbLocation=dbRef.child(userId);


        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDilog();
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,dropdownTime);
        sp_time.setAdapter(adapter);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                address=edt_pickup.getText().toString();
                add_destination=edt_destination.getText().toString();

                Intent i = new Intent(Dashboard.this, ShareCabActivity.class);
                i.putExtra("key_a",address);
                i.putExtra("key_b",add_destination);
                //store data in firebase
                SourceDestination sourceDestination=new SourceDestination(address,add_destination);
                dbLocation.setValue(sourceDestination);
                startActivity(i);
            }
        });

    }
    private void showDatePickerDilog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
    }
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        date=i+"/"+(i1+1)+"/"+i2;
        edt_date.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        /*  if(id==R.id.dashboard){
            Intent intent=new Intent(Dashboard.this,Dashboard.class);
            startActivity(intent);
            return true;
        }    */
        if(id==R.id.logout){
            Intent intent=new Intent(Dashboard.this,LoginActivity.class);
            startActivity(intent);
            Dashboard.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

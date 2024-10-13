package com.example.minicab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShareCabActivity extends AppCompatActivity {

    TextView txt_a,txt_b,txt_c,txt_d;
    String userLat,userLon,value_a,value_b,mainLat,mainLon,latString,lonString,ss,dd,lat_ss,lon_ss,lat_dd,lon_dd;
    double doubleLat,doubleLon,x_source,x_source_main,y_source,y_source_main,x_destination,x_destination_main,y_destination,y_destination_main,double_lat_ss,double_lon_ss,double_lat_dd,double_lon_dd,a,b,c,d;
    private static final String TAG = "GeoCodeLocation";
    ArrayList<String> array_pickup,array_destination;
    int i,int_source_lat,int_source_lon,int_destination_lat,int_destination_lon,int_sos_lat,int_sos_lon,int_des_lat,int_des_lon;
    Button btn_next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_cab);

        txt_a=findViewById(R.id.a);
        txt_b=findViewById(R.id.b);
        txt_c=findViewById(R.id.c);
        txt_d=findViewById(R.id.d);
        btn_next=findViewById(R.id.nextButton);
        array_pickup = new ArrayList<String>();
        array_destination = new ArrayList<String>();

        Intent intent = new Intent(ShareCabActivity.this, MapsActivity.class);

        Bundle extras = getIntent().getExtras();

            value_a = extras.getString("key_a");//Surat//source
            value_b = extras.getString("key_b");//Mumbai//destination
    //Source-latitude
        userLat=getLatitudeFromAddress(value_a);
        intent.putExtra("message1", userLat); //message-1 send
        x_source_main=Double.valueOf(userLat);
        x_source=x_source_main+0.007;
        int_source_lat=(int)x_source;
    //Source-longitude
        userLon=getLongitudeFromAddress(value_a);
        intent.putExtra("message2", userLon);//message-2 send
        y_source_main=Double.valueOf(userLon);
        y_source=y_source_main+0.007;
        int_source_lon=(int)y_source;
    //destination-latitude
        userLat=getLatitudeFromAddress(value_b);
        intent.putExtra("message3", userLat);//message-3 send
        x_destination_main=Double.valueOf(userLat);
        x_destination=x_destination_main+0.007;
        int_destination_lat=(int)x_destination;
    //destination-longitude
        userLon=getLongitudeFromAddress(value_b);
        intent.putExtra("message4", userLon);//message-4 send
        y_destination_main=Double.valueOf(userLon);
        y_destination=y_destination_main+0.007;
        int_destination_lon=(int)y_destination;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("location");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ma = snapshot.child("pickup").getValue().toString();
                    array_pickup.add(ma);
                    String na = snapshot.child("destination").getValue().toString();
                    array_destination.add(na);
                }
                for(i=1;i<=2;i++){
                    // for pickup = ss
                    ss=array_pickup.get(i);
                    lat_ss=getLatitudeFromAddress(ss);
                    double_lat_ss=Double.valueOf(lat_ss);
                    int_sos_lat=(int)double_lat_ss;    //lat
                    lon_ss=getLongitudeFromAddress(ss);
                    double_lon_ss=Double.valueOf(lon_ss);
                    int_sos_lon=(int)double_lon_ss;   //lon
                    // for destination = dd
                    dd=array_destination.get(i);
                    lat_dd=getLatitudeFromAddress(dd);
                    double_lat_dd=Double.valueOf(lat_dd);
                    int_des_lat=(int)double_lat_dd;   //lat
                    lon_dd=getLongitudeFromAddress(dd);
                    double_lon_dd=Double.valueOf(lon_dd);
                    int_des_lon=(int)double_lon_dd;   //lon
                    if(int_source_lat==int_sos_lat && int_source_lon==int_sos_lon) {
                        if(int_destination_lat==int_des_lat && int_destination_lon==int_des_lon) {
                            a = x_source - double_lat_ss;
                            b = y_source - double_lon_ss;
                            c = x_destination - double_lat_dd;
                            d = y_destination - double_lon_dd;
                            if (a >= 0 && b >= 0) {
                                if (c >= 0 && d >= 0) {
                                    txt_a.setText("You are able to share cab");
                                    break;
                                }
                            }
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

     }
    public String getLatitudeFromAddress(String strAddress){
        Geocoder coder=new Geocoder(this);
        List<Address> addressList;
        try {
            addressList=coder.getFromLocationName(strAddress,1);
            if(addressList==null){
                return null;
            }
            doubleLat=addressList.get(0).getLatitude();
            latString=String.valueOf(doubleLat);
            return latString;
        }catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return null;
    }
    public String getLongitudeFromAddress(String strAddress){
        Geocoder coder=new Geocoder(this);
        List<Address> addressList;
        try {
            addressList=coder.getFromLocationName(strAddress,1);
            if(addressList==null){
                return null;
            }
            doubleLon=addressList.get(0).getLongitude();
            lonString=String.valueOf(doubleLon);
            return lonString;
        }catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return null;
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
        if(id==R.id.dashboard) {
            Intent intent = new Intent(ShareCabActivity.this, Dashboard.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.logout){
            Intent intent=new Intent(ShareCabActivity.this,LoginActivity.class);
            startActivity(intent);
            ShareCabActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
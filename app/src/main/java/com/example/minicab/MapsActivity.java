package com.example.minicab;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.minicab.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    String sourceLat,sourceLon,destinationLat,destinationLon;
    double d_lat,d_lon,s_lat,s_lon;
    Button btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_done=findViewById(R.id.btnDone);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        sourceLat = bundle.getString("message1");
        sourceLon = bundle.getString("message2");
        destinationLat = bundle.getString("message3");
        destinationLon = bundle.getString("message4");

        s_lat=Double.valueOf(sourceLat);
        s_lon=Double.valueOf(sourceLon);
        d_lat=Double.valueOf(destinationLat);
        d_lon=Double.valueOf(destinationLon);

        // Add a marker in Sydney and move the camera
        LatLng a = new LatLng(s_lat, s_lon);
        //LatLng b = new LatLng(21.212669, 72.890083);
        LatLng c = new LatLng(d_lat, d_lon);
        mMap.addMarker(new MarkerOptions().position(a).title("Marker in a"));
        //mMap.addMarker(new MarkerOptions().position(b).title("Marker in b"));
        mMap.addMarker(new MarkerOptions().position(c).title("Marker in c"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(a));

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(a)
                .add(new LatLng(s_lat, s_lon))
                .add(new LatLng(d_lat, d_lon));
        mMap.addPolyline(polylineOptions);
       /* polylineOptions.add(varachha)
                .add(new LatLng(21.171021, 72.854210))
                .add(new LatLng(21.212669, 72.890083));
        mMap.addPolyline(polylineOptions);*/
    }


}
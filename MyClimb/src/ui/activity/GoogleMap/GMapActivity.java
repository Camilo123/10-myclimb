
package ui.activity.GoogleMap;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mysport.ui.R;

public class GMapActivity extends FragmentActivity 
implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
 
	
    private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);
    private static final LatLng PERTH = new LatLng(-31.95285, 115.85734);

    private static final LatLng LHR = new LatLng(51.471547, -0.460052);
    private static final LatLng LAX = new LatLng(33.936524, -118.377686);
    private static final LatLng JFK = new LatLng(40.641051, -73.777485);
    private static final LatLng AKL = new LatLng(-37.006254, 174.783018);

    private static final int WIDTH_MAX = 50;
    private static final int HUE_MAX = 360;
    private static final int ALPHA_MAX = 255;
    private Polyline mMutablePolyline;

	
    private GoogleMap mMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);
        
    
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        
        if (savedInstanceState == null) {
            // 第一次得到该  activity.
            mapFragment.setRetainInstance(true);
        } else {
            // 当重启该Activity 时候，不需要要在重新实例化 就可以得到之前的activity
            mMap = mapFragment.getMap();
        }
        
        setUpMapIfNeeded();
    }

    
    
    
    
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        
        if (mMap != null) {
        		setUi();
            
            
            
   //         mMap.setMapType(MAP_TYPE_NORMAL);
 //           mMap.setMapType(MAP_TYPE_HYBRID);
            mMap.setMapType(MAP_TYPE_SATELLITE);
//            mMap.setMapType(MAP_TYPE_TERRAIN);
            
            
//            Location location = new Location("LongPressLocationProvider");
//            location.setLatitude(point.latitude);
//            location.setLongitude(point.longitude);
//            location.setAccuracy(100);
//            mListener.onLocationChanged(location);
//            
            
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
            
            }
    }

    private void setUi(){
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
      //  mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
        mUiSettings = mMap.getUiSettings();
    	
        // Circle centered at Sydney.  This polyline will be mutable.
        PolylineOptions options = new PolylineOptions();
        int radius = 5;
        int numPoints = 100;
        double phase = 2 * Math.PI / numPoints;
        for (int i = 0; i <= numPoints; i++) {
            options.add(new LatLng(SYDNEY.latitude + radius * Math.sin(i * phase),
                    SYDNEY.longitude + radius * Math.cos(i * phase)));
        }
     
        
        options.add(new LatLng(26,119 ));
        
        mMutablePolyline = mMap.addPolyline(options
                .color(Color.RED)
                .width(5));
    }





	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}

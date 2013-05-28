
package ui.activity.GoogleMap;

/**
 * @author DreamTeam 郑运春
 */


import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
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
 
	//代码还需优化 改动  所以略显乱


    private Polyline mMutablePolyline;
    //经纬度
    private double latitude;
    private double longitude;
    private String Name;
    private LocationClient mLocationClient;
    
    private List<LatLng> points = new ArrayList<LatLng>();
    
    
    private GoogleMap mMap;
    private UiSettings mUiSettings;

  
    private static final LocationRequest REQUEST = LocationRequest.create()
        .setInterval(5000)         // 5 seconds
        .setFastestInterval(16)    // 16ms = 60fps
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    
    
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
        
//      points.add(new LatLng(20,120));
//      points.add(new LatLng(25,125));
//      points.add(new LatLng(30,130));
//      points.add(new LatLng(35,135));

    //  drawRouteOnMap();
    }
    
//    class draw implements Runnable{
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			drawRouteOnMap();
//		
//		}
//    }
    
    public void setMarker(){
        //获取由RecDetailsActivity传过来的位置信息
		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
		{
			latitude = bundle.getDouble("lat");
			longitude = bundle.getDouble("lon");
			Name = bundle.getString("Marker");
	       	 mMap.addMarker(new MarkerOptions()
   				.position(new LatLng(longitude,latitude)).title(Name));
	       	 
	       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitude,latitude),15));   	
		}
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
            
            }
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }
    
    @Override
    public void onPause() {
      super.onPause();
      if (mLocationClient != null) {
        mLocationClient.disconnect();
      }
    }
    
    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
          mLocationClient = new LocationClient(
              getApplicationContext(),
              this,  // ConnectionCallbacks
              this); // OnConnectionFailedListener
        }
      }


    
    private void addLatLngPoint(double longitude,double latitude){
    	double lat = latitude;
    	double lon = longitude;
		
    	points.add(new LatLng(lon,lat));
    	
    }
    
 /*   //想在这里取得定位信息
    
//    private void myLocation(){
//    	
//    	LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//		Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//	
//		
//		latitude = location.getLatitude();
//		longitude = location.getLongitude();
//		
//		addLatLngPoint(latitude,longitude);	
//		
//	 
//			LocationListener locationListener = new LocationListener() {
//				
//				//当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 
//				@Override
//				public void onLocationChanged(Location location) {
//					if (location != null) {   
//						
//						latitude = location.getLatitude();
//						longitude = location.getLongitude();  
//					}
//					
//					addLatLngPoint(latitude,longitude);
//				}
//			};
//			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0,(android.location.LocationListener) locationListener);   
//			if(location != null){   
//				latitude = location.getLatitude(); //经度   
//				longitude = location.getLongitude(); //纬度
//				
//				addLatLngPoint(latitude,longitude);
//			}   
//		}
//    	
//    
////    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
////    currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////    if( currentLocation == null){
////    currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
////    }
////    
//    
//    
////    	Toast.makeText(getApplicationContext(), "222",
////			     Toast.LENGTH_SHORT).show();
////    	
////    	LocationManager locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
////    	if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
////    		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////    		if(location !=null){
////    			latitude = location.getLatitude();
////    			longitude = location.getLongitude();
////    			
////    			addLatLngPoint(latitude,longitude);
////    			
////    			String s1 = Double.toString(latitude)+Double.toString(longitude);
////    			Toast.makeText(getApplicationContext(), "默认1 + s1",
////    				     Toast.LENGTH_SHORT).show();
////    		}
////    	}else{
////
////    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8,new LocationListener(){
////
////			@Override
////			public void onLocationChanged(Location location) {
////				// TODO Auto-generated method stub
////				latitude = location.getLatitude();
////    			longitude = location.getLongitude();
////				addLatLngPoint(latitude,longitude);
////				String s2 = Double.toString(latitude)+Double.toString(longitude);
////    			Toast.makeText(getApplicationContext(), "默认2 + s2",
////    				     Toast.LENGTH_SHORT).show();
////			}
////			
////    		};
////    	}
//  
  
    */
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
        mMap.setMyLocationEnabled(true);
        mUiSettings = mMap.getUiSettings();
    }

    private void drawRouteOnMap(){
        if(points.size() >= 2)
        {
		    	PolylineOptions options = new PolylineOptions();
		//        int radius = 5;
		//        int numPoints = 100;
		//        double phase = 2 * Math.PI / numPoints;
		//        for (int i = 0; i <= numPoints; i++) {
		//            options.add(new LatLng(SYDNEY.latitude + radius * Math.sin(i * phase),
		//                    SYDNEY.longitude + radius * Math.cos(i * phase)));
		//        }
		     
		        
		//        options.add(new LatLng(26,119 ));
		        
		    for(int i=0;i<points.size();i++)
		    {
		    	options.add(points.get(i));
				String s2 = Double.toString(points.get(i).longitude)+"    "+Double.toString(points.get(i).latitude);
				Toast.makeText(getApplicationContext(), "坐标 "+ i+":  " + s2,
		 		     Toast.LENGTH_SHORT).show();
		    	
		    }
		    	
		    	
		      //  options.addAll(points);
		        
		        mMutablePolyline = mMap.addPolyline(options
		                .color(Color.RED)
		                .width(3));
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
		BigDecimal   b1   =   new   BigDecimal(latitude);  
		BigDecimal   b2   =   new   BigDecimal(longitude);
		
		double   f1   =   b1.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		double   f2   =   b2.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
		
		addLatLngPoint(f1,f2);
		
//		String s2 = Double.toString(longitude)+"    "+Double.toString(latitude);
//		Toast.makeText(getApplicationContext(), "坐标：    " + s2,
// 		     Toast.LENGTH_SHORT).show();
		
		drawRouteOnMap();
		
//		String s1 = Integer.toString(points.size());
//		Toast.makeText(getApplicationContext(), "长度:" + s1,
//	 		     Toast.LENGTH_SHORT).show();
		
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
	}
	
	  @Override
	  public void onConnected(Bundle connectionHint) {
	    mLocationClient.requestLocationUpdates(
	        REQUEST,
	        this);  // LocationListener
	  }

 
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}
}

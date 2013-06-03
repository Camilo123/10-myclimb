
package ui.activity.GoogleMap;

/**
 * @author DreamTeam 郑运春
 */


import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
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

import domain.businessEntity.gps.LatLngData;
import domain.businessService.gps.ILatLngDataService;
import domain.businessService.gps.LatLngDataService;

public class GMapActivity extends FragmentActivity 
implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
 


    private Polyline mMutablePolyline;
    //经纬度
    private double latitude;
    private double longitude;
    private String strTime;
    private LocationClient mLocationClient;
    
    //标示历史记录轨迹 和 当前轨迹
    private final static int  RECROULT=1;
    private final static int  NOWROUTE = 2;
    
    private ILatLngDataService latLngDataService = null;
    
    private List<LatLng> points = new ArrayList<LatLng>();
    
    private List<LatLngData> dataList = new ArrayList<LatLngData>();
    
    
    private boolean status;//监听第一个界面广播过来的开始或结束的状态 true为开始，false为停止
    private GoogleMap mMap;
    private UiSettings mUiSettings;

  
    private static final LocationRequest REQUEST = LocationRequest.create()
        .setInterval(3000)         // 60 seconds
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
        latLngDataService = new LatLngDataService();
        setUpMapIfNeeded();
        
        setRecRoute();

    }

    public void setRecRoute(){
        //获取由RecDetailsActivity传过来的位置信息
		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
		{
//			latitude = bundle.getDouble("lat");
//			longitude = bundle.getDouble("lon");
//			Name = bundle.getString("Marker");
//			mMap.addMarker(new MarkerOptions()
//   				.position(new LatLng(longitude,latitude)).title(Name));
//	       	 
//	       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitude,latitude),15));   	
			strTime = bundle.getString("time");
			dataList = latLngDataService.getLatLngDataByTime(strTime);
			drawRouteOnMap(RECROULT);
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
        status = StatusReceiver.getStatus();
        
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


    //添加经纬度点
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

    private void drawRouteOnMap(int mode){
        if(points.size() >= 2)
        {
		    if(mode == NOWROUTE){	
	        	PolylineOptions options = new PolylineOptions();
	
	  	    	
	//		    for(int i=0;i<points.size();i++)
	//		    {
	//		    	options.add(points.get(i));
	//				String s2 = Double.toString(points.get(i).longitude)+"    "+Double.toString(points.get(i).latitude);
	//				Toast.makeText(getApplicationContext(), "坐标 "+ i+":  " + s2,
	//		 		     Toast.LENGTH_SHORT).show();
	//		    	
	//		    }
			    			    
			       options.addAll(points);
			        
			        mMutablePolyline = mMap.addPolyline(options
			                .color(Color.RED)
			                .width(2));
		    }
		    if(mode == RECROULT){
		    	Iterator<LatLngData> it = dataList.iterator();
		    	PolylineOptions RecOptions = new PolylineOptions();
		    	
		    	while(it.hasNext()){
		    		LatLngData data = it.next();
		    		RecOptions.add(new LatLng(data.getLat(),data.getLng()));
		    	}
		        mMutablePolyline = mMap.addPolyline(RecOptions
		                .color(Color.GREEN)
		                .width(2));
		    }
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if(status){
		
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				
				addLatLngPoint(latitude,longitude);
				
				//划线
				drawRouteOnMap(NOWROUTE);
				
				//将记录下的点存入数据库
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				String time = sf.format(new java.util.Date());
				
				LatLngData data = new LatLngData();
				
				data.setLat(latitude);
				data.setLng(longitude);
				data.setStartTime(time);
			
	//			Toast.makeText(getApplicationContext(), data.toString(),Toast.LENGTH_LONG).show();
				
				latLngDataService.addLatLngData(data);

		}
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub0
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

package ui.activity.map;

/**
 * @author DreamTeam 郑运春
 */

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.mysport.ui.*;



public class basemap extends FragmentActivity implements
	LocationSource, AMapLocationListener {
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basemap);
		init();
	}

	
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}

	}
	
	
	private void setUpMap() {
		mAMapLocationManager = LocationManagerProxy
				.getInstance(basemap.this);
		aMap.setLocationSource(this);
		aMap.setMyLocationEnabled(true);

	}

	
	@Override
	protected void onPause() {
		super.onPause();
		deactivate();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(android.R.menu.class, menu);
		return true;
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLocationChanged(AMapLocation aLocation) {// 定位成功后回调函数
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);
		}
	}


	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 10, 5000, this);
	}


	@Override
	public void deactivate() {//停止定位
		// TODO Auto-generated method stub
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	

}




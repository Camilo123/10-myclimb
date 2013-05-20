package ui.activity.map;

/**
 * DreamTeam 郑运春
 */

import java.util.ArrayList;
import com.mysport.ui.*;
import com.mysport.ui.R;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;

import ui.viewModel.map.MyDefinedMenu;



public class basemap extends FragmentActivity implements
LocationSource, AMapLocationListener ,OnItemSelectedListener {
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private UiSettings mUiSettings;
	private List<String> titles;	     //标题栏
	private List<List<String>> item_names;	//选项名称
	private List<List<Integer>> item_images;	//选项图标
	private MyDefinedMenu myMenu;	//弹出菜单

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basemap);
			
		init();

        //弹出菜单标题栏
        titles = addItems(new String[]{"菜单"});
        //选项图标
        item_images = new ArrayList<List<Integer>>();
        item_images.add(addItems(new Integer[]{R.drawable.normalmap,
        	R.drawable.earth,R.drawable.tmp, R.drawable.set}));
        
          //选项名称
        item_names = new ArrayList<List<String>>();
        item_names.add(addItems(new String[]{"平面地图", "卫星地图", "实时路况", "设置"}));

        //创建弹出菜单对象
		myMenu = new MyDefinedMenu(this, titles, item_names, 
				item_images, new ItemClickEvent1());
		
	}
	
	//菜单选项点击事件
	public class ItemClickEvent1 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if(arg2 == 0)
				aMap.setMapType(AMap.MAP_TYPE_NORMAL);
			if(arg2 == 1)
				aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
			
			
			myMenu.dismiss();	//菜单消失
			myMenu.currentState = 1;	//标记状态，已消失
			Log.e("MenuState:", "dismissing");
		}
		
	}
	
	

   // 转换为List<String>
    private List<String> addItems(String[] values) {
    	
    	List<String> list = new ArrayList<String>();
    	for (String var : values) {
			list.add(var);
		}
    	
    	return list;
    }
 
   //转换为List<Integer>
    private List<Integer> addItems(Integer[] values) {
    	
    	List<Integer> list = new ArrayList<Integer>();
    	for (Integer var : values) {
			list.add(var);
		}
    	
    	return list;
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		
		if(0 == myMenu.currentState && myMenu.isShowing()) {	
			Log.e("isShowing:", "" + myMenu.isShowing());
			myMenu.dismiss();			//对话框消失
			myMenu.currentState = 1;	//标记状态，已消失
			Log.e("MenuState:", "dismissing");
		} else {
			myMenu.showAtLocation(findViewById(R.id.map), Gravity.BOTTOM, 0,0);
			Log.e("isShowing:", "" + myMenu.isShowing()); 
			myMenu.currentState = 0;	//标记状态，显示中
			Log.e("MenuState:", "showing");
		}
		
		return false;	// true--显示系统自带菜单；false--不显示。
	}
	
	@Override
	public void closeOptionsMenu() {
		// TODO Auto-generated method stub
		super.closeOptionsMenu();
		Log.e("MenuState:", "closeOptionsMenu");
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onOptionsMenuClosed(menu);
		Log.e("MenuState:", "onOptionsMenuClosed");
	}





	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("Activity:", "onPause");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e("Activity:", "onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("Activity:", "onResume");
	}
	

	 //初始化AMap对象
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				mUiSettings = aMap.getUiSettings();
				setUpMap();
			}
		}
	}
	
	

	private void setUpMap() {
		mAMapLocationManager = LocationManagerProxy
				.getInstance(basemap.this);
		aMap.setLocationSource(this);
		mUiSettings.setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		
		//设置比例尺可见
		mUiSettings.setScaleControlsEnabled(true);
		
		 // 设置地图默认的缩放按钮是否显示
		mUiSettings.setZoomControlsEnabled(true);

		 //设置地图默认的指南针是否显示
		mUiSettings.setCompassEnabled(true);

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
  
	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		// 网络定位
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 10, 5000, this);
	}

	
	// 停止定位
	 
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}
 




	/**
	 * 对选择是否显示交通状况事件的响应
	 */
	public void onTrafficToggled(View view) {
		if (AMapUtil.checkReady(this, aMap)) {
			aMap.setTrafficEnabled(((CheckBox) view).isChecked());
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (AMapUtil.checkReady(this, aMap)) {
//			setLayer((String) parent.getItemAtPosition(position));
		}

	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}
 
}

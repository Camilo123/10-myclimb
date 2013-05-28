package ui.activity.gps;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysport.ui.R;

import domain.businessEntity.gps.ClimbData;
import domain.businessService.gps.ClimbDataService;
import domain.businessService.gps.IClimbDataService;

import tool.data.ClimbDataUtil;
import ui.activity.ActivityOfAF4Ad;
import ui.activity.GoogleMap.GMapActivity;
import ui.activity.GoogleMap.GoogleMapActivity;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;
import ui.viewModel.gps.RecDetailViewModel;

public class RecDetailsActivity extends ActivityOfAF4Ad {
	private IClimbDataService dateService;
	// 记录名
	private TextView tv_Name = null;
	// 当前日期
	private TextView tv_Date = null;

	// 开始时间
	private TextView tv_startTime = null;

	// 结束时间
	private TextView tv_stopTime = null;

	// 平均海拔
	private TextView tv_altitudeDiff = null;

	// 经度
	private TextView tv_lat = null;

	// 纬度
	private TextView tv_lon = null;

	// 返回上一个界面
	private ImageView iv_back = null;

	private ImageView iv_delete = null;
	
	private ImageView iv_location;
	private int id;
	//经纬度全局变量方便传值
	private double lat;
	private double lon;
	private String Name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rec_details, menu);
		return true;
	}

	@Override
	protected void initControlsAndRegEvent() {
		tv_Date = (TextView) findViewById(R.id.tv_Date);
		tv_startTime = (TextView) findViewById(R.id.tv_startTime);
		tv_stopTime = (TextView) findViewById(R.id.tv_stopTime);
		tv_altitudeDiff = (TextView) findViewById(R.id.tv_altitudeDiff);
		tv_lat = (TextView) findViewById(R.id.tv_lat);
		tv_lon = (TextView) findViewById(R.id.tv_lon);
		tv_Name = (TextView) findViewById(R.id.tv_recName);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);
		iv_location = (ImageView) findViewById(R.id.iv_loc);
		iv_back.setOnClickListener(new BackToRecord());
		
		dateService = new ClimbDataService();

		
		Intent intent = getIntent();
		ClimbData climbdata;
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			id = bundle.getInt("id");
			climbdata = dateService.getClimbDataById(id);

		} else {
			throw new RuntimeException("查看信息出错");
		}

		if (climbdata != null) {
			Name = climbdata.getClimbName();
			tv_Name.setText(Name);
			Double longitude = climbdata.getLongitude();
			Double latitude = climbdata.getLatitude();
			lat = longitude;
			lon = latitude;
			tv_lat.setText(longitude.toString());

			tv_lon.setText(latitude.toString());
			Date startTime = climbdata.getStartTime();
			Date stopTime = climbdata.getStopTime();
			tv_Date.setText(DateFormat.getDateInstance().format(startTime));
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

			String date1 = sdf.format(startTime);
			String date2 = sdf.format(stopTime);

			tv_startTime.setText(date1);
			tv_stopTime.setText(date2);
			int startAltitude = climbdata.getStartAltitude();
			int stopAltitude = climbdata.getStopAltitude();
			int altitudeDiff = stopAltitude - startAltitude;
			tv_altitudeDiff.setText(altitudeDiff + "");

		}
		//设置删除键监听事件
		iv_delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dateService.deleteCilmbDataByID(id);
				Intent intent = new Intent(RecDetailsActivity.this,RecordActivity.class);
				startActivity(intent);
				
			}
		});
		//设置定位键监听事件
		iv_location.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecDetailsActivity.this,GMapActivity.class);
				Bundle bundle = new Bundle();
				bundle.putDouble("lat", lat);
				bundle.putDouble("lon", lon);
				bundle.putString("Marker",Name);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	class BackToRecord implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(RecDetailsActivity.this, RecordActivity.class);
			RecDetailsActivity.this.startActivity(intent);
			finish();
		}

	}
	
	

	@Override
	protected ViewModel initModel() {

		return null;

	}

	@Override
	protected void upDateView(ViewModel aVM) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processViewModelErrorMsg(List<ModelErrorInfo> errsOfVM,
			String errMsg) {
		// TODO Auto-generated method stub

	}

}

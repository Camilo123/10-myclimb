package ui.activity.gps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mysport.ui.R;

/**
 * @author DreamTeam 沈志鹏
 */
public class GpsObtainActivity extends ActivityOfAF4Ad {
	private TextView tv_altitude;//高度显示控件
	private TextView tv_direction;//方向显示控件
	private TextView tv_speed;//速度显示控件
	private TextView tv_longitude;//经度显示控件
	private TextView tv_latitude;//纬度显示控件
	private Button bt_startAndStop;//开始结束按钮
	private ImageView iv_record;//跳转到记录界面图标
	private Builder builder;
	private EditText editor;//对话框文本输入控件
	private LocationManager locManager;//定义LocationManager对象
	private String cliName;//行程名称
	private Chronometer timer;//定义计时器
	private Date startTime;//记录开始时间
	private Date stopTime;//记录结束时间
	private int startAltitude;//开始海拔
	private int stopAltitude;//结束海拔
	private SimpleDateFormat sDateFormat;
	boolean flag = false;// 设置开始结束按钮标志
	private int currentAltitude;//获取当前高度
	private SensorManager mSensorManager;//定义SensorManager对象
	private double stopLon;//结束时经度
	private double stopLat;//结束时纬度
	private double currentLon;//当前经度
	private double currentLat;//当前纬度
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_obtain_gps, menu);
		return true;
	}

	@Override
	protected void initControlsAndRegEvent() {
		// 获取相应控件id
		bt_startAndStop = (Button) findViewById(R.id.bt_startAndStop);
		tv_altitude = (TextView) findViewById(R.id.tv_altitude);
		tv_direction = (TextView) findViewById(R.id.tv_direction);
		tv_speed = (TextView) findViewById(R.id.tv_speed);
		tv_longitude = (TextView) findViewById(R.id.tv_longitude);
		tv_latitude = (TextView) findViewById(R.id.tv_latitude);
		iv_record = (ImageView) findViewById(R.id.iv_record);
		timer = (Chronometer) findViewById(R.id.timer);
		// 构建对话框输入控件对象
		editor = new EditText(this);
		builder = new AlertDialog.Builder(this);

		// 设置计时器显示格式
		timer.setFormat("%s");
		
		// 设置获取时间格式测试用
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if (flag == true) {
			bt_startAndStop.setText("Stop");
		} else
			bt_startAndStop.setText("Start");

		// 启动GPS
		startGps();

		// 获取方向传感器服务
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// 开始和停止按钮监听事件
		bt_startAndStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag == false) {
					builder.setTitle("请输入行程名称");
					builder.setView(editor);
					builder.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							initControlsAndRegEvent();
						}
					});
					// 设置对话框
					builder.setPositiveButton("确认", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 获取输入
							cliName = editor.getText().toString();
							// 计时器重置
							timer.setBase(SystemClock.elapsedRealtime());
							// 开始计时
							timer.start();
							// 获取开始时间
							startTime = new java.util.Date();
							// 记录开始高度值
							startAltitude = currentAltitude;
							flag = true;
							initControlsAndRegEvent();
						}
					});
					builder.create().show();
				} else {
					flag = false;
					timer.stop();
					// 记录结束是时间
					stopTime = new java.util.Date();
					// 记录结束时高度
					stopAltitude = currentAltitude;
					//记录结束是经度
					stopLon = currentLon;
					//记录结束是纬度
					stopLat = currentLat;
					initControlsAndRegEvent();
				}
			}

		});
		iv_record.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 //测试用
//				 String trans;
//				 trans = sDateFormat.format(stopTime);
//				 Toast toast = Toast.makeText(GpsObtainActivity.this,
//				 trans, Toast.LENGTH_LONG);
//				 toast.show();
				toRecActivity();
			}
		});
	}

	// 跳转到记录界面
	public void toRecActivity() {
		toActivity(this, RecordActivity.class);
	}

	// 开启GPS功能
	public void startGps() {
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		updateGpsView(location);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,
				8, new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {

					}

					@Override
					public void onProviderEnabled(String provider) {
						updateGpsView(locManager.getLastKnownLocation(provider));

					}

					@Override
					public void onProviderDisabled(String provider) {
						updateGpsView(null);

					}

					@Override
					public void onLocationChanged(Location location) {
						updateGpsView(location);

					}
				});
	}

	// 动态更新GPS数据
	public void updateGpsView(Location newLocation) {
		int altitude;
		float speed;
		double lat;
		double lon;
		if (newLocation != null) {
			currentAltitude = altitude = (int) newLocation.getAltitude();
			speed = newLocation.getSpeed();
			currentLat = lat = newLocation.getLatitude();
			currentLon = lon = newLocation.getLongitude();
			tv_altitude.setText(Integer.toString(altitude));
			tv_speed.setText(Float.toString(speed));
			tv_latitude.setText(Double.toString(lat));
			tv_longitude.setText(Double.toString(lon));
		} else {
			tv_altitude.setText("0");
			tv_speed.setText("0");
			tv_latitude.setText("0");
			tv_longitude.setText("0");
		}
	}

	// 设置方向传感器监听类
	SensorEventListener mSersorEventListener = new SensorEventListener() {
		// 传感器值改变
		@Override
		public void onSensorChanged(SensorEvent event) {
			float[] values = event.values;
			if (values[0] >= 0 && values[0] < 30)
				tv_direction.setText("北");
			if (values[0] >= 30 && values[0] < 60)
				tv_direction.setText("东北");
			if (values[0] >= 60 && values[0] < 120)
				tv_direction.setText("东");
			if (values[0] >= 120 && values[0] < 150)
				tv_direction.setText("东南");
			if (values[0] >= 150 && values[0] < 210)
				tv_direction.setText("南");
			if (values[0] >= 210 && values[0] < 240)
				tv_direction.setText("西南");
			if (values[0] >= 240 && values[0] < 300)
				tv_direction.setText("西");
			if (values[0] >= 300 && values[0] < 330)
				tv_direction.setText("西北");
			if (values[0] >= 300 && values[0] <= 360)
				tv_direction.setText("北");

		}

		// 传感器精度改变
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		// 方向传感器注册监听器
		mSensorManager.registerListener(mSersorEventListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		// 程序停止时注销监听器
		mSensorManager.unregisterListener(mSersorEventListener);
		super.onStop();
	}

	@Override
	protected void onPause() {
		// 程序暂停时注销监听器
		mSensorManager.unregisterListener(mSersorEventListener);
		super.onStop();
	}

	@Override
	protected ViewModel initModel() {
		// TODO Auto-generated method stub
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

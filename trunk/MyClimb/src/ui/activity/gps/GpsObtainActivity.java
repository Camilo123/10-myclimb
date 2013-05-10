package ui.activity.gps;

import java.text.SimpleDateFormat;
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
	private TextView tv_altitude;
	private TextView tv_direction;
	private TextView tv_speed;
	private TextView tv_longitude;
	private TextView tv_latitude;
	private TextView tv_time;
	private ImageView iv_startAndStop;
	private Button bt_startAndStop;
	private ImageView iv_record;
	private Builder builder;
	private EditText editor;
	private LocationManager locManager;
	private String cliName;
	private Chronometer timer;
	private String startTime;
	private String stopTime;
	private double startAltitude;
	private double stopAltitude;
	private SimpleDateFormat sDateFormat;
	boolean flag = false;
	private double currentAltitude;

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
		iv_startAndStop = (ImageView) findViewById(R.id.iv_startAndStop);
		bt_startAndStop = (Button) findViewById(R.id.bt_startAndStop);
		tv_altitude = (TextView) findViewById(R.id.tv_altitude);
		tv_direction = (TextView) findViewById(R.id.tv_direction);
		tv_speed = (TextView) findViewById(R.id.tv_speed);
		tv_longitude = (TextView) findViewById(R.id.tv_longitude);
		tv_latitude = (TextView) findViewById(R.id.tv_latitude);
		iv_record = (ImageView) findViewById(R.id.iv_record);
		timer = (Chronometer) findViewById(R.id.timer);
		// 定义对话框输入控件
		editor = new EditText(this);
		builder = new AlertDialog.Builder(this);

		// 设置计时器显示格式
		timer.setFormat("%s");
		// 设置获取时间格式
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if (flag == true) {
			bt_startAndStop.setText("Stop");
		} else
			bt_startAndStop.setText("Start");

		// 启动GPS
		startGps();
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
							dialog.dismiss();
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
							startTime = sDateFormat
									.format(new java.util.Date());
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
					stopTime = sDateFormat.format(new java.util.Date());
					//记录结束时高度
					stopAltitude = currentAltitude;
					initControlsAndRegEvent();
				}
			}

		});
		iv_record.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(GpsObtainActivity.this, Double.toString(startAltitude),
						Toast.LENGTH_LONG);
				toast.show();
				// Intent intent = new Intent(GpsObtainActivity.this,
				// RecordActivity.class);
				// startActivity(intent);
			}
		});
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
		int speed;
		double lat;
		double lon;
		int dir;
		if (newLocation != null) {
			currentAltitude = altitude = (int)newLocation.getAltitude();
			speed = (int) newLocation.getSpeed();
			lat = newLocation.getLatitude();
			lon = newLocation.getLongitude();
			dir = (int) newLocation.getBearing();
			tv_altitude.setText(Integer.toString(altitude));
			tv_speed.setText(Integer.toString(speed));
			tv_latitude.setText(Double.toString(lat));
			tv_longitude.setText(Double.toString(lon));
			tv_direction.setText(Integer.toString(dir));
		} else {
			tv_altitude.setText("0");
			tv_speed.setText("0");
			tv_latitude.setText("0");
			tv_longitude.setText("0");
			tv_direction.setText("");
		}
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

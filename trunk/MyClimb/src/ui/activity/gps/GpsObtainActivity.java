package ui.activity.gps;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysport.ui.R;

import domain.businessService.systemManagement.SystemManagementService;

import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;
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
	private ImageView iv_record;
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
		//获取相应控件id
		iv_startAndStop = (ImageView) findViewById(R.id.iv_startAndStop);
		tv_altitude = (TextView) findViewById(R.id.tv_altitude);
		tv_direction = (TextView) findViewById(R.id.tv_direction);
		tv_speed = (TextView) findViewById(R.id.tv_speed);
		tv_longitude = (TextView) findViewById(R.id.tv_longitude);
		tv_latitude = (TextView) findViewById(R.id.tv_latitude);
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_startAndStop = (ImageView) findViewById(R.id.iv_startAndStop);
		iv_record = (ImageView) findViewById(R.id.iv_record);
		
		iv_startAndStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(GpsObtainActivity.this,RecDetailsActivity.class);
				startActivity(intent);
			}
		});
		
		iv_record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(GpsObtainActivity.this,RecordActivity.class);
				startActivity(intent);
				
			}
		});

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

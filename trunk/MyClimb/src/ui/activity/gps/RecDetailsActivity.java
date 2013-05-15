package ui.activity.gps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysport.ui.R;

import domain.businessEntity.gps.ClimbData;

import tool.data.ClimbDataUtil;
import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;
import ui.viewModel.gps.RecDetailViewModel;

public class RecDetailsActivity extends ActivityOfAF4Ad {
	//当前日期
		private TextView tv_Date=null;
		
		//开始时间
		private TextView tv_startTime=null;
		
		//结束时间
		private TextView tv_stopTime=null;
		
		//平均海拔
		private TextView tv_altitudeDiff=null;
		
		//平均速度
		private TextView tv_avgSpeed=null;
		
		//经度
		private TextView tv_lat=null;
		
		//纬度
		private TextView tv_lon=null;
		
		//返回上一个界面
		private  ImageView iv_back=null;
		
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
		tv_Date=(TextView)findViewById(R.id.tv_Date);
		tv_startTime=(TextView)findViewById(R.id.tv_startTime);
		tv_stopTime=(TextView)findViewById(R.id.tv_stopTime);
		tv_altitudeDiff=(TextView)findViewById(R.id.tv_altitudeDiff);
		tv_avgSpeed=(TextView)findViewById(R.id.tv_avgSpeed);
		tv_lat=(TextView)findViewById(R.id.tv_lat);
		tv_lon=(TextView)findViewById(R.id.tv_lon);
		iv_back=(ImageView)findViewById(R.id.bt_startAndStop);
		iv_back.setOnClickListener(new BackToRecord());
		

	}
	
	class BackToRecord implements OnClickListener{
		 
		 
		 @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 Intent intent=new Intent();
			 intent.setClass(RecDetailsActivity.this, RecordActivity.class);
			 RecDetailsActivity.this.startActivity(intent);
		}
		 
	 }

	@Override
	protected ViewModel initModel() {
		RecDetailViewModel viewModel = new RecDetailViewModel();
		Intent intent = getIntent();
		if(intent != null){
			Bundle bundle = intent.getExtras();
			ClimbData climbdata = ClimbDataUtil.readCardFromBundle(bundle);
			
			viewModel.setClimbdata(climbdata);
		} else {
			throw new RuntimeException("查看信息出错");
		}
		return viewModel;
	}

	@Override
	protected void upDateView(ViewModel aVM) {
		// TODO Auto-generated method stub
		RecDetailViewModel viewModel = (RecDetailViewModel)aVM;
		 ClimbData climbdata = viewModel.getClimbdata();
		
		
		
		int  startAltitude = climbdata.getStartAltitude();
		int stopAltitude = climbdata.getStopAltitude();
		Date startTime = climbdata.getStartTime();
		Date stopTime = climbdata.getStopTime();
		Double longitude = climbdata.getLongitude();
		Double latitude = climbdata.getLatitude();
		
		int altitudeDiff=stopAltitude-startAltitude;
		
	
		 SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		  String now=dateformat1.format(new Date());
	
				
//		DateFormat?d1?=?DateFormat.getDateInstance();
//		String?str1?=?d1.format(now)
//		

		tv_Date.setText(now.toString());
		tv_startTime.setText(startTime.toString());
		tv_stopTime.setText(stopTime.toString());
		tv_altitudeDiff.setText(altitudeDiff);
		
		tv_lat.setText(longitude.toString());
		
		tv_lon.setText(latitude.toString());
		
		
	}

	@Override
	protected void processViewModelErrorMsg(List<ModelErrorInfo> errsOfVM,
			String errMsg) {
		// TODO Auto-generated method stub

	}

}

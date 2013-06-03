package ui.activity.weather;

import java.util.List;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.GestureDetector.OnGestureListener;
import android.widget.LinearLayout;

import com.mysport.ui.R;

import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;

public class WeatherActivity extends ActivityOfAF4Ad {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_weather, menu);
		return true;
	}
	@Override
	protected void initControlsAndRegEvent() {
		// TODO Auto-generated method stub
		
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

package ui.activity.gps;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.mysport.ui.R;

import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;

/**
 * @uml.annotations 
 *    uml_usage="mmi:///#jsrctype^name=BackToRecord[jsrctype^name=RecDetailsActivity[jcu^name=RecDetailsActivity.java[jpack^name=ui.activity.gps[jsrcroot^srcfolder=src[project^id=MyClimb]]]]]$uml.Class"
 */
public class RecordActivity extends ActivityOfAF4Ad {
	private Button toDetails;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_record, menu);
		return true;
	}
	
	@Override
	protected void initControlsAndRegEvent() {
		toDetails = (Button) findViewById(R.id.button1);
		toDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecordActivity.this,RecDetailsActivity.class);
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

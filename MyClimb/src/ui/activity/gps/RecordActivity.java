package ui.activity.gps;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mysport.ui.R;

import domain.businessEntity.gps.ClimbData;
import domain.businessService.gps.*;

import tool.data.ClimbDataUtil;
import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;

/**
 * @uml.annotations 
 *    uml_usage="mmi:///#jsrctype^name=BackToRecord[jsrctype^name=RecDetailsActivity[jcu^name=RecDetailsActivity.java[jpack^name=ui.activity.gps[jsrcroot^srcfolder=src[project^id=MyClimb]]]]]$uml.Class"
 * @author DreamTeam 郑宇 
 */
public class RecordActivity extends ActivityOfAF4Ad {
	private IClimbDataService dateService;
	
	private ListView recList;
	private TextView tv_id;
	List<Map<String,String>> data;
	
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
		
		
		
		dateService=new ClimbDataService();
		data=convertDateToMap(dateService.getClimbData());
		recList=(ListView)findViewById(R.id.recList);
		recList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tv_id=(TextView)findViewById(R.id.recId3);
				Intent intent=new Intent(RecordActivity.this, RecDetailsActivity.class);
				Bundle bundle=new Bundle();
				ClimbData climbData=dateService.getClimbDataById(Integer.parseInt(tv_id.getText().toString()));
				bundle=ClimbDataUtil.writeCardtoBundle(climbData);
				intent.putExtras(bundle);
				startActivity(intent);
				//toActivity();
				
				
			}
		});
		
		
		SimpleAdapter adapter = new SimpleAdapter(this, data, 
				R.layout.activity_reclist2, new String[] {"id","name","date"}, new int[]{R.id.recId3,R.id.recName3,R.id.recDate3});
		recList.setAdapter(adapter);
		
		
		
		
		
		
		
		
		
		
		
		

	}

	private List<Map<String, String>> convertDateToMap(List<ClimbData> climbData) {
		// TODO Auto-generated method stub
		List<Map<String,String>> mapList=new ArrayList<Map<String, String>>();;
		if(climbData!=null)
		{
			int len=climbData.size(),i=0;
			for(;i<len;i++)
			{
				Map<String,String> map=new HashMap<String, String>();
				map.put("id", climbData.get(i).getClimbID()+"");
				map.put("name", climbData.get(i).getClimbName());
				Date date=climbData.get(i).getStartTime();
				map.put("date", DateFormat.getDateInstance().format(date));
				mapList.add(map);
			}
		}
		return mapList;
		
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

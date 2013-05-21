package ui.activity.gps;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.activity.ActivityOfAF4Ad;
import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mysport.ui.R;

import domain.businessEntity.gps.ClimbData;
import domain.businessService.gps.ClimbDataService;
import domain.businessService.gps.IClimbDataService;

/**
 * @uml.annotations 
 *    uml_usage="mmi:///#jsrctype^name=BackToRecord[jsrctype^name=RecDetailsActivity[jcu^name=RecDetailsActivity.java[jpack^name=ui.activity.gps[jsrcroot^srcfolder=src[project^id=MyClimb]]]]]$uml.Class"
 * @author DreamTeam 郑宇 
 */
public class RecordActivity extends ActivityOfAF4Ad {
	private IClimbDataService dateService;
	
	private ListView recList;
	private TextView tv_id;
	private TextView tv_name;
	List<Map<String,String>> data;
	List<ClimbData> list;
	Date date=new Date();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		dateService=new ClimbDataService();
		list=dateService.getClimbData();
		data=convertDateToMap(list);
		recList=(ListView)findViewById(R.id.recList);
		recList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(RecordActivity.this, RecDetailsActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("id", list.get(arg2).getClimbID());
				Toast toast = Toast.makeText(RecordActivity.this, list.get(arg2).getClimbID()+"", Toast.LENGTH_SHORT);
				toast.show();
				intent.putExtras(bundle);
				startActivity(intent);
				
				finish();
				
				
				
			}
		});
		
		
		SimpleAdapter adapter = new SimpleAdapter(this, data, 
				R.layout.activity_reclist2, new String[] {"id","name","date"}, new int[]{R.id.recId3,R.id.recName3,R.id.recDate3});
		recList.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_record, menu);
		return true;
	}
	
	@Override
	protected void initControlsAndRegEvent() {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

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
				map.put("id",climbData.get(i).getClimbID()+"");
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

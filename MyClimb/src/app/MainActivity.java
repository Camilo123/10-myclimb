package app;



import com.mysport.ui.R;

import android.app.TabActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
/**
 * 
 * @author 福建师范大学软件学院  倪友聪、赵康
 *
 */
public class MainActivity extends TabActivity {
	public static final String TAB_GPS = "tabGps";
	public static final String TAB_HISTORY = "tabHistory";
	public static final String TAB_SPORTZONE = "tabSportzone";
	public static final String TAB_MORE = "tabMore";

	private TabHost tabHost;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.activity_tab);

		this.tabHost = getTabHost();
		this.tabHost.addTab(this.tabHost.newTabSpec(TAB_GPS)//添加一个标识为TAB_HOME的Tab分页	
				.setIndicator(TAB_GPS)//设定分页显示的标题
				.setContent(new Intent(this, ui.activity.gps.GpsObtainActivity.class)));
		//设定分页所关联的界面
		
		this.setDefaultTab(0);

		this.tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub

			}
		});

		View tab1 = findViewById(R.id.tab1);
		tab1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				tabHost.setCurrentTabByTag(TAB_GPS);
			}
		});

		View tab2 = findViewById(R.id.tab2);
		tab2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tabHost.setCurrentTabByTag(TAB_HISTORY);

			}
		});
		View tab3 = findViewById(R.id.tab3);
		tab3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				tabHost.setCurrentTabByTag(TAB_SPORTZONE);
			}
		});
		View tab4 = findViewById(R.id.tab4);
		tab4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				tabHost.setCurrentTabByTag(TAB_MORE);
			}
		});

	}

}

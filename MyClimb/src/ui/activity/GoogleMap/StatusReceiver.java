package ui.activity.GoogleMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StatusReceiver extends BroadcastReceiver {
	private static boolean status;
	@Override
	public void onReceive(Context context, Intent intent) {
		status = intent.getExtras().getBoolean("status");
		

	}
	public static boolean getStatus(){
		return status;
	}

}

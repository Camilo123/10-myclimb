
package ui.activity.map;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author DreamTeam 郑运春
 *
 */

public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}
}

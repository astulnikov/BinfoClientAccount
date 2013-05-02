package by.binfo.BusinessInform;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conn.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.d(Utils.LOG_TAG, "Downloader: Do something: Network connected!");
			Utils.isInternet = true;
		} else {
			Log.d(Utils.LOG_TAG, "Downloader: Do something: Not connected!");
			Utils.isInternet = false;
			if(Utils.downloading){
				Utils.onError();
			}
		}
	}
}
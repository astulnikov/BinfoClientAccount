package by.binfo.BusinessInform;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service {
	public final static int STATUS_FINISH = 200;
	public final static String PARAM_PINTENT = "pendingIntent";
	public final static String PARAM_RESULT = "result";
	public final static String PARAM_XMLURL = "xmlurl";
	
	private XMLDownloader firstXmlDownloader;
	private PendingIntent pendingIntent;
	private Thread tr;
	private String xmlUrl;
	private SharedPreferences binfoSP;
	private int mode = Activity.MODE_PRIVATE;

	public void onCreate() {
		super.onCreate();
		Log.d(Utils.LOG_TAG, "MyService onCreate");
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(Utils.LOG_TAG, "MyService onStartCommand");
		xmlUrl = intent.getStringExtra(PARAM_XMLURL);
	    pendingIntent = intent.getParcelableExtra(PARAM_PINTENT);
	    firstXmlDownloader = new XMLDownloader(this);
		tr = new Thread(new MyRun());
		tr.start();
		return START_NOT_STICKY;
	}

	public IBinder onBind(Intent intent) {
		Log.d(Utils.LOG_TAG, "MyService onBind");
		return new Binder();
	}

	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.d(Utils.LOG_TAG, "MyService onRebind");
	}

	public boolean onUnbind(Intent intent) {
		Log.d(Utils.LOG_TAG, "MyService onUnbind");
		return super.onUnbind(intent);
	}

	public void onDestroy() {
		super.onDestroy();
		Log.d(Utils.LOG_TAG, "MyService onDestroy");
	}
	
	class MyRun implements Runnable {

		public void run() {
			if (firstXmlDownloader.getXMLStream(xmlUrl)) {
				Intent intent = new Intent();
		        try {
		        	binfoSP = getSharedPreferences("binfoPref", mode);
		        	SharedPreferences.Editor editor = binfoSP.edit();
					editor.putBoolean(Utils.tabName + "FirstRun", true);
					editor.commit();
					pendingIntent.send(DownloadService.this, STATUS_FINISH, intent);
				} catch (CanceledException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

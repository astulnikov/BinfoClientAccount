package by.binfo.BusinessInform;

import java.io.BufferedReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static int STATUS_FINISH = 200;
	public final static int STATUS_PROGRESS = 100;
	public final static String PARAM_PINTENT = "pendingIntent";
	public final static String PARAM_RESULT = "result";
	public final static String PARAM_XMLURL = "xmlurl";

	Button stroyButton, prodButton, exportButton;
	SharedPreferences binfoSP;
	int mode = Activity.MODE_PRIVATE;
	public static int DIALOG = 1;
	Activity self;
	private String xmlUrl;
	NetworkReceiver receiver;
	BufferedReader bReader;
	ProgressBar bar;
	public static Handler handler;
	PendingIntent pi;
	Intent serviceIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(Utils.LOG_TAG, "onCreate");
		initvars();
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
		registerReceiver(receiver, filter);
		if (Utils.downloading) {
			Log.d(Utils.LOG_TAG, "Downloading is running");
			initProgressBar();
		}
	}

	@SuppressLint("HandlerLeak")
	private void initvars() {
		// TODO инициализировать переменные тут
		self = this;
		Utils.activity = this;
		OnClickListener mainOnClickListener = new catalogClickListener();
		stroyButton = (Button) findViewById(R.id.buttonStr);
		stroyButton.setOnClickListener(mainOnClickListener);
		prodButton = (Button) findViewById(R.id.buttonProd);
		prodButton.setOnClickListener(mainOnClickListener);
		exportButton = (Button) findViewById(R.id.buttonExp);
		exportButton.setOnClickListener(mainOnClickListener);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (bar.getVisibility() != ProgressBar.VISIBLE) {
					bar.setVisibility(ProgressBar.VISIBLE);
				}
				bar.setProgress(msg.what);
				Log.d(Utils.LOG_TAG, "GET MSG FORM THREAD:" + msg.what);
				if (msg.what == 0) {
					bar.setVisibility(ProgressBar.INVISIBLE);
				} else if (msg.what == 1) {
					bar.setVisibility(ProgressBar.INVISIBLE);
					Toast.makeText(self, "Ошибка при загрузке катклога",
							Toast.LENGTH_LONG).show();
				}
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == STATUS_FINISH) {
			Intent intent = new Intent(self, ViewCatalogue.class);
			intent.putExtra("catName", Utils.tabName);
			startActivity(intent);
			bar.setVisibility(ProgressBar.INVISIBLE);
			stopService(serviceIntent);
		} else if (resultCode == STATUS_PROGRESS) {
			int result = data.getIntExtra(PARAM_RESULT, 0);
			bar.setProgress(result);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
		if (Utils.downloading) {
			Log.d(Utils.LOG_TAG, "Downloading: " + Utils.downloading);
		}
		super.onDestroy();
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(Utils.LOG_TAG, "onRestoreInstanceState");
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(Utils.LOG_TAG, "onSaveInstanceState");
	}

	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			// заголовок
			adb.setTitle(R.string.dialog_title);
			// сообщение
			adb.setMessage(R.string.dialog_content);
			// иконка
			adb.setIcon(android.R.drawable.ic_dialog_info);
			// кнопка положительного ответа
			adb.setPositiveButton(android.R.string.ok, dialogClickListener);
			// создаем диалог
			return adb.create();
		}
		return super.onCreateDialog(id);
	}

	private void initProgressBar() {
		int max = 0;
		if (Utils.tabName.equals(Utils.TAB_NAME_STR)) {
			max = Utils.CONSTR_COUNT;
		} else {
			if (Utils.tabName.equals(Utils.TAB_NAME_FOOD)) {
				max = Utils.FOOD_COUNT;
			} else {
				max = Utils.EXPORT_COUNT;
			}
		}
		bar.setMax(max);
		bar.setVisibility(ProgressBar.VISIBLE);
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {

			if (Utils.isInternet) {
				Toast.makeText(self, "Загрузка началась!", Toast.LENGTH_SHORT)
						.show();
				initProgressBar();
				pi = createPendingResult(1, null, 0);
				serviceIntent = new Intent(self, DownloadService.class)
						.putExtra(PARAM_XMLURL, xmlUrl).putExtra(PARAM_PINTENT,
								pi);
				startService(serviceIntent);
			} else {
				Toast.makeText(self, "Нет подключения!", Toast.LENGTH_SHORT)
						.show();
			}
		}
	};

	private class catalogClickListener implements OnClickListener {

		@SuppressWarnings("deprecation")
		public void onClick(View v) {
			binfoSP = getSharedPreferences("binfoPref", mode);
			switch (v.getId()) {
			case R.id.buttonStr:
				Utils.tabName = Utils.TAB_NAME_STR;
				if (!binfoSP.getBoolean(Utils.tabName + "FirstRun", false)) {
					xmlUrl = "http://b-info.by/data/export2mobile/outStr.xml";
					showDialog(DIALOG);
				} else {
					Intent intent = new Intent(self, ViewCatalogue.class);
					intent.putExtra("catName", Utils.tabName);
					startActivity(intent);
				}
				break;
			case R.id.buttonProd:
				Utils.tabName = Utils.TAB_NAME_FOOD;
				if (!binfoSP.getBoolean(Utils.tabName + "FirstRun", false)) {
					xmlUrl = "http://b-info.by/data/export2mobile/outFood.xml";
					showDialog(DIALOG);
				} else {
					Intent intent = new Intent(self, ViewCatalogue.class);
					intent.putExtra("catName", Utils.tabName);
					startActivity(intent);
				}
				break;
			case R.id.buttonExp:
				Utils.tabName = Utils.TAB_NAME_EXPORT;
				if (!binfoSP.getBoolean(Utils.tabName + "FirstRun", false)) {
					xmlUrl = "http://b-info.by/data/export2mobile/outExp.xml";
					showDialog(DIALOG);
				} else {
					Intent intent = new Intent(self, ViewCatalogue.class);
					intent.putExtra("catName", Utils.tabName);
					startActivity(intent);
				}
				break;
			default:
				break;
			}
		}
	}
}

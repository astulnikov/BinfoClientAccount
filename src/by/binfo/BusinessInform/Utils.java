package by.binfo.BusinessInform;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class Utils {
	public static Activity activity;
	public static Boolean isInternet, downloading = false;
	public final static String DIR = "binfo";
	public static final String FOOD_LOGOS = "img/food/logos/";
	public static final String CONSTR_LOGOS = "img/building/logos/";
	public static final String EXPORT_LOGOS = "img/export/logos/";
	public static final String FOOD_VIP = "img/food/vip/";
	public static final String CONSTR_VIP = "img/building/vip/";
	public static final String EXPORT_VIP = "img/export/vip/";
	public static final String FOOD_MODULES = "img/food/modules/";
	public static final String CONSTR_MODULES = "img/building/modules/";
	public static final String EXPORT_MODULES = "img/export/modules/";
	public static final int FOOD_COUNT = 6667;
	public static final int CONSTR_COUNT = 6600;
	public static final int EXPORT_COUNT = 6600;
	public static final int PIC_TYPE_LOGO = 1;
	public static final int PIC_TYPE_VIP = 2;
	public static final int PIC_TYPE_MODULE = 3;
	public static final int EXTRA_CATEGORIES = 1;
	public static final int EXTRA_ALPHABET = 2;
	public static final int EXTRA_REGION = 3;
	public static final int EXTRA_SEARCH = 4;
	public static final String COMPANIES_VIEW_RUBRIC = "rubric";
	public static final String COMPANIES_VIEW_ALPHABET = "alphabet";
	public static final String COMPANIES_VIEW_REGION = "region";
	public static final String COMPANIES_VIEW_SEARCH = "search";
	public static final String LOG_TAG = "binfo";
	public static String tabName = "companies";
	public static final String TAB_NAME_FOOD = "food";
	public static final String TAB_NAME_STR = "building";
	public static final String TAB_NAME_EXPORT = "export";

	public static void onError() {
		MainActivity.handler.sendEmptyMessage(1);
		downloading = false;
		Intent serviceIntent = new Intent(activity, DownloadService.class);
		activity.stopService(serviceIntent);
		DBHelper dbHelp = new DBHelper(activity);
		dbHelp.dropTable(tabName);
		Log.d(LOG_TAG, "Мы вынуждены были удалить таблицу!" + tabName);
		
		FileDownloader.deleteFiles();
		
		SharedPreferences binfoSP = activity.getSharedPreferences("binfoPref",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = binfoSP.edit();
		editor.putBoolean(tabName + "FirstRun", false);
		editor.commit();
	}
}

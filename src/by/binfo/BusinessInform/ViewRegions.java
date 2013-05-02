package by.binfo.BusinessInform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewRegions extends Activity {
	String[] regions;
	ListView regionListView;
	ArrayAdapter<String> adapter;
	Activity self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.region_layout);
		self = this;
		init();
	}
	
	public void init() {
		regions = getResources().getStringArray(R.array.region);
		regionListView = (ListView) findViewById(R.id.listViewRegion);
		adapter = new ArrayAdapter<String>(self,
				android.R.layout.simple_list_item_1, regions);
		regionListView.setAdapter(adapter);
		regionListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String region = regions[arg2];
				Intent intent = new Intent();
				intent.setClass(getParent(), ViewCompanies.class);
				intent.putExtra("type", Utils.COMPANIES_VIEW_REGION);
				intent.putExtra("region", region);
				Log.d(Utils.LOG_TAG, getParent().getLocalClassName());
				ActivityStack activityStack = (ActivityStack) getParent();
				Log.d(Utils.LOG_TAG, activityStack.getLocalClassName());
				activityStack.push("ViewCompanies", intent);
			}
		});
	}
}

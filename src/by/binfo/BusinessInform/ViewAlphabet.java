package by.binfo.BusinessInform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewAlphabet extends Activity {

	String[] alphabet;
	ListView alphabetListView;
	ArrayAdapter<String> adapter;
	Activity self;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alphabet_layout);
		self = this;
		init();
	}

	public void init() {
		alphabet = getResources().getStringArray(R.array.alphabet);
		alphabetListView = (ListView) findViewById(R.id.listViewAlphabet);
		adapter = new ArrayAdapter<String>(self,
				android.R.layout.simple_list_item_1, alphabet);
		alphabetListView.setAdapter(adapter);
		alphabetListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String letter = alphabet[arg2];
				Intent intent = new Intent();
				intent.setClass(getParent(), ViewCompanies.class);
				intent.putExtra("type", Utils.COMPANIES_VIEW_ALPHABET);
				intent.putExtra("letter", letter);
				Log.d(Utils.LOG_TAG, getParent().getLocalClassName());
				ActivityStack activityStack = (ActivityStack) getParent();
				Log.d(Utils.LOG_TAG, activityStack.getLocalClassName());
				activityStack.push("ViewCompanies", intent);
			}
		});
	}
}

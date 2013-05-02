package by.binfo.BusinessInform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ViewSearch extends Activity {

	Button searchButton;
	EditText searchEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		searchEditText = (EditText) findViewById(R.id.editTextSearch);
		searchButton = (Button) findViewById(R.id.buttonSearch);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String name = searchEditText.getText().toString();
				Intent intent = new Intent();
				intent.setClass(getParent(), ViewCompanies.class);
				intent.putExtra("type", Utils.COMPANIES_VIEW_SEARCH);
				intent.putExtra("name", name);
				Log.d(Utils.LOG_TAG, getParent().getLocalClassName());
				ActivityStack activityStack = (ActivityStack) getParent();
				Log.d(Utils.LOG_TAG, activityStack.getLocalClassName());
				activityStack.push("ViewCompanies", intent);
			}
		});
	}
}

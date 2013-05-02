package by.binfo.BusinessInform;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewCompanies extends Activity {
	public DBHelper dBHelp;
	private Activity self;
	private ListView listView;
	ArrayList<Company> companiesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_companies_layout);
		self = this;
		dBHelp = new DBHelper(self);
		listView = (ListView) findViewById(R.id.listViewCompanies);
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if (type.equals(Utils.COMPANIES_VIEW_RUBRIC)) {
			String rubricId = intent.getStringExtra("rubricId");
			if (rubricId.equals("0")) {
				companiesList = dBHelp.getVip();
			} else {
				companiesList = dBHelp.getCompaniesRubric(rubricId);
			}
		} else if (type.equals(Utils.COMPANIES_VIEW_ALPHABET)) {
			String letter = intent.getStringExtra("letter");
			companiesList = dBHelp.getCompaniesAlphabet(letter);
		} else if (type.equals(Utils.COMPANIES_VIEW_REGION)) {
			String region = intent.getStringExtra("region");
			companiesList = dBHelp.getCompaniesRegion(region);
		} else if (type.equals(Utils.COMPANIES_VIEW_SEARCH)) {
			String name = intent.getStringExtra("name");
			companiesList = dBHelp.searchCompanies(name);
		}
		if (companiesList != null) {
			CompaniesListViewAdapter adapter = new CompaniesListViewAdapter(
					self, R.layout.company_name_view, companiesList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String msg = "Position = " + position + " id = " + id;
					Log.d(Utils.LOG_TAG, msg);
					// Кладем в intent выбранный пункт
					Intent intent = new Intent(self, DetailsActivity.class);
					intent.putExtra("id", companiesList.get((int) id).getId());
					Log.d(Utils.LOG_TAG,
							"ViewCompaniesActivity: start DetailsActivity");
					startActivity(intent);
				}
			});
		}
	}
}

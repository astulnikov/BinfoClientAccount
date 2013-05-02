package by.binfo.BusinessInform;

import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ViewCategories extends Activity {
	private Activity self;
	private int xmlResId;
	private ExpandableListView rubricsListView;
	ArrayList<ArrayList<Rubric>> subs;
	ArrayList<Rubric> rubrics, empty;
	DBHelper dBHelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rubrics_layout);
		init();
	}

	private void init() {
		self = this;
		Resources res = getResources();
		dBHelp = new DBHelper(self);
		if (Utils.tabName.equals("building")) {
			xmlResId = R.xml.rubrics_building;
		} else {
			if (Utils.tabName.equals("food")) {
				xmlResId = R.xml.rubrics_food;
			} else {
				xmlResId = R.xml.rubrics_export;
			}
		}
		subs = new ArrayList<ArrayList<Rubric>>();
		// отсюда будубрать родительские элементы
		rubrics = new ArrayList<Rubric>();
		int current = -1;
		XmlResourceParser parser = res.getXml(xmlResId);
		try {
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG
						&& parser.getName().equals("rubrica")) {
					empty = new ArrayList<Rubric>();
					rubrics.add(new Rubric(parser.getAttributeValue(1), parser
							.getAttributeValue(0)));
					subs.add(empty);
					current++;
				}
				if (parser.getEventType() == XmlResourceParser.START_TAG
						&& parser.getName().equals("sub")) {
					subs.get(current).add(
							new Rubric(parser.getAttributeValue(1), parser
									.getAttributeValue(0)));
				}
				parser.next();
			}
		} catch (XmlPullParserException e) {
			Log.d(Utils.LOG_TAG, "Ошибка при загрузке XML");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(Utils.LOG_TAG, "Ошибка при загрузке XML");
			e.printStackTrace();
		}
		rubricsListView = (ExpandableListView) findViewById(R.id.listViewRubrics);
		RubricsListAdapter adapter = new RubricsListAdapter(self, subs, rubrics);
		rubricsListView.setAdapter(adapter);
		rubricsListView.setOnChildClickListener(new OnChildClickListener() {
			
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String rubricId = subs.get(groupPosition).get(childPosition)
						.getId();
				Intent intent = new Intent();
		          intent.setClass(getParent(), ViewCompanies.class);
		          intent.putExtra("type", Utils.COMPANIES_VIEW_RUBRIC);
		          intent.putExtra("rubricId", rubricId);
		          Log.d(Utils.LOG_TAG, getParent().getLocalClassName());
		          ActivityStack activityStack = (ActivityStack) getParent();
		          activityStack.push("ViewCompanies", intent);
				return false;
			}
		});
	}
}

package by.binfo.BusinessInform;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class ViewCatalogue extends TabActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catalogue);
		TabHost tabHost = getTabHost();
		TabHost.TabSpec tabSpec;
		
		tabSpec = tabHost.newTabSpec("categories");
		tabSpec.setIndicator("Категории",
				getResources().getDrawable(R.drawable.ic_action_search));
		Intent intent = new Intent(this, ActivityStack.class);
		intent.putExtra("extra", Utils.EXTRA_CATEGORIES);
		tabSpec.setContent(intent);
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("alphabet");
		tabSpec.setIndicator("Алфавит",
				getResources().getDrawable(R.drawable.ic_action_search));
		Intent intent2 = new Intent(this, ActivityStack.class);
		intent2.putExtra("extra", Utils.EXTRA_ALPHABET);
		tabSpec.setContent(intent2);
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("regions");
		tabSpec.setIndicator("Регионы",
				getResources().getDrawable(R.drawable.ic_action_search));
		Intent intent3 = new Intent(this, ActivityStack.class);
		intent3.putExtra("extra", Utils.EXTRA_REGION);
		tabSpec.setContent(intent3);
        tabHost.addTab(tabSpec);
		
        tabSpec = tabHost.newTabSpec("search");
		tabSpec.setIndicator("Поиск",
				getResources().getDrawable(R.drawable.ic_action_search));
		Intent intent4 = new Intent(this, ActivityStack.class);
		intent4.putExtra("extra", Utils.EXTRA_SEARCH);
		tabSpec.setContent(intent4);
        tabHost.addTab(tabSpec);
	}
}

package by.binfo.BusinessInform;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

@SuppressWarnings("deprecation")
public class ActivityStack extends ActivityGroup {

	private Stack<String> stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (stack == null) {
			stack = new Stack<String>();
		}
		Intent intent = getIntent();
		int tab = intent.getIntExtra("extra", Utils.EXTRA_CATEGORIES);
		switch (tab) {
		case Utils.EXTRA_CATEGORIES:
			push("ViewCategories", new Intent(this, ViewCategories.class));
			break;
		case Utils.EXTRA_ALPHABET:
			push("ViewAlphabet", new Intent(this, ViewAlphabet.class));
			break;
		case Utils.EXTRA_REGION:
			push("ViewRegions", new Intent(this, ViewRegions.class));
			break;
		case Utils.EXTRA_SEARCH:
			push("ViewSearch", new Intent(this, ViewSearch.class));
			break;

		default:
			break;
		}

	}

	@Override
	public void finishFromChild(Activity child) {
		pop();
	}

	@Override
	public void onBackPressed() {
		pop();
	}

	public void push(String id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(id,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			stack.push(id);
			setContentView(window.getDecorView());
		}
	}

	public void pop() {
		if (stack.size() == 1)
			finish();
		LocalActivityManager manager = getLocalActivityManager();
		manager.destroyActivity(stack.pop(), true);
		if (stack.size() > 0) {
			Intent lastIntent = manager.getActivity(stack.peek()).getIntent();
			Window newWindow = manager.startActivity(stack.peek(), lastIntent);
			setContentView(newWindow.getDecorView());
		}
	}
}

package by.binfo.BusinessInform;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class RubricsListAdapter extends BaseExpandableListAdapter {

	private ArrayList<ArrayList<Rubric>> mSubs;
	private ArrayList<Rubric> mRubrics;
	private Context mContext;

	public RubricsListAdapter(Context context,
			ArrayList<ArrayList<Rubric>> subs, ArrayList<Rubric> rubrics) {
		mContext = context;
		mSubs = subs;
		mRubrics = rubrics;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return mSubs.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.subrubric_view, null);
			holder = new ViewHolder();
			holder.subrubricsTextView = (TextView) convertView
					.findViewById(R.id.textSubrubric);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.subrubricsTextView.setText(mSubs.get(groupPosition).get(childPosition).getName());
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return mSubs.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return mRubrics.get(groupPosition);
	}

	public int getGroupCount() {
		return mRubrics.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.rubric_view, null);
			holder = new ViewHolder();
			holder.rubricsTextView = (TextView) convertView
					.findViewById(R.id.textRubric);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (isExpanded) {
			// Изменяем что-нибудь, если текущая Group раскрыта
		} else {
			// Изменяем что-нибудь, если текущая Group скрыта
		}

		holder.rubricsTextView.setText(mRubrics.get(groupPosition).getName());

		return convertView;

	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	static class ViewHolder {
		public TextView rubricsTextView, subrubricsTextView;
	}

}

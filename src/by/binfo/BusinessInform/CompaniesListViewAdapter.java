package by.binfo.BusinessInform;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CompaniesListViewAdapter extends ArrayAdapter<Company> {

	Context context;
	ArrayList<Company> data = null;
	int mLayoutResourceId;

	public CompaniesListViewAdapter(Context context, int layoutResourceId,
			ArrayList<Company> companiesList) {
		super(context, layoutResourceId, companiesList);
		this.context = context;
		this.data = companiesList;
		mLayoutResourceId = layoutResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CompanyHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
			holder = new CompanyHolder();
			holder.imgIcon = (ImageView) row
					.findViewById(R.id.imageCompanyIcon);
			holder.txtTitle = (TextView) row.findViewById(R.id.textCompanyName);
			row.setTag(holder);
		} else {
			holder = (CompanyHolder) row.getTag();
		}
		Company company = data.get(position);
		holder.txtTitle.setText(company.getName());
		if (company.getVip() == 1) {
			holder.imgIcon.setImageResource(android.R.drawable.star_on);
			holder.imgIcon.setVisibility(View.VISIBLE);
		} else if(company.getPicString().length() > 1){
			holder.imgIcon.setImageResource(android.R.drawable.star_big_off);
			holder.imgIcon.setVisibility(View.VISIBLE);
		} else if(company.getPicString().length() == 0){
			holder.imgIcon.setVisibility(View.INVISIBLE);
		}
		return row;
	}

	static class CompanyHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}

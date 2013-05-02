package by.binfo.BusinessInform;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	public DBHelper dBHelp;
	private Activity self;
	TextView nemeTextView, addressTextView, phoneTextView, emailTextView,
			siteTextView, descriptionTextView;
	ImageView logoImageView, moduleImageView;
	String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_layout);
		self = this;
		dBHelp = new DBHelper(self);
		nemeTextView = (TextView) findViewById(R.id.textViewName);
		addressTextView = (TextView) findViewById(R.id.textViewAddress);
		phoneTextView = (TextView) findViewById(R.id.textViewPhone);
		emailTextView = (TextView) findViewById(R.id.textViewEmail);
		siteTextView = (TextView) findViewById(R.id.textViewSite);
		descriptionTextView = (TextView) findViewById(R.id.textViewDescription);

		logoImageView = (ImageView) findViewById(R.id.imageViewLogo);
		moduleImageView = (ImageView) findViewById(R.id.imageViewModule);

		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		Company company = dBHelp.getCompany(id);
		Log.d(Utils.LOG_TAG, "Company: " + company.toString());
		nemeTextView.setText(company.getName());
		addressTextView.setText(company.getAddress());
		phoneTextView.setText(company.getPhone());
		if (company.geteMail() != null) {
			emailTextView.setText(company.geteMail());
			findViewById(R.id.row_email).setVisibility(View.VISIBLE);
		}
		if (company.getSite() != null) {
			siteTextView.setText(company.getSite());
			findViewById(R.id.row_site).setVisibility(View.VISIBLE);
		}
		if (company.getDirection() != null) {
			descriptionTextView.setText(company.getDirection());
			descriptionTextView.setVisibility(View.VISIBLE);
		}

		String[] picsArr = company.getPic();
		for (int i = 0; i < picsArr.length; i++) {
			if (picsArr[i] != "") {
				String uriString = "file://"
						+ Environment.getExternalStorageDirectory()
								.getAbsolutePath() + "/" + Utils.DIR + "/"
						+ Utils.tabName + "/" + picsArr[i] + ".JPG";
				final Uri uri = Uri.parse(uriString);
				Log.d(Utils.LOG_TAG, "Image: " + uriString);
				int type = Integer.parseInt(picsArr[i].substring(0, 1));
				if (type == Utils.PIC_TYPE_LOGO) {
					logoImageView.setImageURI(uri);
					logoImageView.setVisibility(View.VISIBLE);
				} else {
					moduleImageView.setImageURI(uri);
					moduleImageView.setVisibility(View.VISIBLE);
					moduleImageView.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_VIEW);
							intent.setDataAndType(uri, "image/*");
							startActivity(intent);
						}
					});
				}
			}
		}
	}
}

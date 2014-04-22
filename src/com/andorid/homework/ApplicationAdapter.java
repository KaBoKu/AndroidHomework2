package com.andorid.homework;

import java.util.List;

import com.javatechig.listapps.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> {
	private List<ApplicationInfo> appsList = null;
	private Context context;
	private PackageManager packageManager;
	private float rating;
	private SharedPreferences preferences;
	private SharedPreferences.Editor preferencesEditor;
	public static final String MY_PREFERENCES = "myPreferences";

	public ApplicationAdapter(Context context, int textViewResourceId,
			List<ApplicationInfo> appsList) {
		super(context, textViewResourceId, appsList);
		this.context = context;
		this.appsList = appsList;
		packageManager = context.getPackageManager();

	}

	@Override
	public int getCount() {
		return ((null != appsList) ? appsList.size() : 0);
	}

	@Override
	public ApplicationInfo getItem(int position) {
		return ((null != appsList) ? appsList.get(position) : null);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (null == view) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.snippet_list_row, null);
		}

		final ApplicationInfo data = appsList.get(position);
		if (null != data) {
			preferences = context.getSharedPreferences(
					MY_PREFERENCES, context.MODE_PRIVATE);
			TextView appName = (TextView) view.findViewById(R.id.app_name);
			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
			
			ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
			ratingBar
					.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

						@Override
						public void onRatingChanged(RatingBar ratingBar,
								float rating, boolean fromUser) {
							// TODO Auto-generated method stub
							
							preferencesEditor = preferences.edit();
							preferencesEditor.putFloat(
									data.loadLabel(packageManager).toString(),
									ratingBar.getRating());
							preferencesEditor.commit();

						}
					});

			appName.setText(data.loadLabel(packageManager));
			ratingBar.setRating(preferences.getFloat(data.loadLabel(packageManager).toString(), 1.0f));
			iconview.setImageDrawable(data.loadIcon(packageManager));
		}
		return view;
	}
};
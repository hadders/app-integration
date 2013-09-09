package com.example.chickenshop;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chickenshop.activity.MainActivity;

public class AppsListAdapter extends BaseAdapter {

	private List<AppItem> data;
	private WeakReference<MainActivity> activity;

	public AppsListAdapter(List<AppItem> data, WeakReference<MainActivity> activity) {
		this.data = data;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();

		if (convertView == null) {
			convertView = LayoutInflater.from(activity.get()).inflate(
					R.layout.list_item, null, false);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final AppItem item = data.get(position);

		try {
			viewHolder.image.setImageDrawable(activity.get()
					.getPackageManager()
					.getApplicationIcon(item.getPackageName()));

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		viewHolder.name.setText(item.getAppName());

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.get().startActivity(item);

			}
		});

		return convertView;
	}

	private static class ViewHolder {
		TextView name;
		ImageView image;
	}
}

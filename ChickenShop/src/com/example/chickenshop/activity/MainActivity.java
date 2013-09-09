package com.example.chickenshop.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chickenshop.AppItem;
import com.example.chickenshop.AppsListAdapter;
import com.example.chickenshop.R;

public class MainActivity extends Activity {

	private static final String INTENT_ALBERT_MIME_TYPE = "vnd.com.example.request/vnd.com.example.payment.v1";
	private static final String INTENT_ACTION_PAYMENT = "com.example.albert.PAYMENT";
	private static final String INTENT_EXTRAS_PAYMENT_AMOUNT = "payment.amount";
	private static final String INTENT_CATEGORY_FACILITATE = "com.example.albert.FACILITATE";
	private static final String INTENT_CATEGORY_PAY = "com.example.albert.PAY";

	private TextView result;
	private EditText amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		result = (TextView) findViewById(R.id.result);
		amount = (EditText) findViewById(R.id.amount_entry);
	}

	@Override
	protected void onResume() {
		super.onResume();

		ListView redeemApps = (ListView) findViewById(R.id.payment_list);
		AppsListAdapter adapter = new AppsListAdapter(
				listActivities(INTENT_CATEGORY_PAY),
				new WeakReference<MainActivity>(this));
		redeemApps.setAdapter(adapter);

		ListView facilitationApps = (ListView) findViewById(R.id.facilitation_list);
		AppsListAdapter adapter2 = new AppsListAdapter(
				listActivities(INTENT_CATEGORY_FACILITATE),
				new WeakReference<MainActivity>(this));
		facilitationApps.setAdapter(adapter2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String amount = data.getExtras()
				.getString(INTENT_EXTRAS_PAYMENT_AMOUNT);
		result.setText(amount);
	}

	private List<AppItem> listActivities(String category) {
		List<AppItem> items = new ArrayList<AppItem>();
		PackageManager manager = getPackageManager();
		Intent intent = new Intent();
		intent.setAction(INTENT_ACTION_PAYMENT);
		intent.addCategory(category);
		intent.setType(INTENT_ALBERT_MIME_TYPE);

		List<ResolveInfo> infos = manager.queryIntentActivities(intent,
				PackageManager.GET_RESOLVED_FILTER);
		for (ResolveInfo info : infos) {
			ActivityInfo activityInfo = info.activityInfo;
			IntentFilter filter = info.filter;
			if (null != filter) {
				String activityPackageName = activityInfo.packageName;
				String activityName = activityInfo.name;
				String appName = activityInfo.applicationInfo
						.loadLabel(manager).toString();

				items.add(new AppItem(activityPackageName, activityName,
						appName));
			}
		}
		return items;
	}

	public void startActivity(AppItem item) {
		Intent intent = new Intent();
		intent.setClassName(item.getPackageName(), item.getActivityName());
		intent.setAction(INTENT_ACTION_PAYMENT);
		intent.addCategory(INTENT_CATEGORY_PAY);
		intent.putExtra(INTENT_EXTRAS_PAYMENT_AMOUNT, amount.getText()
				.toString());
		intent.setType(INTENT_ALBERT_MIME_TYPE);
		startActivityForResult(intent, 1);
	}

}

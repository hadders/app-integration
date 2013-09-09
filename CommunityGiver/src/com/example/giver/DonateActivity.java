package com.example.giver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DonateActivity extends Activity {

	private static final String INTENT_EXTRAS_PAYMENT_AMOUNT = "payment.amount";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);

		Bundle extras = getIntent().getExtras();
		TextView amount = (TextView) findViewById(R.id.amount);
		amount.setText(extras.getString(INTENT_EXTRAS_PAYMENT_AMOUNT));

		Button button = (Button) findViewById(R.id.done);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(INTENT_EXTRAS_PAYMENT_AMOUNT,
						"$2 given to charity");
				setResult(RESULT_OK, intent);
				finish();

			}
		});
	}

}

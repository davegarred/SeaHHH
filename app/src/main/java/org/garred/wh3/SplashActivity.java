package org.garred.wh3;

import org.garred.wh3.service.CallController;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

		Log.d("SplashActivity", "loading events");
		setContentView(R.layout.splash);
		new CallController(this);
	}
}

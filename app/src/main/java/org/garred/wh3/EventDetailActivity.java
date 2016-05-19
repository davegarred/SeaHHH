package org.garred.wh3;

import org.garred.seah3.model.v1.HashEvent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class EventDetailActivity extends Activity {

	private HashEvent event;
	private String link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.event_details);

		Bundle startBundle = getIntent().getExtras();
		if (startBundle != null && startBundle.containsKey(ContentHolder.EVENT_DETAIL_ID)) {
			event = ContentHolder.ITEM_MAP.get(startBundle.getString(ContentHolder.EVENT_DETAIL_ID));
		}
		if (event != null) {
			setContentView(createView());
		} else {
			Log.d("EventDetailActivity","returning from Event Detail due to no event");
			finish();
		}
	}

	private void restart() {
		Intent detailIntent = new Intent(this, SplashActivity.class);
		startActivity(detailIntent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public View createView() {
		return onCreateView(this.getLayoutInflater(), null, null);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.event_details, container, false);
		completeFields(rootView);
		return rootView;
	}

	private void completeFields(View rootView) {
		if (event != null) {
			TextView textView;
			textView = (TextView) rootView.findViewById(R.id.textView_title);
			textView.setText(event.getTitle());
			textView = (TextView) rootView.findViewById(R.id.textView_hash_name);
			textView.setText(event.getEventName());
			textView = (TextView) rootView.findViewById(R.id.textView_hares);
			textView.setText(event.getHare());
			textView = (TextView) rootView.findViewById(R.id.textView_date_time);
			if (event.getDescription().length() > 0) {
				textView.setText(event.getDateTime());
			} else {
				textView.setText("");
			}

			Button mapButton = (Button) rootView.findViewById(R.id.button_map);
			link = event.getMapLink();
			if (link == null || link.isEmpty()) {
				mapButton.setVisibility(View.INVISIBLE);
			} else {
				mapButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showMap();
					}
				});
			}

			textView = (TextView) rootView.findViewById(R.id.textView_description);
			String description = event.getDescription();
			description = description.replaceAll("<br>", "\n");
			description = description.replaceAll("•", "");
			textView.setText(description);
		}
		setImage(rootView);
	}

	private void showMap() {
		Intent mapIntent = new Intent(Intent.ACTION_VIEW);
		mapIntent.setData(Uri.parse(link));
		startActivity(mapIntent);
	}

	private void setImage(View rootView) {
		int icon = 0;
		switch (event.getType()) {
			case SEATTLE:
				icon = R.drawable.seattle;
				break;
			case TACOMA:
				icon = R.drawable.tacoma;
				break;
			case HAPPY_HOUR:
				icon = R.drawable.happy2;
				break;
			case RENTON_HAPPY_HOUR:
				icon = R.drawable.happy3;
				break;
			case SOUTH_SOUND:
				icon = R.drawable.south_sound;
				break;
			case HSWTF:
				icon = R.drawable.hswtf;
				break;
			case BASH:
				icon = R.drawable.bike_hash;
				break;
			case NO_BALLS:
				icon = R.drawable.nbh3;
				break;
			case PUGET_SOUND:
				icon = R.drawable.puget;
				break;
			case RAIN_CITY:
				icon = R.drawable.raincity;
				break;
			case SEAMON:
				icon = R.drawable.seamon;
				break;
			case FULL_MOON:
			case OTHER:
		}
		ImageView image = (ImageView) rootView.findViewById(R.id.imageView_event_icon);
		if (icon > 0) {
			image.setImageResource(icon);
		} else {
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layout_event_detail);
			layout.removeView(image);
		}
	}
}

package org.garred.wh3;

import org.garred.seah3.model.HashEvent;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(ContentHolder.ITEMS.isEmpty()) {
			finish();
			Log.d("EventListActivity", "returning from Event List Activity due to no events");
			return;
		}

		setContentView(R.layout.activity_event_list);
		ArrayAdapter<HashEvent> adapter = new EventListAdapter(this, R.layout.event_list_item, R.id.event_list_item_title, ContentHolder.ITEMS);
		setListAdapter(adapter);
		
		if(ContentHolder.getMessage() != null && !ContentHolder.getMessage().isEmpty()) {
			String message = ContentHolder.getMessage();
			ContentHolder.setMessage(null);
			popupMessage(message);
		}

	}

	protected void onListItemClick(ListView listView, View view, int position, long id) {
		if(ContentHolder.ITEMS.get(position).hasDetails()) {
			super.onListItemClick(listView, view, position, id);
			Intent detailIntent = new Intent(this, EventDetailActivity.class);
			detailIntent.putExtra(ContentHolder.EVENT_DETAIL_ID, ContentHolder.ITEMS.get(position).getId().toString());
			startActivity(detailIntent);		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
		case android.R.id.home:
			quitThis();
			return true;
        case R.id.action_email_us:
            emailUs();
            return true;
        case R.id.action_help:
        	popupMessage(getResources().getString(R.string.help_text));
            return true;
        default:
            return super.onOptionsItemSelected(item);
	    }
	}

	private void quitThis() {
		Intent intent = new Intent(this, SplashActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}

	private void emailUs() {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"fbeavershit@gmail.com"});		  
		email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_us_subject_line));
		email.putExtra(Intent.EXTRA_TEXT,"");
		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email,getResources().getString(R.string.email_us_chooser_message)));
	}

	private void popupMessage(String message) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setMessage(message);
		dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getResources().getString(R.string.popup_dialog_close_text),
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog.show();
	}
	
	@Override
	public void onBackPressed() {
		quitThis();
	}

}

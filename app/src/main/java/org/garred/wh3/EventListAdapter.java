package org.garred.wh3;

import java.util.List;

import org.garred.seah3.model.HashEvent;
import org.garred.seah3.model.v2.Kennel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<HashEvent> {

	private final Context context;
	
	public EventListAdapter(Context context, int resource, int textViewResourceId, List<HashEvent> items) {
		super(context, resource, textViewResourceId, items);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashEvent event = ContentHolder.ITEMS.get(position);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		// TODO need an enum instead of these if-else
		View view = null;
		if(event.getType() != Kennel.OTHER) {
			if(!event.getHare().isEmpty()) {
				view = inflater.inflate(R.layout.event_list_item_kennel_hare, parent, false);
			} else {
				view = inflater.inflate(R.layout.event_list_item_kennel, parent, false);
			}
		} else if(!event.getHare().isEmpty()) {
			view = inflater.inflate(R.layout.event_list_item_hare, parent, false);
		} else {
			view = inflater.inflate(R.layout.event_list_item, parent, false);
		}

		TextView textKennel = (TextView) view.findViewById(R.id.event_list_item_title_kennel);
		if(textKennel != null) {
			textKennel.setText(event.getType().getName());
		}		
		TextView textHare = (TextView) view.findViewById(R.id.event_list_item_hare);
		if(textHare != null) {
			textHare.setText(event.getHare());
		}
		
		if(!event.hasDetails()) {
			view.setBackgroundColor(Color.rgb(204, 204, 204));
		}

//		View view = super.getView(position, convertView, parent);
		TextView textDate = (TextView) view.findViewById(R.id.event_list_item_date);
		TextView textTitle = (TextView) view.findViewById(R.id.event_list_item_title);
		textTitle.setText(event.getEventName());
		textDate.setText(event.getDateStringShort());
		return view;
	}
}


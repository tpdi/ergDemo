package org.diffenbach.enumradiogroup;

import java.util.Calendar;

import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

public class TimeDatePicker extends LinearLayout {

	public TimeDatePicker(Context context, TimePicker t, DatePicker d, Button b) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		addView(t);
		addView(d);
		addView(b);
	}
	
	public TimeDatePicker(Context context, Button b) {
		this(context, new TimePicker(context), new DatePicker(context), b);
	}
	
	TimePicker getTimePicker() {
		return (TimePicker) getChildAt(0);
	}
	
	DatePicker getDatePicker() {
		return (DatePicker) getChildAt(1);
	}
	
	long getTimeMillis() {
		DatePicker d = getDatePicker();
		TimePicker t = getTimePicker();
		Calendar c = Calendar.getInstance();
		c.set(d.getYear(), d.getMonth(), d.getDayOfMonth(), t.getCurrentHour(), t.getCurrentMinute(), 0);
		return c.getTimeInMillis();
	}

}

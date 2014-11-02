package org.diffenbach.enumradiogroup;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextTwoUp extends LinearLayout {
	
	private final TextView first;
	private final TextView second;
	
	public TextTwoUp(Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(getResources().getColor(android.R.color.background_light));
		setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, 160));
		setPadding(15, 15, 15, 15);
		
		addView(first = makeFirst(context));
		addView(second = makeSecond(context));	
	}

	public TextView getFirst() {
		return first;
	}

	public TextView getSecond() {
		return second;
	}
	
	private TextView makeFirst(Context context) {
		TextView ret = new TextView(context);
		ret.setTextColor(getResources().getColor(android.R.color.primary_text_light));
		ret.setTextSize(18);
		return ret;
	}
	
	private TextView makeSecond(Context context) {
		TextView ret = new TextView(context);
		ret .setGravity(Gravity.RIGHT);	
		LinearLayout.LayoutParams params = 
				new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		params.weight = 1.0f;
		params.gravity = Gravity.RIGHT;
		
		ret.setLayoutParams(params);
		ret.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
		ret.setTextSize(14);
		return ret;
	} 
}

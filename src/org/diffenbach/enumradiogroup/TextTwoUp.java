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
		setOrientation(LinearLayout.HORIZONTAL);
		setBackgroundColor(Color.LTGRAY);
		setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, 120));
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
		ret.setTextSize(20);
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
		return ret;
	}
}
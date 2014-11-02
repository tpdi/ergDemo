package org.diffenbach.enumradiogroup;

import org.diffenbach.android.widgets.EnumRadioGroup;

import android.content.Context;
import android.widget.Button;

public class SymptomUtils {
	
	public static EnumRadioGroup<Polar> makeNoYes(Context context) {
		return new EnumRadioGroup<Polar>(context, Polar.NO,
				R.array.noyes).filter(EnumRadioGroup.includeAllBut(Polar.UNKNOWN));
	}
	
	public static Button makeDatePickerButton(Context context) {
		Button b = new Button(context);
		b.setText("Click here to enter when you took it");
		return b;
	}
	
	public static TextTwoUp makeTextTwoUp(Context context, String text) {
		TextTwoUp ret = new TextTwoUp(context);
		ret.getFirst().setText(text);
		return ret;
	}

}

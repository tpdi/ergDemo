package org.diffenbach.enumradiogroup;

import org.diffenbach.android.widgets.EnumRadioGroup;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class SymptomUtils {
	
	public static EnumRadioGroup<Polar> makeNoYes(Context context) {
		return new EnumRadioGroup<Polar>(context, Polar.UNKNOWN,
				R.array.noyes).filter(EnumRadioGroup.includeAllBut(Polar.UNKNOWN));
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> EnumRadioGroup<T> makeEnumRadioGroup(
			Context context, T defaultAndHidden, int strings) {
		return new EnumRadioGroup<T>(context, defaultAndHidden, strings).
				filter(EnumRadioGroup.includeAllBut(defaultAndHidden));
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
	
	public static TextTwoUp makeTextTwoUp(Context context, int stringId) {
		return makeTextTwoUp(context, context.getResources().getString(stringId));
	}

	public static void saveTo(Bundle bundle, String key, EnumRadioGroup<?> egr) {
		bundle.putInt(key, egr.getCheckedValue().ordinal());
	}

	public static <T extends Enum<T>> void  restoreFrom(Bundle bundle, String key, EnumRadioGroup<T> egr) {
		int ord = bundle.getInt(key, -1);
		if (ord != -1) {
			egr.check( egr.values()[ord]);
		}
	}

}

package org.diffenbach.enumradiogroup;

import java.util.Calendar;

import org.diffenbach.android.widgets.EnumRadioGroup;

import android.content.Context;
import android.widget.Button;

public class MedicationCollapsePanel 
	extends CollapsePanel<MedicationTimestamp, MedicationTimestamp, TextTwoUp, MedicationTimestampCollapsePanel> {

	private static TextTwoUp makeTextTwoUp(Context context, Medication medication) {
		TextTwoUp ret = new TextTwoUp(context);
		ret.getFirst().setText("Did you take your " + medication.getName());
		return ret;
	}
	
	private static EnumRadioGroup<Polar> makeNoYes(Context context) {
		return new EnumRadioGroup<Polar>(context, Polar.NO, R.array.agreed_without_no);
	}
	
	private static MedicationTimestampCollapsePanel makeMedicationTimestampCollapsePanel(
			Context context, MedicationTimestamp medicationTimestamp) {
		Button b = new Button(context);
		b.setText("Click here to enter when you took it");
		return new MedicationTimestampCollapsePanel(
				context, medicationTimestamp, makeNoYes(context), b, null);
	}
	
	public MedicationCollapsePanel(Context context, Medication medication, Collapsable next) {
		super(context, makeTextTwoUp(context, medication), 
				makeMedicationTimestampCollapsePanel(context, new MedicationTimestamp(medication)), next);
		
		// ugh
		getBody().setNext(this);
	}
	
	public MedicationCollapsePanel(Context context, Medication medication, MedicationTimestampCollapsePanel mdtcp, Collapsable next) {
		super(context, makeTextTwoUp(context, medication), mdtcp, next);
		getBody().setNext(this);
		// do  not call setValue here! .setValue(new MedicationTimestamp(medication));
		
	}

	@Override
	public MedicationTimestamp getResult() {
		return getBody().getResult();
	}

	@Override
	public CollapsePanel<MedicationTimestamp, MedicationTimestamp, TextTwoUp, MedicationTimestampCollapsePanel> setValue(
			MedicationTimestamp value) {
		getBody().setValue(value);
		return this;
	}

	@Override 
	protected void onCollapse(boolean collapsed) {
		if (collapsed) {
			String s = getBody().getHead().getCheckedValue().toString();
			if(getBody().getHead().getCheckedValue() == Polar.YES){
				Calendar ts = getResult().getTimestamp();
				if(ts != null)
					s += " at " + String.format("%tR %tD", ts, ts);
			}
			getHead().getSecond().setText(s);
		}
	}


}

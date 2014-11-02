package org.diffenbach.enumradiogroup;

import java.util.Calendar;

import org.diffenbach.android.widgets.EnumRadioGroup;
import org.diffenbach.android.widgets.EnumRadioGroup.OnCheckedChangeListener;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicationCollapsePanel extends
		CollapsePanel<TextTwoUp, MedicationCollapsePanel.MedicationTimestampCollapsePanel> 
		implements SaveRestore {

	
	private static TextTwoUp makeTextTwoUp(Context context, Medication medication) {
		return SymptomUtils.makeTextTwoUp(context, "Did you take your " + medication.getName());
	}

	public MedicationCollapsePanel(Context context, Medication medication, Collapsable next) {
		super(context, makeTextTwoUp(context, medication), 
				new MedicationTimestampCollapsePanel(context, medication, null), next);
		getBody().setParent(this);
	}
	
	MedicationTimestamp getMedicationTimestamp() {
		return getBody().medicationTimestamp;
	}

	@Override
	protected void onCollapse(boolean collapsed) {
		if (collapsed) {
			String s = getBody().getHead().getCheckedValue().toString();
			Calendar ts = getMedicationTimestamp().getTimestamp();
			if (ts != null) {
				s += " at " + String.format("%tR %tD", ts, ts);
			}
			getHead().getSecond().setText(s);
		}
	}
	
	private String key() {
		return "Medication-" + getMedicationTimestamp().getMedication().getId() + "-";
	}
	
	private String timeStampkey() {
		return key() + "ts";
	}
	
	private String egrKey() {
		return key() + "egr";
	}

	@Override
	public void saveTo(Bundle bundle) {
		SymptomUtils.saveTo(bundle, egrKey(), getBody().getHead());
		MedicationTimestamp ms = getMedicationTimestamp();
		if(ms != null && ms.getTimestamp() != null)
			bundle.putLong(timeStampkey(), getMedicationTimestamp().getTimestamp().getTimeInMillis());
		super.saveTo(bundle);
		
	}

	@Override
	public void restoreFrom(Bundle bundle) {
		SymptomUtils.restoreFrom(bundle, egrKey(), getBody().getHead());
		long millis = bundle.getLong(timeStampkey());
		if (millis != 0L) { 
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(millis);
			getMedicationTimestamp().setTimestamp(c);
			collapse(true);
		} 
		super.restoreFrom(bundle);
	}
	
	static class MedicationTimestampCollapsePanel extends CollapsePanelBase<EnumRadioGroup<Polar>, Button> {

		private MedicationTimestamp medicationTimestamp;
		private MedicationCollapsePanel parent;

		public MedicationTimestampCollapsePanel(Context context, Medication medication, final Collapsable next) {
			super(context, SymptomUtils.makeNoYes(context), SymptomUtils.makeDatePickerButton(context), next);
			this.medicationTimestamp = new MedicationTimestamp(medication);

			getHead().setOnCheckedChangeListener(new OnCheckedChangeListener<Polar>() {

				@Override
				public void onCheckedChanged(EnumRadioGroup<Polar> group, Polar currentValue, int checkedId) {
					if (currentValue == Polar.NO) {
						medicationTimestamp.setTimestamp(null);
						collapse(true);
						// next is really parent -- we want to collapse
						// the parent
						parent.collapse(true);
					} else {
						collapse(false);
					}
				}
			});

			getBody().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					medicationTimestamp.setTimestamp(c);
					getBody().setText(String.format("%tR %tD", c, c));
					parent.collapse(true);
				}
			});

		}

		public void setParent(MedicationCollapsePanel parent) {
			this.parent = parent;
		}

	}

}

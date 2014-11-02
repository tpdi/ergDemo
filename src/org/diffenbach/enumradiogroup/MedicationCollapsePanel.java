package org.diffenbach.enumradiogroup;

import java.util.Calendar;

import org.diffenbach.android.widgets.EnumRadioGroup;
import org.diffenbach.android.widgets.EnumRadioGroup.OnCheckedChangeListener;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class MedicationCollapsePanel extends
		CollapsePanel<MedicationTimestamp, TextTwoUp, MedicationCollapsePanel.MedicationTimestampCollapsePanel> {

	private MedicationTimestamp medicationTimestamp;
	
	private static TextTwoUp makeTextTwoUp(Context context, Medication medication) {
		return SymptomUtils.makeTextTwoUp(context, "Did you take your " + medication.getName());
	}

	public MedicationCollapsePanel(Context context, Medication medication, Collapsable next) {
		super(context, makeTextTwoUp(context, medication), new MedicationTimestampCollapsePanel(context,
				new MedicationTimestamp(medication), null), next);
		getBody().setParent(this);

	}

	@Override
	public MedicationTimestamp getResult() {
		return medicationTimestamp;
	}

	@Override
	public CollapsePanel<MedicationTimestamp, TextTwoUp, MedicationTimestampCollapsePanel> setValue(
			MedicationTimestamp value) {
		medicationTimestamp = value;
		return this;
	}

	@Override
	protected void onCollapse(boolean collapsed) {
		if (collapsed) {
			String s = getBody().getHead().getCheckedValue().toString();
			if (getBody().getHead().getCheckedValue() == Polar.YES) {
				Calendar ts = getResult().getTimestamp();
				if (ts != null)
					s += " at " + String.format("%tR %tD", ts, ts);
			}
			getHead().getSecond().setText(s);
		}
	}

	static class MedicationTimestampCollapsePanel extends CollapsePanelBase<Polar, Polar, EnumRadioGroup<Polar>, Button> {

		private MedicationTimestamp medicationTimestamp;
		private MedicationCollapsePanel parent;

		public MedicationTimestampCollapsePanel(Context context, MedicationTimestamp pmedicationTimestamp,
				final Collapsable next) {
			super(context, SymptomUtils.makeNoYes(context), SymptomUtils.makeDatePickerButton(context), next);
			this.medicationTimestamp = pmedicationTimestamp;

			getHead().setOnCheckedChangeListener(new OnCheckedChangeListener<Polar>() {

				@Override
				public void onCheckedChanged(EnumRadioGroup<Polar> group, Polar currentValue, int checkedId) {
					if (currentValue == Polar.NO) {
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
					medicationTimestamp.setTimestamp(Calendar.getInstance());
					parent.collapse(true);
				}
			});

		}


		public void setParent(MedicationCollapsePanel parent) {
			this.parent = parent;
		}


		@Override
		public CollapsePanelBase<Polar, Polar, EnumRadioGroup<Polar>, Button> setValue(Polar value) {
			getHead().check(value);
			return this;
		}


		@Override
		public Polar getResult() {
			return getHead().getCheckedValue();
		}

	}

}

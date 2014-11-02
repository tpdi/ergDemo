package org.diffenbach.enumradiogroup;

import java.util.Calendar;

import org.diffenbach.android.widgets.EnumRadioGroup;
import org.diffenbach.android.widgets.EnumRadioGroup.OnCheckedChangeListener;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class MedicationTimestampCollapsePanel 
	extends CollapsePanel<MedicationTimestamp, MedicationTimestamp, EnumRadioGroup<Polar>, Button>{

	private MedicationTimestamp medicationTimestamp;
	
	public MedicationTimestampCollapsePanel(Context context, MedicationTimestamp pmedicationTimestamp,
			EnumRadioGroup<Polar> erg, Button b, final Collapsable next) {
		super(context, erg, b, next);
		this.medicationTimestamp = pmedicationTimestamp;
		getHead().setOnCheckedChangeListener( new OnCheckedChangeListener<Polar>() {

			@Override
			public void onCheckedChanged(EnumRadioGroup<Polar> group,
					Polar currentValue, int checkedId) {
				if (currentValue == Polar.NO) {
					collapse(true);
					// next is really parent -- we want to collapse the parent
					getNext().collapse(true);
				} else {
					collapse(false);
				}
			}
		});
		
		getBody().setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				medicationTimestamp.setTimestamp(Calendar.getInstance());
				getNext().collapse(true);
			}
		});
		
	}

	@Override
	public MedicationTimestamp getResult() {
		return medicationTimestamp;
	}

	@Override
	public CollapsePanel<MedicationTimestamp, MedicationTimestamp, EnumRadioGroup<Polar>, Button> setValue(
			MedicationTimestamp value) {
		if (value.getTimestamp() == null) {
			getHead().check(value.getTimestamp() == null ? Polar.NO : Polar.YES);
		}
		this.medicationTimestamp = value;
		return this;
	}
	
}

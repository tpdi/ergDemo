package org.diffenbach.enumradiogroup;

import org.diffenbach.android.widgets.EnumRadioGroup;

import android.content.Context;
import android.os.Bundle;

public class ERG2CollapsePanel<T extends Enum<T>> extends CollapsePanel<TextTwoUp, EnumRadioGroup<T>> {
	
	private final int questionId;

	public ERG2CollapsePanel(Context context, int questionId, EnumRadioGroup<T> answers, Collapsable next) {
		super(context, SymptomUtils.makeTextTwoUp(context, questionId), answers, next);
		
		this.questionId = questionId;
		
		getBody().setOnCheckedChangeListener( new EnumRadioGroup.OnCheckedChangeListener<T>(){
			@Override
			public void onCheckedChanged(EnumRadioGroup<T> group, T currentValue, int checkedId) {
				getHead().getSecond().setText(group.findViewByEnum(currentValue).getText());
				collapse(true);
			}			
		});
	}

	private String key() {
		return getClass().getName() + questionId;
	}
	
	@Override
	public void saveTo(Bundle bundle) {
		SymptomUtils.saveTo(bundle, key(), getBody());
		super.saveTo(bundle);
		
	}

	@Override
	public void restoreFrom(Bundle bundle) {
		SymptomUtils.restoreFrom(bundle, key(), getBody());
		super.restoreFrom(bundle);
	}

}

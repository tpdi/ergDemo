package org.diffenbach.enumradiogroup;

import org.diffenbach.android.widgets.EnumRadioGroup;

import android.content.Context;

public class ERG2CollapsePanel<T extends Enum<T>> 
	extends CollapsePanel<T, TextTwoUp, EnumRadioGroup<T>> {

	private static TextTwoUp makeTextTwoUp(Context context, String question) {
		TextTwoUp ret = new TextTwoUp(context);
		ret.getFirst().setText(question);
		return ret;
	}
	
	public ERG2CollapsePanel(Context context, String pquestion, EnumRadioGroup<T> answers, Collapsable next) {
		super(context, makeTextTwoUp(context, pquestion), answers, next);
		
		getBody().setOnCheckedChangeListener( new EnumRadioGroup.OnCheckedChangeListener<T>(){
			@Override
			public void onCheckedChanged(EnumRadioGroup<T> group, T currentValue, int checkedId) {
				getHead().getSecond().setText(group.findViewByEnum(currentValue).getText());
				collapse(true);
			}			
		});
	}

	@Override
	public T getResult() {
		return getBody().getCheckedValue();
	}

	@Override
	public CollapsePanel<T, TextTwoUp, EnumRadioGroup<T>> setValue(T value) {
		getBody().check(value);
		return this;
	}

}

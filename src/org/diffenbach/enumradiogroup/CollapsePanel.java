package org.diffenbach.enumradiogroup;

import org.diffenbach.android.widgets.EnumRadioGroup;
import org.diffenbach.android.widgets.EnumRadioGroup.OnCheckedChangeListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CollapsePanel<T extends Enum<T>> extends LinearLayout implements Collapsable {

	private Button makeQuestion(Context context, String question) {
		Button ret = new Button(context);
		ret.setText(question);
		ret.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				collapse(false);
			}
		});
		return ret;
	}
	private final String question;
	private final Button head;
	private final EnumRadioGroup<T> body;
	private final Collapsable next;

	public CollapsePanel(Context context, String pquestion, EnumRadioGroup<T> answers, 
			Collapsable pnext) {
		super(context);
		this.question = pquestion;
		setOrientation(LinearLayout.VERTICAL);
		addView(head = makeQuestion(context, pquestion));
		addView(body = answers);
		this.next = pnext;
		body.setOnCheckedChangeListener( new OnCheckedChangeListener<T>() {

			@Override
			public void onCheckedChanged(EnumRadioGroup<T> group,
					T currentValue, int checkedId) {
				head.setText(question + "  " + group.findViewByEnum(currentValue).getText());
				collapse(true);
				if (next != null) next.collapse(false);
			}
		});
		collapse(true);

	}

	public Button getHead() {
		return head;
	}

	public EnumRadioGroup<T> getBody() {
		return body;
	}
	
	public CollapsePanel<T> collapse(boolean collapsed) {
		body.setVisibility(collapsed? View.GONE : View.VISIBLE);
		return this;
	}
	
	public Enum<T> getValue() {
		return body.getCheckedValue();
	}
	
	public CollapsePanel<T> setValue(T value) {
		body.check(value);
		return this;
	}
	
	public View addAllTo(ViewGroup parent) {
		parent.addView(this);
		return next != null ? next.addAllTo(parent) : parent;
	}

}

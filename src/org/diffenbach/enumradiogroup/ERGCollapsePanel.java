package org.diffenbach.enumradiogroup;

import org.diffenbach.android.widgets.EnumRadioGroup;
import org.diffenbach.android.widgets.EnumRadioGroup.OnCheckedChangeListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ERGCollapsePanel<T extends Enum<T>> extends LinearLayout implements Collapsable {

	private TextTwoUp makeQuestion(Context context, String question) {
		TextTwoUp ret = new TextTwoUp(context);
		ret.getFirst().setText(question);
		ret.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				collapse(! isCollapsed());
			}
		});
		return ret;
	}

	private final TextTwoUp head;
	private final EnumRadioGroup<T> body;
	private final Collapsable next;

	public ERGCollapsePanel(Context context, String pquestion, EnumRadioGroup<T> answers, 
			Collapsable pnext) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		addView(head = makeQuestion(context, pquestion));
		addView(body = answers);
		this.next = pnext;
		body.setOnCheckedChangeListener( new OnCheckedChangeListener<T>() {

			@Override
			public void onCheckedChanged(EnumRadioGroup<T> group,
					T currentValue, int checkedId) {
				head.getSecond().setText(group.findViewByEnum(currentValue).getText());
				collapse(true);
				if (next != null) next.collapse(false);
			}
		});
		collapse(true);
		setVisibility(View.GONE);

	}

	public TextTwoUp getHead() {
		return head;
	}

	public EnumRadioGroup<T> getBody() {
		return body;
	}
	
	public ERGCollapsePanel<T> collapse(boolean collapsed) {
		setVisibility(View.VISIBLE);
		body.setVisibility(collapsed? View.GONE : View.VISIBLE);
		return this;
	}
	
	public boolean isCollapsed() {
		return body.getVisibility() != View.VISIBLE;
	}
	
	public Enum<T> getValue() {
		return body.getCheckedValue();
	}
	
	public ERGCollapsePanel<T> setValue(T value) {
		body.check(value);
		return this;
	}
	
	public View addAllTo(ViewGroup parent) {
		parent.addView(this);
		return next != null ? next.addAllTo(parent) : parent;
	}

}

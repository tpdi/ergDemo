package org.diffenbach.enumradiogroup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class CollapsePanelBase<TR, T, H extends View, B extends View> 
		extends LinearLayout implements Collapsable {
	
	private final H head;
	private final B body;
	private Collapsable next;
	
	public CollapsePanelBase(Context context, H h, B b, Collapsable next ) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		setVisibility(View.GONE);
		addView(head=h);
		addView(body=b);
		this.next = next;
		body.setVisibility(View.GONE);
	}
	
	public H getHead() {
		return head;
	}

	public B getBody() {
		return body;
	}

	public Collapsable getNext() {
		return next;
	}
	
	protected CollapsePanelBase<TR, T, H, B> setNext(Collapsable n) {
		next = n;
		return this;
	}

	public abstract TR getResult();
	
	public abstract CollapsePanelBase<TR, T, H, B> setValue(T value);
	
	public CollapsePanelBase<TR, T, H, B> collapse(boolean collapsed) {
		onCollapse(collapsed);
		setVisibility(View.VISIBLE);
		body.setVisibility(collapsed? View.GONE : View.VISIBLE);
		if (collapsed && getNext() != null) getNext().collapse(false);
		return this;
	}
	
	protected void onCollapse(boolean collapsed) {
		
	}
	
	public boolean isCollapsed() {
		return body.getVisibility() != View.VISIBLE;
	}
	
	public View addAllTo(ViewGroup parent) {
		parent.addView(this);
		return next != null ? next.addAllTo(parent) : parent;
	}

}

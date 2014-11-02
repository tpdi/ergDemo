package org.diffenbach.enumradiogroup;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class CollapsePanelBase<H extends View, B extends View> 
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
	
	protected CollapsePanelBase<H, B> setNext(Collapsable n) {
		next = n;
		return this;
	}
	
	public CollapsePanelBase<H, B> collapse(boolean collapsed) {
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
	
	@Override
	public void saveTo(Bundle bundle) {
		if (next != null) next.saveTo(bundle);
	}

	@Override
	public void restoreFrom(Bundle bundle) {
		if (next != null) next.restoreFrom(bundle);
	}

}

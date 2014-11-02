package org.diffenbach.enumradiogroup;

import android.content.Context;
import android.view.View;

public abstract class CollapsePanel<T, H extends View, B extends View> extends CollapsePanelBase<T, T, H, B> {

	public CollapsePanel(Context context, H h, B b, Collapsable next) {
		super(context, h, b, next);
		
		getHead().setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isCollapsed()) collapse(false);
			}
		});
	}

	

}

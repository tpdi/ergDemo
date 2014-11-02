package org.diffenbach.enumradiogroup;

import android.view.View;
import android.view.ViewGroup;

public interface Collapsable extends SaveRestore {
	Collapsable collapse(boolean collapsed);
	View addAllTo(ViewGroup parent);
}

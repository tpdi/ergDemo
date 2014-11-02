package org.diffenbach.enumradiogroup;

import android.os.Bundle;

public interface SaveRestore {
	void saveTo(Bundle bundle);
	void restoreFrom(Bundle bundle);
}

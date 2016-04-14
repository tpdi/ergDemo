package org.diffenbach;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.diffenbach.android.widgets.EnumRadioGroup;
import org.diffenbach.enumradiogroup.MainActivity;
import org.diffenbach.enumradiogroup.MainActivity.Agreed;
import org.diffenbach.enumradiogroup.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;

@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
    	Activity a = new MainActivity();
        String hello = a.getResources().getString(R.string.app_name);
        assertThat(hello, equalTo("EnumRadioGroup Demo"));
        
        a = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        EnumRadioGroup<MainActivity.Agreed> agreed1 = EnumRadioGroup.findById(a, R.id.agreed1);
        assertEquals(Agreed.YES, agreed1.getCheckedValue());
    }
}
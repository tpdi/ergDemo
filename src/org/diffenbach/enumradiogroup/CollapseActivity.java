package org.diffenbach.enumradiogroup;

import org.diffenbach.android.utils.ViewUtils;
import org.diffenbach.android.widgets.EnumRadioGroup.DisplayPredicate;
import org.diffenbach.android.widgets.multilistener.EnumRadioGroup;
import org.diffenbach.enumradiogroup.MainActivity.Pie;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.widget.LinearLayout;
// for most EnumRadioGroups in this file, we'll be using multilisteners
// so we've imported that class and will fully qualify any single listeners

public class CollapseActivity extends Activity {
	
	public enum Agreed {NO, YES, MAYBE}
	public enum Pie {APPLE, CHERRY, POTATO}
	public enum Sex {REQUIRED, FEMALE, MALE}
	public enum Pet {NONE, CAT, DOG, BOTH} 
	
	// these Ids are only to enable the Robotium tests to easily find the views
	// they are unnecessary for anything else.
	public static final int P_AGREED_ID = 1;
	public static final int P_PIES_ID = 2;
	public static final int P_PETS_ID = 3;
	
	// this references are to the EnumRadioGroup type imported above
	EnumRadioGroup<Agreed> programmaticAgreeds;
	// this reference is explicitly to the multi-listener type
	org.diffenbach.android.widgets.multilistener.EnumRadioGroup<Pie> programmaticPies;
	// this reference is explicitly to the single-listener type
	org.diffenbach.android.widgets.EnumRadioGroup<Pet> pets;
	
	DisplayPredicate<Pie>[] pieFilters;
	int pieFilterOffset = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Medication medication = new Medication("Morphine");
		 
		ERG2CollapsePanel<Agreed> cp = 
			new ERG2CollapsePanel<Agreed>(this, "Do you agree?", 
				new EnumRadioGroup<Agreed>(
						this, 							
						Agreed.NO, 						
						R.array.agreed_without_no),
						
			new MedicationCollapsePanel(this, medication, 
					
			new ERG2CollapsePanel<Pie>(this, "What pie is your favorite", 
				 new EnumRadioGroup<Pie>(
						 this,							// context
						 Pie.POTATO,                    // the default button we clear to
						 R.array.pie, 					// a list of localized names for the buttons
						 org.diffenbach.android.widgets.R.layout.wrapped_radio_button),
			
			new ERG2CollapsePanel<Pet>(this, "What kind of pets do you have", 
				new EnumRadioGroup<Pet>(this, Pet.NONE, R.array.pet, 
							R.layout.wrapped_radio_button),
			null))));
		
			
			setContentView(cp.collapse(false).addAllTo(
							ViewUtils.setOrientation(LinearLayout.VERTICAL, new LinearLayout(this))));
			

			
		
	}

	// This is a generic method, so it properly handles EnumRadioGroups 
	// parameterized on any Enum type. Adding the <T extends Enum<T>> is 
	// the price you pay for calling new OnCheckChangedListener<T> in the method.
	
	// If, as this example, you've imported the multi-listener type 
	// org.diffenbach.android.widgets.multilistener.EnumRadioGroup<T>
	// you may also want methods to take the (single listener) base type
	// in that case, as the multi- and single-listener types have the same unqualified name
	// you need to fully qualify the name of the single-listener type:
	// org.diffenbach.android.widgets.EnumRadioGroup<T>
	private <T extends Enum<T>> void setUpRadioGroupCallback( org.diffenbach.android.widgets.EnumRadioGroup<T> erg, final int textViewid) {
		erg.setOnCheckedChangeListener( new EnumRadioGroup.OnCheckedChangeListener<T>() {

			// Please note that even for multi-listener, the type of the EnumRadioGroup
			// passed back to the listener is always the  base single listener type
			// (org.diffenbach.android.widgets.EnumRadioGroup).
			// This allows the same listener to be freely used with both single and multi types,
			// at the cost of removing (safe) access to the add/set multi listener methods.
			// It seems reasonable that you're not likely going to want to add a listener
			// in a listener callback (though you infrequently  might want to replace it).
			@Override
			public void onCheckedChanged(org.diffenbach.android.widgets.EnumRadioGroup<T> group, T currentValue, int checkedId) {
				
				// we're given the current value, for most things that's all we'll need
				String currentValueName = currentValue.toString() ;
				
				// Getting the (possibly translated) label is about the only reason
				// to ever get the child RadioButtons
				// If you need to do this, findViewByEnum is a (typed) convenience function:
				// But note the RadioButtons themselves are not generic.
				RadioButton currentValueRadioButton = group.findViewByEnum(currentValue);
				
				String currentValueString = currentValueRadioButton.getText().toString();
				
				((TextView) CollapseActivity.this.findViewById(textViewid))
					.setText(String.format("%s (%s) (%sdefault) id: %d", 
							currentValueName, currentValueString, 
							group.isSetToDefault() ? "" : "not ", checkedId)); 
			}
		});
	}
	
	// Button callback
	public void clear(View v) {
	
		// Clearing an EnumRadioGroup resets it to the default you
		// specified at construction. So the Group always has a value
		// and that value is always strongly typed.
		
		// You can clear it as a RadioGroup, calling the overridden clearCheck:
		((RadioGroup) findViewById(R.id.agreed1)).clearCheck();
		
		// Or you can get it with the type-inferring function, EnumRadioGroup.findById
		// and clear it using the overridden RadioGroup method:
		EnumRadioGroup.findById(this, R.id.sex).check(-1);
		
		// But preferably, you'd use the type inferring function and clearCheck
		// to reset the group to the default:
		EnumRadioGroup.findById(this, R.id.sex).clearCheck();
		
		// Or you can set it to any enum constant of its enum type;
		// set it with an enum value, and type-safety
		// means you can't set it to any non-existing value.
		programmaticAgreeds.check(Agreed.NO);
		
		// so this correctly won't work
		//programmaticAgreeds.check(Sex.FEMALE);
		
		// And you can get it as a value too:
		Pie selected = programmaticPies.getCheckedValue();
		// (for efficiency, we should put Pie.values() in a local, but...)
		Pie next = Pie.values()[ (selected.ordinal() + 1) % Pie.values().length];
		programmaticPies.check(next);
		
		pets.clearCheck();
		pets.findViewByEnum(Pet.NONE).setEnabled(! pets.findViewByEnum(Pet.NONE).isEnabled());
		pets.findViewByEnum(Pet.DOG).setTextColor(pets.findViewByEnum(Pet.DOG).getTextColors().getDefaultColor() == Color.RED ? Color.BLACK : Color.RED);
	}
	
	// Button callback
	public void changeFilter(View v) {
		// we can filter a group after it's created
		pieFilterOffset = (pieFilterOffset + 1) % pieFilters.length;
		EnumRadioGroup.DisplayPredicate<Pie> predicate = pieFilters[pieFilterOffset];
		// calling toString on a predicate gives its English description
		((TextView) findViewById(R.id.p_pies_includes)).setText(predicate.toString());
		programmaticPies.filter(predicate);
	}

	// just a convenience function, to insert a programmatic EnumRadioGroup
	// in its parent widget
	private void addViewToWrapper(int parentId, int index, View child) {
		ViewGroup parent = (ViewGroup) findViewById(parentId);
		parent.addView(child, index);
	}
	

}

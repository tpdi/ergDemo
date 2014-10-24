package org.diffenbach.enumradiogroup;

import static org.diffenbach.android.utils.ViewUtils.setId;
import static org.diffenbach.android.utils.ViewUtils.setOrientation;

import org.diffenbach.android.widgets.EnumRadioGroup.DisplayPredicate;
import org.diffenbach.android.widgets.multilistener.EnumRadioGroup;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.widget.LinearLayout;
// for most EnumRadioGroups in this file, we'll be using multilisteners
// so we've imported that class and will fully qualify any single listeners

public class MainActivity extends Activity {
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addViewToWrapper(R.id.p_agreed_wrapper, 0, programmaticAgreeds = 
				// Add a view with a string array that filters what RadioButtons are visible;
				// a layout for the RadioButtons will be selected based on the
				// orientation of the EnumRadioGroup.
				
				// But using the name array to filter is a poor practice
				// only allowed to make it easier for users creating the Group from XML.
				// In programmatic use, just call filter (see below for an example)...
				
				//              Enum type
				new EnumRadioGroup<Agreed>(
						this, 							// context
						Agreed.NO, 						// the default button we clear to
						R.array.agreed_without_no));	// a list of localized names for the buttons
		
		addViewToWrapper(R.id.p_pies_wrapper, 0, programmaticPies =
				// or add a layout id for the RadioButtons
				 new EnumRadioGroup<Pie>(
						 this,							// context
						 Pie.POTATO,                    // the default button we clear to
						 R.array.pie, 					// a list of localized names for the buttons
						 org.diffenbach.android.widgets.R.layout.wrapped_radio_button) // the button layout
						 
						 // Since filter returns this, we can (essentially) filter at creation,
						 // before we assign to a variable.
					.filter(EnumRadioGroup.includeAllBut(Pie.APPLE))	 
				); 
		
		
		setId(P_AGREED_ID, programmaticAgreeds);
		// and then set the EnumRadioGroup's orientation match the layout.
		setId(P_PIES_ID, programmaticPies).setOrientation(LinearLayout.HORIZONTAL);
		
		
		
		// all the comments make it look too complicated, so let me show it again:
		
		pets = 	new org.diffenbach.android.widgets.EnumRadioGroup<Pet>(this, Pet.NONE, R.array.pet, 
						R.layout.wrapped_radio_button);
		
		
		//pets.setOrientation(LinearLayout.HORIZONTAL);
		addViewToWrapper(R.id.pets, 1, setOrientation(LinearLayout.HORIZONTAL, setId(P_PETS_ID, pets)));
		
	
		
		// See the setUpRadioGroupCallback for setting up callbacks.
		// EnumRadioGroup.findById is a somewhat typesafe way to get an EnumRadioGroup.
		// note that we *must* specify the type parameter in the call to findById
		// either explicitly, by assigning to a type, or explicitly.
		// The funny looking  "EnumRadioGroup.<Sex> findById" sets it explicitly
		// only because the call to setUpRadioGroupCallback needs it.
		// alternatively, we can do this: 
		
		org.diffenbach.android.widgets.EnumRadioGroup<Agreed> agreed 
			= EnumRadioGroup.findById(this, R.id.agreed1);
		setUpRadioGroupCallback( agreed, R.id.agreed1_text);
		
		// setUpRadioGroupCallback needs the actual type because it news up a generic callback. 
		
		//setUpRadioGroupCallback(EnumRadioGroup.<Agreed>findById(this, R.id.agreed1), R.id.agreed1_text);
		setUpRadioGroupCallback(EnumRadioGroup.<org.diffenbach.android.widgets.EnumRadioGroup<Sex>> findById( this, R.id.sex), R.id.sex_text);
		setUpRadioGroupCallback(programmaticAgreeds, R.id.p_agreed_text);
		setUpRadioGroupCallback(programmaticPies, R.id.p_pies_text);
		setUpRadioGroupCallback(pets, R.id.p_pets_text);
		
		// we can make, keep, and reuse references to typed filters 
		// that take the Enum class, EnumSets, or Enum constants
		pieFilters = EnumRadioGroup.makeDisplayPredicateArray(
				EnumRadioGroup.includeAll(Pie.class),
				EnumRadioGroup.include(Pie.APPLE, Pie.CHERRY),
				EnumRadioGroup.includeAllBut(Pie.APPLE),
				EnumRadioGroup.includeAllBut(Pie.APPLE, Pie.CHERRY)
				);  
		
		// As filters are typed, so this correctly won't work:
		// programmaticAgreeds.filter(pieFilters[0]);
		// Neither will this:
		// programmaticAgreeds.filter(EnumRadioGroup.includeAll(Pie.class));
		// Or this:
		// programmaticAgreeds.filter(EnumRadioGroup.includeAllBut(Pie.APPLE));
		
		TextView pieLabel = new TextView(this); // notice it has no padding, unlike the XML Views
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
		pieLabel.setPadding(px, px, px/2, px);
		pieLabel.setText(R.string.pielabel); 
		programmaticPies.addView(pieLabel, 0);
		
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
				
				((TextView) MainActivity.this.findViewById(textViewid))
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
		
		// Or you an get it with the type-inferring function, EnumRadioGroup.findById
		// and clear it using the overridden RadioGroup method:
		EnumRadioGroup.findById(this, R.id.sex).check(-1);
		
		//Preferably, you'd use the type inferring function and clearCheck:
		EnumRadioGroup.findById(this, R.id.sex).clearCheck();
		
		// Or you can set it to any enum constant of its enum type;
		// set it with an enum value, and type-safety
		// means you can't set it to the a non-existing value.
		programmaticAgreeds.check(Agreed.NO);
		
		// And you can get it as a value too:
		Pie selected = programmaticPies.getCheckedValue();
		// for efficiency, we should put Pie.values() in a local, but...
		Pie next = Pie.values()[ (selected.ordinal() + 1) % Pie.values().length];
		programmaticPies.check(next);
		
		pets.clearCheck();
	}
	
	// Button callback
	public void changeFilter(View v) {
		// we can filter a group after it's created
		pieFilterOffset = (pieFilterOffset + 1) % pieFilters.length;
		EnumRadioGroup.DisplayPredicate<Pie> predicate = pieFilters[pieFilterOffset];
		((TextView) findViewById(R.id.p_pies_includes)).setText(predicate.toString());
		programmaticPies.filter(predicate);
	}

	// just a convenience function, to insert a programmatic EnumRadioGroup
	private void addViewToWrapper( int parentId, int index, View child) {
		ViewGroup parent = (ViewGroup) findViewById(parentId);
		parent.addView(child, index);
	}
	

}

package org.diffenbach.enumradiogroup;

import java.util.Calendar;

public class MedicationTimestamp {
	
	private Medication medication;
	private Calendar timestamp;
	
	public MedicationTimestamp(Medication medication, Calendar timestamp) {
		super();
		this.medication = medication;
		this.timestamp = timestamp;
	}
	
	public MedicationTimestamp(Medication medication) {
		this(medication, null);
	}

	public Medication getMedication() {
		return medication;
	}

	public void setMedication(Medication medication) {
		this.medication = medication;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

}

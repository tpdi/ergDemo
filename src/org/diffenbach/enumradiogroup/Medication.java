package org.diffenbach.enumradiogroup;

public class Medication {
	private int id;
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public Medication(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

}

package recipeapplication.components;

public class Name {
	private String first;
	private String last;

	public Name() {

	}

	public Name(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		Name theName = (Name) obj;
		return (this.first.equals(theName.getFirst()) && this.last.equals(theName.getLast()));
	}
}

package components;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

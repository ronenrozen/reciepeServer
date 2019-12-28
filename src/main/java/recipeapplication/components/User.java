package recipeapplication.components;

import javax.validation.constraints.NotNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
	
	@NotNull
	private String id;
	
	@NotNull
	private Role role;
	
	@NotNull
	private Name name;

	public User() {
	}

	public User(String id, Role role, Name name) {
		super();
		this.id = id;
		this.role = role;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
}

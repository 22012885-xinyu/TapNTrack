/**
 * 
 * I declare that this code was written by me, xandr. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: xandra
 * Student ID: 22022591
 * Date created: 2024-Oct-27 10:27:55 pm 
 * 
 */
package fyp.admin;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * @author xandr
 *
 */
@Entity
public class Member {
	/*
	 * @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) private
	 * List<History> histories;
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="Please select your avatar!")
	private String avatar;
	
	@NotEmpty(message="Username cannot be empty!")
	@Size(min=2, max=50, message="Username must be between 5 to 50 characters long")
	private String username;
	
	@NotEmpty(message="Password cannot be empty!")
	@Size(min=1, max=100, message="Password must be between 5 to 50 characters long")
	private String password;
	
	@NotEmpty(message="Email cannot be empty!")
	@Size(min=5, max=50, message="Email must be valid!")
	private String email;
	
	private int points=0;

	
	private String role;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

/*	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	} */

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	@ElementCollection
	private List<String> history = new ArrayList<>();

	public List<String> getHistory() {
	    return history;
	}

	public void addHistoryEntry(String entry) {
	    this.history.add(entry);
	}


}

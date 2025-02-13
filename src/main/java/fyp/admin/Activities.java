/**
 * 
 * I declare that this code was written by me, xandr. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: xandra
 * Student ID: 22022591
 * Date created: 2024-Nov-13 4:14:44 pm 
 * 
 */
package fyp.admin;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author xandr
 *
 */
@Entity
public class Activities {
	@ManyToMany
	private List<Member> members;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String imagesName;

	@NotEmpty(message = "Please select your diploma!")
	private String diploma;

	@NotEmpty(message = "Please enter your booth!")
	private String booth;

	@NotEmpty(message = "Please enter the activity!")
	private String activity;

	@NotEmpty(message = "Please enter the activity's description!")
	private String description;

	@NotNull(message = "Please enter the amount of points to reward for the activity!")
	@Min(value = 10, message = "Minimum point is 10!")
	private int pointsRewarded;

	private boolean participated;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagesName() {
		return imagesName;
	}

	public void setImagesName(String imagesName) {
		this.imagesName = imagesName;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getBooth() {
		return booth;
	}

	public void setBooth(String booth) {
		this.booth = booth;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPointsRewarded() {
		return pointsRewarded;
	}

	public void setPointsRewarded(int pointsRewarded) {
		this.pointsRewarded = pointsRewarded;
	}

	public boolean isParticipated() {
		return participated;
	}

	public void setParticipated(boolean participated) {
		this.participated = participated;
	}
}
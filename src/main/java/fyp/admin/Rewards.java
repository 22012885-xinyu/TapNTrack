/**
 * 
 * I declare that this code was written by me, ${user}. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: 
 * Student ID: 
 * Date created: ${id:date('YYYY-MMM-dd')} ${time} 
 * 
 */

package fyp.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
public class Rewards {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rewardsId;
	
	@NotBlank(message="Description is required")
	@Size(min=10, max=150, message="Description should be between 10 and 150 characters")
	private String description;
	
	@Positive(message="Quantity should be a positive whole number")
	private int quantity;
	
	@Positive(message="Points Required should be a positive whole number")
	private int pointsRequired;
	
	@NotBlank(message="Status is required")
	private String status;
		
	private String imgName;
	
	public int getRewardsId() {
		return rewardsId;
	}
	
	public void setRewardsId(int rewardsId) {
		this.rewardsId = rewardsId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPointsRequired() {
		return pointsRequired;
	}
	
	public void setPointsRequired(int pointsRequired) {
		this.pointsRequired = pointsRequired;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getImgName() {
		return imgName;
	}
	
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

}

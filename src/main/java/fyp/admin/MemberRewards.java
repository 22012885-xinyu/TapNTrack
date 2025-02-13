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

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;

@Entity
public class MemberRewards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberRewardsId;
	
	@Positive(message = "Member Points must be a positive number.")
	private int memberPoints;
	
	@Positive(message = "Redeemed quantity must be a positive number.")
	private int redeemedQty;
	
	private LocalDate redeemedDate;
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "rewards_id", nullable = false)
	private Rewards rewards;
	
	public int getMemberRewardsId() {
		return memberRewardsId;
	}
	
	public void setMemberRewardsId(int memberRewardsId) {
		this.memberRewardsId = memberRewardsId;
	}
	
	public int getMemberPoints() {
		return memberPoints;
	}
	
	public void setMemberPoints(int memberPoints) {
		this.memberPoints = memberPoints;
	}
	
	public int getRedeemedQty() {
		return redeemedQty;
	}
	
	public void setRedeemedQty(int redeemedQty) {
		this.redeemedQty = redeemedQty;
	}
	
	public LocalDate getRedeemedDate() {
		return redeemedDate;
	}
	
	public void setRedeemedDate(LocalDate redeemedDate) {
		this.redeemedDate = redeemedDate;
	}
	
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	public Rewards getRewards() {
		return rewards;
	}
	
	public void setRewards(Rewards rewards) {
		this.rewards = rewards;
	}
}

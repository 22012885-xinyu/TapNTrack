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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberRewardsService {

	@Autowired
	private MemberRewardsRepository memberRewardsRepository;
	
	public MemberRewardsService(MemberRewardsRepository memberRewardsRepository) {
		this.memberRewardsRepository = memberRewardsRepository;
		
	}
	
	public List<MemberRewards>getRewardsByMember(Member member){
		return memberRewardsRepository.findByMember(member);
	}

	public void save(MemberRewards memberRewards) {
		memberRewardsRepository.save(memberRewards);
	}
	
	public List<MemberRewards> getAllMemberRewards(){
		return memberRewardsRepository.findAll();
	}
	
	public MemberRewards getMemberRewardById(int id) {
		return memberRewardsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid MemberReward ID: " + id));
	}
	
	public void deleteMemberRewardById(int id) {
		memberRewardsRepository.deleteById(id);
	}
}

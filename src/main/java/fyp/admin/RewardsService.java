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
public class RewardsService {

	@Autowired
	private RewardsRepository rewardsRepository;
	
	public List<Rewards> getAllRewards(){
		return rewardsRepository.findAll();
	}
	
	public List<Rewards> getRewardsSortedByPointsAsc(){
		return rewardsRepository.findAllByOrderByPointsRequiredAsc();
	}
	
	public List<Rewards> getRewardsSortedByPointsDesc(){
		return rewardsRepository.findAllByOrderByPointsRequiredDesc();
	}
	
	public boolean existByDescription(String description) {
		return rewardsRepository.findByDescription(description) != null;
	}
	
	public void save(Rewards rewards) {
		rewardsRepository.save(rewards);
	}
}

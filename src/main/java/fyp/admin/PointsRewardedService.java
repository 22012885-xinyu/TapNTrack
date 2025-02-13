package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PointsRewardedService {

	@Autowired
	private PointsRewardedRepository pointsRewardedRepository;

	// Original calculateTotalPoints method
	public Integer calculateTotalPoints(Member member) {
		List<PointsRewarded> pointsHistory = pointsRewardedRepository.findByMember(member);

		if (pointsHistory == null || pointsHistory.isEmpty()) {
			return 0; // No points available
		}

		return pointsHistory.stream().mapToInt(pr -> {
			try {
				return Integer.parseInt(pr.getPointsRewardedForm());
			} catch (NumberFormatException e) {
				System.err.println("Invalid points value: " + pr.getPointsRewardedForm());
				return 0;
			}
		}).sum();
	}

	// Original save method
	public void save(PointsRewarded pointsRewarded) {
		if (pointsRewarded == null || pointsRewarded.getMember() == null
				|| pointsRewarded.getPointsRewardedForm() == null) {
			throw new IllegalArgumentException("Invalid PointsRewarded object");
		}

		pointsRewardedRepository.save(pointsRewarded);
	}

	// Original savePointsRewarded method
	public void savePointsRewarded(PointsRewarded pointsRewarded) {
		pointsRewardedRepository.save(pointsRewarded);
	}

	// Original getMemberPointsHistory method
	public List<PointsRewarded> getMemberPointsHistory(Member member) {
		return pointsRewardedRepository.findByMember(member);
	}

	// New method to log points for debugging
	public void logPoints(Member member) {
		List<PointsRewarded> pointsHistory = pointsRewardedRepository.findByMember(member);

		if (pointsHistory == null || pointsHistory.isEmpty()) {
			System.out.println("No points history found for member: " + member.getId());
		} else {
			pointsHistory.forEach(pr -> System.out.println("PointsRewarded ID: " + pr.getId() + ", Points: "
					+ pr.getPointsRewardedForm() + ", Member ID: " + pr.getMember().getId()));
		}
	}
}

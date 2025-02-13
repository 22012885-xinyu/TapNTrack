package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.security.Principal;

@RestController
public class PointsApiController {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PointsRewardedService pointsRewardedService;

	@GetMapping("/api/member/points")
	public Map<String, Integer> getMemberPoints(Principal principal, @RequestParam(required = false) Integer memberId) {
	    Map<String, Integer> response = new HashMap<>();
	    Member member = null;

	    if (memberId != null) {
	        member = memberRepository.findById(memberId).orElse(null);
	    } else if (principal != null) {
	        String username = principal.getName();
	        member = memberRepository.findByUsername(username);
	    }

	    if (member != null) {
	        int totalPoints = pointsRewardedService.calculateTotalPoints(member);
	        response.put("totalPoints", totalPoints);
	    } else {
	        response.put("totalPoints", 0);
	    }

	    return response;
	}

}

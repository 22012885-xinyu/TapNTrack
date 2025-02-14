package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class PointsRewardedController {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private PointsRewardedService pointsRewardedService;

	@Autowired
	private PointsRewardedRepository pointsRewardedRepository;

	@GetMapping("/pointsRewarded")
	public String showPointsRewardedForm(@RequestParam(value = "memberId", required = false) Integer memberId,
			Model model) {
		if (memberId == null) {
			model.addAttribute("errorMessage", "Member ID is missing.");
			return "error"; // Redirect to a generic error page
		}

		Member member = memberRepository.findById(memberId).orElse(null);
		if (member == null) {
			model.addAttribute("errorMessage", "Member not found.");
			return "error";
		}

		PointsRewarded pointsRewarded = new PointsRewarded();
		pointsRewarded.setMember(member); // Set the member in PointsRewarded

		model.addAttribute("member", member);
		model.addAttribute("pointsRewarded", pointsRewarded);

		List<Activities> activities = activityService.getAllActivities();
		model.addAttribute("activitiesList", activities);

		return "pointsRewarded"; // Render the pointsRewarded.html template
	}

	@PostMapping("/pointsRewarded/save")
	public String savePoints(@ModelAttribute PointsRewarded pointsRewarded, RedirectAttributes redirectAttributes) {
		Member member = pointsRewarded.getMember();
		if (member != null) {
			Member existingMember = memberRepository.findById(member.getId()).orElse(null);

			if (existingMember == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "Member not found.");
				return "redirect:/error"; // Redirect to a proper error page
			}

			try {
				int additionalPoints = Integer.parseInt(pointsRewarded.getPointsRewardedForm());

				// Update the member's points
				existingMember.setPoints(existingMember.getPoints() + additionalPoints);

				// Save the updated member
				memberRepository.save(existingMember);

				// Save the points rewarded record
				pointsRewardedService.save(pointsRewarded);

				// Add success message
				redirectAttributes.addFlashAttribute("successMessage", "Points rewarded successfully!");
			} catch (NumberFormatException e) {
				// Handle invalid input for points
				redirectAttributes.addFlashAttribute("errorMessage",
						"Invalid points value. Please enter a valid number.");
			}
		} else {
			// Handle invalid or missing member details
			redirectAttributes.addFlashAttribute("errorMessage", "Invalid member details. Please try again.");
		}

		// Redirect back to the index page
		return "redirect:/"; // Redirect to index.html
	}

	@GetMapping("/history")
	public String showHistoryPage(Model model, Principal principal) {
		String username = principal.getName();
		Member member = memberRepository.findByUsername(username);

		if (member == null) {
			model.addAttribute("error", "Member not found.");
			return "error";
		}

		// Fetch points history
		List<PointsRewarded> pointsHistory = pointsRewardedRepository.findByMember(member);
		model.addAttribute("listPointsRewarded", pointsHistory);

		// Unwrap total points and handle null values
		Integer totalPoints = pointsRewardedService.calculateTotalPoints(member);
		model.addAttribute("memberPoints", totalPoints != null ? totalPoints : 0);

		return "history"; // Return the correct template
	}

}

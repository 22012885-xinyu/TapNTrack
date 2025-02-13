package fyp.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@Autowired
	private MemberRepository memberRepository;

	@GetMapping("/")
	public String getLeaderboard(Model model) {
		// Fetch all members
		List<Member> members = memberRepository.findAll();

		// Filter only ROLE_USER and sort by points in descending order
		List<Member> topMembers = members.stream().filter(member -> "ROLE_USER".equals(member.getRole())) // Include
																											// only
																											// ROLE_USER
				.sorted((m1, m2) -> Integer.compare(m2.getPoints(), m1.getPoints())) // Sort by points (desc)
				.collect(Collectors.toList()); // Collect to list

		// Add the sorted list to the model
		model.addAttribute("topMembers", topMembers);

		return "index"; // Thymeleaf template for leaderboard
	}

	@GetMapping("/leaderboard")
	public String leaderboard(Model model) {
		// Fetch all members and sort by points in descending order
		List<Member> topMembers = memberRepository.findAll().stream()
				.sorted((m1, m2) -> Integer.compare(m2.getPoints(), m1.getPoints())) // Sort by points (desc)
				.collect(Collectors.toList()); // Collect to list

		// Add the sorted list to the model
		model.addAttribute("topMembers", topMembers);

		return "leaderboard"; // Thymeleaf template for leaderboard
	}


	@GetMapping("/403")
	public String error403() {
		return "403"; // Access denied page
	}
}

/**
 * 
 * I declare that this code was written by me, xandr. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: xandra
 * Student ID: 22022591
 * Date created: 2024-Oct-27 10:24:48 pm 
 * 
 */
package fyp.admin;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


/**
 * @author xandr
 *
 */
@Controller
public class MemberController {
	@Autowired
	private MemberRepository memberRepository;
	
	 @Autowired
	private MemberDetailsService memberDetailsService;
	
	 @GetMapping("/members")
	 public String viewMembers(Model model) {
	     List<Member> listMembers = memberRepository.findAll(); // Fetch all members
	     listMembers.sort((m1, m2) -> Integer.compare(m2.getPoints(), m1.getPoints())); // Sort the list by points in descending order
	     model.addAttribute("listMembers", listMembers); // Add the sorted list to the model
	     
	     // Return the Thymeleaf template name
	     return "view_member"; // This is your leaderboard page
	 }

	
	 @GetMapping("members/add")
	    public String showAddMemberForm(Model model) {
	        model.addAttribute("member", new Member());
	        return "add_member";
	    }

	 @PostMapping("/members/save")
		public String saveMember(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult, Model model) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("error", "Validation failed. Please check the form fields.");
				return "add_member";
			}

			// Check if username already exists
			if (memberDetailsService.usernameExists(member.getUsername())) {
				model.addAttribute("errorMessage", "Username already taken. Please choose another one.");
				return "add_member";
			}

			// Encrypt the password
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			member.setPassword(passwordEncoder.encode(member.getPassword()));

			// Assign default role if not provided
			if (member.getRole() == null
					|| (!member.getRole().equals("ROLE_ADMIN") && !member.getRole().equals("ROLE_USER"))) {
				member.setRole("ROLE_USER");
			}

			// Save Member
			Member savedMember = memberRepository.save(member);

			// Generate Unique Link with Correct Member ID
//			String uniqueLink = "https://tapntrackfyp.onrender.com/members/" + savedMember.getId() + "/addPoints";
			String uniqueLink = "https://tapntrackfyp.onrender.com/members/" + savedMember.getId();

			model.addAttribute("uniqueLink", uniqueLink);
			model.addAttribute("success", "Member successfully added!");
			return "add_member";
		}


	

	@GetMapping("/members/edit/{id}")
	public String editMember(@PathVariable("id") Integer id, Model model) {
		Member member = memberRepository.getReferenceById(id);
		model.addAttribute("member", member);
		return "edit_member";
	}
	
	@PostMapping("/members/edit/{id}")
	public String savedUpdatedMember(@PathVariable("id") Integer id, Member member) {
		member.setPassword(memberRepository.getReferenceById(id).getPassword());
		memberRepository.save(member);
		return "redirect:/";
	}
	
	@GetMapping("/members/delete/{id}")
	public String deleteMember(@PathVariable("id") Integer id) {
		memberRepository.deleteById(id);
		return "redirect:/members";
	}
	
	// View single member
		@GetMapping("/members/{id}")
		public String viewSingleMember(@PathVariable("id") Integer id, Model model) {

			Member member = memberRepository.getReferenceById(id);
			model.addAttribute("member", member);

			return "view_single_member";
		}
		
    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName(); // Get the current logged-in user's username
        Member member = memberRepository.findByUsername(username);
        model.addAttribute("member", member);
        return "profile"; // Thymeleaf template to view profile
    }

    // Add method to edit the current user's profile
    @GetMapping("/profile/edit")
    public String editProfile(Model model, Principal principal) {
        String username = principal.getName();
        Member member = memberRepository.findByUsername(username);
        model.addAttribute("member", member);
        return "edit_profile"; // Thymeleaf template to edit profile
    }

    // Save edited profile details
    @PostMapping("/profile/edit")
    public String saveProfile(@ModelAttribute("member") Member member, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName(); // Get the current logged-in user's username
        Member existingMember = memberRepository.findByUsername(username);
        
        // Make sure the existing member was found
        if (existingMember == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/profile"; // Redirect back to the profile page
        }
        
        // Keep the old password (don't overwrite it)
        member.setPassword(existingMember.getPassword());
        
        // You can also retain other necessary fields like the ID or role
        member.setId(existingMember.getId());  // Don't overwrite the ID field
        
        // Save the updated member details
        memberRepository.save(member); // Save updated profile data
        
        // Redirect back to profile page with a success message
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }
    
    @GetMapping("/members/delete")
    public String deleteAccount(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        if (principal != null) {
            String username = principal.getName(); // Get logged-in user's username
            Member member = memberRepository.findByUsername(username);
            if (member != null) {
                memberRepository.delete(member); // Delete the user account
                
                // Logout user and invalidate session
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }
                
                SecurityContextHolder.clearContext(); // Clear authentication context
                request.getSession().invalidate(); // Invalidate session
            }
        }
        return "redirect:/login?logout"; // Redirect to login page after logout
    }

}


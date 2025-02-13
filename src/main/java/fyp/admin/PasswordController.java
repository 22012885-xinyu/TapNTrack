package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam("username") String username,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        System.out.println("Password reset request received for: " + username);

        Member user = memberRepository.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "change_password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "change_password";
        }

        // Encrypt and update password
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        memberRepository.save(user);

        System.out.println("Password updated successfully for: " + username);
        System.out.println("New encoded password: " + encodedPassword);  // Debugging

        model.addAttribute("success", "Password changed successfully. You can now log in.");
        return "redirect:/login";
    }
}

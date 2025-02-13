package fyp.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.mail.MessagingException;

@Controller
public class EmailController {

    @Autowired
    private EmailSender emailService;

    @Autowired
    private MemberRepository memberRepository;

    // POST request to send OTP
    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam String email, Model model) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            try {
                // Send OTP to email and store it
                emailService.sendOtpEmail(email);
                model.addAttribute("success", "OTP sent successfully. Check your email, if you don't see it in your inbox, do check your spam/junk folder.");
                model.addAttribute("email", email); // Pass email to OTP verification page
                return "otpVerification"; // Redirect to OTP verification page
            } catch (MessagingException e) {
                model.addAttribute("error", "Failed to send OTP. Please try again.");
            }
        } else {
            model.addAttribute("error", "Email not registered!");
        }

        return "sendOtp"; // Stay on the same page with an error message
    }

    // POST request to verify OTP
    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String otpEntered, @RequestParam String email, Model model) {
        // Validate OTP
        if (emailService.verifyOtp(email, otpEntered)) {
            model.addAttribute("success", "OTP verified successfully. You can now reset your password.");
            return "change_password"; // Redirect to password reset page
        } else {
            model.addAttribute("error", "Invalid OTP or OTP expired. Please try again.");
            return "otpVerification"; // Stay on the same page with error message
        }
    }

    // GET request to show the password reset page
    @GetMapping("/changePassword")
    public String changePassword() {
        return "change_password"; // Return the change password page
    }
}

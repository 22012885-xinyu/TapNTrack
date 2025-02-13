package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SENDER_EMAIL = "fyp.tapntrack@gmail.com";
    private final SecureRandom random = new SecureRandom();

    // Store OTPs with timestamps (could be stored in a session or cache for real-world usage)
    private static Map<String, String> otpStorage = new HashMap<>();
    private static Map<String, Long> otpTimestamp = new HashMap<>();

    // Generate a 6-digit OTP
    public String generateOTP() {
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000-999999
        return String.valueOf(otp);
    }

    // Send OTP Email and store it
    public String sendOtpEmail(String recipientEmail) throws MessagingException {
        String otp = generateOTP(); // Generate OTP
        long currentTime = System.currentTimeMillis(); // Get current time in milliseconds

        // Store OTP and timestamp for later verification
        otpStorage.put(recipientEmail, otp);
        otpTimestamp.put(recipientEmail, currentTime);

        // Create email message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(SENDER_EMAIL);
        helper.setTo(recipientEmail);
        helper.setSubject("Your OTP for Password Reset");
        helper.setText("Dear User,\n\nYour OTP for password reset is: " + otp + "\n\nPlease use this OTP within 5 minutes.\n\nRegards,\nTap & Track Team");

        javaMailSender.send(message); // Send email
        System.out.println("OTP sent to " + recipientEmail + ": " + otp); // Logging for debug

        return otp; // Return OTP for logging or other purposes (or remove if using a persistent storage)
    }

    // Verify OTP (with expiry check)
    public boolean verifyOtp(String email, String otpEntered) {
        String storedOtp = otpStorage.get(email);
        Long storedTimestamp = otpTimestamp.get(email);

        System.out.println("Stored OTP: " + storedOtp);
        System.out.println("Entered OTP: " + otpEntered);

        // Check if OTP exists and is within 5 minutes of generation
        if (storedOtp != null && storedTimestamp != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - storedTimestamp <= 300000) { // 300000 ms = 5 minutes
                if (storedOtp.trim().equals(otpEntered.trim())) { // Trim spaces
                    otpStorage.remove(email); // Remove OTP after successful verification
                    otpTimestamp.remove(email); // Clean up timestamp
                    return true;
                }
            } else {
                System.out.println("OTP expired.");
            }
        } else {
            System.out.println("No OTP found for email.");
        }

        return false; // OTP either doesn't exist or expired
    }
}
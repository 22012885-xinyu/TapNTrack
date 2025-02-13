package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(memberRepository.findByUsername(username))
                .map(MemberDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user: " + username));
    }

    // Checks if the username already exists in the repository
    public boolean usernameExists(String username) {
        return memberRepository.existsByUsername(username);
    }

    // Save member with an encrypted password
    public void save(Member member) {  // Renamed from saveMember to save
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encodedPassword);
        }
        memberRepository.save(member);
    }

    // Update the password for a specific username, ensuring it's hashed
    public boolean updatePassword(String username, String newPassword) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            System.out.println("User not found: " + username);  // Debugging message
            return false;
        }

        // Encrypt the new password before saving
        String encodedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        System.out.println("Password updated successfully for: " + username);  // Debugging message
        return true;
    }

    // Check the password for the given user
    public boolean checkPassword(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            return false;
        }
        return passwordEncoder.matches(password, member.getPassword());
    }
}

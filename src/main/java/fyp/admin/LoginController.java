/**
 * 
 * I declare that this code was written by me, xandr. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: xandra
 * Student ID: 22022591
 * Date created: 2024-Oct-26 11:47:24 pm 
 * 
 */
package fyp.admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xandr
 *
 */
@Controller
public class LoginController {
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/forget")
	public String forget() {
		return "forget";
	}

}

/**
 * 
 * I declare that this code was written by me, xandr. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: xandra
 * Student ID: 22022591
 * Date created: 2024-Nov-20 10:48:24 am 
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
public class MoreController {
	@GetMapping("/more")
	public String more() {
		return "more";
	}
	@GetMapping("/avatar")
	public String avatar() {
		return "avatar";
	}
	@GetMapping("/guide")
	public String guide() {
		return "guide";
	}
	@GetMapping("/feedback")
	public String feedback() {
		return "feedback";
	}
}

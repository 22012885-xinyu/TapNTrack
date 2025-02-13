package fyp.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new MemberDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers("/rewards/add", "/rewards/edit/*", "/rewards/delete/*", "/rewards/save",
						"/activities/add", "/activities/edit/*", "/activities/delete/*", "/activities/save", "/members",
						"/members/add", "/members/edit/*", "/members/delete/*", "/members/save", "/login", "/pointsRewarded")
				.hasRole("ADMIN")
				.requestMatchers("/", "/leaderboard", "/history", "/rewards", "/activities", "/more", "/avatar",
						"/profile", "/guide", "/feedback", "/forget", "/changePassword", "/sendOtp", "/verifyOtp",
						"/images/*", "NFC GUIDE.pdf", "TapNTrack User Manual.pdf")
				.permitAll() // Home page is visible without logging in
				.requestMatchers("/bootstrap/*/*").permitAll() // for static resources, visible to all
				.requestMatchers("/images/**").permitAll() // for static resources, visible to all
				.requestMatchers("/uploads/**").permitAll()// for upload images, visible to all
				.requestMatchers("/avatar/**").permitAll()// for avatar images, visible to all
				.anyRequest().authenticated())
				.formLogin((login) -> login.loginPage("/login").permitAll().defaultSuccessUrl("/", true)
						.failureUrl("/login?error=true")) // Ensures error is passed correctly
				.logout((logout) -> logout.logoutSuccessUrl("/"))
				.exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/403"));

		return http.build();
	}
}



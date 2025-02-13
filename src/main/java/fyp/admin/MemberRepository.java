/**
 * 
 * I declare that this code was written by me, xandr. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: xandra
 * Student ID: 22022591
 * Date created: 2024-Oct-27 10:26:46 pm 
 * 
 */
package fyp.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author xandr
 *
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findByUsername(String username);
	@Query("SELECT m FROM Member m ORDER BY m.points DESC")
    List<Member> findAllOrderByPoints();
	boolean existsByUsername(String username);
	Optional<Member> findByEmail(String email);
	Optional<Member> findByUsernameAndEmail(String username, String email);
	Optional<Member> findById(Long id);

	
	
}
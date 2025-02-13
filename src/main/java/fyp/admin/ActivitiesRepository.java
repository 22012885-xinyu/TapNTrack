package fyp.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long> {

	@Query("SELECT a.activity, COUNT(m.id) " + "FROM Activities a " + "JOIN a.members m " + "GROUP BY a.activity")
	List<Object[]> findParticipantCountsByActivity();

	List<Activities> findByParticipated(boolean participated);
}

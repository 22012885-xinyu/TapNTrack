package fyp.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Integer> {
    List<Rewards> findAllByOrderByPointsRequiredAsc();

    List<Rewards> findAllByOrderByPointsRequiredDesc();

    Rewards findByDescription(String description);

    // Custom findById method that directly returns Rewards instead of Optional<Rewards>
    Rewards findById(int rewardsId);
}

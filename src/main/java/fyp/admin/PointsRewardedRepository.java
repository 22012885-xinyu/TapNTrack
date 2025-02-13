package fyp.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PointsRewardedRepository extends JpaRepository<PointsRewarded, Integer> {
    List<PointsRewarded> findByMember(Member member);
    
    List<PointsRewarded> findByMemberId(Integer memberId);

}




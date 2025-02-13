package fyp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

	@Autowired
	private ActivitiesRepository activitiesRepository;

	// Fetch all activities
	public List<Activities> getAllActivities() {
		return activitiesRepository.findAll();
	}

	// Fetch participant counts for each activity
	public List<ParticipationResponse> getParticipationData() {
		List<Object[]> rawData = activitiesRepository.findParticipantCountsByActivity(); // Updated method name

		return rawData.stream().map(data -> new ParticipationResponse((String) data[0], ((Number) data[1]).intValue()))
				.collect(Collectors.toList());
	}

	// Save a new activity
	public void saveActivity(Activities activities) {
		activitiesRepository.save(activities);
	}

	// Delete an activity by ID
	public void deleteActivity(Long id) {
		if (activitiesRepository.existsById(id)) {
			activitiesRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Activity with ID " + id + " does not exist.");
		}
	}

	// Get an activity by ID
	public Activities getActivityById(Long id) {
		return activitiesRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Activity with ID " + id + " not found."));
	}
}

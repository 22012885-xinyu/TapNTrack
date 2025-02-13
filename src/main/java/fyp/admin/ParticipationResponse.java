package fyp.admin;

public class ParticipationResponse {

	private String date;
    private int participantCount;

    public ParticipationResponse(String date, int participantCount) {
        this.date = date;
        this.participantCount = participantCount;
    }

    public String getDate() {
        return date;
    }

    public int getParticipantCount() {
        return participantCount;
    }
}

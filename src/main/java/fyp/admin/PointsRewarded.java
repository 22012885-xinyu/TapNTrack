package fyp.admin;

import jakarta.persistence.*;

@Entity
public class PointsRewarded {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false) // Ensure this relationship is mandatory
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String boothForm;

    private String activityForm;

    private String pointsRewardedForm;

    // Getters and Setters
 
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getBoothForm() {
        return boothForm;
    }

    public void setBoothForm(String boothForm) {
        this.boothForm = boothForm;
    }

    public String getActivityForm() {
        return activityForm;
    }

    public void setActivityForm(String activityForm) {
        this.activityForm = activityForm;
    }

    public String getPointsRewardedForm() {
        return pointsRewardedForm;
    }

    public void setPointsRewardedForm(String pointsRewardedForm) {
        this.pointsRewardedForm = pointsRewardedForm;
    }
}

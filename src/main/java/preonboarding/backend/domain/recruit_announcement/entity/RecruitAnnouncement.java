package preonboarding.backend.domain.recruit_announcement.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import preonboarding.backend.domain.Auditable;
import preonboarding.backend.domain.company.entity.Company;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitAnnouncement extends Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Announcement_ID", updatable = false)
    private Long id;
    @Column(nullable = false)
    private String recruitPosition;
    @Column(nullable = false)
    private String workingArea;
    @Column(nullable = false)
    private Integer compensationForEmployment;
    @Column(nullable = false)
    private String skill;
    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    private Company company;

    public void addAnnounceToCompany(Company company) {
        this.company = company;
        company.getRecruitmentAnnouncementList().add(this);
    }
}

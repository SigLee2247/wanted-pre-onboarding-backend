package preonboarding.backend.domain.recruit_announcement.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import preonboarding.backend.domain.company.entity.Company;
import preonboarding.backend.domain.recruit_announcement.dto.AnnouncementIdResponseDto;
import preonboarding.backend.domain.recruit_announcement.dto.AnnouncementPostRequestDto;
import preonboarding.backend.domain.recruit_announcement.entity.RecruitAnnouncement;
import preonboarding.backend.dto.ResponseDto;

@Component
public class RecruitAnnouncementMapper {

    public RecruitAnnouncement toEntity(AnnouncementPostRequestDto post) {
        return RecruitAnnouncement.builder()
                .recruitPosition(post.getRecruitPosition())
                .content(post.getContent())
                .skill(post.getSkill())
                .company(new Company(post.getCompanyId()))
                .compensationForEmployment(post.getCompensationForEmployment())
                .workingArea(post.getWorkingArea())
                .build();
    }

    public ResponseDto<AnnouncementIdResponseDto> toResponseDto(RecruitAnnouncement result,
            HttpStatus status) {
        return ResponseDto.<AnnouncementIdResponseDto>builder()
                .data(toIdResponseDto(result))
                .status(status)
                .build();
    }

    private AnnouncementIdResponseDto toIdResponseDto(RecruitAnnouncement recruitAnnouncement) {
        return new AnnouncementIdResponseDto(recruitAnnouncement.getId());
    }
}
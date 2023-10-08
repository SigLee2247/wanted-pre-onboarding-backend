package preonboarding.backend.domain.recruit_announcement.service;

import static preonboarding.backend.domain.recruit_announcement.exception.RecruitAnnouncementExceptionCode.ANNOUNCEMENT_NOT_FOUND;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preonboarding.backend.domain.recruit_announcement.entity.RecruitAnnouncement;
import preonboarding.backend.domain.recruit_announcement.repository.RecruitAnnouncementRepository;
import preonboarding.backend.exception.BusinessLogicException;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitAnnouncementService {

    private final RecruitAnnouncementRepository repository;

    public RecruitAnnouncement postAnnouncement(RecruitAnnouncement announcement) {
        return repository.save(announcement);
    }

    public RecruitAnnouncement patchAnnouncement(RecruitAnnouncement announcement) {
        RecruitAnnouncement savedData = validAnnouncement(announcement.getId());

        changeAnnouncement(announcement, savedData);

        return savedData;
    }


    private void changeAnnouncement(RecruitAnnouncement announcement,
            RecruitAnnouncement savedData) {
        Optional.ofNullable(announcement.getSkill()).ifPresent(savedData::setSkill);
        Optional.ofNullable(announcement.getContent()).ifPresent(savedData::setContent);
        Optional.ofNullable(announcement.getWorkingArea()).ifPresent(savedData::setWorkingArea);
        Optional.ofNullable(announcement.getRecruitPosition())
                .ifPresent(savedData::setRecruitPosition);
        Optional.ofNullable(announcement.getCompensationForEmployment())
                .ifPresent(savedData::setCompensationForEmployment);
    }

    private RecruitAnnouncement validAnnouncement(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessLogicException(
                ANNOUNCEMENT_NOT_FOUND));
    }
}

package preonboarding.backend.domain.recruit_announcement.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import preonboarding.backend.domain.recruit_announcement.dto.AnnouncementIdResponseDto;
import preonboarding.backend.domain.recruit_announcement.dto.AnnouncementPatchRequestDto;
import preonboarding.backend.domain.recruit_announcement.dto.AnnouncementPostRequestDto;
import preonboarding.backend.domain.recruit_announcement.entity.RecruitAnnouncement;
import preonboarding.backend.domain.recruit_announcement.mapper.RecruitAnnouncementMapper;
import preonboarding.backend.domain.recruit_announcement.service.RecruitAnnouncementService;
import preonboarding.backend.dto.ResponseDto;
import preonboarding.backend.utils.UriBuilder;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class RecruitAnnouncementController {

    private static final String DEFAULT = "/api/announcement";
    private final RecruitAnnouncementMapper mapper;
    private final RecruitAnnouncementService service;

    @PostMapping
    public ResponseEntity<ResponseDto<AnnouncementIdResponseDto>> postAnnouncement(
            @RequestBody @Valid
            AnnouncementPostRequestDto post) {
        RecruitAnnouncement request = mapper.toEntity(post);

        RecruitAnnouncement result = service.postAnnouncement(request);

        var responseDto = mapper.toResponseDto(result, CREATED);
        URI location = UriBuilder.createUri(DEFAULT, result.getId());

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{announcement-id}")
    public ResponseEntity<ResponseDto<AnnouncementIdResponseDto>> patchAnnouncement(
            @PathVariable("announcement-id") Long announcementId,
            @RequestBody @Valid
            AnnouncementPatchRequestDto patch) {
        patch.setAnnouncementId(announcementId);
        RecruitAnnouncement request = mapper.toEntity(patch);

        RecruitAnnouncement result = service.patchAnnouncement(request);

        var responseDto = mapper.toResponseDto(result, OK);
        URI location = UriBuilder.createUri(DEFAULT, result.getId());

        return ResponseEntity.ok().header("location", location.toString()).body(responseDto);
    }

    @DeleteMapping("/{announcement-id}")
    public ResponseEntity deleteAnnouncement(@PathVariable("announcement-id") Long announcementId) {
        RecruitAnnouncement request = mapper.toEntity(announcementId);

        service.deleteAnnouncement(request);

        return ResponseEntity.noContent().build();
    }
}

package preonboarding.backend.domain.user.controller;

import static org.springframework.http.HttpStatus.CREATED;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import preonboarding.backend.domain.recruit_announcement.entity.RecruitAnnouncement;
import preonboarding.backend.domain.recruit_announcement.mapper.RecruitAnnouncementMapper;
import preonboarding.backend.domain.user.dto.UserIdResponseDto;
import preonboarding.backend.domain.user.dto.UserPostRequestDto;
import preonboarding.backend.domain.user.dto.UserRecruitPostRequestDto;
import preonboarding.backend.domain.user.dto.UserRecruitPostResponseDto;
import preonboarding.backend.domain.user.entity.User;
import preonboarding.backend.domain.user.mapper.UserMapper;
import preonboarding.backend.domain.user.service.UserService;
import preonboarding.backend.dto.ResponseDto;
import preonboarding.backend.utils.UriBuilder;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자 API",description = "사용자 관련 API 존재, 사용자 등록, 채용 공고")
public class UserController {

    private static final String DEFAULT = "/api/users";
    private final UserService service;
    private final UserMapper userMapper;
    private final RecruitAnnouncementMapper announcementMapper;

    @PostMapping
    @ApiResponse(responseCode = "201")
    @Operation(summary = "사용자 등록",description = "사용자 등록, email,username, password 전부 필수")
    public ResponseEntity<ResponseDto<UserIdResponseDto>> postUser(
            @RequestBody @Valid UserPostRequestDto post) {
        User request = userMapper.toEntity(post);

        User result = service.postUser(request);

        ResponseDto<UserIdResponseDto> responseDto = userMapper.toResponseDto(result, CREATED);
        URI location = UriBuilder.createUri(DEFAULT, result.getId());

        return ResponseEntity.created(location).body(responseDto);
    }

    @PostMapping("/recruit/{announcement-id}")
    @Operation(summary = "채용 공고 등록",description = "채용 공고 등록, userId 필수,\n test 시 announcement-id = 1 권장")
    public ResponseEntity<ResponseDto<UserRecruitPostResponseDto>> postRecruit(
            @PathVariable("announcement-id") Long announceId,
            @RequestBody @Valid UserRecruitPostRequestDto post) {
        post.setAnnouncementId(announceId);
        User requestUser = userMapper.toEntity(post.getUserId());
        RecruitAnnouncement requestAnnounce = announcementMapper.toEntity(post.getAnnouncementId());

        User result = service.recruit(requestUser, requestAnnounce);

        ResponseDto<UserRecruitPostResponseDto> responseDto = userMapper.toRecruitResponseDto(
                result, requestAnnounce.getId(), HttpStatus.OK);

        return ResponseEntity.ok(responseDto);
    }
}

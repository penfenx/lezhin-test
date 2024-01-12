package com.lezhin.penfen.controller;

import com.lezhin.penfen.dto.ContentsDislikes;
import com.lezhin.penfen.dto.ContentsLikes;
import com.lezhin.penfen.entity.Contents;
import com.lezhin.penfen.request.ContentsUpdateRequest;
import com.lezhin.penfen.request.ViewLogRequest;
import com.lezhin.penfen.entity.User;
import com.lezhin.penfen.entity.ViewLog;
import com.lezhin.penfen.service.ContentsService;
import com.lezhin.penfen.service.ViewLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contents")
@AllArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;
    private final ViewLogService viewLogService;

    /**
     * 좋아요 수가 가장 많은 상위 3개의 작품을 반환하는 API
     *
     * @return 좋아요 수가 가장 많은 상위 3개의 작품
     */
    @GetMapping("/top-likes")
    public List<ContentsLikes> getTop3ContentsByMostLikes() {
        return contentsService.findTop3ContentsByMostLikes();
    }

    /**
     * 싫어요 수가 가장 많은 상위 3개의 작품을 반환하는 API
     *
     * @return 싫어요 수가 가장 많은 상위 3개의 작품
     */
    @GetMapping("/top-dislikes")
    public List<ContentsDislikes> getTop3ContentsByMostDislikes() {
        return contentsService.findTop3ContentsByMostDislikes();
    }

    /**
     * 특정 콘텐츠를 조회한 사용자 목록을 반환하는 API
     *
     * @param id 조회할 콘텐츠의 ID
     * @param pageable 페이지네이션 정보 객체
     * @return 특정 콘텐츠를 조회한 사용자 목록
     */
    @GetMapping("/{id}/users")
    public List<User> findAllUsersByContentsId(@PathVariable int id, @PageableDefault(size = 10) Pageable pageable) {
        return contentsService.findUsersByContentsId(id, pageable);
    }

    /**
     * 사용자가 콘텐츠를 조회하였을 때, 이를 로그로 기록하는 API
     *
     * @param viewLogRequest 사용자 ID와 콘텐츠 ID를 담은 요청 객체
     * @return 생성되어 저장된 조회 로그
     */
    @PostMapping("/view-log")
    public ViewLog createViewLog(@RequestBody ViewLogRequest viewLogRequest) {
        return viewLogService.save(viewLogRequest.getUserId(), viewLogRequest.getContentsId());
    }

    /**
     * 특정 작품을 유료 혹은 무료로 설정하는 API.
     * 컨텐츠의 'coin' 값이 0일 경우, 작품은 무료로 설정되며, 0 이상일 경우 작품은 유료로 설정됩니다.
     *
     * @param id      유료/무료 설정을 변경하려는 작품의 ID입니다.
     * @param request 작품의 'coin' 값을 변경하기 위한 요청 객체입니다.
     * @return 업데이트된 작품 객체를 반환합니다.
     */
    @PutMapping("/{id}/coin")
    public Contents changeContentsFreeOrPaid(@PathVariable int id, @RequestBody ContentsUpdateRequest request) {
        return contentsService.changeContentsFreeOrPaid(id, request.getCoin());
    }


}

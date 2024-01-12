package com.lezhin.penfen.controller;

import com.lezhin.penfen.entity.User;
import com.lezhin.penfen.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * 성인 작품을 최근 {days}일 동안 {minViews}회 이상 조회한 사용자 목록을 조회하는 API
     *
     * @param days     컨텐츠가 조회된 기간 (일).
     * @param minViews 컨텐츠의 최소 조회 수.
     * @param pageable 페이지네이션 및 정렬을 위한 pageable 객체.
     * @return 주어진 기준을 만족하는 성인 컨텐츠 조회 사용자 리스트.
     */
    @GetMapping("/adult-contents/{days}/{minViews}")
    public List<User> findUsersViewedAdultContents(@PathVariable int days, @PathVariable int minViews, @PageableDefault(size = 10) Pageable pageable) {
        return userService.findUsersViewedAdultContents(days, minViews, pageable);
    }


    /**
     * 최근 {days}일 동안 가입한 사용자들 중, 성인 작품을 {minViews}회 이상 조회한 사용자 목록을 조회하는 API
     *
     * @param days     조회 기간 (일).
     * @param minViews 최소 조회 수.
     * @param pageable 페이지네이션 및 정렬을 위한 pageable 객체.
     * @return 주어진 기준을 만족하는 성인 콘텐츠 조회 사용자 리스트.
     */
    @GetMapping("/recent-users/adult-contents/{days}/{minViews}")
    public List<User> findRecentUsersWithAdultContentsView(@PathVariable int days, @PathVariable int minViews, @PageableDefault(size = 10) Pageable pageable) {
        return userService.findRecentUsersWithAdultContentsView(days, minViews, pageable);
    }


    /**
     * 특정 사용자와 사용자의 모든 평가 및 조회 이력을 삭제하는 API.
     * 이 API는 관리자에 의한 사용자 제거나 회원 탈퇴 등에 사용될 수 있습니다.
     *
     * @param id 사용자 ID. 이 ID에 해당하는 사용자와 사용자의 모든 평가 및 조회 이력이 삭제됩니다.
     * @return HTTP 상태 코드 200 (OK). 해당 사용자와 사용자의 모든 평가 및 조회 이력이 성공적으로 삭제되면 200 상태 코드를 반환합니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAndRelatedInfo(@PathVariable int id) {
        userService.deleteUserAndRelatedInfo(id);
        return ResponseEntity.ok().build();
    }


}

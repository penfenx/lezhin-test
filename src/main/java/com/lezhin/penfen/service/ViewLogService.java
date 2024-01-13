package com.lezhin.penfen.service;

import com.lezhin.penfen.entity.Contents;
import com.lezhin.penfen.entity.User;
import com.lezhin.penfen.entity.ViewLog;
import com.lezhin.penfen.repository.ContentsRepository;
import com.lezhin.penfen.repository.UserRepository;
import com.lezhin.penfen.repository.ViewLogRepository;
import com.lezhin.penfen.type.UserType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class ViewLogService {

    private final ViewLogRepository viewLogRepository;
    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;

    public ViewLog save(int userId, int contentsId) {

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist!");
        }

        Contents contents = contentsRepository.findById(contentsId);
        if (contents == null) {
            throw new IllegalArgumentException("Contents does not exist!");
        }

        // 성인이 아닌 유저가 성인 콘텐츠를 조회하려고 할 때, 로그 기록
        if(user.getType() != UserType.ADULT && contents.isAdult()) {
            log.error("User {} is not adult but trying to view adult contents {}", user.getUserName(), contents.getContentsName());
        }

        ViewLog viewLog = new ViewLog();
        viewLog.setUserId(userId);
        viewLog.setContentsId(contentsId);
        viewLog.setViewDate(new Date());

        return viewLogRepository.save(viewLog);
    }

}

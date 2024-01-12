package com.lezhin.penfen.service;

import com.lezhin.penfen.entity.ViewLog;
import com.lezhin.penfen.repository.ContentsRepository;
import com.lezhin.penfen.repository.UserRepository;
import com.lezhin.penfen.repository.ViewLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class ViewLogService {

    private final ViewLogRepository viewLogRepository;
    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;

    public ViewLog save(int userId, int contentsId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User does not exist!");
        }

        if (!contentsRepository.existsById(contentsId)) {
            throw new IllegalArgumentException("Contents does not exist!");
        }

        ViewLog viewLog = new ViewLog();
        viewLog.setUserId(userId);
        viewLog.setContentsId(contentsId);
        viewLog.setViewDate(new Date());

        return viewLogRepository.save(viewLog);
    }

}

package com.lezhin.penfen.service;

import com.lezhin.penfen.entity.User;
import com.lezhin.penfen.repository.ReviewRepository;
import com.lezhin.penfen.repository.UserRepository;
import com.lezhin.penfen.repository.ViewLogRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final ReviewRepository reviewRepository;
    private final ViewLogRepository viewLogRepository;
    private final UserRepository userRepository;


    public User getUserById(int id) {
        return userRepository.findById(id);
    }


    public List<User> findUsersViewedAdultContents(int days, int minViews, Pageable pageable) {
        return userRepository.findUsersViewedAdultContents(days, minViews, pageable);
    }

    public List<User> findRecentUsersWithAdultContentsView(int days, int minViews, Pageable pageable) {
        return userRepository.findRecentUsersWithAdultContentsView(days, minViews, pageable);
    }

    @Transactional
    public void deleteUserAndRelatedInfo(int userId) {
        if(!userRepository.existsById(userId)){
            throw new IllegalArgumentException("User does not exist!");
        }

        reviewRepository.deleteByUserId(userId);
        viewLogRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
}

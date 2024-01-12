package com.lezhin.penfen.service;

import com.lezhin.penfen.dto.ContentsDislikes;
import com.lezhin.penfen.dto.ContentsLikes;
import com.lezhin.penfen.entity.Contents;
import com.lezhin.penfen.entity.User;
import com.lezhin.penfen.repository.ContentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;

    public List<ContentsLikes> findTop3ContentsByMostLikes() {
        return contentsRepository.findTop3ByMostLikes(3);
    }

    public List<ContentsDislikes> findTop3ContentsByMostDislikes() {
        return contentsRepository.findTop3ByMostDislikes(3);
    }

    public List<User> findUsersByContentsId(int id, Pageable pageable) {
        return contentsRepository.findAllUsersByContentsId(id, pageable);
    }

    public Contents changeContentsFreeOrPaid(int contentsId, int coin) {
        contentsRepository.updateCoin(contentsId, coin);
        return contentsRepository.findById(contentsId);
    }
}
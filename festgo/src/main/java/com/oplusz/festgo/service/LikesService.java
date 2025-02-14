package com.oplusz.festgo.service;

import com.oplusz.festgo.repository.FestivalLikesDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

    private final FestivalLikesDao likesDao;

    public int getLikeCount(int feId) {
        return likesDao.countLikesByFeId(feId);
    }

    public boolean isLiked(int feId, int meId) {
        return likesDao.checkLikeByFeIdAndMeId(feId, meId) > 0;
    }

    @Transactional
    public boolean toggleLike(int feId, int meId) {
        boolean isLiked = isLiked(feId, meId);

        if (isLiked) {
            likesDao.deleteLikeByFeIdAndMeId(feId, meId);
            log.info("좋아요 취소 - feId: {}, meId: {}", feId, meId);
            return false;
        } else {
            likesDao.insertLikeByFeIdAndMeId(feId, meId);
            log.info("좋아요 추가 - feId: {}, meId: {}", feId, meId);
            return true;
        }
    }
}

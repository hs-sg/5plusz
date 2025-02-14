package com.oplusz.festgo.web;

import com.oplusz.festgo.service.LikesService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/festival")
public class LikesController {

    private final LikesService likesService;

    // 특정 축제의 좋아요 개수 및 현재 로그인 사용자의 좋아요 여부 확인
    @GetMapping("/{feId}/likes")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getLikes(@PathVariable int feId, HttpSession session) {
        Integer meId = (Integer) session.getAttribute("meId");
        // null 체크 추가
        int userId = (meId != null) ? meId : 0;
        
        log.info("좋아요 상태 요청 - festivalId: {}, userId: {}", feId, userId);
        
        int likeCount = likesService.getLikeCount(feId);
        boolean liked = (userId > 0) && likesService.isLiked(feId, userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);
        response.put("liked", liked);

        return ResponseEntity.ok(response);
    }

    // 특정 축제의 좋아요 토글
    @PostMapping("/{feId}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable int feId, HttpSession session) {
        Integer meId = (Integer) session.getAttribute("meId");
        if (meId == null) {
        	log.warn("좋아요 요청 실패 - 로그인 필요");
            return ResponseEntity.badRequest().body(Map.of("message", "로그인이 필요합니다."));
        }
        
        log.info("좋아요 요청 - festivalId: {}, userId: {}", feId, meId);

        boolean liked = likesService.toggleLike(feId, meId);
        int likeCount = likesService.getLikeCount(feId);

        return ResponseEntity.ok(Map.of("liked", liked, "likeCount", likeCount));
    }
}

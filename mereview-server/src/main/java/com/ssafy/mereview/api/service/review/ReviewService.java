package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.request.KeywordCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewCreateServiceRequest;
import com.ssafy.mereview.api.service.review.dto.request.ReviewUpdateServiceRequest;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.Member;
import com.ssafy.mereview.domain.member.repository.MemberInterestQueryRepository;
import com.ssafy.mereview.domain.review.entity.BackgroundImage;
import com.ssafy.mereview.domain.review.entity.Keyword;
import com.ssafy.mereview.domain.review.entity.Notification;
import com.ssafy.mereview.domain.review.entity.Review;
import com.ssafy.mereview.domain.review.repository.BackgroundImageRepository;
import com.ssafy.mereview.domain.review.repository.KeywordRepository;
import com.ssafy.mereview.domain.review.repository.NotificationRepository;
import com.ssafy.mereview.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final KeywordRepository keywordRepository;
    private final BackgroundImageRepository backgroundImageRepository;
    private final MemberInterestQueryRepository interestQueryRepository;
    private final NotificationRepository notificationRepository;

    private static final int MEMBER_LIMIT_COUNT = 100;

    public Long create(ReviewCreateServiceRequest request) {
        Long saveId = reviewRepository.save(request.toEntity()).getId();

        List<Keyword> keywords = createKeywords(saveId, request.getKeywordServiceRequests());
        keywordRepository.saveAll(keywords);

        BackgroundImage backgroundImage = createBackgroundImage(saveId, request.getUploadFile());
        backgroundImageRepository.save(backgroundImage);

        List<Notification> notifications = createNotifications(request, saveId);
        notificationRepository.saveAll(notifications);

        return saveId;
    }

    public Long update(ReviewUpdateServiceRequest request) {
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(NoSuchElementException::new);

        review.update(request);

        return request.getReviewId();
    }

    public Long delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NoSuchElementException::new);
        reviewRepository.delete(review);
        return reviewId;
    }

    /**
     * private methods
     */

    private List<Keyword> createKeywords(Long saveId, List<KeywordCreateServiceRequest> keywordServiceRequests) {
        return keywordServiceRequests.stream()
                .map(request -> request.toEntity(saveId))
                .collect(Collectors.toList());
    }

    private BackgroundImage createBackgroundImage(Long reviewId, UploadFile uploadFile) {
        return BackgroundImage.builder()
                .review(Review.builder().id(reviewId).build())
                .uploadFile(uploadFile)
                .build();
    }

    private List<Notification> createNotifications(ReviewCreateServiceRequest request, Long saveId) {
        List<Long> memberIds = interestQueryRepository.searchRandomMember(request.getGenreId(), MEMBER_LIMIT_COUNT);
        List<Notification> notifications = new ArrayList<>();
        for (Long memberId : memberIds) {
            Notification notification = Notification.builder()
                    .review(Review.builder().id(saveId).build())
                    .member(Member.builder().id(memberId).build())
                    .build();
            notifications.add(notification);
        }
        return notifications;
    }
}

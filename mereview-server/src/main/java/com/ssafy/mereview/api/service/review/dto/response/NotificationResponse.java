package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.NotificationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class NotificationResponse {

    private Long notificationId;
    private Long memberId;
    private Long reviewId;
    private NotificationStatus status;
    private LocalDateTime createdTime;

    @Builder
    public NotificationResponse(Long notificationId, Long memberId, Long reviewId, NotificationStatus status, LocalDateTime createdTime) {
        this.notificationId = notificationId;
        this.memberId = memberId;
        this.reviewId = reviewId;
        this.status = status;
        this.createdTime = createdTime;
    }
}

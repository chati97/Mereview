package com.ssafy.mereview.api.service.review.dto.response;

import com.ssafy.mereview.domain.review.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.ssafy.mereview.domain.review.entity.CommentLikeType.DISLIKE;
import static com.ssafy.mereview.domain.review.entity.CommentLikeType.LIKE;

@ToString
@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long memberId;
    private Long nickname;
    private String content;
    private boolean isDone;
    private int likeCount;
    private int dislikeCount;
    private LocalDateTime createdTime;

    @Builder
    public CommentResponse(Long commentId, Long memberId, Long nickname, String content, boolean isDone, int likeCount, int dislikeCount, LocalDateTime createdTime) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.isDone = isDone;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdTime = createdTime;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .likeCount((int) comment.getLikes().stream().filter(commentLike -> commentLike.getType().equals(LIKE)).count())
                .dislikeCount((int) comment.getLikes().stream().filter(commentLike -> commentLike.getType().equals(DISLIKE)).count())
                .createdTime(comment.getCreatedTime())
                .build();
    }
}

package com.ssafy.mereview.domain.review.entity;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Attachment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "attachment_id")
    private Long id;
    @Embedded
    UploadFile uploadFile;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    private Attachment(Long id, UploadFile uploadFile, Review review) {
        this.id = id;
        this.uploadFile = uploadFile;
        this.review = review;
    }
}

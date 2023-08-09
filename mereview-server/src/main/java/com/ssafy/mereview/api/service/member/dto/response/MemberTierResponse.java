package com.ssafy.mereview.api.service.member.dto.response;

import com.ssafy.mereview.domain.member.entity.MemberTier;
import com.ssafy.mereview.domain.member.entity.Rank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberTierResponse {

    private Long id;
    private Rank funTier;
    private Rank usefulTier;
    private int funExperience;
    private int usefulExperience;
    private String genreName;

    @Builder
    public MemberTierResponse(Long id, Rank funTier, Rank usefulTier, int funExperience, int usefulExperience, String genreName) {
        this.id = id;
        this.funTier = funTier;
        this.usefulTier = usefulTier;
        this.funExperience = funExperience;
        this.usefulExperience = usefulExperience;
        this.genreName = genreName;
    }

    public static MemberTierResponse of(MemberTier memberTier){
        return MemberTierResponse.builder()
                .funTier(memberTier.getFunTier())
                .usefulTier(memberTier.getUsefulTier())
                .funExperience(memberTier.getFunExperience())
                .usefulExperience(memberTier.getUsefulExperience())
                .genreName(memberTier.getGenre().getGenreName())
                .build();
    }
}


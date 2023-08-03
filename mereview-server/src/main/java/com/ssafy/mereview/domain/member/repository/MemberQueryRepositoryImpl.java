package com.ssafy.mereview.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.mereview.domain.member.entity.*;
import com.ssafy.mereview.domain.movie.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.mereview.domain.member.entity.QAchievement.achievement;
import static com.ssafy.mereview.domain.member.entity.QInterest.interest;
import static com.ssafy.mereview.domain.member.entity.QMember.member;
import static com.ssafy.mereview.domain.member.entity.QMemberAchievement.memberAchievement;
import static com.ssafy.mereview.domain.member.entity.QMemberTier.memberTier;
import static com.ssafy.mereview.domain.movie.entity.QGenre.genre;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> searchById(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.id.eq(memberId))
                .fetchOne());
    }

    @Override
    public Member searchByEmail(String email) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    public List<Genre> searchAllGenre() {
        return queryFactory
                .select(genre)
                .from(genre)
                .fetch();
    }

    public List<Interest> searchInterestByMemberId(Long memberId) {

        return queryFactory.select(interest)
                .from(interest)
                .innerJoin(interest.member, member)
                .on(interest.member.id.eq(member.id))
                .innerJoin(interest.genre, genre)
                .on(interest.genre.id.eq(genre.id))
                .fetch();
    }

    @Override
    public List<MemberTier> searchUserTierByMemberId(Long memberId) {
        return queryFactory.select(memberTier)
                .from(memberTier)
                .innerJoin(memberTier.member, member)
                .on(memberTier.member.id.eq(member.id))
                .where(memberTier.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<MemberAchievement> searchMemberAchievementByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(memberAchievement)
                .innerJoin(memberAchievement.achievement, achievement)
                .innerJoin(memberAchievement.genre, genre)
                .on(genre.id.eq(memberAchievement.genre.id))
                .fetch();
    }

    @Override
    public Genre searchGenreByGenreName(String genreName) {
        return queryFactory
                .select(genre)
                .from(genre)
                .where(genre.genreName.eq(genreName))
                .fetchOne();
    }

}

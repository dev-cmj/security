package com.cmj.app.domain.post.repository;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.cmj.app.domain.post.entity.QPost.*;


@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // custom query methods
    @Override
    public Optional<Post> findPostWithMemberAndBoardById(Long postId) {
       return Optional.ofNullable(queryFactory
                .selectFrom(post)
                .leftJoin(post.member).fetchJoin()
                .leftJoin(post.board).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne());
    }
}

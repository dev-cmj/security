package com.cmj.app.domain.post.repository;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cmj.app.domain.comment.entity.QComment.comment;
import static com.cmj.app.domain.like.entity.QLike.like;
import static com.cmj.app.domain.post.entity.QPost.post;


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

    //Pagination query
    @Override
    public Page<PostProjection> findPostsPage(PostSearchCondition condition) {

        List<PostProjection> content = getPostProjections(condition);
        Long totalCount = getCount(condition);
        Pageable pageable = PageRequest.of(condition.page(), condition.size(), Sort.by(Sort.Direction.fromString(condition.order()), condition.sortField()));

        return new PageImpl<>(content, pageable, totalCount);
    }

    private List<PostProjection> getPostProjections(PostSearchCondition condition) {
        return queryFactory
                .select(new QPostProjection(
                        post.id,
                        post.title,
                        post.content,
                        post.viewCount,
                        post.member.username,
                        JPAExpressions
                                .select(comment.count())   // 서브쿼리를 사용해 댓글 수를 가져옴
                                .from(comment)
                                .where(comment.post.eq(post)),
                        JPAExpressions
                                .select(like.count())      // 서브쿼리를 사용해 좋아요 수를 가져옴
                                .from(like)
                                .where(like.post.eq(post))
                ))
                .from(post)
                .leftJoin(post.member)
                .offset((long) condition.page() * condition.size())
                .limit(condition.size())
                .where(
                        containsTitle(condition.title()),
                        containsContent(condition.content()),
                        containsAuthor(condition.author()),
                        containsComment(condition.comment())
                )
                .fetch();
    }

    private Long getCount(PostSearchCondition condition) {
        return queryFactory
                .select(post.count())
                .from(post)
                .leftJoin(post.member)
                .where(
                        containsTitle(condition.title()),
                        containsContent(condition.content()),
                        containsAuthor(condition.author()),
                        containsComment(condition.comment())
                )
                .fetchOne();
    }

    private BooleanExpression containsTitle(String title) {
        return title == null ? null : post.title.contains(title);
    }

    private BooleanExpression containsContent(String content) {
        return content == null ? null : post.content.contains(content);
    }

    private BooleanExpression containsAuthor(String author) {
        return author == null ? null : post.member.username.contains(author);
    }

    private BooleanExpression containsComment(String comment) {
        return comment == null ? null : post.comments.any().content.contains(comment);
    }



}

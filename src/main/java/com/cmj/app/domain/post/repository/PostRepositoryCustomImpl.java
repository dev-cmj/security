package com.cmj.app.domain.post.repository;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.entity.PostSort;
import com.cmj.app.domain.post.entity.QPostProjection;
import com.cmj.app.global.domain.PagingUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.cmj.app.domain.comment.entity.QComment.comment;
import static com.cmj.app.domain.like.entity.QLike.like;
import static com.cmj.app.domain.post.entity.QPost.post;
import static com.cmj.app.global.domain.QueryDslUtils.applyPageableAndSorting;
import static com.cmj.app.global.domain.SortUtils.getSort;


@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public Optional<Post> findPostByIdWithEntity(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(post)
                .leftJoin(post.member).fetchJoin()
                .leftJoin(post.board).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne());
    }

    @Override
    public Optional<PostProjection> findPostByIdWithProjection(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(new QPostProjection(
                        post.id,
                        post.title,
                        post.content,
                        post.viewCount,
                        post.member.username,
                        comment.countDistinct(),
                        like.countDistinct()
                ))
                .from(post)
                .leftJoin(post.member)
                .leftJoin(post.comments, comment)
                .leftJoin(post.likes, like)
                .where(post.id.eq(postId))
                .fetchOne());
    }

    @Override
    public Page<PostProjection> findPostsPage(PostSearchCondition condition) {

        Pageable pageable = PagingUtils.getPageable(condition.page(), condition.size());
        OrderSpecifier<?> orderSpec = getSort(condition.sortField(), condition.order(), PostSort.class);

        Long totalCount = getCount(condition);

        JPAQuery<PostProjection> query = getJpaQuery(condition);
        query = applyPageableAndSorting(query, pageable, orderSpec);

        List<PostProjection> list = query.fetch();
        return new PageImpl<>(list, pageable, totalCount);
    }

    @Override
    public Slice<PostProjection> findPostsSlice(PostSearchCondition condition) {

        Pageable pageable = PagingUtils.getPageable(condition.page(), condition.size());
        OrderSpecifier<?> orderSpec = getSort(condition.sortField(), condition.order(), PostSort.class);

        JPAQuery<PostProjection> query = getJpaQuery(condition);
        query = applyPageableAndSorting(query, pageable, orderSpec);

        List<PostProjection> result = query.fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.removeLast();  // +1로 가져온 데이터는 잘라내기
            hasNext = true;
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }

    @Override
    public void increaseViewCount(Long postId) {
        new JPAUpdateClause(entityManager, post)
                .where(post.id.eq(postId))
                .set(post.viewCount, post.viewCount.add(1))
                .execute();
    }

    @Override
    public Optional<PostProjection> findViewCountById(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(new QPostProjection(
                        post.id,
                        post.viewCount
                ))
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne());
    }

    private JPAQuery<PostProjection> getJpaQuery(PostSearchCondition condition) {
        return queryFactory
                .select(new QPostProjection(
                        post.id,
                        post.title,
                        post.content,
                        post.viewCount,
                        post.member.username,
                        comment.countDistinct(),
                        like.countDistinct()
                ))
                .from(post)
                .leftJoin(post.member)
                .leftJoin(post.comments, comment)
                .leftJoin(post.likes, like)
                .where(buildWhereCondition(condition));
    }

    private Long getCount(PostSearchCondition condition) {
        return queryFactory
                .select(post.id.count())
                .from(post)
                .where(buildWhereCondition(condition))
                .fetchOne();
    }

    private BooleanBuilder buildWhereCondition(PostSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(condition.title())) {
            builder.and(post.title.contains(condition.title()));
        }
        if (StringUtils.hasText(condition.content())) {
            builder.and(post.content.contains(condition.content()));
        }
        if (StringUtils.hasText(condition.author())) {
            builder.and(post.member.username.contains(condition.author()));
        }
        if (StringUtils.hasText(condition.comment())) {
            builder.and(post.comments.any().content.contains(condition.comment()));
        }

        return builder;
    }

}

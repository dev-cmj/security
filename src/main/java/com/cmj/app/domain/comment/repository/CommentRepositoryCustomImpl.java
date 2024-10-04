package com.cmj.app.domain.comment.repository;

import com.cmj.app.domain.comment.dto.CommentSearchCondition;
import com.cmj.app.domain.comment.entity.CommentProjection;
import com.cmj.app.domain.comment.entity.QCommentProjection;
import com.cmj.app.global.domain.PagingUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.cmj.app.domain.comment.entity.QComment.comment;
import static com.cmj.app.global.domain.QueryDslUtils.applyPageableAndSorting;
import static com.cmj.app.global.domain.SortUtils.getSort;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<CommentProjection> findCommentsPage(CommentSearchCondition condition) {
        Pageable pageable = PagingUtils.getPageable(condition.page(), condition.size());
        OrderSpecifier<?> orderSpecifier = getSort(condition.sortField(), condition.order(), CommentSort.class);

        Long totalCount = getCount(condition);

        JPAQuery<CommentProjection> query = getJpaQuery(condition);
        query = applyPageableAndSorting(query, pageable, orderSpecifier);
        List<CommentProjection> content = query.fetch();

        return new PageImpl<>(content, pageable, totalCount);
    }

    @Override
    public Slice<CommentProjection> findCommentsSlice(CommentSearchCondition condition) {
        Pageable pageable = PagingUtils.getPageable(condition.page(), condition.size());
        OrderSpecifier<?> orderSpecifier = getSort(condition.sortField(), condition.order(), CommentSort.class);

        JPAQuery<CommentProjection> query = getJpaQuery(condition);
        query = applyPageableAndSorting(query, pageable, orderSpecifier);
        List<CommentProjection> result = query.fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.removeLast();
            hasNext = true;
        }

        return new SliceImpl<>(result, pageable, hasNext);
    }

    private Long getCount(CommentSearchCondition condition) {
        return queryFactory
                .select(comment.id.count())
                .from(comment)
                .where(buildWhereCondition(condition))
                .fetchOne();
    }

    private JPAQuery<CommentProjection> getJpaQuery(CommentSearchCondition condition) {
        return queryFactory
                .select(new QCommentProjection(
                        comment.id,
                        comment.content,
                        comment.member.username,
                        comment.member.id,
                        comment.post.id,
                        comment.parent.id,  // 부모 댓글 ID 추가
                        comment.createdDate,
                        comment.modifiedDate
                ))
                .from(comment)
                .leftJoin(comment.member)
                .leftJoin(comment.post)
                .leftJoin(comment.parent)  // 부모 댓글 조인
                .where(buildWhereCondition(condition))
                .orderBy(comment.parent.id.asc(), comment.createdDate.asc()); // 부모 댓글과 대댓글 순서 유지
    }

    private BooleanBuilder buildWhereCondition(CommentSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(condition.content())) {
            builder.and(comment.content.contains(condition.content()));
        }

        if (condition.postId() != null) {
            builder.and(comment.post.id.eq(condition.postId()));
        }

        if (condition.memberId() != null) {
            builder.and(comment.member.id.eq(condition.memberId()));
        }

        if (condition.parentId() != null) {
            builder.and(comment.parent.id.eq(condition.parentId()));
        }

        return builder;
    }

}

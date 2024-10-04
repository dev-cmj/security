package com.cmj.app.global.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class QueryDslUtils {

    public static <T> JPAQuery<T> applyPageableAndSorting(JPAQuery<T> query, Pageable pageable, OrderSpecifier<?> orderSpecifier) {
        if (orderSpecifier != null) {
            query = query.orderBy(orderSpecifier);
        }

        if (PagingUtils.hasPageable(pageable)) {
            query = query.offset(pageable.getOffset()).limit(pageable.getPageSize() + 1);  // +1은 Slice를 위한 처리
        }
        return query;
    }
}

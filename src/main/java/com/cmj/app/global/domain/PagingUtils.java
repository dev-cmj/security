package com.cmj.app.global.domain;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingUtils {

    public static Pageable getPageable(Integer page, Integer size) {
        if (page == null || size == null) return CustomUnPaged.of();
        return PageRequest.of(page, size);
    }

    public static boolean hasPageable(Pageable pageable) {
        return pageable.toOptional().isPresent();
    }

    public static Pageable getDefaultPageable() {
        return PageRequest.of(0, 10);
    }


}
package com.cmj.app.domain.post.entity;

import com.cmj.app.global.domain.Sortable;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.cmj.app.domain.post.entity.QPost.post;

@RequiredArgsConstructor
@Getter
public enum PostSort implements Sortable {
    ID("id", post.id),
    TITLE("title", post.title),
    CONTENT("content", post.content),
    VIEW_COUNT("viewCount", post.viewCount),
    AUTHOR("author", post.member.username),
    ;

    private final String fieldName;
    private final ComparableExpressionBase<?> comparableExpression;

    public static PostSort fromFieldName(String fieldName) {
        for (PostSort sort : values()) {
            if (sort.getFieldName().equalsIgnoreCase(fieldName)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("Invalid field name: " + fieldName);
    }

}

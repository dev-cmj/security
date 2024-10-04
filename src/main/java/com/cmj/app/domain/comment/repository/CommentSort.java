package com.cmj.app.domain.comment.repository;

import com.cmj.app.global.domain.Sortable;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.cmj.app.domain.comment.entity.QComment.comment;


@RequiredArgsConstructor
@Getter
public enum CommentSort implements Sortable {

    ID("id", comment.id),
    CONTENT("content", comment.content),
    AUTHOR("author", comment.member.username),
    CREATED_DATE("createdDate", comment.createdDate);

    private final String fieldName;
    private final ComparableExpressionBase<?> comparableExpression;

    public static CommentSort fromFieldName(String fieldName) {
        for (CommentSort sort : values()) {
            if (sort.getFieldName().equalsIgnoreCase(fieldName)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("Invalid field name: " + fieldName);
    }
}

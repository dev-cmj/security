package com.cmj.app.global.domain;

import com.querydsl.core.types.dsl.ComparableExpressionBase;

public interface Sortable {
    String getFieldName();
    ComparableExpressionBase<?> getComparableExpression();
}
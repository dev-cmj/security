package com.cmj.app.global.domain;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

public class SortUtils {
    public static <T extends Enum<T> & Sortable> OrderSpecifier<?> getSort(String sortField, String sortOrder, Class<T> sortClass) {
        if (sortField == null || sortOrder == null) return null;

        Order order = Order.valueOf(sortOrder.toUpperCase());

        T sortEnum = fromFieldName(sortField, sortClass);

        return new OrderSpecifier<>(order, sortEnum.getComparableExpression());
    }

    public static <T extends Enum<T> & Sortable> T fromFieldName(String fieldName, Class<T> sortClass) {
        for (T sortEnum : sortClass.getEnumConstants()) {
            if (sortEnum.getFieldName().equalsIgnoreCase(fieldName)) {
                return sortEnum;
            }
        }
        throw new IllegalArgumentException("Invalid field name: " + fieldName);
    }
}

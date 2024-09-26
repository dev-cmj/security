package com.cmj.app.global.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomUnPaged implements Pageable {

    private final Sort sort = Sort.unsorted();

    private static final CustomUnPaged INSTANCE = new CustomUnPaged();

    private CustomUnPaged() {
    }

    public static CustomUnPaged of() {
        return INSTANCE;
    }

    public int getPageSize() {
        return 10;
    }

    public int getPageNumber() {
        return 0;
    }

    public long getOffset() {
        return 0;
    }

    public boolean isPaged() {
        return false;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public Pageable next() {
        return this;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        if (pageNumber == 0) {
            return this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}

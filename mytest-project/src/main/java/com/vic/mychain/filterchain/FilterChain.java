package com.vic.mychain.filterchain;

import com.vic.mychain.domain.ChainContext;
import com.vic.mychain.domain.Request;

import java.util.List;

public interface FilterChain<P extends Request, R> {

    Entry<P, R> getEntry(String name);

    Entry<P, R> getEntry(RequestFilter<P, R> filter);

    Entry<P, R> getEntry(Class<? extends RequestFilter<P, R>> filterType);

    RequestFilter<P, R> get(String name);

    RequestFilter<P, R> get(Class<? extends RequestFilter<P, R>> filterType);

    RequestFilter.NextFilter<P, R> getNextFilter(String name);

    RequestFilter.NextFilter<P, R> getNextFilter(RequestFilter<P, R> filter);

    RequestFilter.NextFilter<P, R> getNextFilter(Class<? extends RequestFilter<P, R>> filterType);

    List<Entry<P, R>> getAll();

    List<Entry<P, R>> getAllReversed();

    boolean contains(String name);

    boolean contains(RequestFilter<P, R> filter);

    boolean contains(Class<? extends RequestFilter<P, R>> filterType);

    void addFirst(String name, RequestFilter<P, R> filter);

    void addLast(String name, RequestFilter<P, R> filter);

    void addBefore(String baseName, String name, RequestFilter<P, R> filter);

    void addAfter(String baseName, String name, RequestFilter<P, R> filter);

    RequestFilter<P, R> replace(String name, RequestFilter<P, R> newFilter);

    void replace(RequestFilter<P, R> oldFilter, RequestFilter<P, R> newFilter);

    RequestFilter<P, R> replace(Class<? extends RequestFilter<P, R>> oldFilterType, RequestFilter<P, R> newFilter);

    RequestFilter<P, R> remove(String name);

    void remove(RequestFilter<P, R> filter);

    RequestFilter<P, R> remove(Class<? extends RequestFilter<P, R>> filterType);

    void clear() throws Exception;

    public R fireRequest(ChainContext context, P request) throws Exception;

    public interface Entry<P extends Request, R> {

        /**
         * Returns the name of the filter.
         */
        String getName();

        /**
         * Returns the filter.
         */
        RequestFilter<P, R> getFilter();

        RequestFilter.NextFilter<P,R> getNextFilter();
    }
}

package com.vic.mychain.chain;
import com.vic.mychain.filter.MessageFilter;
import com.vic.mychain.message.IMessage;

import java.util.List;

public interface MessageFilterChain {

    Entry getEntry(String name);

    Entry getEntry(MessageFilter filter);

//    Entry<P, R> getEntry(Class<? extends RequestFilter<P, R>> filterType);

    MessageFilter get(String name);

//    RequestFilter<P, R> get(Class<? extends RequestFilter<P, R>> filterType);

    MessageFilter.NextFilter getNextFilter(String name);

//    MessageFilter.NextFilter<P> getNextFilter(MessageFilter<P> filter);
//
//    RequestFilter.NextFilter<P, R> getNextFilter(Class<? extends RequestFilter<P, R>> filterType);

    List<Entry> getAll();
//
//    List<Entry<P, R>> getAllReversed();
//
    boolean contains(String name);
//
    boolean contains(MessageFilter filter);
//
//    boolean contains(Class<? extends RequestFilter<P, R>> filterType);
//
    void addFirst(String name, MessageFilter filter);

    void addLast(String name, MessageFilter filter);

    void addBefore(String baseName, String name, MessageFilter filter);

    void addAfter(String baseName, String name, MessageFilter filter);

//    RequestFilter<P, R> replace(String name, RequestFilter<P, R> newFilter);

//    void replace(RequestFilter<P, R> oldFilter, RequestFilter<P, R> newFilter);
//
//    RequestFilter<P, R> replace(Class<? extends RequestFilter<P, R>> oldFilterType, RequestFilter<P, R> newFilter);

     MessageFilter remove(String name);

    void remove(MessageFilter filter);

//    RequestFilter<P, R> remove(Class<? extends RequestFilter<P, R>> filterType);

    void clear() throws Exception;

    public void fireRequest(IMessage request) throws Exception;

    public interface Entry {

        /**
         * Returns the name of the filter.
         */
        String getName();

        /**
         * Returns the filter.
         */
        MessageFilter getFilter();

        MessageFilter.NextFilter getNextFilter();
    }
}

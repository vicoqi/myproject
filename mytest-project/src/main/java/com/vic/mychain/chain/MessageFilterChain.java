package com.vic.mychain.chain;
import com.vic.mychain.filter.MessageFilter;
import com.vic.mychain.message.IMessage;

import java.util.List;

public interface MessageFilterChain<P extends IMessage> {

    Entry<P> getEntry(String name);

    Entry<P> getEntry(MessageFilter<P> filter);

//    Entry<P, R> getEntry(Class<? extends RequestFilter<P, R>> filterType);

    MessageFilter<P> get(String name);

//    RequestFilter<P, R> get(Class<? extends RequestFilter<P, R>> filterType);

    MessageFilter.NextFilter<P> getNextFilter(String name);

//    MessageFilter.NextFilter<P> getNextFilter(MessageFilter<P> filter);
//
//    RequestFilter.NextFilter<P, R> getNextFilter(Class<? extends RequestFilter<P, R>> filterType);

    List<Entry<P>> getAll();
//
//    List<Entry<P, R>> getAllReversed();
//
    boolean contains(String name);
//
    boolean contains(MessageFilter<P> filter);
//
//    boolean contains(Class<? extends RequestFilter<P, R>> filterType);
//
    void addFirst(String name, MessageFilter<P> filter);

    void addLast(String name, MessageFilter<P> filter);

    void addBefore(String baseName, String name, MessageFilter<P> filter);

    void addAfter(String baseName, String name, MessageFilter<P> filter);

//    RequestFilter<P, R> replace(String name, RequestFilter<P, R> newFilter);

//    void replace(RequestFilter<P, R> oldFilter, RequestFilter<P, R> newFilter);
//
//    RequestFilter<P, R> replace(Class<? extends RequestFilter<P, R>> oldFilterType, RequestFilter<P, R> newFilter);

     MessageFilter<P> remove(String name);

    void remove(MessageFilter<P> filter);

//    RequestFilter<P, R> remove(Class<? extends RequestFilter<P, R>> filterType);

    void clear() throws Exception;

    public void fireRequest(P request) throws Exception;

    public interface Entry<P extends IMessage> {

        /**
         * Returns the name of the filter.
         */
        String getName();

        /**
         * Returns the filter.
         */
        MessageFilter<P> getFilter();

        MessageFilter.NextFilter<P> getNextFilter();
    }
}

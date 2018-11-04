/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.chain.filterchain;

import com.vic.chain.AGVContext;
import com.vic.chain.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultRequestFilterChain<P extends Request, R> implements RequestFilterChain<P, R> {

    private final Map<String, Entry<P, R>> name2entry = new ConcurrentHashMap<String, Entry<P, R>>();

    /** The chain head */
    private final EntryImpl head;

    /** The chain tail */
    private final EntryImpl tail;

    /** current service */
    private RequestBehavior<P, R> requestBehavior;

    public DefaultRequestFilterChain(RequestBehavior<P, R> requestBehavior) {
        if (requestBehavior == null) {
            throw new NullPointerException("requestBehavior is  null");
        }
        this.requestBehavior = requestBehavior;
        head = new EntryImpl(null, null, "head", new HeadFilter());
        tail = new EntryImpl(head, null, "tail", new TailFilter());
        head.nextEntry = tail;
    }

    public Entry<P, R> getEntry(String name) {
        Entry<P, R> e = name2entry.get(name);
        if (e == null) {
            return null;
        }
        return e;
    }

    public Entry<P, R> getEntry(RequestFilter<P, R> filter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getFilter() == filter) {
                return e;
            }
            e = e.nextEntry;
        }
        return null;
    }

    public Entry<P, R> getEntry(Class<? extends RequestFilter<P, R>> filterType) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (filterType.isAssignableFrom(e.getFilter().getClass())) {
                return e;
            }
            e = e.nextEntry;
        }
        return null;
    }

    public RequestFilter<P, R> get(String name) {
        Entry<P, R> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getFilter();
    }

    public RequestFilter<P, R> get(Class<? extends RequestFilter<P, R>> filterType) {
        Entry<P, R> e = getEntry(filterType);
        if (e == null) {
            return null;
        }

        return e.getFilter();
    }

    public RequestFilter.NextFilter<P, R> getNextFilter(String name) {
        Entry<P, R> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public RequestFilter.NextFilter<P, R> getNextFilter(RequestFilter<P, R> filter) {
        Entry<P, R> e = getEntry(filter);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public RequestFilter.NextFilter<P, R> getNextFilter(Class<? extends RequestFilter<P, R>> filterType) {
        Entry<P, R> e = getEntry(filterType);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public synchronized void addFirst(String name, RequestFilter<P, R> filter) {
        checkAddable(name);
        register(head, name, filter);
    }

    public synchronized void addLast(String name, RequestFilter<P, R> filter) {
        checkAddable(name);
        register(tail.prevEntry, name, filter);
    }

    public synchronized void addBefore(String baseName, String name, RequestFilter<P, R> filter) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry.prevEntry, name, filter);
    }

    public synchronized void addAfter(String baseName, String name, RequestFilter<P, R> filter) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry, name, filter);
    }

    public synchronized RequestFilter<P, R> remove(String name) {
        EntryImpl entry = checkOldName(name);
        deregister(entry);
        return entry.getFilter();
    }

    public synchronized void remove(RequestFilter<P, R> filter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getFilter() == filter) {
                deregister(e);
                return;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Filter not found: " + filter.getClass().getName());
    }

    public synchronized RequestFilter<P, R> remove(Class<? extends RequestFilter<P, R>> filterType) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (filterType.isAssignableFrom(e.getFilter().getClass())) {
                RequestFilter<P, R> oldFilter = e.getFilter();
                deregister(e);
                return oldFilter;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Filter not found: " + filterType.getName());
    }

    public synchronized RequestFilter<P, R> replace(String name, RequestFilter<P, R> newFilter) {
        EntryImpl entry = checkOldName(name);
        RequestFilter<P, R> oldFilter = entry.getFilter();
        entry.setFilter(newFilter);
        return oldFilter;
    }

    public synchronized void replace(RequestFilter<P, R> oldFilter, RequestFilter<P, R> newFilter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getFilter() == oldFilter) {
                e.setFilter(newFilter);
                return;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Filter not found: " + oldFilter.getClass().getName());
    }

    public synchronized RequestFilter<P, R> replace(Class<? extends RequestFilter<P, R>> oldFilterType,
        RequestFilter<P, R> newFilter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (oldFilterType.isAssignableFrom(e.getFilter().getClass())) {
                RequestFilter<P, R> oldFilter = e.getFilter();
                e.setFilter(newFilter);
                return oldFilter;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Filter not found: " + oldFilterType.getName());
    }

    public synchronized void clear() throws Exception {
        List<Entry<P, R>> l = new ArrayList<Entry<P, R>>(name2entry.values());
        for (RequestFilterChain.Entry<P, R> entry : l) {
            try {
                deregister((EntryImpl)entry);
            } catch (Exception e) {
                throw new Exception("clear(): " + entry.getName(), e);
            }
        }
    }


    /**
     * 把 entry 串成链表
     * @param prevEntry
     * @param name
     * @param filter
     */
    private void register(EntryImpl prevEntry, String name, RequestFilter<P, R> filter) {
        //告诉 newEntry 前后的 entry
        EntryImpl newEntry = new EntryImpl(prevEntry, prevEntry.nextEntry, name, filter);

        // 把 newEntry  接入到现在链表中
        prevEntry.nextEntry.prevEntry = newEntry;
        prevEntry.nextEntry = newEntry;
        name2entry.put(name, newEntry);
    }

    private void deregister(EntryImpl entry) {

        deregister0(entry);
    }

    private void deregister0(EntryImpl entry) {
        EntryImpl prevEntry = entry.prevEntry;
        EntryImpl nextEntry = entry.nextEntry;
        prevEntry.nextEntry = nextEntry;
        nextEntry.prevEntry = prevEntry;

        name2entry.remove(entry.name);
    }

    /**
     * Throws an exception when the specified filter name is not registered in
     * this chain.
     * 
     * @return An filter entry with the specified name.
     */
    private EntryImpl checkOldName(String baseName) {
        EntryImpl e = (EntryImpl)name2entry.get(baseName);
        if (e == null) {
            throw new IllegalArgumentException("Filter not found:" + baseName);
        }
        return e;
    }

    /**
     * Checks the specified filter name is already taken and throws an exception
     * if already taken.
     */
    private void checkAddable(String name) {
        if (name2entry.containsKey(name)) {
            throw new IllegalArgumentException("Other filter is using the same name '" + name + "'");
        }
    }

    public R fireRequest(AGVContext context, P request) throws Exception {

        Entry<P, R> head = this.head;
        return callNextOnQuery(context, head, request);
    }

    private R callNextOnQuery(AGVContext context, Entry<P, R> entry, P request) throws Exception {
            RequestFilter<P, R> filter = entry.getFilter();
            RequestFilter.NextFilter<P, R> nextFilter = entry.getNextFilter();
            return filter.request(context, nextFilter, request);
    }

    public List<Entry<P, R>> getAll() {
        List<Entry<P, R>> list = new ArrayList<Entry<P, R>>();
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            list.add(e);
            e = e.nextEntry;
        }

        return list;
    }

    public List<Entry<P, R>> getAllReversed() {
        List<Entry<P, R>> list = new ArrayList<Entry<P, R>>();
        EntryImpl e = tail.prevEntry;
        while (e != head) {
            list.add(e);
            e = e.prevEntry;
        }
        return list;
    }

    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    public boolean contains(RequestFilter<P, R> filter) {
        return getEntry(filter) != null;
    }

    public boolean contains(Class<? extends RequestFilter<P, R>> filterType) {
        return getEntry(filterType) != null;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{ ");

        boolean empty = true;

        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (!empty) {
                buf.append(", ");
            } else {
                empty = false;
            }

            buf.append('(');
            buf.append(e.getName());
            buf.append(':');
            buf.append(e.getFilter());
            buf.append(')');

            e = e.nextEntry;
        }

        if (empty) {
            buf.append("empty");
        }

        buf.append(" }");

        return buf.toString();
    }

    private class HeadFilter extends RequestFilterAdapter<P, R> {}

    private class TailFilter extends RequestFilterAdapter<P, R> {

        @Override
        public R request(AGVContext context, NextFilter<P, R> nextFilter, P request) throws Exception {
            return requestBehavior.getHandler().request(context, request);
        }

    }

    private class EntryImpl implements Entry<P, R> {

        private EntryImpl prevEntry;

        private EntryImpl nextEntry;

        private final String name;

        private RequestFilter<P, R> filter;

        private final RequestFilter.NextFilter<P, R> nextFilter;

        private EntryImpl(EntryImpl prevEntry, EntryImpl nextEntry, String name, RequestFilter<P, R> filter) {
            if (filter == null) {
                throw new IllegalArgumentException("filter");
            }
            if (name == null) {
                throw new IllegalArgumentException("name");
            }

            this.prevEntry = prevEntry;
            this.nextEntry = nextEntry;
            this.name = name;
            this.filter = filter;
            this.nextFilter = new RequestFilter.NextFilter<P, R>() {

                public R request(AGVContext context, P request) throws Exception {
                    Entry<P, R> nextEntry = EntryImpl.this.nextEntry;
                    return callNextOnQuery(context, nextEntry, request);
                }

                public String toString() {
                    return EntryImpl.this.nextEntry.name;
                }
            };
        }

        public String getName() {
            return name;
        }

        public RequestFilter<P, R> getFilter() {
            return filter;
        }

        private void setFilter(RequestFilter<P, R> filter) {
            if (filter == null) {
                throw new IllegalArgumentException("filter");
            }

            this.filter = filter;
        }

        public RequestFilter.NextFilter<P, R> getNextFilter() {
            return nextFilter;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            // Add the current filter
            sb.append("('").append(getName()).append('\'');

            // Add the previous filter
            sb.append(", prev: '");

            if (prevEntry != null) {
                sb.append(prevEntry.name);
                sb.append(':');
                sb.append(prevEntry.getFilter().getClass().getSimpleName());
            } else {
                sb.append("null");
            }

            // Add the next filter
            sb.append("', next: '");

            if (nextEntry != null) {
                sb.append(nextEntry.name);
                sb.append(':');
                sb.append(nextEntry.getFilter().getClass().getSimpleName());
            } else {
                sb.append("null");
            }

            sb.append("')");
            return sb.toString();
        }

        public void addAfter(String name, RequestFilter<P, R> filter) {
            DefaultRequestFilterChain.this.addAfter(getName(), name, filter);
        }

        public void addBefore(String name, RequestFilter<P, R> filter) {
            DefaultRequestFilterChain.this.addBefore(getName(), name, filter);
        }

        public void remove() {
            DefaultRequestFilterChain.this.remove(getName());
        }

        public void replace(RequestFilter<P, R> newFilter) {
            DefaultRequestFilterChain.this.replace(getName(), newFilter);
        }
    }

}

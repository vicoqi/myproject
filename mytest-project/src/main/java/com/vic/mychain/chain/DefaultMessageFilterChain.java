/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.mychain.chain;

import com.vic.mychain.filter.MessageFilter;
import com.vic.mychain.filter.MessageFilterAdapter;
import com.vic.mychain.message.IMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMessageFilterChain<P extends IMessage, R> implements MessageFilterChain<P, R> {

    private final Map<String, Entry<P, R>> name2entry = new ConcurrentHashMap<String, Entry<P, R>>();

    /** The chain head */
    private final EntryImpl head;

    /** The chain tail */
    private final EntryImpl tail;

    /** current service */
    private RequestBehavior<P, R> requestBehavior;

    public DefaultMessageFilterChain(RequestBehavior<P, R> requestBehavior) {
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

    public Entry<P, R> getEntry(MessageFilter<P, R> filter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getFilter() == filter) {
                return e;
            }
            e = e.nextEntry;
        }
        return null;
    }

    public Entry<P, R> getEntry(Class<? extends MessageFilter<P, R>> filterType) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (filterType.isAssignableFrom(e.getFilter().getClass())) {
                return e;
            }
            e = e.nextEntry;
        }
        return null;
    }

    public MessageFilter<P, R> get(String name) {
        Entry<P, R> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getFilter();
    }

    public MessageFilter<P, R> get(Class<? extends MessageFilter<P, R>> filterType) {
        Entry<P, R> e = getEntry(filterType);
        if (e == null) {
            return null;
        }

        return e.getFilter();
    }

    public MessageFilter.NextFilter<P, R> getNextFilter(String name) {
        Entry<P, R> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public MessageFilter.NextFilter<P, R> getNextFilter(MessageFilter<P, R> filter) {
        Entry<P, R> e = getEntry(filter);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public MessageFilter.NextFilter<P, R> getNextFilter(Class<? extends MessageFilter<P, R>> filterType) {
        Entry<P, R> e = getEntry(filterType);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public synchronized void addFirst(String name, MessageFilter<P, R> filter) {
        checkAddable(name);
        register(head, name, filter);
    }

    public synchronized void addLast(String name, MessageFilter<P, R> filter) {
        checkAddable(name);
        register(tail.prevEntry, name, filter);
    }

    public synchronized void addBefore(String baseName, String name, MessageFilter<P, R> filter) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry.prevEntry, name, filter);
    }

    public synchronized void addAfter(String baseName, String name, MessageFilter<P, R> filter) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry, name, filter);
    }

    public synchronized MessageFilter<P, R> remove(String name) {
        EntryImpl entry = checkOldName(name);
        deregister(entry);
        return entry.getFilter();
    }

    public synchronized void remove(MessageFilter<P, R> filter) {
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

    public synchronized MessageFilter<P, R> remove(Class<? extends MessageFilter<P, R>> filterType) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (filterType.isAssignableFrom(e.getFilter().getClass())) {
                MessageFilter<P, R> oldFilter = e.getFilter();
                deregister(e);
                return oldFilter;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Filter not found: " + filterType.getName());
    }

    public synchronized MessageFilter<P, R> replace(String name, MessageFilter<P, R> newFilter) {
        EntryImpl entry = checkOldName(name);
        MessageFilter<P, R> oldFilter = entry.getFilter();
        entry.setFilter(newFilter);
        return oldFilter;
    }

    public synchronized void replace(MessageFilter<P, R> oldFilter, MessageFilter<P, R> newFilter) {
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

    public synchronized MessageFilter<P, R> replace(Class<? extends MessageFilter<P, R>> oldFilterType,
        MessageFilter<P, R> newFilter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (oldFilterType.isAssignableFrom(e.getFilter().getClass())) {
                MessageFilter<P, R> oldFilter = e.getFilter();
                e.setFilter(newFilter);
                return oldFilter;
            }
            e = e.nextEntry;
        }
        throw new IllegalArgumentException("Filter not found: " + oldFilterType.getName());
    }

    public synchronized void clear() throws Exception {
        List<Entry<P, R>> l = new ArrayList<Entry<P, R>>(name2entry.values());
        for (Entry<P, R> entry : l) {
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
    private void register(EntryImpl prevEntry, String name, MessageFilter<P, R> filter) {
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
        return callEntry(context, head, request);
    }
    
    private R callEntry(AGVContext context, Entry<P, R> entry, P request) throws Exception {
            MessageFilter<P, R> filter = entry.getFilter();
            MessageFilter.NextFilter<P, R> nextFilter = entry.getNextFilter();
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

    public boolean contains(MessageFilter<P, R> filter) {
        return getEntry(filter) != null;
    }

    public boolean contains(Class<? extends MessageFilter<P, R>> filterType) {
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

    private class HeadFilter extends MessageFilterAdapter<P, R> {}

    private class TailFilter extends MessageFilterAdapter<P, R> {

        @Override
        public R request(AGVContext context, NextFilter<P, R> nextFilter, P request) throws Exception {
            return requestBehavior.getHandler().request(context, request);
        }

    }

    private class EntryImpl implements Entry<P, R> {

        private EntryImpl prevEntry;

        private EntryImpl nextEntry;

        private final String name;

        private MessageFilter<P, R> filter;

        private final MessageFilter.NextFilter<P, R> nextFilter;

        private EntryImpl(EntryImpl prevEntry, EntryImpl nextEntry, String name, MessageFilter<P, R> filter) {
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
            this.nextFilter = new MessageFilter.NextFilter<P, R>() {

                public R request(AGVContext context, P request) throws Exception {
                    Entry<P, R> nextEntry = EntryImpl.this.nextEntry;
                    return callEntry(context, nextEntry, request);
                }

                public String toString() {
                    return EntryImpl.this.nextEntry.name;
                }
            };
        }

        public String getName() {
            return name;
        }

        public MessageFilter<P, R> getFilter() {
            return filter;
        }

        private void setFilter(MessageFilter<P, R> filter) {
            if (filter == null) {
                throw new IllegalArgumentException("filter");
            }

            this.filter = filter;
        }

        public MessageFilter.NextFilter<P, R> getNextFilter() {
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

        public void addAfter(String name, MessageFilter<P, R> filter) {
            DefaultMessageFilterChain.this.addAfter(getName(), name, filter);
        }

        public void addBefore(String name, MessageFilter<P, R> filter) {
            DefaultMessageFilterChain.this.addBefore(getName(), name, filter);
        }

        public void remove() {
            DefaultMessageFilterChain.this.remove(getName());
        }

        public void replace(MessageFilter<P, R> newFilter) {
            DefaultMessageFilterChain.this.replace(getName(), newFilter);
        }
    }

}

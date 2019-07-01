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

public class DefaultMessageFilterChain<P extends IMessage, R> implements MessageFilterChain<P> {

    private final Map<String, Entry<P>> name2entry = new ConcurrentHashMap<String, Entry<P>>();

    /** The chain head */
    private final EntryImpl head;

    /** The chain tail */
    private final EntryImpl tail;

    /** current service */
    private RequestBehavior<P> requestBehavior;

    public DefaultMessageFilterChain(RequestBehavior<P> requestBehavior) {
        if (requestBehavior == null) {
            throw new NullPointerException("requestBehavior is  null");
        }
        this.requestBehavior = requestBehavior;
        head = new EntryImpl(null, null, "head", new HeadFilter());
        tail = new EntryImpl(head, null, "tail", new TailFilter());
        head.nextEntry = tail;
    }

    public Entry<P> getEntry(String name) {
        Entry<P> e = name2entry.get(name);
        if (e == null) {
            return null;
        }
        return e;
    }

    public Entry<P> getEntry(MessageFilter<P> filter) {
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            if (e.getFilter() == filter) {
                return e;
            }
            e = e.nextEntry;
        }
        return null;
    }
    public MessageFilter<P> get(String name) {
        Entry<P> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getFilter();
    }

    public MessageFilter.NextFilter<P> getNextFilter(String name) {
        Entry<P> e = getEntry(name);
        if (e == null) {
            return null;
        }

        return e.getNextFilter();
    }

    public synchronized void addFirst(String name, MessageFilter<P> filter) {
        checkAddable(name);
        register(head, name, filter);
    }

    public synchronized void addLast(String name, MessageFilter<P> filter) {
        checkAddable(name);
        register(tail.prevEntry, name, filter);
    }

    public synchronized void addBefore(String baseName, String name, MessageFilter<P> filter) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry.prevEntry, name, filter);
    }

    public synchronized void addAfter(String baseName, String name, MessageFilter<P> filter) {
        EntryImpl baseEntry = checkOldName(baseName);
        checkAddable(name);
        register(baseEntry, name, filter);
    }

    public synchronized MessageFilter<P> remove(String name) {
        EntryImpl entry = checkOldName(name);
        deregister(entry);
        return entry.getFilter();
    }

    public synchronized void remove(MessageFilter<P> filter) {
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

    public synchronized void clear() throws Exception {
        List<Entry<P>> l = new ArrayList<Entry<P>>(name2entry.values());
        for (Entry<P> entry : l) {
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
    private void register(EntryImpl prevEntry, String name, MessageFilter<P> filter) {
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

    public void  fireRequest(P request) throws Exception {
        Entry<P> head = this.head;
        callEntry(head, request);
    }
    
    private void callEntry(Entry<P> entry, P request) throws Exception {
            MessageFilter<P> filter = entry.getFilter();
            MessageFilter.NextFilter<P> nextFilter = entry.getNextFilter();
            filter.request(nextFilter, request);
    }

    public List<Entry<P>> getAll() {
        List<Entry<P>> list = new ArrayList<Entry<P>>();
        EntryImpl e = head.nextEntry;
        while (e != tail) {
            list.add(e);
            e = e.nextEntry;
        }
        return list;
    }

    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    public boolean contains(MessageFilter<P> filter) {
        return getEntry(filter) != null;
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

    private class HeadFilter extends MessageFilterAdapter<P> {}

    private class TailFilter extends MessageFilterAdapter<P> {
        @Override
        public void request(NextFilter<P> nextFilter, P request) throws Exception {
            requestBehavior.getHandler().request(request);
        }
    }
    private class EntryImpl implements Entry<P> {

        private EntryImpl prevEntry;

        private EntryImpl nextEntry;

        private final String name;

        private MessageFilter<P> filter;

        private final MessageFilter.NextFilter<P> nextFilter;

        private EntryImpl(EntryImpl prevEntry, EntryImpl nextEntry, String name, MessageFilter<P> filter) {
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
            this.nextFilter = new MessageFilter.NextFilter<P>() {

                public void  request(P request) throws Exception {
                    Entry<P> nextEntry = EntryImpl.this.nextEntry;
                    callEntry(nextEntry, request);
                }

                public String toString() {
                    return EntryImpl.this.nextEntry.name;
                }
            };
        }

        public String getName() {
            return name;
        }

        public MessageFilter<P> getFilter() {
            return filter;
        }

        private void setFilter(MessageFilter<P> filter) {
            if (filter == null) {
                throw new IllegalArgumentException("filter");
            }
            this.filter = filter;
        }

        public MessageFilter.NextFilter<P> getNextFilter() {
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
        public void addAfter(String name, MessageFilter<P> filter) {
            DefaultMessageFilterChain.this.addAfter(getName(), name, filter);
        }

        public void addBefore(String name, MessageFilter<P> filter) {
            DefaultMessageFilterChain.this.addBefore(getName(), name, filter);
        }
        public void remove() {
            DefaultMessageFilterChain.this.remove(getName());
        }
    }

}

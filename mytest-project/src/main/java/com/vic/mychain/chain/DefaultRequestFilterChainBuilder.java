package com.vic.mychain.chain;

import com.vic.mychain.filter.MessageFilter;
import com.vic.mychain.message.IMessage;
import com.vic.mychain.chain.MessageFilterChain.Entry;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultRequestFilterChainBuilder<P extends IMessage> {
    private final List<Entry<P>> entries;

    public DefaultRequestFilterChainBuilder() {
        entries = new CopyOnWriteArrayList<Entry<P>>();
    }

    public void buildFilterChain(MessageFilterChain<P> chain) throws Exception {
        for (Entry<P> e : entries) {
            chain.addLast(e.getName(), e.getFilter());
        }
    }

    public Entry<P> getEntry(String name) {
        for (Entry<P> e : entries) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }

    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    private void register(int index, Entry<P> e) {
        if (contains(e.getName())) {
            throw new IllegalArgumentException("Other filter is using the same name: " + e.getName());
        }

        entries.add(index, e);
    }

    public synchronized void addLast(String name, MessageFilter<P> filter) {
        register(entries.size(), new EntryImpl(name, filter));
    }

    public synchronized void addBefore(String baseName, String name, MessageFilter<P> filter) {

        for (ListIterator<Entry<P>> i = entries.listIterator(); i.hasNext();) {
            Entry<P> base = i.next();
            if (base.getName().equals(baseName)) {
                register(i.previousIndex(), new EntryImpl(name, filter));
                break;
            }
        }
    }

    /**
     * @see RequestFilterChain#addAfter(String, String, RequestFilter)
     */
    public synchronized void addAfter(String baseName, String name, RequestFilter<P, R> filter) {
        for (ListIterator<Entry<P, R>> i = entries.listIterator(); i.hasNext();) {
            Entry<P, R> base = i.next();
            if (base.getName().equals(baseName)) {
                register(i.nextIndex(), new EntryImpl(name, filter));
                break;
            }
        }
    }

    /**
     * @see RequestFilterChain#remove(String)
     */
    public synchronized RequestFilter<P, R> remove(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        for (ListIterator<Entry<P, R>> i = entries.listIterator(); i.hasNext();) {
            Entry<P, R> e = i.next();
            if (e.getName().equals(name)) {
                entries.remove(i.previousIndex());
                return e.getFilter();
            }
        }

        throw new IllegalArgumentException("Unknown filter name: " + name);
    }



    private class EntryImpl implements Entry<P, R> {

        private final String name;

        private volatile RequestFilter<P, R> filter;

        private EntryImpl(String name, RequestFilter<P, R> filter) {
            if (name == null) {
                throw new IllegalArgumentException("name");
            }
            if (filter == null) {
                throw new IllegalArgumentException("filter");
            }

            this.name = name;
            this.filter = filter;
        }

        public String getName() {
            return name;
        }

        public RequestFilter<P, R> getFilter() {
            return filter;
        }

        private void setFilter(RequestFilter<P, R> filter) {
            this.filter = filter;
        }

        public RequestFilter.NextFilter<P, R> getNextFilter() {
            throw new IllegalStateException();
        }

        @Override
        public String toString() {
            return "(" + getName() + ':' + filter + ')';
        }

        public void addAfter(String name, RequestFilter<P, R> filter) {
            DefaultRequestFilterChainBuilder.this.addAfter(getName(), name, filter);
        }

        public void addBefore(String name, RequestFilter<P, R> filter) {
            DefaultRequestFilterChainBuilder.this.addBefore(getName(), name, filter);
        }

        public void remove() {
            DefaultRequestFilterChainBuilder.this.remove(getName());
        }

        public void replace(RequestFilter<P, R> newFilter) {
            return;
        }
    }






}

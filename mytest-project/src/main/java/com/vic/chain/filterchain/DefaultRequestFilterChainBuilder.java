package com.vic.chain.filterchain;

import com.vic.chain.Request;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vic.chain.filterchain.RequestFilterChain.Entry;

public class DefaultRequestFilterChainBuilder<P extends Request, R> {
    private final List<Entry<P, R>> entries;

    public DefaultRequestFilterChainBuilder() {
        entries = new CopyOnWriteArrayList<Entry<P, R>>();
    }

    public void buildFilterChain(RequestFilterChain<P, R> chain) throws Exception {
        for (Entry<P, R> e : entries) {
            chain.addLast(e.getName(), e.getFilter());
        }
    }

    public Entry<P, R> getEntry(String name) {
        for (Entry<P, R> e : entries) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }

    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    private void register(int index, Entry<P, R> e) {
        if (contains(e.getName())) {
            throw new IllegalArgumentException("Other filter is using the same name: " + e.getName());
        }

        entries.add(index, e);
    }



    /**
     * @see RequestFilterChain#addLast(String, RequestFilter)
     */
    public synchronized void addLast(String name, RequestFilter<P, R> filter) {
        register(entries.size(), new EntryImpl(name, filter));
    }

    /**
     * @see RequestFilterChain#addBefore(String, String, RequestFilter)
     */
    public synchronized void addBefore(String baseName, String name, RequestFilter<P, R> filter) {

        for (ListIterator<Entry<P, R>> i = entries.listIterator(); i.hasNext();) {
            Entry<P, R> base = i.next();
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



    private class EntryImpl implements RequestFilterChain.Entry<P, R> {

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

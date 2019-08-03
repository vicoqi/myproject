package com.vic.mychain.chain;

import com.vic.mychain.filter.MessageFilter;
import com.vic.mychain.chain.MessageFilterChain.Entry;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultRequestFilterChainBuilder {
    private final List<Entry> entries;

    public DefaultRequestFilterChainBuilder() {
        entries = new CopyOnWriteArrayList<Entry>();
    }

    public void buildFilterChain(MessageFilterChain chain) throws Exception {
        for (Entry e : entries) {
            chain.addLast(e.getName(), e.getFilter());
        }
    }

    public Entry getEntry(String name) {
        for (Entry e : entries) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public boolean contains(String name) {
        return getEntry(name) != null;
    }

    private void register(int index, Entry e) {
        if (contains(e.getName())) {
            throw new IllegalArgumentException("Other filter is using the same name: " + e.getName());
        }
        entries.add(index, e);
    }

    public synchronized void addLast(String name, MessageFilter filter) {
        register(entries.size(), new EntryImpl(name, filter));
    }

    public synchronized void addBefore(String baseName, String name, MessageFilter filter) {
        for (ListIterator<Entry> i = entries.listIterator(); i.hasNext();) {
            Entry base = i.next();
            if (base.getName().equals(baseName)) {
                register(i.previousIndex(), new EntryImpl(name, filter));
                break;
            }
        }
    }

    public synchronized void addAfter(String baseName, String name, MessageFilter filter) {
        for (ListIterator<Entry> i = entries.listIterator(); i.hasNext();) {
            Entry base = i.next();
            if (base.getName().equals(baseName)) {
                register(i.nextIndex(), new EntryImpl(name, filter));
                break;
            }
        }
    }

    public synchronized MessageFilter remove(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        for (ListIterator<Entry> i = entries.listIterator(); i.hasNext();) {
            Entry e = i.next();
            if (e.getName().equals(name)) {
                entries.remove(i.previousIndex());
                return e.getFilter();
            }
        }
        throw new IllegalArgumentException("Unknown filter name: " + name);
    }

    private class EntryImpl implements Entry {

        private final String name;

        private volatile MessageFilter filter;

        private EntryImpl(String name, MessageFilter filter) {
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

        public MessageFilter getFilter() {
            return filter;
        }

        private void setFilter(MessageFilter filter) {
            this.filter = filter;
        }

        public MessageFilter.NextFilter getNextFilter() {
            throw new IllegalStateException();
        }

        @Override
        public String toString() {
            return "(" + getName() + ':' + filter + ')';
        }

        public void addAfter(String name, MessageFilter filter) {
            DefaultRequestFilterChainBuilder.this.addAfter(getName(), name, filter);
        }

        public void addBefore(String name, MessageFilter filter) {
            DefaultRequestFilterChainBuilder.this.addBefore(getName(), name, filter);
        }

        public void remove() {
            DefaultRequestFilterChainBuilder.this.remove(getName());
        }
        public void replace(MessageFilter newFilter) {
            return;
        }
    }






}

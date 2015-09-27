package com.noah.mapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LRULinkedHashMap<K, V> extends LinkedHashMap<Object, Object> {
    private static final long serialVersionUID = 3566536751086052834L;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private final int maxCapacity;
    private final transient Lock lock = new ReentrantLock();

    public LRULinkedHashMap(int maxCapacity) {
        super(maxCapacity, 0.75F, true);
        this.maxCapacity = maxCapacity;
    }

    protected boolean removeEldestEntry(Entry<Object, Object> eldest) {
        return this.size() > this.maxCapacity;
    }

    public boolean containsKey(Object key) {
        boolean var2;
        try {
            this.lock.lock();
            var2 = super.containsKey(key);
        } finally {
            this.lock.unlock();
        }

        return var2;
    }

    public Object get(Object key) {
        Object var2;
        try {
            this.lock.lock();
            var2 = super.get(key);
        } finally {
            this.lock.unlock();
        }

        return var2;
    }

    public Object put(Object key, Object value) {
        Object var3;
        try {
            this.lock.lock();
            var3 = super.put(key, value);
        } finally {
            this.lock.unlock();
        }

        return var3;
    }

    public int size() {
        int var1;
        try {
            this.lock.lock();
            var1 = super.size();
        } finally {
            this.lock.unlock();
        }

        return var1;
    }

    public void clear() {
        try {
            this.lock.lock();
            super.clear();
        } finally {
            this.lock.unlock();
        }

    }

    public Collection<Entry<K, V>> getAll() {
        ArrayList var1;
        try {
            this.lock.lock();
            var1 = new ArrayList(super.entrySet());
        } finally {
            this.lock.unlock();
        }

        return var1;
    }
}

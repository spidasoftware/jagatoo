/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
package org.jagatoo.util.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * A hashmap based cache that holds it's values with a SoftReference. The cache
 * has an initial capacity for it's hash table. It will grow if more then the
 * load factor (entries / capacity > load factor) is in use.
 * 
 * @author mam
 * @author Marvin Froehlich (aka Qudus)
 * 
 * @see java.lang.ref.SoftReference
 * 
 * @param <K> the cache-key type
 * @param <T> the cached item type
 */
public final class ResourceCache< K, T >
{
    private class Entry< U > extends SoftReference< U >
    {
        public final K key;
        public final int hash;
        public Entry< U > next;
        
        @SuppressWarnings( "unchecked" )
        public Entry( int hash, K key, U value, ReferenceQueue< U > queue, Entry next )
        {
            super( value, queue );
            
            this.hash = hash;
            this.key = key;
            this.next = next;
        }
    }
    
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    private final ReferenceQueue< Entry< T >> queue = new ReferenceQueue< Entry< T > >();
    
    private final float loadFactor;
    
    private Entry< T >[] table;
    private int size;
    private int threshold;
    private int hits;
    private int misses;
    private int initialCapacity;
    
    /**
     * Constructs a new Cache object with a given initial capacity and load
     * factor.
     * 
     * @param initialCapacity The initial capacity of the cache
     * @param loadFactor The load factor.
     */
    @SuppressWarnings( "unchecked" )
    public ResourceCache( int initialCapacity, float loadFactor )
    {
        if ( initialCapacity <= 0 )
            throw new IllegalArgumentException( "Illegal initial capacity: " + initialCapacity );
        
        if ( initialCapacity > MAXIMUM_CAPACITY )
            initialCapacity = MAXIMUM_CAPACITY;
        
        if ( loadFactor <= 0 || Float.isNaN( loadFactor ) )
            throw new IllegalArgumentException( "Illegal load factor: " + loadFactor );
        
        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while ( capacity < initialCapacity )
            capacity <<= 1;
        
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.threshold = (int)( capacity * loadFactor );
        this.table = new Entry[ capacity ];
    }
    
    /**
     * Constructs a new Cache object with a given initial capacity and default
     * load factor.
     * 
     * @param initialCapacity The initial capacity of the cache
     */
    public ResourceCache( int initialCapacity )
    {
        this( initialCapacity, DEFAULT_LOAD_FACTOR );
    }
    
    /**
     * Constructs a new Cache object with default initial capacity and default
     * load factor.
     */
    public ResourceCache()
    {
        this( DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR );
    }
    
    /**
     * Clears the caches
     */
    @SuppressWarnings( "unchecked" )
    public void clear()
    {
        table = new Entry[ initialCapacity ];
        size = 0;
    }
    
    private static int hash( Object x )
    {
        int h = x.hashCode();
        
        h += ~( h << 9 );
        h ^= ( h >>> 14 );
        h += ( h << 4 );
        h ^= ( h >>> 10 );
        
        return ( h );
    }
    
    /**
     * Retrives a object from the cache.
     * 
     * @param key The key under which the Texture is to be stored.
     * @return The Texture or null if it is not in the cache.
     */
    public T get( String key )
    {
        checkQueue();
        
        int hash = hash( key );
        Entry< T > e = table[ hash & ( table.length - 1 ) ];
        for ( ; e != null; e = e.next )
        {
            if ( e.hash == hash && ( key == e.key || key.equals( e.key ) ) )
            {
                ++hits;
                return ( e.get() );
            }
        }
        
        ++misses;
        
        return ( null );
    }
    
    /**
     * Puts a entry in the cache. If an entry is already registered under the
     * given key, then the entry will be replaced.
     * 
     * @param key The key under which the object should be stored. May not be
     *            null.
     * @param value The object that should be stored.
     * @return value. (For simplyfied calling)
     */
    @SuppressWarnings( "unchecked" )
    public T put( K key, T value )
    {
        checkQueue();
        
        if ( key == null )
            throw new IllegalArgumentException( "Cache doesn't support null keys" );
        if ( value == null )
            throw new IllegalArgumentException( "Cache doesn't support null values" );
        
        int hash = hash( key );
        int idx = hash & ( table.length - 1 );
        Entry< T > e = table[ idx ];
        for ( Entry< T > prev = null; e != null; e = e.next )
        {
            if ( e.hash == hash && ( key == e.key || key.equals( e.key ) ) )
            {
                // TODO: check, if this doesn't crash!!!
                e = new Entry( hash, key, value, queue, e.next );
                if ( prev == null )
                    table[ idx ] = e;
                else
                    prev.next = e;
                return ( value );
            }
            prev = e;
        }
        
        // TODO: check, if this doesn't crash!!!
        table[ idx ] = new Entry( hash, key, value, queue, table[ idx ] );
        if ( size++ >= threshold )
            resize( 2 * table.length );
        
        return ( value );
    }
    
    public void removeByKey( K key )
    {
        removeEntry( key );
    }
    
    public void removeByObject( T obj )
    {
        // TODO: implement removal
    }
    
    @SuppressWarnings( "unchecked" )
    private void resize( int newCapacity )
    {
        Entry< T >[] oldTable = table;
        int oldCapacity = oldTable.length;
        if ( oldCapacity == MAXIMUM_CAPACITY )
        {
            threshold = Integer.MAX_VALUE;
            return;
        }
        
        Entry< T >[] newTable = new Entry[ newCapacity ];
        
        for ( int i = 0; i < oldCapacity; ++i )
        {
            Entry< T > e = oldTable[ i ];
            if ( e != null )
            {
                oldTable[ i ] = null;
                do
                {
                    Entry< T > next = e.next;
                    int idx = e.hash & ( newCapacity - 1 );
                    e.next = newTable[ idx ];
                    newTable[ idx ] = e;
                    e = next;
                }
                while ( e != null );
            }
        }
        
        table = newTable;
        threshold = (int)( newCapacity * loadFactor );
    }
    
    private void removeEntry( K key )
    {
        int hash = hash( key );
        int idx = hash & ( table.length - 1 );
        Entry< T > prev = table[ idx ];
        Entry< T > e = prev;
        
        while ( e != null )
        {
            Entry< T > next = e.next;
            if ( e.hash == hash && ( key == e.key || key.equals( e.key ) ) )
            {
                size--;
                if ( prev == e )
                    table[ idx ] = next;
                else
                    prev.next = next;
                return;
            }
            prev = e;
            e = next;
        }
    }
    
    @SuppressWarnings( "unchecked" )
    private void checkQueue()
    {
        for ( ;; )
        {
            //Reference<Entry<T>> e = queue.poll();
            Object e = queue.poll();
            if ( e == null )
                return;
            
            removeEntry( ( (Entry< T >)( e ) ).key );
        }
    }
    
    /**
     * Returns the number of cache hits. A cache hit is a call to get() that
     * @return a non-null reference.
     * 
     * @return The number of cache hits.
     */
    public int getCacheHits()
    {
        return ( hits );
    }
    
    /**
     * Returns the number of cache misses. A cache miss is a call to get() that
     * returned a null reference.
     * 
     * @return The number of cache misses.
     */
    public int getCacheMisses()
    {
        return ( misses );
    }
    
    /**
     * @return The number of entries currently in the cache.
     */
    public int getCacheEntries()
    {
        return ( size );
    }
    
    /**
     * Resets the hit / miss statistik to zero.
     */
    public void resetStatistic()
    {
        hits = 0;
        misses = 0;
    }
    
    /**
     * Creates a String that contains name of the cache and the statistic.
     * 
     * @return A textual representation of the cache statistik.
     */
    @Override
    public String toString()
    {
        return ( getClass().getName() + "[size=" + size + ",hits=" + hits + ",misses=" + misses + "]" );
    }
}

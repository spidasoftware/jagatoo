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
package org.jagatoo.util.lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.jagatoo.datatypes.DoublyChainable;

/**
 * The {@link ChainedList} is a {@link List} implementation, that contains
 * {@link DoublyChainable} objects. This way it produces zero garbage, while
 * the {@link java.util.LinkedList} produces a lot of garbage and is therefore
 * unusable for gaming stuff.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ChainedList< E extends DoublyChainable< E > > implements java.util.List< E >, java.util.Queue< E >
{
    private E head = null;
    private E tail = null;
    
    private int size = 0;
    
    public ChainedList()
    {
    }
    
    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param  c the collection whose elements are to be placed into this list.
     * @throws NullPointerException if the specified collection is null.
     */
    public ChainedList( Collection< ? extends E > c )
    {
        this();
        
        addAll( c );
    }
    
    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return ( size );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return ( size == 0 );
    }
    
    /**
     * Returns the head of this {@link ChainedList}.
     * 
     * @return the first element in this list.
     */
    public E getHead()
    {
        return head;
    }
    
    /**
     * Returns the tail of this {@link ChainedList}.
     * 
     * @return the last element in this list.
     */
    public E getTail()
    {
        return ( tail );
    }
    
    /**
     * Removes and returns the first element from this list.
     * 
     * @return the first element from this list.
     * @throws    NoSuchElementException if this list is empty.
     */
    public E removeHead()
    {
        return ( remove( 0 ) );
    }
    
    /**
     * Removes and returns the last element from this list.
     * 
     * @return the last element from this list.
     * @throws    NoSuchElementException if this list is empty.
     */
    public E removeTail()
    {
        final E result = tail;
        
        if ( tail != null )
            remove( tail );
        
        return ( result );
    }
    
    /**
     * Inserts the given element at the beginning of this list.
     * 
     * @param o the element to be inserted at the beginning of this list.
     */
    public void addHead( E o )
    {
        addBefore( o, head );
    }
    
    /**
     * Appends the given element to the end of this list.  (Identical in
     * function to the <tt>add</tt> method; included only for consistency.)
     * 
     * @param o the element to be inserted at the end of this list.
     */
    public void append( E o )
    {
        if ( tail == null )
        {
            this.head = o;
            o.setPrevious( null );
        }
        else
        {
            this.tail.setNext( o );
            o.setPrevious( tail );
        }
        
        this.tail = o;
        o.setNext( null );
        
        size++;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean add( E o )
    {
        append( o );
        
        return ( true );
    }
    
    /**
     * {@inheritDoc}
     */
    public void add( int index, E element )
    {
        if ( index >= size )
        {
            append( element );
            return;
        }
        
        addBefore( element, get( index ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean addAll( Collection< ? extends E > c )
    {
        for ( Iterator< ? extends E > it = c.iterator(); it.hasNext(); )
        {
            append( it.next() );
        }
        
        return ( !c.isEmpty() );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean addAll( int index, Collection< ? extends E > c )
    {
        if ( c.isEmpty() )
        {
            return ( false );
        }
        
        if ( index >= size )
        {
            return ( addAll( c ) );
        }
        
        final E elem = get( index );
        final E prev = elem.getPrevious();
        final E oldTail = getTail();
        tail = prev;
        
        E lastAdded = null;
        for ( Iterator< ? extends E > it = c.iterator(); it.hasNext(); )
        {
            lastAdded = it.next();
            append( lastAdded );
        }
        
        lastAdded.setNext( elem );
        elem.setPrevious( lastAdded );
        tail = oldTail;
        
        return ( true );
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean remove( Object o )
    {
        E elem = (E)o;
        
        boolean result = false;
        
        if ( elem == head )
        {
            head = elem.getNext();
            result = true;
        }
        
        if ( elem == tail )
        {
            tail = elem.getPrevious();
            result = true;
        }
        
        if ( elem.getPrevious() != null )
        {
            elem.getPrevious().setNext( elem.getNext() );
            result = true;
        }
        
        if ( elem.getNext() != null )
        {
            elem.getNext().setPrevious( elem.getPrevious() );
            result = true;
        }
        
        size--;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public E remove( int index )
    {
        int i = 0;
        E elem = head;
        
        while ( elem != null )
        {
            if ( i == index )
            {
                remove( elem );
                
                return ( elem );
            }
            
            elem = elem.getNext();
            i++;
        }
        
        return ( null );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean removeAll( Collection< ? > c )
    {
        final Object[] items = new Object[ c.size() ];
        
        int i = 0;
        for ( Iterator< ? > it = c.iterator(); it.hasNext(); )
        {
            items[ i++ ] = it.next();
        }
        
        boolean result = false;
        for ( i = 0; i < items.length; i++ )
        {
            result = remove( items[ i ] ) || result;
        }
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean retainAll( Collection< ? > c )
    {
        final Object[] items = new Object[ this.size() ];
        
        E elem = head;
        int i = 0;
        while ( elem != null )
        {
            items[ i ] = elem;
            
            elem = elem.getNext();
            i++;
        }
        
        boolean result = false;
        for ( i = 0; i < items.length; i++ )
        {
            if ( !c.contains( items[ i ] ) )
            {
                remove( items[ i ] );
                result = true;
            }
        }
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public E set( int index, E element )
    {
        final E old = get( index );
        
        if ( old != null )
        {
            final E prev = old.getPrevious();
            final E next = old.getNext();
            
            element.setPrevious( prev );
            element.setNext( next );
            
            if ( prev != null )
                prev.setNext( element );
            
            if ( next != null )
                next.setPrevious( element );
            
            if ( head == old )
                head = element;
            
            if ( tail == old )
                tail = element;
            
            return ( old );
        }
        
        return ( null );
    }
    
    /**
     * {@inheritDoc}
     */
    public E get( int index )
    {
        int i = 0;
        E elem = head;
        
        while ( elem != null )
        {
            if ( i == index )
                return ( elem );
            
            elem = elem.getNext();
            i++;
        }
        
        return ( null );
    }
    
    /**
     * {@inheritDoc}
     */
    public int indexOf( Object o )
    {
        int i = 0;
        E elem = head;
        
        while ( elem != null )
        {
            if ( elem.equals( o ) )
                return ( i );
            
            elem = elem.getNext();
            i++;
        }
        
        return ( -1 );
    }
    
    /**
     * {@inheritDoc}
     */
    public int lastIndexOf( Object o )
    {
        int i = size - 1;
        E elem = tail;
        
        while ( elem != null )
        {
            if ( elem.equals( o ) )
                return ( i );
            
            elem = elem.getPrevious();
            i--;
        }
        
        return ( -1 );
    }
    
    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean contains( Object o )
    {
        E elem = head;
        
        while ( elem != null )
        {
            if ( elem.equals( o ) )
                return ( true );
            
            elem = elem.getNext();
        }
        
        return ( false );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean containsAll( Collection< ? > c )
    {
        for ( Iterator< ? > it = c.iterator(); it.hasNext(); )
        {
            Object o = it.next();
            E elem = head;
            
            while ( elem != null )
            {
                if ( elem.equals( o ) )
                    return ( true );
                
                elem = elem.getNext();
            }
        }
        
        return ( false );
    }
    
    /**
     * {@inheritDoc}
     */
    public List< E > subList( int fromIndex, int toIndex )
    {
        throw new UnsupportedOperationException( "subList() is not yet implemented" );
    }
    
    /**
     * {@inheritDoc}
     */
    public E peek()
    {
        if ( size == 0 )
            return ( null );
        
        return ( getHead() );
    }
    
    /**
     * {@inheritDoc}
     */
    public E element()
    {
        return ( getHead() );
    }
    
    /**
     * {@inheritDoc}
     */
    public E poll()
    {
        if ( size == 0 )
            return ( null );
        
        return ( removeHead() );
    }
    
    /**
     * {@inheritDoc}
     */
    public E remove()
    {
        return ( removeHead() );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean offer( E o )
    {
        return ( add( o ) );
    }
    
    private class ListItr implements ListIterator< E >
    {
        private E lastReturned = head;
        private E next;
        private int nextIndex;
        
        ListItr( int index )
        {
            if ( ( index < 0 ) || ( index > size ) )
                throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
            
            if ( index < ( size >> 1 ) )
            {
                next = head.getNext();
                for ( nextIndex = 0; nextIndex < index; nextIndex++ )
                    next = next.getNext();
            }
            else
            {
                next = head;
                for ( nextIndex = size; nextIndex > index; nextIndex-- )
                    next = next.getPrevious();
            }
        }
        
        public boolean hasNext()
        {
            return ( nextIndex != size );
        }
        
        public E next()
        {
            //checkForComodification();
            if ( nextIndex == size )
                throw new NoSuchElementException();
            
            lastReturned = next;
            next = next.getNext();
            nextIndex++;
            return ( lastReturned );
        }
        
        public boolean hasPrevious()
        {
            return ( nextIndex != 0 );
        }
        
        public E previous()
        {
            if ( nextIndex == 0 )
                throw new NoSuchElementException();
            
            lastReturned = next = next.getPrevious();
            nextIndex--;
            //checkForComodification();
            return ( lastReturned );
        }
        
        public int nextIndex()
        {
            return ( nextIndex );
        }
        
        public int previousIndex()
        {
            return ( nextIndex - 1 );
        }
        
        public void remove()
        {
            //checkForComodification();
            E lastNext = lastReturned.getNext();
            try
            {
                ChainedList.this.remove( lastReturned );
            }
            catch ( NoSuchElementException e )
            {
                throw new IllegalStateException();
            }
            if ( next == lastReturned )
                next = lastNext;
            else
                nextIndex--;
            lastReturned = head;
            //expectedModCount++;
        }
        
        public void set( E o )
        {
            if ( lastReturned == head )
                throw new IllegalStateException();
            //checkForComodification();
            lastReturned = o;
        }
        
        public void add( E o )
        {
            //checkForComodification();
            lastReturned = head;
            addBefore( o, next );
            nextIndex++;
            //expectedModCount++;
        }
        
        /*
        final void checkForComodification()
        {
            if ( modCount != expectedModCount )
                throw new ConcurrentModificationException() );
        }
        */
    }
    
    /**
     * {@inheritDoc}
     */
    public ListIterator< E > listIterator( int index )
    {
        return ( new ListItr( index ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public ListIterator< E > listIterator()
    {
        return ( listIterator( 0 ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public Iterator< E > iterator()
    {
        return ( listIterator() );
    }
    
    private void addBefore( E o, E e )
    {
        if ( e.getPrevious() != null )
        {
            e.getPrevious().setNext( o );
        }
        o.setPrevious( e.getPrevious() );
        o.setNext( e );
        e.setPrevious( o );
        
        size++;
    }
    
    /**
     * Returns a shallow copy of this <tt>ChainedList</tt>.
     * (The elements themselves are not cloned.)
     * 
     * @return a shallow copy of this <tt>ChainedList</tt> instance.
     */
    @SuppressWarnings("unchecked")
    @Override
    public ChainedList< E > clone()
    {
        ChainedList< E > clone = null;
        try
        {
            clone = (ChainedList< E >)super.clone();
        }
        catch ( CloneNotSupportedException e )
        {
            throw new InternalError();
        }
        
        clone.head = this.head;
        clone.tail = this.tail;
        clone.size = this.size;
        
        return ( clone );
    }
    
    /**
     * Returns an array containing all of the elements in this list
     * in the correct order.
     * 
     * @return an array containing all of the elements in this list
     *         in the correct order.
     */
    public Object[] toArray()
    {
        final Object[] result = new Object[ size ];
        
        int i = 0;
        E elem = head;
        
        while ( elem != null )
        {
            result[ i ] = elem;
            
            elem = elem.getNext();
            i++;
        }
        
        return ( result );
    }
    
    /**
     * Returns an array containing all of the elements in this list in
     * the correct order; the runtime type of the returned array is that of
     * the specified array.  If the list fits in the specified array, it
     * is returned therein.  Otherwise, a new array is allocated with the
     * runtime type of the specified array and the size of this list.<p>
     *
     * If the list fits in the specified array with room to spare
     * (i.e., the array has more elements than the list),
     * the element in the array immediately following the end of the
     * collection is set to null.  This is useful in determining the length
     * of the list <i>only</i> if the caller knows that the list
     * does not contain any null elements.
     *
     * @param a the array into which the elements of the list are to
     *      be stored, if it is big enough; otherwise, a new array of the
     *      same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list.
     * @throws ArrayStoreException if the runtime type of a is not a
     *         supertype of the runtime type of every element in this list.
     * @throws NullPointerException if the specified array is null.
     */
    @SuppressWarnings("unchecked")
    public < T > T[] toArray( T[] a )
    {
        if ( a.length < size )
            a = (T[])java.lang.reflect.Array.newInstance( a.getClass().getComponentType(), size );
        Object[] result = a;
        
        E elem = head;
        
        int i = 0;
        while ( elem != null )
        {
            result[ i ] = elem;
            
            elem = elem.getNext();
            i++;
        }
        
        if ( a.length > size )
            a[ size ] = null;
        
        return ( a );
    }
}

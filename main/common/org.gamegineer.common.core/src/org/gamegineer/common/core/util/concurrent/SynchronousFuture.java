/*
 * SynchronousFuture.java
 * Copyright 2008-2015 Gamegineer contributors and others.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Oct 10, 2011 at 8:05:06 PM.
 */

package org.gamegineer.common.core.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An implementation of {@link Future} used when the result of an asynchronous
 * operation is computed synchronously.
 * 
 * @param <V>
 *        The type of the operation result.
 */
@Immutable
@NonNullByDefault( {} )
public final class SynchronousFuture<@Nullable V>
    implements Future<V>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The operation exception or {@code null} if no exception occurred. */
    private final Throwable exception_;

    /** The operation result. */
    private final V result_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SynchronousFuture} class with a
     * {@code null} result.
     */
    public SynchronousFuture()
    {
        this( null, null );
    }

    /**
     * Initializes a new instance of the {@code SynchronousFuture} class with
     * the specified result.
     * 
     * @param result
     *        The operation result; may be {@code null}.
     */
    public SynchronousFuture(
        final V result )
    {
        this( result, null );
    }

    /**
     * Initializes a new instance of the {@code SynchronousFuture} class with
     * the specified exception.
     * 
     * @param exception
     *        The operation exception; must not be {@code null}.
     */
    public SynchronousFuture(
        @NonNull
        final Throwable exception )
    {
        this( null, exception );
    }

    /**
     * Initializes a new instance of the {@code SynchronousFuture} class.
     * 
     * @param result
     *        The operation result; may be {@code null}.
     * @param exception
     *        The operation exception; may be {@code null}.
     */
    private SynchronousFuture(
        final V result,
        final Throwable exception )
    {
        exception_ = exception;
        result_ = result;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.util.concurrent.Future#cancel(boolean)
     */
    @Override
    public boolean cancel(
        @SuppressWarnings( "unused" )
        final boolean mayInterruptIfRunning )
    {
        return false;
    }

    /*
     * @see java.util.concurrent.Future#get()
     */
    @Override
    public V get()
        throws ExecutionException
    {
        if( exception_ != null )
        {
            throw new ExecutionException( exception_ );
        }

        return result_;
    }

    /*
     * @see java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public V get(
        @SuppressWarnings( "unused" )
        final long timeout,
        @SuppressWarnings( "unused" )
        final TimeUnit unit )
        throws ExecutionException
    {
        return get();
    }

    /*
     * @see java.util.concurrent.Future#isCancelled()
     */
    @Override
    public boolean isCancelled()
    {
        return false;
    }

    /*
     * @see java.util.concurrent.Future#isDone()
     */
    @Override
    public boolean isDone()
    {
        return true;
    }
}

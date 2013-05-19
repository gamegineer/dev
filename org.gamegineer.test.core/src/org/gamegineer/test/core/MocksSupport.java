/*
 * MocksSupport.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on May 9, 2013 at 11:09:23 PM.
 */

package org.gamegineer.test.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.easymock.IAnswer;

/**
 * Provides a collection of useful methods for working with EasyMock mocks.
 */
@NotThreadSafe
public final class MocksSupport
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of active asynchronous answers. */
    private final Collection<AsyncAnswer<?>> asyncAnswers_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MocksSupport} class.
     */
    public MocksSupport()
    {
        asyncAnswers_ = new ArrayList<AsyncAnswer<?>>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new asynchronous answer that is expected to be called once and
     * returns {@code null}.
     * 
     * @param <T>
     *        The answer type.
     * 
     * @return A new asynchronous answer; never {@code null}.
     */
    /* @NonNull */
    public <T> IAnswer<T> asyncAnswer()
    {
        return asyncAnswer( 1 );
    }

    /**
     * Creates a new asynchronous answer that is expected to be called once and
     * delegates to the specified answer.
     * 
     * @param <T>
     *        The answer type.
     * @param answer
     *        The answer to which the operation is delegated; must not be
     *        {@code null}.
     * 
     * @return A new asynchronous answer; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code answer} is {@code null}.
     */
    /* @NonNull */
    public <T> IAnswer<T> asyncAnswer(
        /* @NonNull */
        final IAnswer<T> answer )
    {
        return asyncAnswer( answer, 1 );
    }

    /**
     * Creates a new asynchronous answer that is expected to be called the
     * specified number of times and returns {@code null}.
     * 
     * @param <T>
     *        The answer type.
     * @param count
     *        The count of calls expected to the answer.
     * 
     * @return A new asynchronous answer; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code count} is {@code null}.
     */
    /* @NonNull */
    public <T> IAnswer<T> asyncAnswer(
        final int count )
    {
        return asyncAnswer( MocksSupport.<T>defaultAnswer(), count );
    }

    /**
     * Creates a new asynchronous answer that is expected to be called the
     * specified number of times and delegates to the specified answer.
     * 
     * @param <T>
     *        The answer type.
     * @param answer
     *        The answer to which the operation is delegated; must not be
     *        {@code null}.
     * @param count
     *        The count of calls expected to the answer.
     * 
     * @return A new asynchronous answer; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code count} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code answer} is {@code null}.
     */
    /* @NonNull */
    public <T> IAnswer<T> asyncAnswer(
        /* @NonNull */
        final IAnswer<T> answer,
        final int count )
    {
        if( answer == null )
        {
            throw new NullPointerException( "answer" ); //$NON-NLS-1$
        }

        final AsyncAnswer<T> asyncAnswer = new AsyncAnswer<T>( answer, count );
        asyncAnswers_.add( asyncAnswer );
        return asyncAnswer;
    }

    /**
     * Awaits the completion of all active asynchronous answers.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     */
    public void awaitAsyncAnswers()
        throws InterruptedException
    {
        for( final AsyncAnswer<?> asyncAnswer : asyncAnswers_ )
        {
            asyncAnswer.await();
        }
    }

    /**
     * Creates a new default answer for the specified type.
     * 
     * @param <T>
     *        The answer type.
     * 
     * @return A new default answer; never {@code null}.
     */
    /* @NonNull */
    private static <T> IAnswer<T> defaultAnswer()
    {
        return new IAnswer<T>()
        {
            @Override
            public T answer()
            {
                return null;
            }
        };
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An answer that may be invoked asynchronously and supports waiting for
     * that invocation to complete.
     * 
     * @param <T>
     *        The answer type.
     */
    @ThreadSafe
    private static final class AsyncAnswer<T>
        implements IAnswer<T>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The answer to which all operations are delegated. */
        private final IAnswer<T> delegate_;

        /** The latch used to await invocations of this answer. */
        private final CountDownLatch latch_;


        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AsyncAnswer} class.
         * 
         * @param delegate
         *        The answer to which all operations are delegated; must not be
         *        {@code null}.
         * @param count
         *        The count of calls expected to this answer.
         * 
         * @throws java.lang.IllegalArgumentException
         *         If {@code count} is negative.
         */
        AsyncAnswer(
            /* @NonNull */
            final IAnswer<T> delegate,
            final int count )
        {
            assert delegate != null;

            delegate_ = delegate;
            latch_ = new CountDownLatch( count );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.easymock.IAnswer#answer()
         */
        @Override
        public T answer()
            throws Throwable
        {
            latch_.countDown();
            return delegate_.answer();
        }

        /**
         * Awaits completion of all invocations of this answer.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted.
         */
        void await()
            throws InterruptedException
        {
            latch_.await();
        }
    }
}

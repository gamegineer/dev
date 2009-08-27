/*
 * DummyFactory.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jul 26, 2008 at 10:24:55 PM.
 */

package org.gamegineer.test.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * A factory for creating test dummies.
 */
public final class DummyFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dummy invocation handler. */
    private static final InvocationHandler HANDLER = new DummyInvocationHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DummyFactory} class.
     */
    private DummyFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a dummy for the specified type.
     * 
     * <p>
     * Any method call on the dummy will result in an {@code AssertionError}
     * being thrown.
     * </p>
     * 
     * @param <T>
     *        The type of the dummy.
     * @param type
     *        The type of dummy to create; must not be {@code null}.
     * 
     * @return A new dummy for the specified type; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code type} is not an interface type.
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    /* @NonNull */
    public static <T> T createDummy(
        /* @NonNull */
        final Class<T> type )
    {
        if( type == null )
        {
            throw new NullPointerException( "type" ); //$NON-NLS-1$
        }

        final Class<?>[] interfaces = new Class<?>[] {
            type
        };
        return type.cast( Proxy.newProxyInstance( type.getClassLoader(), interfaces, HANDLER ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The invocation handler for all dummy proxies.
     */
    private static final class DummyInvocationHandler
        implements InvocationHandler
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DummyInvocationHandler}
         * class.
         */
        DummyInvocationHandler()
        {
            super();
        }

        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
         */
        public Object invoke(
            @SuppressWarnings( "unused" )
            final Object proxy,
            @SuppressWarnings( "unused" )
            final Method method,
            @SuppressWarnings( "unused" )
            final Object[] args )
            throws Throwable
        {
            throw new AssertionError( "unexpected call to dummy method" ); //$NON-NLS-1$
        }
    }
}

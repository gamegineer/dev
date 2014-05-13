/*
 * Assert.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Feb 26, 2008 at 12:20:58 AM.
 */

package org.gamegineer.common.core.runtime;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of methods useful for asserting conditions at runtime.
 * 
 * <p>
 * The purpose of this class is to encapsulate the most common types of runtime
 * assertions (such as null pointer checks and invalid argument checks) in a
 * single easy-to-call method. For example, instead of typing the following to
 * ensure the method argument {@code value} is legal:
 * </p>
 * 
 * <p>
 * 
 * <pre>
 * if( value.length() != 5 )
 * {
 *     throw new IllegalArgumentException( &quot;value&quot; );
 * }
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * you can instead make the following call:
 * </p>
 * 
 * <p>
 * 
 * <pre>
 * Assert.assertArgumentLegal( value.length() != 5, &quot;value&quot; );
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * to obtain the same result.
 * </p>
 * 
 * <p>
 * This class is only meant to assert preconditions and postconditions related
 * to a class or method contract. It is not intended to be a replacement for the
 * Java {@code assert} keyword, which should continue to be used for asserting
 * general runtime conditions related to the implementation details of a class
 * or method.
 * </p>
 */
@ThreadSafe
public final class Assert
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Assert} class.
     */
    private Assert()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts an argument is legal.
     * 
     * @param expression
     *        The expression that tests the argument.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code expression} is {@code false}.
     */
    public static void assertArgumentLegal(
        final boolean expression )
    {
        assertArgumentLegal( expression, null, null );
    }

    /**
     * Asserts an argument is legal.
     * 
     * @param expression
     *        The expression that tests the argument.
     * @param paramName
     *        The name of the associated parameter; may be {@code null}. This
     *        value should not be localized.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code expression} is {@code false}.
     */
    public static void assertArgumentLegal(
        final boolean expression,
        @Nullable
        final String paramName )
    {
        assertArgumentLegal( expression, paramName, null );
    }

    /**
     * Asserts an argument is legal.
     * 
     * @param expression
     *        The expression that tests the argument.
     * @param paramName
     *        The name of the associated parameter; may be {@code null}. This
     *        value should not be localized.
     * @param message
     *        The message to include in the exception; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code expression} is {@code false}.
     */
    public static void assertArgumentLegal(
        final boolean expression,
        @Nullable
        final String paramName,
        @Nullable
        final String message )
    {
        if( !expression )
        {
            final String displayParamName = (paramName != null) ? paramName : NonNlsMessages.Assert_defaultParamName;
            final String displayMessage = (message != null) ? message : NonNlsMessages.Assert_assertArgumentLegal_defaultMessage;
            throw new IllegalArgumentException( NonNlsMessages.Assert_message( displayParamName, displayMessage ) );
        }
    }

    /**
     * Asserts an object's state is legal.
     * 
     * @param expression
     *        The expression that tests the object's state.
     * 
     * @throws java.lang.IllegalStateException
     *         If {@code expression} is {@code false}.
     */
    public static void assertStateLegal(
        final boolean expression )
    {
        assertStateLegal( expression, null );
    }

    /**
     * Asserts an object's state is legal.
     * 
     * @param expression
     *        The expression that tests the object's state.
     * @param message
     *        The message to include in the exception; may be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If {@code expression} is {@code null}.
     */
    public static void assertStateLegal(
        final boolean expression,
        @Nullable
        final String message )
    {
        if( !expression )
        {
            throw new IllegalStateException( message );
        }
    }
}

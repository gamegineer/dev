/*
 * AssertTest.java
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
 * Created on Feb 27, 2008 at 11:06:06 PM.
 */

package org.gamegineer.common.core.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.common.core.runtime.Assert}
 * class.
 */
public final class AssertTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AssertTest} class.
     */
    public AssertTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code assertArgumentLegal(boolean)} method throws an
     * exception if the argument is not legal.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertArgumentLegal_Expression_False()
    {
        Assert.assertArgumentLegal( false );
    }

    /**
     * Ensures the {@code assertArgumentLegal(boolean)} method does not throw an
     * exception if the argument is legal.
     */
    @Test
    public void testAssertArgumentLegal_Expression_True()
    {
        Assert.assertArgumentLegal( true );
    }

    /**
     * Ensures the {@code assertArgumentLegal(boolean, java.lang.String)} method
     * throws an exception if the argument is not legal.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertArgumentLegalWithParamName_Expression_False()
    {
        Assert.assertArgumentLegal( false, "paramName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code assertArgumentLegal(boolean, java.lang.String)} method
     * does not throw an exception if the argument is legal.
     */
    @Test
    public void testAssertArgumentLegalWithParamName_Expression_True()
    {
        Assert.assertArgumentLegal( true, "paramName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code assertArgumentLegal(boolean, java.lang.String)} method
     * does not throw an exception if the parameter name is {@code null}.
     */
    @Test
    public void testAssertArgumentLegalWithParamName_ParamName_Null()
    {
        Assert.assertArgumentLegal( true, null );
    }

    /**
     * Ensures the
     * {@code assertArgumentLegal(boolean, java.lang.String, java.lang.String)}
     * method throws an exception if the argument is not legal.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertArgumentLegalWithParamNameAndMessage_Expression_False()
    {
        Assert.assertArgumentLegal( false, "paramName", "message" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the
     * {@code assertArgumentLegal(boolean, java.lang.String, java.lang.String)}
     * method does not throw an exception if the argument is legal.
     */
    @Test
    public void testAssertArgumentLegalWithParamNameAndMessage_Expression_True()
    {
        Assert.assertArgumentLegal( true, "paramName", "message" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the
     * {@code assertArgumentLegal(boolean, java.lang.String, java.lang.String)}
     * method does not throw an exception if the message is {@code null}.
     */
    @Test
    public void testAssertArgumentLegalWithParamNameAndMessage_Message_Null()
    {
        Assert.assertArgumentLegal( true, "paramName", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code assertArgumentLegal(boolean, java.lang.String, java.lang.String)}
     * method does not throw an exception if the parameter name is {@code null}.
     */
    @Test
    public void testAssertArgumentLegalWithParamNameAndMessage_ParamName_Null()
    {
        Assert.assertArgumentLegal( true, null, "message" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code assertArgumentNotNull(java.lang.Object)} method does
     * not throw an exception if the argument is not {@code null}.
     */
    @Test
    public void testAssertArgumentNotNull_Object_NotNull()
    {
        Assert.assertArgumentNotNull( new Object() );
    }

    /**
     * Ensures the {@code assertArgumentNotNull(java.lang.Object)} method throws
     * an exception if the argument is {@code null}.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertArgumentNotNull_Object_Null()
    {
        Assert.assertArgumentNotNull( null );
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String)} method
     * does not throw an exception if the parameter name is {@code null}.
     */
    @Test
    public void testAssertArgumentNotNullWithParamName_ParamName_Null()
    {
        Assert.assertArgumentNotNull( new Object(), null );
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String)} method
     * does not throw an exception if the argument is not {@code null}.
     */
    @Test
    public void testAssertArgumentNotNullWithParamName_Object_NotNull()
    {
        Assert.assertArgumentNotNull( new Object(), "paramName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String)} method
     * throws an exception if the argument is {@code null}.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertArgumentNotNullWithParamName_Object_Null()
    {
        Assert.assertArgumentNotNull( null, "paramName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String, java.lang.String)}
     * method does not throw an exception if the message is {@code null}.
     */
    @Test
    public void testAssertArgumentNotNullWithParamNameAndMessage_Message_Null()
    {
        Assert.assertArgumentNotNull( new Object(), "paramName", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String, java.lang.String)}
     * method does not throw an exception if the parameter name is {@code null}.
     */
    @Test
    public void testAssertArgumentNotNullWithParamNameAndMessage_ParamName_Null()
    {
        Assert.assertArgumentNotNull( new Object(), null, "message" ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String, java.lang.String)}
     * method does not throw an exception if the argument is not {@code null}.
     */
    @Test
    public void testAssertArgumentNotNullWithParamNameAndMessage_Object_NotNull()
    {
        Assert.assertArgumentNotNull( new Object(), "paramName", "message" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the
     * {@code assertArgumentNotNull(java.lang.Object, java.lang.String, java.lang.String)}
     * method throws an exception if the argument is {@code null}.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertArgumentNotNullWithParamNameAndMessage_Object_Null()
    {
        Assert.assertArgumentNotNull( null, "paramName", "message" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code assertStateLegal(boolean)} method throws an exception
     * if the state is not legal.
     */
    @Test( expected = IllegalStateException.class )
    public void testAssertStateLegal_Expression_False()
    {
        Assert.assertStateLegal( false );
    }

    /**
     * Ensures the {@code assertStateLegal(boolean)} method does not throw an
     * exception if the state is legal.
     */
    @Test
    public void testAssertStateLegal_Expression_True()
    {
        Assert.assertStateLegal( true );
    }

    /**
     * Ensures the {@code assertStateLegal(boolean, java.lang.String)} method
     * throws an exception if the state is not legal.
     */
    @Test( expected = IllegalStateException.class )
    public void testAssertStateLegalWithMessage_Expression_False()
    {
        Assert.assertStateLegal( false, "expression" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code assertStatergumentLegal(boolean, java.lang.String)}
     * method does not throw an exception if the state is legal.
     */
    @Test
    public void testAssertStateLegalWithMessage_Expression_True()
    {
        Assert.assertStateLegal( true, "expression" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code assertStateLegal(boolean, java.lang.String)} method
     * correctly propagates the specified message to the exception thrown if the
     * state is not legal.
     */
    @Test
    public void testAssertStateLegalWithMessage_Message_NotNull()
    {
        final String message = "the quick brown fox jumped over the lazy dog"; //$NON-NLS-1$
        try
        {
            Assert.assertStateLegal( false, message );
            fail();
        }
        catch( final IllegalStateException e )
        {
            assertEquals( message, e.getMessage() );
        }
    }

    /**
     * Ensures the {@code assertStateLegal(boolean, java.lang.String)} method
     * does not throw an exception if the message is {@code null}.
     */
    @Test
    public void testAssertStateLegalWithMessage_Message_Null()
    {
        Assert.assertStateLegal( true, null );
    }
}

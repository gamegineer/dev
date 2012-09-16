/*
 * AbstractMessageTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Feb 26, 2011 at 10:39:03 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.transport.IMessage} interface.
 */
public abstract class AbstractMessageTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message under test in the fixture. */
    private IMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractMessageTestCase} class.
     */
    protected AbstractMessageTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the message to be tested.
     * 
     * @return The message to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IMessage createMessage()
        throws Exception;

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        message_ = createMessage();
        assertNotNull( message_ );
    }

    /**
     * Ensures the {@link IMessage#setCorrelationId} method throws an exception
     * when passed a correlation identifier greater than the maximum correlation
     * identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetCorrelationId_CorrelationId_Illegal_GreaterThanMaxCorrelationId()
    {
        message_.setCorrelationId( IMessage.MAXIMUM_ID + 1 );
    }

    /**
     * Ensures the {@link IMessage#setCorrelationId} method throws an exception
     * when passed a correlation identifier less than the minimum correlation
     * identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetCorrelationId_CorrelationId_Illegal_LessThanMinCorrelationId()
    {
        message_.setCorrelationId( IMessage.NULL_CORRELATION_ID - 1 );
    }

    /**
     * Ensures the {@link IMessage#setId} method throws an exception when passed
     * an identifier greater than the maximum identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetId_Id_Illegal_GreaterThanMaxId()
    {
        message_.setId( IMessage.MAXIMUM_ID + 1 );
    }

    /**
     * Ensures the {@link IMessage#setId} method throws an exception when passed
     * an identifier less than the minimum identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetId_Id_Illegal_LessThanMinId()
    {
        message_.setId( IMessage.MINIMUM_ID - 1 );
    }
}

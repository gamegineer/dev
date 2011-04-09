/*
 * AbstractMessageTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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
import org.gamegineer.table.internal.net.transport.messages.AbstractMessage;
import org.junit.After;
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
        super();
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
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        message_ = null;
    }

    /**
     * Ensures the {@code setTag} method throws an exception when passed a tag
     * greater than the maximum tag.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetTag_Tag_Illegal_GreaterThanMaxTag()
    {
        message_.setTag( AbstractMessage.MAX_TAG + 1 );
    }

    /**
     * Ensures the {@code setTag} method throws an exception when passed a tag
     * less than zero.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetTag_Tag_Illegal_LessThanZero()
    {
        message_.setTag( -1 );
    }
}

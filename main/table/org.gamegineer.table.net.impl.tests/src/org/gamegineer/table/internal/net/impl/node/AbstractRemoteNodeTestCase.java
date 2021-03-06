/*
 * AbstractRemoteNodeTestCase.java
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
 * Created on Apr 16, 2011 at 11:04:26 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IRemoteNode} interface.
 * 
 * @param <T>
 *        The type of the remote node.
 */
public abstract class AbstractRemoteNodeTestCase<T extends IRemoteNode>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The remote node under test in the fixture. */
    private Optional<T> remoteNode_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteNodeTestCase}
     * class.
     */
    protected AbstractRemoteNodeTestCase()
    {
        remoteNode_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the remote node to be tested.
     * 
     * @return The remote node to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract T createRemoteNode()
        throws Exception;

    /**
     * Gets the remote node under test in the fixture.
     * 
     * @return The remote node under test in the fixture.
     */
    protected final T getRemoteNode()
    {
        return remoteNode_.get();
    }

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
        remoteNode_ = Optional.of( createRemoteNode() );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}

/*
 * RunnableTaskTest.java
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
 * Created on Nov 23, 2010 at 8:35:03 PM.
 */

package org.gamegineer.common.ui.operation;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.operation.RunnableTask} class.
 */
public final class RunnableTaskTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The task under test in the fixture. */
    private RunnableTask<?, ?> task_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RunnableTaskTest} class.
     */
    public RunnableTaskTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        task_ = new RunnableTask<Object, Object>()
        {
            @Override
            protected Object doInBackground()
            {
                return null;
            }
        };
    }

    /**
     * Ensures the {@code setDescription} method throws an exception when passed
     * a {@code null} description.
     */
    @Test( expected = NullPointerException.class )
    public void testSetDescription_Description_Null()
    {
        task_.setDescription( null );
    }
}

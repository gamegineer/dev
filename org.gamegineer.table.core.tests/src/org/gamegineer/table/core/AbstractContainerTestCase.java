/*
 * AbstractContainerTestCase.java
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
 * Created on Mar 26, 2012 at 8:31:54 PM.
 */

package org.gamegineer.table.core;

import org.junit.Before;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IContainer} interface.
 * 
 * @param <T>
 *        The type of the container.
 */
public abstract class AbstractContainerTestCase<T extends IContainer>
    extends AbstractComponentTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerTestCase}
     * class.
     */
    protected AbstractContainerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container under test in the fixture.
     * 
     * @return The container under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getContainer()
    {
        return getComponent();
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        super.setUp();
    }
}

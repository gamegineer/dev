/*
 * ContainerListenerAsContainerListenerTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Mar 29, 2012 at 8:50:08 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.table.core.test.AbstractContainerListenerTestCase;

/**
 * A fixture for testing the {@link ContainerListener} class to ensure it does
 * not violate the contract of the {@link IContainerListener} interface.
 */
public final class ContainerListenerAsContainerListenerTest
    extends AbstractContainerListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerListenerAsContainerListenerTest} class.
     */
    public ContainerListenerAsContainerListenerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractContainerListenerTestCase#createContainerListener()
     */
    @Override
    protected IContainerListener createContainerListener()
    {
        return new ContainerListener();
    }
}

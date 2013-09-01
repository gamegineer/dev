/*
 * ContainerModelListenerAsContainerModelListenerTest.java
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
 * Created on Aug 3, 2011 at 8:47:24 PM.
 */

package org.gamegineer.table.internal.ui.model;

/**
 * A fixture for testing the {@link ContainerModelListener} class to ensure it
 * does not violate the contract of the {@link IContainerModelListener}
 * interface.
 */
public final class ContainerModelListenerAsContainerModelListenerTest
    extends AbstractContainerModelListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerModelListenerAsContainerModelListenerTest} class.
     */
    public ContainerModelListenerAsContainerModelListenerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractContainerModelListenerTestCase#createContainerModelListener()
     */
    @Override
    protected IContainerModelListener createContainerModelListener()
    {
        return new ContainerModelListener();
    }
}

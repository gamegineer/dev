/*
 * ContainerLayoutRegistryAsContainerLayoutRegistryTest.java
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
 * Created on Aug 9, 2012 at 8:39:28 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.AbstractContainerLayoutRegistryTestCase;
import org.gamegineer.table.core.IContainerLayoutRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ContainerLayoutRegistry} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.IContainerLayoutRegistry} interface.
 */
public final class ContainerLayoutRegistryAsContainerLayoutRegistryTest
    extends AbstractContainerLayoutRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerLayoutRegistryAsContainerLayoutRegistryTest} class.
     */
    public ContainerLayoutRegistryAsContainerLayoutRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractContainerLayoutRegistryTestCase#createContainerLayoutRegistry()
     */
    @Override
    protected IContainerLayoutRegistry createContainerLayoutRegistry()
    {
        return new ContainerLayoutRegistry();
    }
}

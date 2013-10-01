/*
 * ContainerLayoutRegistryAsContainerLayoutRegistryTest.java
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
 * Created on Aug 9, 2012 at 8:39:28 PM.
 */

package org.gamegineer.table.internal.core.impl;

import org.gamegineer.common.core.util.registry.IRegistry;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerLayoutRegistry;
import org.gamegineer.table.core.test.AbstractContainerLayoutRegistryTestCase;

/**
 * A fixture for testing the {@link ContainerLayoutRegistry} class to ensure it
 * does not violate the contract of the {@link IContainerLayoutRegistry}
 * interface.
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
     * @see org.gamegineer.common.core.util.registry.test.AbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected IRegistry<ContainerLayoutId, IContainerLayout> createRegistry()
    {
        return new ContainerLayoutRegistry();
    }
}

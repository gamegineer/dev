/*
 * ContainerLayoutRegistryAsAbstractRegistryTest.java
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
 * Created on Nov 16, 2012 at 9:41:36 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.common.core.util.registry.AbstractAbstractRegistryTestCase;
import org.gamegineer.common.core.util.registry.AbstractRegistry;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ContainerLayoutRegistry} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.util.registry.AbstractRegistry} class.
 */
public final class ContainerLayoutRegistryAsAbstractRegistryTest
    extends AbstractAbstractRegistryTestCase<ContainerLayoutId, IContainerLayout>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerLayoutRegistryAsAbstractRegistryTest} class.
     */
    public ContainerLayoutRegistryAsAbstractRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractAbstractRegistryTestCase#createRegistry()
     */
    @Override
    protected AbstractRegistry<ContainerLayoutId, IContainerLayout> createRegistry()
    {
        return new ContainerLayoutRegistry();
    }
}
/*
 * AbstractContainerLayoutRegistryTestCase.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Aug 9, 2012 at 8:13:46 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IContainerLayoutRegistry} interface.
 */
public abstract class AbstractContainerLayoutRegistryTestCase
    extends AbstractRegistryTestCase<ContainerLayoutId, IContainerLayout>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractContainerLayoutRegistryTestCase} class.
     */
    protected AbstractContainerLayoutRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#cloneObject(java.lang.Object)
     */
    @Override
    protected final IContainerLayout cloneObject(
        final IContainerLayout object )
    {
        return TestContainerLayouts.cloneContainerLayout( object );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#createUniqueObject()
     */
    @Override
    protected final IContainerLayout createUniqueObject()
    {
        return TestContainerLayouts.createUniqueContainerLayout();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractRegistryTestCase#getObjectId(java.lang.Object)
     */
    @Override
    protected final ContainerLayoutId getObjectId(
        final IContainerLayout object )
    {
        return object.getId();
    }
}

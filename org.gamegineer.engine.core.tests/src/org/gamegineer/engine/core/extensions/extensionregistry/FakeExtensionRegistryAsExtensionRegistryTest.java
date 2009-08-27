/*
 * FakeExtensionRegistryAsExtensionRegistryTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Apr 24, 2009 at 10:15:18 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import org.gamegineer.engine.core.IEngineContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.FakeExtensionRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry}
 * interface.
 */
public final class FakeExtensionRegistryAsExtensionRegistryTest
    extends AbstractExtensionRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code FakeExtensionRegistryAsExtensionRegistryTest} class.
     */
    public FakeExtensionRegistryAsExtensionRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.extensionregistry.AbstractExtensionRegistryTestCase#createExtensionRegistry(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected IExtensionRegistry createExtensionRegistry(
        @SuppressWarnings( "unused" )
        final IEngineContext context )
    {
        return new FakeExtensionRegistry();
    }
}

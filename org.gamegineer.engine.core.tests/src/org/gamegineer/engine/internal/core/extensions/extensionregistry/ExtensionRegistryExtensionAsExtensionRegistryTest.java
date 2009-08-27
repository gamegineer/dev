/*
 * ExtensionRegistryExtensionAsExtensionRegistryTest.java
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
 * Created on Apr 19, 2008 at 10:19:15 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.extensionregistry.AbstractExtensionRegistryTestCase;
import org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.ExtensionRegistryExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.extensionregistry.IExtensionRegistry}
 * interface.
 */
public final class ExtensionRegistryExtensionAsExtensionRegistryTest
    extends AbstractExtensionRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ExtensionRegistryExtensionAsExtensionRegistryTest} class.
     */
    public ExtensionRegistryExtensionAsExtensionRegistryTest()
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
        final IEngineContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final ExtensionRegistryExtension extension = new ExtensionRegistryExtension();
        extension.start( context );
        return extension;
    }
}

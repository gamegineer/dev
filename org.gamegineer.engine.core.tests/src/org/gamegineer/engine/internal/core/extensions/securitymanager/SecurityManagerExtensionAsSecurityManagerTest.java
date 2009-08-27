/*
 * SecurityManagerExtensionAsSecurityManagerTest.java
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
 * Created on Apr 1, 2009 at 10:03:57 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.securitymanager.AbstractSecurityManagerTestCase;
import org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.securitymanager.SecurityManagerExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager}
 * interface.
 */
public final class SecurityManagerExtensionAsSecurityManagerTest
    extends AbstractSecurityManagerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SecurityManagerExtensionAsSecurityManagerTest} class.
     */
    public SecurityManagerExtensionAsSecurityManagerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.securitymanager.AbstractSecurityManagerTestCase#createEngineContext()
     */
    @Override
    protected IEngineContext createEngineContext()
    {
        return SecurityManagerExtensionTest.createEngineContext();
    }

    /*
     * @see org.gamegineer.engine.core.extensions.securitymanager.AbstractSecurityManagerTestCase#createSecurityManager(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected ISecurityManager createSecurityManager(
        final IEngineContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final SecurityManagerExtension extension = new SecurityManagerExtension();
        extension.start( context );
        return extension;
    }
}

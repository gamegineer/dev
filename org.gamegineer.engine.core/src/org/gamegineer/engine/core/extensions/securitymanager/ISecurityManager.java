/*
 * ISecurityManager.java
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
 * Created on Mar 30, 2009 at 11:00:44 PM.
 */

package org.gamegineer.engine.core.extensions.securitymanager;

import java.security.Principal;
import org.gamegineer.engine.core.IEngineContext;

/**
 * An engine extension that manages engine security.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ISecurityManager
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the user principal associated with the specified context.
     * 
     * @param context
     *        The context within which the extension is executed; must not be
     *        {@code null}.
     * 
     * @return The user principal associated with the specified context; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    public Principal getUserPrincipal(
        /* @NonNull */
        IEngineContext context );
}

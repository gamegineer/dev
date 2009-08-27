/*
 * Principals.java
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
 * Created on Apr 8, 2009 at 10:50:57 PM.
 */

package org.gamegineer.engine.core.extensions.securitymanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.Principal;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.engine.internal.core.extensions.securitymanager.UserPrincipal;

/**
 * A factory for creating security principals suitable for use with the security
 * manager extension.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class Principals
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The anonymous user principal. */
    private static final Principal ANONYMOUS_USER_PRINCIPAL = new UserPrincipal( "" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Principals} class.
     */
    private Principals()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a user principal with the specified name.
     * 
     * @param name
     *        The user name; must not be {@code null}.
     * 
     * @return A user principal; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public static Principal createUserPrincipal(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return new UserPrincipal( name );
    }

    /**
     * Gets the anonymous user principal.
     * 
     * @return The anonymous user principal; never {@code null}.
     */
    /* @NonNull */
    public static Principal getAnonymousUserPrincipal()
    {
        return ANONYMOUS_USER_PRINCIPAL;
    }
}

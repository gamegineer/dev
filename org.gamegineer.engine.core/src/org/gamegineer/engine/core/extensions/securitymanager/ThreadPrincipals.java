/*
 * ThreadPrincipals.java
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
 * Created on Mar 30, 2009 at 11:07:00 PM.
 */

package org.gamegineer.engine.core.extensions.securitymanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.Principal;
import net.jcip.annotations.ThreadSafe;

/**
 * A facade for getting and setting the principals associated with the current
 * thread.
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
public final class ThreadPrincipals
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The thread user principal. */
    private static final ThreadLocal<Principal> principal_ = new ThreadLocal<Principal>()
    {
        @Override
        protected Principal initialValue()
        {
            return Principals.getAnonymousUserPrincipal();
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ThreadPrincipals} class.
     */
    private ThreadPrincipals()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clears the user principal;associated with the current thread.
     * 
     * <p>
     * This method effectively sets the user principal associated with the
     * current thread to the anonymous user.
     * </p>
     */
    public static void clearUserPrincipal()
    {
        setUserPrincipal( Principals.getAnonymousUserPrincipal() );
    }

    /**
     * Gets the user principal associated with the current thread.
     * 
     * @return The user principal associated with the current thread; never
     *         {@code null}.
     */
    /* @NonNull */
    public static Principal getUserPrincipal()
    {
        return principal_.get();
    }

    /**
     * Sets the user principal associated with the current thread.
     * 
     * @param principal
     *        The user principal; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code principal} is {@code null}.
     */
    public static void setUserPrincipal(
        /* @NonNull */
        final Principal principal )
    {
        assertArgumentNotNull( principal, "principal" ); //$NON-NLS-1$

        principal_.set( principal );
    }
}

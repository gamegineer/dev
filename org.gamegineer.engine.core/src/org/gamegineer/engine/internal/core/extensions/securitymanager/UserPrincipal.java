/*
 * UserPrincipal.java
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
 * Created on Apr 10, 2009 at 9:26:34 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.security.Principal;
import net.jcip.annotations.Immutable;

/**
 * A user principal.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class UserPrincipal
    implements Principal
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The user name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code UserPrincipal} class.
     * 
     * @param name
     *        The user name; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    public UserPrincipal(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( obj == this )
        {
            return true;
        }

        if( !(obj instanceof UserPrincipal) )
        {
            return false;
        }

        final UserPrincipal other = (UserPrincipal)obj;
        return name_.equals( other.name_ );
    }

    /*
     * @see java.security.Principal#getName()
     */
    public String getName()
    {
        return name_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = result * 31 + name_.hashCode();
        return result;
    }
}

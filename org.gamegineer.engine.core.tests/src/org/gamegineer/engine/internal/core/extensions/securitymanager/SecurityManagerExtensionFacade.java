/*
 * SecurityManagerExtensionFacade.java
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
 * Created on May 4, 2009 at 9:20:46 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import java.lang.reflect.Field;

/**
 * A class for transparently accessing inaccessible members of the
 * {@code SecurityManagerExtension} class for testing purposes.
 */
final class SecurityManagerExtensionFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SecurityManagerExtensionFacade}
     * class.
     */
    private SecurityManagerExtensionFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Accessible facade for the {@code ATTR_USER_PRINCIPAL} class field.
     * 
     * @return The value of the {@code ATTR_USER_PRINCIPAL} class field; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ATTR_USER_PRINCIPAL()
    {
        try
        {
            final Field field = SecurityManagerExtension.class.getDeclaredField( "ATTR_USER_PRINCIPAL" ); //$NON-NLS-1$
            field.setAccessible( true );
            return (String)field.get( null );
        }
        catch( final Exception e )
        {
            throw new AssertionError( "failed to read 'ATTR_USER_PRINCIPAL'" ); //$NON-NLS-1$
        }
    }
}

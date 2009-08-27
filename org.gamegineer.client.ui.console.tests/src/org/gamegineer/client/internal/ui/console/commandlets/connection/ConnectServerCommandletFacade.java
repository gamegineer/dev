/*
 * ConnectServerCommandletFacade.java
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
 * Created on May 25, 2009 at 9:20:47 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.connection;

import java.lang.reflect.Field;

/**
 * A class for transparently accessing inaccessible members of the
 * {@code ConnectServerCommandlet} class for testing purposes.
 */
final class ConnectServerCommandletFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConnectServerCommandletFacade}
     * class.
     */
    private ConnectServerCommandletFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Accessible facade for the {@code CONNECTION_PROPERTY_LOCAL_ID} class
     * field.
     * 
     * @return The value of the {@code CONNECTION_PROPERTY_LOCAL_ID} class
     *         field; never {@code null}.
     */
    /* @NonNull */
    static String CONNECTION_PROPERTY_LOCAL_ID()
    {
        try
        {
            final Field field = ConnectServerCommandlet.class.getDeclaredField( "CONNECTION_PROPERTY_LOCAL_ID" ); //$NON-NLS-1$
            field.setAccessible( true );
            return (String)field.get( null );
        }
        catch( final Exception e )
        {
            throw new AssertionError( "failed to read 'CONNECTION_PROPERTY_LOCAL_ID'" ); //$NON-NLS-1$
        }
    }

    /**
     * Accessible facade for the {@code CONNECTION_TYPE_LOCAL} class field.
     * 
     * @return The value of the {@code CONNECTION_TYPE_LOCAL} class field; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CONNECTION_TYPE_LOCAL()
    {
        try
        {
            final Field field = ConnectServerCommandlet.class.getDeclaredField( "CONNECTION_TYPE_LOCAL" ); //$NON-NLS-1$
            field.setAccessible( true );
            return (String)field.get( null );
        }
        catch( final Exception e )
        {
            throw new AssertionError( "failed to read 'CONNECTION_TYPE_LOCAL'" ); //$NON-NLS-1$
        }
    }
}

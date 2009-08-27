/*
 * ExtensionRegistryExtensionFacade.java
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
 * Created on May 2, 2009 at 11:25:31 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import java.lang.reflect.Field;
import java.util.Map;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.util.attribute.Attribute;

/**
 * A class for transparently accessing inaccessible members of the
 * {@code ExtensionRegistryExtension} class for testing purposes.
 */
final class ExtensionRegistryExtensionFacade
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ExtensionRegistryExtensionFacade} class.
     */
    private ExtensionRegistryExtensionFacade()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Accessible facade for the {@code EXTENSIONS_ATTRIBUTE} class field.
     * 
     * @return The value of the {@code EXTENSIONS_ATTRIBUTE} class field; never
     *         {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "unchecked" )
    static Attribute<Map<Class<?>, IExtension>> EXTENSIONS_ATTRIBUTE()
    {
        try
        {
            final Field field = ExtensionRegistryExtension.class.getDeclaredField( "EXTENSIONS_ATTRIBUTE" ); //$NON-NLS-1$
            field.setAccessible( true );
            return (Attribute<Map<Class<?>, IExtension>>)field.get( null );
        }
        catch( final Exception e )
        {
            throw new AssertionError( "failed to read 'EXTENSIONS_ATTRIBUTE'" ); //$NON-NLS-1$
        }
    }
}

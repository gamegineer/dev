/*
 * ComponentCreationContext.java
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
 * Created on Apr 10, 2008 at 10:53:17 PM.
 */

package org.gamegineer.common.internal.core.services.component;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.IComponentCreationContext;

/**
 * Implementation of
 * {@link org.gamegineer.common.core.services.component.IComponentCreationContext}
 * .
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class ComponentCreationContext
    implements IComponentCreationContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The map of initialization attributes. */
    private final Map<String, Object> attributeMap_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentCreationContext} class
     * using the specified attribute mappings.
     * 
     * @param attributeMap
     *        The map of initialization attributes; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any value in {@code attributeMap} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code attributeMap} is {@code null}.
     */
    public ComponentCreationContext(
        /* @NonNull */
        final Map<String, Object> attributeMap )
    {
        assertArgumentNotNull( attributeMap, "attributeMap" ); //$NON-NLS-1$
        assertArgumentLegal( !attributeMap.containsValue( null ), "attributeMap", Messages.ComponentCreationContext_attributeMap_containsNullValue ); //$NON-NLS-1$

        attributeMap_ = new HashMap<String, Object>( attributeMap );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentCreationContext#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributeMap_.containsKey( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentCreationContext#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributeMap_.get( name );
    }
}

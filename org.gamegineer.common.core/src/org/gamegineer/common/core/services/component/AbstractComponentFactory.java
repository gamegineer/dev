/*
 * AbstractComponentFactory.java
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
 * Created on May 21, 2008 at 9:34:30 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator;

/**
 * Superclass for implementations of
 * {@link org.gamegineer.common.core.services.component.IComponentFactory}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public abstract class AbstractComponentFactory
    implements IComponentFactory, IAttributeAccessor, IAttributeMutator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The map of factory attributes. */
    private final ConcurrentMap<String, Object> attributeMap_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentFactory} class.
     */
    protected AbstractComponentFactory()
    {
        attributeMap_ = new ConcurrentHashMap<String, Object>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributeMap_.containsKey( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#getAttribute(java.lang.String)
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributeMap_.get( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(
        final String name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( value, "value" ); //$NON-NLS-1$

        attributeMap_.put( name, value );
    }
}

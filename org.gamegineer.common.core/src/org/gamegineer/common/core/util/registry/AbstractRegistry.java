/*
 * AbstractRegistry.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Nov 15, 2012 at 9:40:17 PM.
 */

package org.gamegineer.common.core.util.registry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.core.Debug;

/**
 * Superclass for implementations of {@link IRegistry}.
 * 
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
@ThreadSafe
public abstract class AbstractRegistry<ObjectIdType, ObjectType>
    implements IRegistry<ObjectIdType, ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of objects managed by this object. */
    private final ConcurrentMap<ObjectIdType, ObjectType> objects_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRegistry} class.
     */
    protected AbstractRegistry()
    {
        objects_ = new ConcurrentHashMap<ObjectIdType, ObjectType>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#get(java.lang.Object)
     */
    @Override
    public final ObjectType get(
        final ObjectIdType id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return objects_.get( id );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#getAll()
     */
    @Override
    public final Collection<ObjectType> getAll()
    {
        return new ArrayList<ObjectType>( objects_.values() );
    }

    /**
     * Gets the identifier of the specified object.
     * 
     * @param object
     *        The object; must not be {@code null}.
     * 
     * @return The identifier of the specified object; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code object} is {@code null}.
     */
    /* @NonNull */
    protected abstract ObjectIdType getId(
        /* @NonNull */
        ObjectType object );

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#register(java.lang.Object)
     */
    @Override
    public final void register(
        final ObjectType object )
    {
        assertArgumentNotNull( object, "object" ); //$NON-NLS-1$
        final ObjectIdType id = getId( object );
        assertArgumentLegal( objects_.putIfAbsent( id, object ) == null, "object", NonNlsMessages.AbstractRegistry_register_object_registered( id ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered object '%s'", id ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#unregister(java.lang.Object)
     */
    @Override
    public final void unregister(
        final ObjectType object )
    {
        assertArgumentNotNull( object, "object" ); //$NON-NLS-1$
        final ObjectIdType id = getId( object );
        assertArgumentLegal( objects_.remove( id, object ), "object", NonNlsMessages.AbstractRegistry_unregister_object_unregistered( id ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered object '%s'", id ) ); //$NON-NLS-1$
    }
}

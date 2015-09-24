/*
 * AbstractRegistry.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
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
public abstract class AbstractRegistry<@NonNull ObjectIdType, @NonNull ObjectType>
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
        objects_ = new ConcurrentHashMap<>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#getObject(java.lang.Object)
     */
    @Override
    public final @Nullable ObjectType getObject(
        final ObjectIdType id )
    {
        return objects_.get( id );
    }

    /**
     * Gets the identifier of the specified object.
     * 
     * @param object
     *        The object.
     * 
     * @return The identifier of the specified object.
     */
    protected abstract ObjectIdType getObjectId(
        ObjectType object );

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#getObjects()
     */
    @Override
    public final Collection<ObjectType> getObjects()
    {
        return new ArrayList<>( objects_.values() );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#registerObject(java.lang.Object)
     */
    @Override
    public final void registerObject(
        final ObjectType object )
    {
        final ObjectIdType id = getObjectId( object );
        assertArgumentLegal( objects_.putIfAbsent( id, object ) == null, "object", NonNlsMessages.AbstractRegistry_registerObject_object_registered( id ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered object '%s'", id ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.core.util.registry.IRegistry#unregisterObject(java.lang.Object)
     */
    @Override
    public final void unregisterObject(
        final ObjectType object )
    {
        final ObjectIdType id = getObjectId( object );
        assertArgumentLegal( objects_.remove( id, object ), "object", NonNlsMessages.AbstractRegistry_unregisterObject_object_unregistered( id ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered object '%s'", id ) ); //$NON-NLS-1$
    }
}

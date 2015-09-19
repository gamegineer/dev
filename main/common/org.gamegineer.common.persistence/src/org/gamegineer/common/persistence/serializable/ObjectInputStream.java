/*
 * ObjectInputStream.java
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
 * Created on Jun 21, 2008 at 8:55:41 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A stream used for deserializing objects previously serialized using an
 * {@code ObjectOutputStream}.
 * 
 * <p>
 * After deserializing an object, the stream will query its persistence delegate
 * registry for an associated persistence delegate and give it an opportunity to
 * substitute the deserialized object with a compatible object.
 * </p>
 * 
 * <p>
 * To contribute a persistence delegate for a specific class, register it with
 * the {@code IPersistenceDelegateRegistry} passed to the input stream.
 * </p>
 */
@NotThreadSafe
public final class ObjectInputStream
    extends java.io.ObjectInputStream
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry. */
    private final IPersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectInputStream} class.
     * 
     * @param in
     *        The input stream from which to read; must not be {@code null}.
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry; must not be {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs while reading the stream header.
     * @throws java.io.StreamCorruptedException
     *         If the stream header is incorrect.
     */
    public ObjectInputStream(
        final InputStream in,
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
        throws IOException
    {
        super( in );

        persistenceDelegateRegistry_ = persistenceDelegateRegistry;

        enableResolveObject( true );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.io.ObjectInputStream#resolveClass(java.io.ObjectStreamClass)
     */
    @Override
    protected @Nullable Class<?> resolveClass(
        final @Nullable ObjectStreamClass desc )
        throws IOException, ClassNotFoundException
    {
        if( desc != null )
        {
            final IPersistenceDelegate delegate = persistenceDelegateRegistry_.getPersistenceDelegate( desc.getName() );
            if( delegate != null )
            {
                return delegate.resolveClass( this, desc );
            }
        }

        return super.resolveClass( desc );
    }

    /*
     * @see java.io.ObjectInputStream#resolveObject(java.lang.Object)
     */
    @Override
    protected @Nullable Object resolveObject(
        final @Nullable Object obj )
        throws IOException
    {
        Object object = obj;
        while( true )
        {
            if( object != null )
            {
                final IPersistenceDelegate delegate = persistenceDelegateRegistry_.getPersistenceDelegate( object.getClass().getName() );
                final Object resolvedObject = (delegate != null) ? delegate.resolveObject( object ) : super.resolveObject( object );
                if( object != resolvedObject )
                {
                    object = resolvedObject;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        return object;
    }
}

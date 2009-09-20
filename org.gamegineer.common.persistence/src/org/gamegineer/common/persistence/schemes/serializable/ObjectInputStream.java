/*
 * ObjectInputStream.java
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
 * Created on Jun 21, 2008 at 8:55:41 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import java.io.IOException;
import java.io.InputStream;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.IAdapterManager;
import org.gamegineer.common.internal.persistence.Services;

/**
 * A stream used for deserializing objects previously serialized using an
 * {@code ObjectOutputStream}.
 * 
 * <p>
 * After deserializing an object, the stream will query the platform for an
 * associated persistence delegate and give it an opportunity to substitute the
 * deserialized object with a compatible object.
 * </p>
 */
@NotThreadSafe
public final class ObjectInputStream
    extends java.io.ObjectInputStream
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The platform adapter manager. */
    private final IAdapterManager adapterManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectInputStream} class.
     * 
     * @param in
     *        The input stream from which to read; must not be {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs while reading the stream header.
     * @throws java.io.StreamCorruptedException
     *         If the stream header is incorrect.
     * @throws java.lang.NullPointerException
     *         If {@code in} is {@code null}.
     */
    public ObjectInputStream(
        /* @NonNull */
        final InputStream in )
        throws IOException
    {
        super( in );

        adapterManager_ = Services.getDefault().getAdapterManager();

        enableResolveObject( true );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.io.ObjectInputStream#resolveObject(java.lang.Object)
     */
    @Override
    protected Object resolveObject(
        final Object obj )
        throws IOException
    {
        final IPersistenceDelegate delegate = (IPersistenceDelegate)adapterManager_.getAdapter( obj, IPersistenceDelegate.class );
        if( delegate != null )
        {
            return delegate.resolveObject( obj );
        }

        return super.resolveObject( obj );
    }
}

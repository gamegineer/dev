/*
 * ObjectOutputStream.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jun 21, 2008 at 9:29:01 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import java.io.IOException;
import java.io.OutputStream;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.persistence.Services;
import org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;

/**
 * A stream used for serializing objects.
 * 
 * <p>
 * Before serializing an object, the stream will query the platform for an
 * associated persistence delegate and give it an opportunity to substitute the
 * object with a compatible serializable object.
 * </p>
 * 
 * <p>
 * To contribute a persistence delegate for a specific class, register it with
 * the platform's {@code IPersistenceDelegateRegistry}.
 * </p>
 */
@NotThreadSafe
public final class ObjectOutputStream
    extends java.io.ObjectOutputStream
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
     * Initializes a new instance of the {@code ObjectOutputStream} class.
     * 
     * @param out
     *        The output stream on which to write; must not be {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs while writing the stream header.
     * @throws java.lang.NullPointerException
     *         If {@code out} is {@code null}.
     */
    public ObjectOutputStream(
        /* @NonNull */
        final OutputStream out )
        throws IOException
    {
        super( out );

        persistenceDelegateRegistry_ = Services.getDefault().getSerializablePersistenceDelegateRegistry();

        enableReplaceObject( true );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.io.ObjectOutputStream#annotateClass(java.lang.Class)
     */
    @Override
    protected void annotateClass(
        final Class<?> cl )
        throws IOException
    {
        final IPersistenceDelegate delegate = persistenceDelegateRegistry_.getPersistenceDelegate( cl.getName() );
        if( delegate != null )
        {
            delegate.annotateClass( this, cl );
        }
        else
        {
            super.annotateClass( cl );
        }
    }

    /*
     * @see java.io.ObjectOutputStream#replaceObject(java.lang.Object)
     */
    @Override
    protected Object replaceObject(
        final Object obj )
        throws IOException
    {
        final IPersistenceDelegate delegate = persistenceDelegateRegistry_.getPersistenceDelegate( obj.getClass().getName() );
        if( delegate != null )
        {
            return delegate.replaceObject( obj );
        }

        return super.replaceObject( obj );
    }
}

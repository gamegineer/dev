/*
 * ObjectOutputStream.java
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
 * Created on Jun 21, 2008 at 9:29:01 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.io.IOException;
import java.io.OutputStream;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A stream used for serializing objects.
 * 
 * <p>
 * Before serializing an object, the stream will query its persistence delegate
 * registry for an associated persistence delegate and give it an opportunity to
 * substitute the object with a compatible serializable object.
 * </p>
 * 
 * <p>
 * To contribute a persistence delegate for a specific class, register it with
 * the {@code IPersistenceDelegateRegistry} passed to the output stream.
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
     *        The output stream on which to write.
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs while writing the stream header.
     */
    public ObjectOutputStream(
        final OutputStream out,
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
        throws IOException
    {
        super( out );

        persistenceDelegateRegistry_ = persistenceDelegateRegistry;

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
        final @Nullable Class<?> cl )
        throws IOException
    {
        if( cl != null )
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
        else
        {
            super.annotateClass( cl );
        }
    }

    /*
     * @see java.io.ObjectOutputStream#replaceObject(java.lang.Object)
     */
    @Override
    protected @Nullable Object replaceObject(
        final @Nullable Object obj )
        throws IOException
    {
        Object object = obj;
        while( true )
        {
            if( object != null )
            {
                final IPersistenceDelegate delegate = persistenceDelegateRegistry_.getPersistenceDelegate( object.getClass().getName() );
                final Object replacedObject = (delegate != null) ? delegate.replaceObject( object ) : super.replaceObject( object );
                if( object != replacedObject )
                {
                    object = replacedObject;
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

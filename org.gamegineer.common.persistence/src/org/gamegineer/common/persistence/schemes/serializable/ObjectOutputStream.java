/*
 * ObjectOutputStream.java
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
 * Created on Jun 21, 2008 at 9:29:01 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import java.io.IOException;
import java.io.OutputStream;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.IAdapterManager;
import org.gamegineer.common.internal.persistence.Services;

/**
 * A stream used for serializing objects.
 * 
 * <p>
 * Before serializing an object, the stream will query the platform for an
 * associated persistence delegate and give it an opportunity to substitute the
 * object with a compatible serializable object.
 * </p>
 */
@NotThreadSafe
public final class ObjectOutputStream
    extends java.io.ObjectOutputStream
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

        adapterManager_ = Services.getDefault().getAdapterManager();

        enableReplaceObject( true );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.io.ObjectOutputStream#replaceObject(java.lang.Object)
     */
    @Override
    protected Object replaceObject(
        final Object obj )
        throws IOException
    {
        final IPersistenceDelegate delegate = (IPersistenceDelegate)adapterManager_.getAdapter( obj, IPersistenceDelegate.class );
        if( delegate != null )
        {
            return delegate.replaceObject( obj );
        }

        return super.replaceObject( obj );
    }
}

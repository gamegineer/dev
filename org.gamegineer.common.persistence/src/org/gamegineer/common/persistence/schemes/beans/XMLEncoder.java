/*
 * XMLEncoder.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 21, 2008 at 11:07:32 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.beans.PersistenceDelegate;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IAdapterManager;
import org.gamegineer.common.internal.persistence.Services;

/**
 * An encoder used for persisting objects using an XML representation.
 * 
 * <p>
 * Before encoding an object, the encoder will query the platform for an
 * associated persistence delegate that will assume the responsibility for
 * encoding the object state. The persistence delegate registered with the
 * platform will take precedence over any other persistence delegate registered
 * as outlined in {@link java.beans.Encoder#getPersistenceDelegate(Class)}.
 * </p>
 * 
 * <p>
 * To contribute a persistence delegate for a specific class, register it with
 * the platform's {@code IAdapterManager}.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class XMLEncoder
    extends java.beans.XMLEncoder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The platform adapter manager. */
    private final IAdapterManager m_adapterManager;

    /** The map of persistence delegates registered through the adapter manager. */
    private final Map<Class<?>, PersistenceDelegate> m_delegateMap;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XMLEncoder} class.
     * 
     * @param out
     *        The output stream on which to write; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code out} is {@code null}.
     */
    public XMLEncoder(
        /* @NonNull */
        final OutputStream out )
    {
        super( out );

        assertArgumentNotNull( out, "out" ); //$NON-NLS-1$

        m_adapterManager = Services.getDefault().getAdapterManager();
        m_delegateMap = new HashMap<Class<?>, PersistenceDelegate>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.beans.Encoder#getPersistenceDelegate(java.lang.Class)
     */
    @Override
    public PersistenceDelegate getPersistenceDelegate(
        final Class<?> type )
    {
        final PersistenceDelegate delegate = m_delegateMap.get( type );
        if( delegate != null )
        {
            return delegate;
        }

        return super.getPersistenceDelegate( type );
    }

    /*
     * @see java.beans.XMLEncoder#writeObject(java.lang.Object)
     */
    @Override
    public void writeObject(
        final Object o )
    {
        // We must cache any persistence delegate registered through the adapter
        // manager here because of the impedance mismatch between Encoder and
        // IAdapterManager.  The adapter manager only accepts objects while Encoder
        // requests persistence delegates explicitly by class.  Therefore, we request
        // the adapter manager's persistence delegate here using the object so it can
        // be queried by class in getPersistenceDelegate().
        //
        // Note also that we store null values in the map.  A null value is used as a
        // flag to stop us from querying the adapter manager for a persistence
        // delegate when it has already indicated none have been registered for the
        // object's class.

        if( (o != null) && !m_delegateMap.containsKey( o.getClass() ) )
        {
            final PersistenceDelegate delegate = (PersistenceDelegate)m_adapterManager.getAdapter( o, PersistenceDelegate.class );
            m_delegateMap.put( o.getClass(), delegate );
        }

        super.writeObject( o );
    }
}

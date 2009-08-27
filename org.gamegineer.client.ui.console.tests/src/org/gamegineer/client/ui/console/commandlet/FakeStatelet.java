/*
 * FakeStatelet.java
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
 * Created on May 15, 2009 at 9:14:50 PM.
 */

package org.gamegineer.client.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Fake implementation of
 * {@link org.gamegineer.client.ui.console.commandlet.IStatelet}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class FakeStatelet
    implements IStatelet
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute collection. */
    private final Map<String, Object> m_attributes;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeStatelet} class.
     */
    public FakeStatelet()
    {
        m_attributes = new HashMap<String, Object>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.commandlet.IStatelet#addAttribute(java.lang.String, java.lang.Object)
     */
    public void addAttribute(
        final String name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( !m_attributes.containsKey( name ), "name" ); //$NON-NLS-1$

        m_attributes.put( name, value );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.IStatelet#containsAttribute(java.lang.String)
     */
    public boolean containsAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return m_attributes.containsKey( name );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.IStatelet#getAttribute(java.lang.String)
     */
    public Object getAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( m_attributes.containsKey( name ), "name" ); //$NON-NLS-1$

        return m_attributes.get( name );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.IStatelet#getAttributeNames()
     */
    public Set<String> getAttributeNames()
    {
        return Collections.unmodifiableSet( m_attributes.keySet() );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.IStatelet#removeAttribute(java.lang.String)
     */
    public void removeAttribute(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( m_attributes.containsKey( name ), "name" ); //$NON-NLS-1$

        m_attributes.remove( name );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.IStatelet#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(
        final String name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( m_attributes.containsKey( name ), "name" ); //$NON-NLS-1$

        m_attributes.put( name, value );
    }
}

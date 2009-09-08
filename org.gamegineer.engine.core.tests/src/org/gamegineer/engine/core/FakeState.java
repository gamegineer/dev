/*
 * FakeState.java
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
 * Created on May 31, 2008 at 8:41:54 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Fake implementation of {@link org.gamegineer.engine.core.IState}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class FakeState
    implements IState
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute map. */
    private Map<AttributeName, Object> attributeMap_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeState} class.
     */
    public FakeState()
    {
        attributeMap_ = new HashMap<AttributeName, Object>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.IState#addAttribute(org.gamegineer.engine.core.AttributeName, java.lang.Object)
     */
    public void addAttribute(
        final AttributeName name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( !attributeMap_.containsKey( name ), "name" ); //$NON-NLS-1$

        attributeMap_.put( name, value );
    }

    /*
     * @see org.gamegineer.engine.core.IState#containsAttribute(org.gamegineer.engine.core.AttributeName)
     */
    public boolean containsAttribute(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributeMap_.containsKey( name );
    }

    /*
     * @see org.gamegineer.engine.core.IState#getAttribute(org.gamegineer.engine.core.AttributeName)
     */
    public Object getAttribute(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributeMap_.containsKey( name ), "name" ); //$NON-NLS-1$

        return attributeMap_.get( name );
    }

    /*
     * @see org.gamegineer.engine.core.IState#getAttributeNames()
     */
    public Set<AttributeName> getAttributeNames()
    {
        return Collections.unmodifiableSet( attributeMap_.keySet() );
    }

    /*
     * @see org.gamegineer.engine.core.IState#removeAttribute(org.gamegineer.engine.core.AttributeName)
     */
    public void removeAttribute(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributeMap_.containsKey( name ), "name" ); //$NON-NLS-1$

        attributeMap_.remove( name );
    }

    /*
     * @see org.gamegineer.engine.core.IState#setAttribute(org.gamegineer.engine.core.AttributeName, java.lang.Object)
     */
    public void setAttribute(
        final AttributeName name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributeMap_.containsKey( name ), "name" ); //$NON-NLS-1$

        attributeMap_.put( name, value );
    }
}

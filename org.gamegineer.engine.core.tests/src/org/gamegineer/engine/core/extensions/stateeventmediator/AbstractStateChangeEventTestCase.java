/*
 * AbstractStateChangeEventTestCase.java
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
 * Created on Jun 2, 2008 at 8:59:54 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateChangeEvent}
 * interface.
 * 
 * @param <T>
 *        The type of the state change event.
 */
public abstract class AbstractStateChangeEventTestCase<T extends IStateChangeEvent>
    extends AbstractStateEventTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractStateChangeEventTestCase} class.
     */
    protected AbstractStateChangeEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code containsAttributeChange} method throws an exception
     * when passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttributeChange_Name_Null()
    {
        getStateEvent().containsAttributeChange( null );
    }

    /**
     * Ensures the {@code getAttributeChange} method throws an exception when
     * passed a name that is absent.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetAttributeChange_Name_Absent()
    {
        getStateEvent().getAttributeChange( new AttributeName( Scope.APPLICATION, "unknown_attribute" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttributeChange} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttributeChange_Name_Null()
    {
        getStateEvent().getAttributeChange( null );
    }

    /**
     * Ensures the {@code getAttributeChanges} method returns an immutable
     * collection.
     */
    @Test
    public void testGetAttributeChanges_ReturnValue_Immutable()
    {
        assertImmutableCollection( getStateEvent().getAttributeChanges() );
    }
}

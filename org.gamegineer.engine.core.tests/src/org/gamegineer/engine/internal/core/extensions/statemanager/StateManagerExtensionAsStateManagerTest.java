/*
 * StateManagerExtensionAsStateManagerTest.java
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
 * Created on Jul 2, 2008 at 11:32:10 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MementoBuilder;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.statemanager.AbstractStateManagerTestCase;
import org.gamegineer.engine.core.extensions.statemanager.IStateManager;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.statemanager.StateManagerExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.statemanager.IStateManager}
 * interface.
 */
public final class StateManagerExtensionAsStateManagerTest
    extends AbstractStateManagerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StateManagerExtensionAsStateManagerTest} class.
     */
    public StateManagerExtensionAsStateManagerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.statemanager.AbstractStateManagerTestCase#createStateManager(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected IStateManager createStateManager(
        final IEngineContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final StateManagerExtension extension = new StateManagerExtension( new FakeStateManager() );
        extension.start( context );
        return extension;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake implementation of {@code IStateManager} to test the implementation
     * of {@code StateManagerExtension}.
     */
    private static final class FakeStateManager
        implements IStateManager
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeStateManager} class.
         */
        FakeStateManager()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.extensions.statemanager.IStateManager#getMemento(org.gamegineer.engine.core.IEngineContext)
         */
        public IMemento getMemento(
            final IEngineContext context )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

            final MementoBuilder builder = new MementoBuilder();
            return builder.toMemento();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.statemanager.IStateManager#setMemento(org.gamegineer.engine.core.IEngineContext, org.gamegineer.common.persistence.memento.IMemento)
         */
        public void setMemento(
            final IEngineContext context,
            final IMemento memento )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
            assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$
        }
    }
}

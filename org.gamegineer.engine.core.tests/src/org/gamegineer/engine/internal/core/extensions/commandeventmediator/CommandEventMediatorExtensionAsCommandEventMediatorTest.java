/*
 * CommandEventMediatorExtensionAsCommandEventMediatorTest.java
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
 * Created on May 30, 2008 at 9:12:23 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandEventMediatorTestCase;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.CommandEventMediatorExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEventMediator}
 * interface.
 */
public final class CommandEventMediatorExtensionAsCommandEventMediatorTest
    extends AbstractCommandEventMediatorTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandEventMediatorExtensionAsCommandEventMediatorTest} class.
     */
    public CommandEventMediatorExtensionAsCommandEventMediatorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandEventMediatorTestCase#createCommandEventMediator(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected ICommandEventMediator createCommandEventMediator(
        final IEngineContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final CommandEventMediatorExtension extension = new CommandEventMediatorExtension();
        extension.start( context );
        return extension;
    }
}

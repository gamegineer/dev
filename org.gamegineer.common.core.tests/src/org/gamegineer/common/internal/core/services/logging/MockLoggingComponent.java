/*
 * MockLoggingComponent.java
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
 * Created on May 24, 2008 at 9:31:51 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.IComponentFactory;

/**
 * A mock logging component that can be used for testing of a logging component
 * factory.
 */
@Immutable
final class MockLoggingComponent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component factory for this class. */
    static final IComponentFactory FACTORY = new AbstractLoggingComponentFactory<MockLoggingComponent>( MockLoggingComponent.class )
    {
        // no overrides
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockLoggingComponent} class.
     */
    MockLoggingComponent()
    {
        super();
    }
}

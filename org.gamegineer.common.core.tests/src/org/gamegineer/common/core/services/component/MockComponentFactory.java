/*
 * MockComponentFactory.java
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
 * Created on May 17, 2008 at 1:40:57 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * Mock implementation of
 * {@link org.gamegineer.common.core.services.component.IComponentFactory}.
 * 
 * <p>
 * This class is thread compatible.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class MockComponentFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockComponentFactory} class.
     */
    public MockComponentFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * The default implementation throws {@code ComponentCreationException}.
     * 
     * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    public Object createComponent(
        final IComponentCreationContext context )
        throws ComponentCreationException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        throw new ComponentCreationException();
    }
}

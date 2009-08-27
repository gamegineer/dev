/*
 * ExtensionContextAsExtensionContextTest.java
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
 * Created on May 2, 2009 at 8:56:48 PM.
 */

package org.gamegineer.engine.internal.core;

import org.gamegineer.engine.core.contexts.extension.AbstractExtensionContextTestCase;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.ExtensionContext} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.engine.core.contexts.extension.IExtensionContext}
 * interface.
 */
public final class ExtensionContextAsExtensionContextTest
    extends AbstractExtensionContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ExtensionContextAsExtensionContextTest} class.
     */
    public ExtensionContextAsExtensionContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.contexts.extension.AbstractExtensionContextTestCase#createExtensionContext()
     */
    @Override
    protected IExtensionContext createExtensionContext()
    {
        return new ExtensionContext();
    }
}

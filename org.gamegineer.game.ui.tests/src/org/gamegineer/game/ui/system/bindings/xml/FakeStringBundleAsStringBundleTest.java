/*
 * FakeStringBundleAsStringBundleTest.java
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
 * Created on Feb 28, 2009 at 9:28:04 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Map;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.ui.system.bindings.xml.FakeStringBundle} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.game.ui.system.bindings.xml.IStringBundle} interface.
 */
public final class FakeStringBundleAsStringBundleTest
    extends AbstractStringBundleTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code FakeStringBundleAsStringBundleTest} class.
     */
    public FakeStringBundleAsStringBundleTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.ui.system.xml.AbstractStringBundleTestCase#createStringBundle(java.util.Map)
     */
    @Override
    protected IStringBundle createStringBundle(
        final Map<String, String> entries )
    {
        assertArgumentNotNull( entries, "entries" ); //$NON-NLS-1$

        return new FakeStringBundle( entries );
    }
}

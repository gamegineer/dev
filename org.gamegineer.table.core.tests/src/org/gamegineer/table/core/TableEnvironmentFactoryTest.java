/*
 * TableEnvironmentFactoryTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 6, 2009 at 11:07:48 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableEnvironmentFactory} class.
 */
public final class TableEnvironmentFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentFactoryTest}
     * class.
     */
    public TableEnvironmentFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link TableEnvironmentFactory#createTableEnvironment} method
     * throws an exception when passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableEnvironment_Context_Null()
    {
        TableEnvironmentFactory.createTableEnvironment( null );
    }

    /**
     * Ensures the {@link TableEnvironmentFactory#createTableEnvironment} method
     * does not return {@code null}.
     */
    @Test
    public void testCreateTableEnvironment_ReturnValue_NonNull()
    {
        assertNotNull( TableEnvironmentFactory.createTableEnvironment( EasyMock.createMock( ITableEnvironmentContext.class ) ) );
    }
}

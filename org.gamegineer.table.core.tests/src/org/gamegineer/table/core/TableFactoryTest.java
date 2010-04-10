/*
 * TableFactoryTest.java
 * Copyright 2008-2010 Gamegineer.org
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
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.TableFactory}
 * class.
 */
public final class TableFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableFactoryTest} class.
     */
    public TableFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createTable()} method does not return {@code null}.
     */
    @Test
    public void testCreateTable_ReturnValue_NonNull()
    {
        assertNotNull( TableFactory.createTable() );
    }

    /**
     * Ensures the {@code createTable(IMemento)} method throws an exception when
     * passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableFromMemento_Memento_Null()
        throws Exception
    {
        TableFactory.createTable( null );
    }
}

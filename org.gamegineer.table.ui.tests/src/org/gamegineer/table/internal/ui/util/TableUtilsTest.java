/*
 * TableUtilsTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 30, 2012 at 9:24:32 PM.
 */

package org.gamegineer.table.internal.ui.util;

import org.easymock.EasyMock;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerListener;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.util.TableUtils} class.
 */
public final class TableUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableUtilsTest} class.
     */
    public TableUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code addContainerListenerAndGetComponents} method throws an
     * exception when passed a {@code null} container.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerListenerAndGetComponents_Container_Null()
    {
        TableUtils.addContainerListenerAndGetComponents( null, EasyMock.createMock( IContainerListener.class ) );
    }

    /**
     * Ensures the {@code addContainerListenerAndGetComponents} method throws an
     * exception when passed a {@code null} container listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddContainerListenerAndGetComponents_ContainerListener_Null()
    {
        TableUtils.addContainerListenerAndGetComponents( EasyMock.createMock( IContainer.class ), null );
    }
}

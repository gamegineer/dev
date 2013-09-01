/*
 * JMenuItemGroupTest.java
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
 * Created on Aug 12, 2010 at 10:52:57 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import javax.swing.Action;
import javax.swing.JMenu;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A fixture for testing the {@link JMenuItemGroup} class.
 */
public final class JMenuItemGroupTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JMenuItemGroupTest} class.
     */
    public JMenuItemGroupTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link JMenuItemGroup#JMenuItemGroup} constructor throws an
     * exception when passed a {@code null} action.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Action_Null()
    {
        new JMenuItemGroup( new JMenu(), null, EasyMock.createMock( JMenuItemGroup.IContentProvider.class ) );
    }

    /**
     * Ensures the {@link JMenuItemGroup#JMenuItemGroup} constructor throws an
     * exception when passed a {@code null} content provider.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_ContentProvider_Null()
    {
        new JMenuItemGroup( new JMenu(), EasyMock.createMock( Action.class ), null );
    }

    /**
     * Ensures the {@link JMenuItemGroup#JMenuItemGroup} constructor throws an
     * exception when passed a {@code null} menu.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Menu_Null()
    {
        new JMenuItemGroup( null, EasyMock.createMock( Action.class ), EasyMock.createMock( JMenuItemGroup.IContentProvider.class ) );
    }
}

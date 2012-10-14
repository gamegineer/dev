/*
 * ComponentFactoryMenuBuilderTest.java
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
 * Created on Oct 5, 2012 at 9:19:14 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.ComponentFactoryMenuBuilder}
 * class.
 */
public final class ComponentFactoryMenuBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component factory menu builder under test in the fixture. */
    private ComponentFactoryMenuBuilder componentFactoryMenuBuilder_;

    /** The expected root menu. */
    private JMenu expectedRootMenu_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentFactoryMenuBuilderTest}
     * class.
     */
    public ComponentFactoryMenuBuilderTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the specified menus are equal.
     * 
     * @param menu1
     *        The first object to compare; may be {@code null}.
     * @param menu2
     *        The second object to compare; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two objects are not equal.
     */
    @SuppressWarnings( "boxing" )
    private static void assertMenuEquals(
        /* @Nullable */
        final JMenu menu1,
        /* @Nullable */
        final JMenu menu2 )
    {
        if( menu1 == null )
        {
            assertNull( menu2 );
        }
        else if( menu2 == null )
        {
            assertNull( menu1 );
        }
        else
        {
            assertEquals( menu1.getText(), menu2.getText() );
            assertEquals( menu1.getMnemonic(), menu2.getMnemonic() );

            assertEquals( menu1.getMenuComponentCount(), menu2.getMenuComponentCount() );
            for( int index = 0, size = menu1.getMenuComponentCount(); index < size; ++index )
            {
                final Component component1 = menu1.getMenuComponent( index );
                final Component component2 = menu2.getMenuComponent( index );
                if( (component1 instanceof JMenu) && (component2 instanceof JMenu) )
                {
                    assertMenuEquals( (JMenu)component1, (JMenu)component2 );
                }
                else if( (component1 instanceof JMenuItem) && (component2 instanceof JMenuItem) )
                {
                    assertMenuItemEquals( (JMenuItem)component1, (JMenuItem)component2 );
                }
                else
                {
                    fail( String.format( "unequal menu compoenents at index %d", index ) ); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * Ensures the specified menu items are equal.
     * 
     * @param menuItem1
     *        The first object to compare; may be {@code null}.
     * @param menuItem2
     *        The second object to compare; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two objects are not equal.
     */
    private static void assertMenuItemEquals(
        /* @Nullable */
        final JMenuItem menuItem1,
        /* @Nullable */
        final JMenuItem menuItem2 )
    {
        if( menuItem1 == null )
        {
            assertNull( menuItem2 );
        }
        else if( menuItem2 == null )
        {
            assertNull( menuItem1 );
        }
        else
        {
            assertEquals( menuItem1.getText(), menuItem2.getText() );
            assertEquals( menuItem1.getMnemonic(), menuItem2.getMnemonic() );
        }
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        final String expectedRootName = "rootName"; //$NON-NLS-1$
        final int expectedRootMnemonic = KeyEvent.VK_0;
        expectedRootMenu_ = new JMenu( expectedRootName );
        expectedRootMenu_.setMnemonic( expectedRootMnemonic );
        componentFactoryMenuBuilder_ = new ComponentFactoryMenuBuilder( expectedRootName, expectedRootMnemonic );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addCategory} method
     * correctly adds categories to the menu when an ancestor category is added
     * followed by adding a descendant category.
     */
    @Test
    public void testAddCategory_AncestorFollowedByDescendant()
    {
        final String id1 = "id1"; //$NON-NLS-1$
        final int mnemonic1 = KeyEvent.VK_1;
        final String name1 = "name1"; //$NON-NLS-1$
        final JMenu menu1 = new JMenu( name1 );
        menu1.setMnemonic( mnemonic1 );
        expectedRootMenu_.add( menu1 );
        final String id2 = "id2"; //$NON-NLS-1$
        final int mnemonic2 = KeyEvent.VK_2;
        final String name2 = "name2"; //$NON-NLS-1$
        final JMenu menu2 = new JMenu( name2 );
        menu2.setMnemonic( mnemonic2 );
        menu1.add( menu2 );

        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( id1, name1, mnemonic1, Arrays.<String>asList() ) );
        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( id2, name2, mnemonic2, Arrays.asList( id1 ) ) );

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addCategory} method
     * correctly adds categories to the menu when a descendant category is added
     * followed by adding an ancestor category.
     */
    @Test
    public void testAddCategory_DescendantFollowedByAncestor()
    {
        final String id1 = "id1"; //$NON-NLS-1$
        final int mnemonic1 = KeyEvent.VK_1;
        final String name1 = "name1"; //$NON-NLS-1$
        final JMenu menu1 = new JMenu( name1 );
        menu1.setMnemonic( mnemonic1 );
        expectedRootMenu_.add( menu1 );
        final String id2 = "id2"; //$NON-NLS-1$
        final int mnemonic2 = KeyEvent.VK_2;
        final String name2 = "name2"; //$NON-NLS-1$
        final JMenu menu2 = new JMenu( name2 );
        menu2.setMnemonic( mnemonic2 );
        menu1.add( menu2 );

        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( id2, name2, mnemonic2, Arrays.asList( id1 ) ) );
        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( id1, name1, mnemonic1, Arrays.<String>asList() ) );

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addCategory} method adds a
     * category to the menu root.
     */
    @Test
    public void testAddCategory_Root()
    {
        final String id = "id"; //$NON-NLS-1$
        final int mnemonic = KeyEvent.VK_1;
        final String name = "name"; //$NON-NLS-1$
        final JMenu menu = new JMenu( name );
        menu.setMnemonic( mnemonic );
        expectedRootMenu_.add( menu );

        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( id, name, mnemonic, Collections.<String>emptyList() ) );

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addComponentFactory}
     * method adds a component factory when the component factory is added after
     * its associated category.
     */
    @Test
    public void testAddComponentFactory_AfterCategory()
    {
        final String categoryId = "categoryId"; //$NON-NLS-1$
        final int categoryMnemonic = KeyEvent.VK_1;
        final String categoryName = "categoryName"; //$NON-NLS-1$
        final JMenu categoryMenu = new JMenu( categoryName );
        categoryMenu.setMnemonic( categoryMnemonic );
        expectedRootMenu_.add( categoryMenu );
        final String componentFactoryId = "componentFactoryId"; //$NON-NLS-1$
        final int componentFactoryMnemonic = KeyEvent.VK_2;
        final String componentFactoryName = "componentFactoryName"; //$NON-NLS-1$
        final JMenuItem componentFactoryMenuItem = new JMenu( componentFactoryName );
        componentFactoryMenuItem.setMnemonic( componentFactoryMnemonic );
        categoryMenu.add( componentFactoryMenuItem );

        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( categoryId, categoryName, categoryMnemonic, Collections.<String>emptyList() ) );
        componentFactoryMenuBuilder_.addComponentFactory( new ComponentFactory( componentFactoryId, componentFactoryName, componentFactoryMnemonic, categoryId ) );

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addComponentFactory}
     * method adds a component factory when the component factory is added
     * before its associated category.
     */
    @Test
    public void testAddComponentFactory_BeforeCategory()
    {
        final String categoryId = "categoryId"; //$NON-NLS-1$
        final int categoryMnemonic = KeyEvent.VK_1;
        final String categoryName = "categoryName"; //$NON-NLS-1$
        final JMenu categoryMenu = new JMenu( categoryName );
        categoryMenu.setMnemonic( categoryMnemonic );
        expectedRootMenu_.add( categoryMenu );
        final String componentFactoryId = "componentFactoryId"; //$NON-NLS-1$
        final int componentFactoryMnemonic = KeyEvent.VK_2;
        final String componentFactoryName = "componentFactoryName"; //$NON-NLS-1$
        final JMenuItem componentFactoryMenuItem = new JMenu( componentFactoryName );
        componentFactoryMenuItem.setMnemonic( componentFactoryMnemonic );
        categoryMenu.add( componentFactoryMenuItem );

        componentFactoryMenuBuilder_.addComponentFactory( new ComponentFactory( componentFactoryId, componentFactoryName, componentFactoryMnemonic, categoryId ) );
        componentFactoryMenuBuilder_.addCategory( new ComponentFactoryCategory( categoryId, categoryName, categoryMnemonic, Collections.<String>emptyList() ) );

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addComponentFactory}
     * method does not add a component factory when its associated category is
     * missing.
     */
    @Test
    public void testAddComponentFactory_MissingCategory()
    {
        final String id = "id"; //$NON-NLS-1$
        final int mnemonic = KeyEvent.VK_1;
        final String name = "name"; //$NON-NLS-1$

        componentFactoryMenuBuilder_.addComponentFactory( new ComponentFactory( id, name, mnemonic, "unknown" ) ); //$NON-NLS-1$

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#addComponentFactory}
     * method adds a component factory to the menu root.
     */
    @Test
    public void testAddComponentFactory_Root()
    {
        final String id = "id"; //$NON-NLS-1$
        final int mnemonic = KeyEvent.VK_1;
        final String name = "name"; //$NON-NLS-1$
        final JMenuItem menuItem = new JMenu( name );
        menuItem.setMnemonic( mnemonic );
        expectedRootMenu_.add( menuItem );

        componentFactoryMenuBuilder_.addComponentFactory( new ComponentFactory( id, name, mnemonic, null ) );

        assertMenuEquals( expectedRootMenu_, componentFactoryMenuBuilder_.toMenu() );
    }

    /**
     * Ensures the {@link ComponentFactoryMenuBuilder#toMenu} method returns the
     * correct value when the menu builder is empty.
     */
    @Test
    public void testToMenu_Empty()
    {
        final JMenu actualValue = componentFactoryMenuBuilder_.toMenu();

        assertEquals( 0, actualValue.getMenuComponentCount() );
    }
}

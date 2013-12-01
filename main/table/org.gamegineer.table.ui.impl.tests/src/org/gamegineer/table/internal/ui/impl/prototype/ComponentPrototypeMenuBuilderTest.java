/*
 * ComponentPrototypeMenuBuilderTest.java
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
 * Created on Oct 5, 2012 at 9:19:14 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.ui.impl.action.BasicAction;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentPrototypeMenuBuilder} class.
 */
public final class ComponentPrototypeMenuBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The actual root menu. */
    private JMenu actualRootMenu_;

    /** The component prototype menu builder under test in the fixture. */
    private ComponentPrototypeMenuBuilder componentPrototypeMenuBuilder_;

    /** The expected root menu. */
    private JMenu expectedRootMenu_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentPrototypeMenuBuilderTest} class.
     */
    public ComponentPrototypeMenuBuilderTest()
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
        actualRootMenu_ = new JMenu( expectedRootName );
        actualRootMenu_.setMnemonic( expectedRootMnemonic );
        componentPrototypeMenuBuilder_ = new ComponentPrototypeMenuBuilder( new BasicAction( "id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link ComponentPrototypeMenuBuilder#addComponentPrototype}
     * method adds a component prototype when the component prototype is added
     * after its associated category.
     */
    @Test
    public void testAddComponentPrototype_AfterCategory()
    {
        final String categoryId = "categoryId"; //$NON-NLS-1$
        final int categoryMnemonic = KeyEvent.VK_1;
        final String categoryName = "categoryName"; //$NON-NLS-1$
        final JMenu categoryMenu = new JMenu( categoryName );
        categoryMenu.setMnemonic( categoryMnemonic );
        expectedRootMenu_.add( categoryMenu );
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int componentPrototypeMnemonic = KeyEvent.VK_2;
        final String componentPrototypeName = "componentPrototypeName"; //$NON-NLS-1$
        final JMenuItem componentPrototypeMenuItem = new JMenu( componentPrototypeName );
        componentPrototypeMenuItem.setMnemonic( componentPrototypeMnemonic );
        categoryMenu.add( componentPrototypeMenuItem );

        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( categoryId, categoryName, categoryMnemonic, Collections.<String>emptyList() ) );
        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( componentPrototypeName, componentPrototypeMnemonic, categoryId, componentPrototypeFactory ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the {@link ComponentPrototypeMenuBuilder#addComponentPrototype}
     * method adds a component prototype when the component prototype is added
     * before its associated category.
     */
    @Test
    public void testAddComponentPrototype_BeforeCategory()
    {
        final String categoryId = "categoryId"; //$NON-NLS-1$
        final int categoryMnemonic = KeyEvent.VK_1;
        final String categoryName = "categoryName"; //$NON-NLS-1$
        final JMenu categoryMenu = new JMenu( categoryName );
        categoryMenu.setMnemonic( categoryMnemonic );
        expectedRootMenu_.add( categoryMenu );
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int componentPrototypeMnemonic = KeyEvent.VK_2;
        final String componentPrototypeName = "componentPrototypeName"; //$NON-NLS-1$
        final JMenuItem componentPrototypeMenuItem = new JMenu( componentPrototypeName );
        componentPrototypeMenuItem.setMnemonic( componentPrototypeMnemonic );
        categoryMenu.add( componentPrototypeMenuItem );

        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( componentPrototypeName, componentPrototypeMnemonic, categoryId, componentPrototypeFactory ) );
        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( categoryId, categoryName, categoryMnemonic, Collections.<String>emptyList() ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the {@link ComponentPrototypeMenuBuilder#addComponentPrototype}
     * method does not add a component prototype when its associated category is
     * missing.
     */
    @Test
    public void testAddComponentPrototype_MissingCategory()
    {
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int mnemonic = KeyEvent.VK_1;
        final String name = "name"; //$NON-NLS-1$

        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( name, mnemonic, "unknown", componentPrototypeFactory ) ); //$NON-NLS-1$
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the {@link ComponentPrototypeMenuBuilder#addComponentPrototype}
     * method adds a component prototype to the menu root.
     */
    @Test
    public void testAddComponentPrototype_Root()
    {
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int mnemonic = KeyEvent.VK_1;
        final String name = "name"; //$NON-NLS-1$
        final JMenuItem menuItem = new JMenu( name );
        menuItem.setMnemonic( mnemonic );
        expectedRootMenu_.add( menuItem );

        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( name, mnemonic, null, componentPrototypeFactory ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypeMenuBuilder#addComponentPrototypeCategory}
     * method correctly adds component prototype categories to the menu when an
     * ancestor category is added followed by adding a descendant category.
     */
    @Test
    public void testAddComponentPrototypeCategory_AncestorFollowedByDescendant()
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
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int componentPrototypeMnemonic = KeyEvent.VK_3;
        final String componentPrototypeName = "componentPrototypeName"; //$NON-NLS-1$
        final JMenuItem menuItem = new JMenu( componentPrototypeName );
        menuItem.setMnemonic( componentPrototypeMnemonic );
        menu2.add( menuItem );

        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id1, name1, mnemonic1, Arrays.<String>asList() ) );
        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id2, name2, mnemonic2, Arrays.asList( id1 ) ) );
        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( componentPrototypeName, componentPrototypeMnemonic, id2, componentPrototypeFactory ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypeMenuBuilder#addComponentPrototypeCategory}
     * method correctly adds component prototype categories to the menu when a
     * descendant category is added followed by adding an ancestor category.
     */
    @Test
    public void testAddComponentPrototypeCategory_DescendantFollowedByAncestor()
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
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int componentPrototypeMnemonic = KeyEvent.VK_3;
        final String componentPrototypeName = "componentPrototypeName"; //$NON-NLS-1$
        final JMenuItem menuItem = new JMenu( componentPrototypeName );
        menuItem.setMnemonic( componentPrototypeMnemonic );
        menu2.add( menuItem );

        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id2, name2, mnemonic2, Arrays.asList( id1 ) ) );
        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id1, name1, mnemonic1, Arrays.<String>asList() ) );
        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( componentPrototypeName, componentPrototypeMnemonic, id2, componentPrototypeFactory ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the
     * {@link ComponentPrototypeMenuBuilder#addComponentPrototypeCategory}
     * method adds a component prototype category to the menu root.
     */
    @Test
    public void testAddComponentPrototypeCategory_Root()
    {
        final String id = "id"; //$NON-NLS-1$
        final int mnemonic = KeyEvent.VK_1;
        final String name = "name"; //$NON-NLS-1$
        final JMenu menu = new JMenu( name );
        menu.setMnemonic( mnemonic );
        expectedRootMenu_.add( menu );
        final IComponentPrototypeFactory componentPrototypeFactory = EasyMock.createMock( IComponentPrototypeFactory.class );
        final int componentPrototypeMnemonic = KeyEvent.VK_2;
        final String componentPrototypeName = "componentPrototypeName"; //$NON-NLS-1$
        final JMenuItem menuItem = new JMenu( componentPrototypeName );
        menuItem.setMnemonic( componentPrototypeMnemonic );
        menu.add( menuItem );

        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id, name, mnemonic, Collections.<String>emptyList() ) );
        componentPrototypeMenuBuilder_.addComponentPrototype( new ComponentPrototype( componentPrototypeName, componentPrototypeMnemonic, id, componentPrototypeFactory ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the {@link ComponentPrototypeMenuBuilder#buildMenu} method
     * excludes component prototype categories from the menu when they have no
     * component prototype descendants.
     */
    @Test
    public void testBuildMenu_ComponentPrototypeCategoryWithNoComponentPrototypeDescendants()
    {
        final String id1 = "id1"; //$NON-NLS-1$
        final int mnemonic1 = KeyEvent.VK_1;
        final String name1 = "name1"; //$NON-NLS-1$
        final String id2 = "id2"; //$NON-NLS-1$
        final int mnemonic2 = KeyEvent.VK_2;
        final String name2 = "name2"; //$NON-NLS-1$

        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id1, name1, mnemonic1, Arrays.<String>asList() ) );
        componentPrototypeMenuBuilder_.addComponentPrototypeCategory( new ComponentPrototypeCategory( id2, name2, mnemonic2, Arrays.asList( id1 ) ) );
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }

    /**
     * Ensures the {@link ComponentPrototypeMenuBuilder#buildMenu} method builds
     * the menu correctly when the menu builder is empty.
     */
    @Test
    public void testBuildMenu_Empty()
    {
        componentPrototypeMenuBuilder_.buildMenu( actualRootMenu_ );

        assertMenuEquals( expectedRootMenu_, actualRootMenu_ );
    }
}

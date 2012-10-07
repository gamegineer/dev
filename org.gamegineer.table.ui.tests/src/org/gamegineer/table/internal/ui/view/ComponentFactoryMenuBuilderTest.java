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
                else
                {
                    fail( String.format( "unequal menu compoenents at index %d", index ) ); //$NON-NLS-1$
                }
            }
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

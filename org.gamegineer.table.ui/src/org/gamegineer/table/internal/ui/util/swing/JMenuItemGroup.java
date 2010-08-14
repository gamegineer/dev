/*
 * JMenuItemGroup.java
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
 * Created on Aug 7, 2010 at 10:40:31 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import net.jcip.annotations.NotThreadSafe;

/**
 * A menu item group whose menu items are generated each time before its parent
 * menu is selected.
 * 
 * <p>
 * A menu item group is useful for creating user interface paradigms such as
 * most-recently-used (MRU) file lists and open window lists.
 * </p>
 * 
 * <p>
 * The menu item group appears in the menu as a separator followed by the menu
 * items generated from the content provider. If the menu item group is empty,
 * it will be totally hidden in the menu.
 * </p>
 */
@NotThreadSafe
public final class JMenuItemGroup
    extends JSeparator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name prefix for each menu item in the group. */
    private static final String MENU_ITEM_NAME_PREFIX = "JMenuItemGroup.menuItem"; //$NON-NLS-1$

    /** Serializable class version number. */
    private static final long serialVersionUID = -4944520712413642322L;

    /** The action associated with all menu items in the group. */
    private final Action action_;

    /** The content provider used to generate the menu items in the group. */
    private final IContentProvider contentProvider_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JMenuItemGroup} class.
     * 
     * @param menu
     *        The parent menu; must not be {@code null}.
     * @param action
     *        The action associated with all menu items in the group; must not
     *        be {@code null}.
     * @param contentProvider
     *        The content provider used to generate the menu items in the group;
     *        must not be {@code null}.
     */
    public JMenuItemGroup(
        /* @NonNull */
        final JMenu menu,
        /* @NonNull */
        final Action action,
        /* @NonNull */
        final IContentProvider contentProvider )
    {
        assertArgumentNotNull( menu, "menu" ); //$NON-NLS-1$
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$
        assertArgumentNotNull( contentProvider, "contentProvider" ); //$NON-NLS-1$

        action_ = action;
        contentProvider_ = contentProvider;

        menu.addMenuListener( new MenuListener()
        {
            @Override
            public void menuCanceled(
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                // do nothing
            }

            @Override
            public void menuDeselected(
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                // do nothing
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void menuSelected(
                final MenuEvent event )
            {
                updateMenuItems( (JMenu)event.getSource() );
            }
        } );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Updates the menu items in the group.
     * 
     * @param menu
     *        The menu that hosts the menu item group; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void updateMenuItems(
        /* @NonNull */
        final JMenu menu )
    {
        assert menu != null;

        int beginIndex = -1, endIndex = -1;
        for( int index = 0; index < menu.getMenuComponentCount(); ++index )
        {
            final Component component = menu.getMenuComponent( index );
            if( beginIndex == -1 )
            {
                if( this == component )
                {
                    beginIndex = index;
                }
            }
            else
            {
                final String name = component.getName();
                if( (name != null) && name.startsWith( MENU_ITEM_NAME_PREFIX ) )
                {
                    endIndex = index;
                }
                else
                {
                    break;
                }
            }
        }

        assert beginIndex != -1;
        if( endIndex != -1 )
        {
            for( int index = beginIndex + 1; index <= endIndex; ++index )
            {
                menu.remove( beginIndex + 1 );
            }
        }

        final Collection<String> menuItemLabels = contentProvider_.getMenuItemLabels();
        int index = 1;
        for( final String menuItemLabel : menuItemLabels )
        {
            final JMenuItem menuItem = new JMenuItem( action_ );
            menuItem.setName( MENU_ITEM_NAME_PREFIX + index );
            menuItem.setActionCommand( menuItemLabel );
            menuItem.setText( String.format( "%1$d %2$s", index, menuItemLabel ) ); //$NON-NLS-1$
            menuItem.setAccelerator( null );
            menuItem.setMnemonic( KeyEvent.VK_0 + index );
            menu.add( menuItem, beginIndex + index );
            ++index;
        }

        setVisible( !menuItemLabels.isEmpty() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A content provider for a menu item group.
     */
    public interface IContentProvider
    {
        /**
         * Gets a collection of menu item labels for the group.
         * 
         * <p>
         * The group menu items will appear in the same order as the labels in
         * the returned collection.
         * </p>
         * 
         * @return A collection of menu item labels for the group; never {@code
         *         null}.
         */
        /* @NonNull */
        public Collection<String> getMenuItemLabels();
    }
}

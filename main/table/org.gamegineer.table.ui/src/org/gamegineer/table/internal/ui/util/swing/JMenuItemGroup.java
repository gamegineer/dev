/*
 * JMenuItemGroup.java
 * Copyright 2008-2010 Gamegineer contributors and others.
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
import net.jcip.annotations.Immutable;
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

        final Collection<MenuItemDescriptor> menuItemDescriptors = contentProvider_.getMenuItemDescriptors();
        int index = 1;
        for( final MenuItemDescriptor menuItemDescriptor : menuItemDescriptors )
        {
            final JMenuItem menuItem = new JMenuItem( action_ );
            menuItem.setName( MENU_ITEM_NAME_PREFIX + index );
            menuItem.setText( String.format( "%1$d %2$s", index, menuItemDescriptor.getText() ) ); //$NON-NLS-1$
            menuItem.setActionCommand( menuItemDescriptor.getActionCommand() );
            menuItem.setAccelerator( null );
            menuItem.setMnemonic( KeyEvent.VK_0 + index );
            menu.add( menuItem, beginIndex + index );
            ++index;
        }

        setVisible( !menuItemDescriptors.isEmpty() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A content provider for a menu item group.
     */
    public interface IContentProvider
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets a collection of menu item descriptors for the group.
         * 
         * <p>
         * The group menu items will appear in the same order as the descriptors
         * in the returned collection.
         * </p>
         * 
         * @return A collection of menu item descriptors for the group; never
         *         {@code null}.
         */
        /* @NonNull */
        public Collection<MenuItemDescriptor> getMenuItemDescriptors();
    }

    /**
     * A descriptor for a menu item in a menu item group.
     */
    @Immutable
    public static final class MenuItemDescriptor
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The menu item action command. */
        private final String actionCommand_;

        /** The menu item text. */
        private final String text_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MenuItemDescriptor} class
         * with the specified text and action command.
         * 
         * @param text
         *        The menu item text; may be {@code null}.
         * @param actionCommand
         *        The menu item action command; may be {@code null}.
         */
        public MenuItemDescriptor(
            /* @Nullable */
            final String text,
            /* @Nullable */
            final String actionCommand )
        {
            text_ = text;
            actionCommand_ = actionCommand;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the menu item action command.
         * 
         * @return The menu item action command; may be {@code null}.
         */
        /* @Nullable */
        public String getActionCommand()
        {
            return actionCommand_;
        }

        /**
         * Gets the menu item text.
         * 
         * @return The menu item text; may be {@code null}.
         */
        /* @Nullable */
        public String getText()
        {
            return text_;
        }
    }
}

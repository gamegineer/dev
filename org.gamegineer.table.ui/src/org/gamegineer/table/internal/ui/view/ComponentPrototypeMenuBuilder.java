/*
 * ComponentPrototypeMenuBuilder.java
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
 * Created on Oct 4, 2012 at 9:53:30 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * A component prototype menu builder.
 */
@NotThreadSafe
final class ComponentPrototypeMenuBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action used for all menu items. */
    private final Action menuItemAction_;

    /**
     * The collection of menu items for each category. The key is the category
     * identifier. The value is the collection of menu items.
     */
    private final Map<String, Collection<JMenuItem>> menuItemCollections_;

    /** The root menu descriptor. */
    private final MenuDescriptor rootMenuDescriptor_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPrototypeMenuBuilder}
     * class.
     * 
     * @param rootMenuLabel
     *        The root menu label; must not be {@code null}.
     * @param rootMenuMnemonic
     *        The root menu mnemonic.
     * @param menuItemAction
     *        The action used for all menu items; must not be {@code null}.
     */
    ComponentPrototypeMenuBuilder(
        /* @NonNull */
        final String rootMenuLabel,
        final int rootMenuMnemonic,
        /* @NonNull */
        final Action menuItemAction )
    {
        assert rootMenuLabel != null;
        assert menuItemAction != null;

        menuItemAction_ = menuItemAction;
        menuItemCollections_ = new HashMap<String, Collection<JMenuItem>>();
        rootMenuDescriptor_ = new MenuDescriptor( null );
        rootMenuDescriptor_.setMenu( rootMenuLabel, rootMenuMnemonic );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified component prototype to the menu.
     * 
     * @param componentPrototype
     *        The component prototype to add to the menu; must not be
     *        {@code null}.
     */
    void addComponentPrototype(
        /* @NonNull */
        final ComponentPrototype componentPrototype )
    {
        assert componentPrototype != null;

        Collection<JMenuItem> menuItems = menuItemCollections_.get( componentPrototype.getCategoryId() );
        if( menuItems == null )
        {
            menuItems = new ArrayList<JMenuItem>();
            menuItemCollections_.put( componentPrototype.getCategoryId(), menuItems );
        }

        final JMenuItem menuItem = new JMenuItem( menuItemAction_ );
        menuItem.setText( componentPrototype.getName() );
        menuItem.setMnemonic( componentPrototype.getMnemonic() );
        // TODO: extract constant for the property name
        menuItem.putClientProperty( "org.gamegineer.table.ui.prototype.componentFactory", componentPrototype.getComponentFactory() ); //$NON-NLS-1$
        menuItems.add( menuItem );
    }

    /**
     * Adds the specified component prototype category to the menu.
     * 
     * @param componentPrototypeCategory
     *        The component prototype category to add to the menu; must not be
     *        {@code null}.
     */
    void addComponentPrototypeCategory(
        /* @NonNull */
        final ComponentPrototypeCategory componentPrototypeCategory )
    {
        assert componentPrototypeCategory != null;

        final MenuDescriptor menuDescriptor = rootMenuDescriptor_.getDescendantMenuDescriptor( componentPrototypeCategory.getPath() );
        menuDescriptor.setMenu( componentPrototypeCategory.getName(), componentPrototypeCategory.getMnemonic() );
    }

    /**
     * Collects all component prototype category identifiers associated with the
     * specified menu descriptor and its descendants.
     * 
     * @param menuDescriptor
     *        The menu descriptor; must not be {@code null}.
     * @param ids
     *        The collection that will receive the component prototype category
     *        identifiers; must not be {@code null}.
     */
    /* @NonNull */
    private static void collectAllComponentPrototypeCategoryIds(
        /* @NonNull */
        final MenuDescriptor menuDescriptor,
        /* @NonNull */
        final Collection<String> ids )
    {
        assert menuDescriptor != null;
        assert ids != null;

        ids.add( menuDescriptor.getId() );

        for( final MenuDescriptor childMenuDescriptor : menuDescriptor.getChildMenuDescriptors() )
        {
            collectAllComponentPrototypeCategoryIds( childMenuDescriptor, ids );
        }
    }

    /**
     * Creates a new menu based on the state of this builder.
     * 
     * @return A new menu; never {@code null}.
     */
    /* @NonNull */
    JMenu toMenu()
    {
        final JMenu rootMenu = toMenu( rootMenuDescriptor_ );
        assert rootMenu != null;
        return rootMenu;
    }

    /**
     * Creates a new menu from the specified menu descriptor.
     * 
     * @param menuDescriptor
     *        The menu descriptor; must not be {@code null}.
     * 
     * @return The new menu or {@code null} if a menu is not associated with the
     *         specified menu descriptor.
     */
    /* @Nullable */
    private JMenu toMenu(
        /* @NonNull */
        final MenuDescriptor menuDescriptor )
    {
        assert menuDescriptor != null;

        final JMenu menu = menuDescriptor.getMenu();
        if( menu == null )
        {
            final Collection<String> ids = new ArrayList<String>();
            collectAllComponentPrototypeCategoryIds( menuDescriptor, ids );
            Loggers.getDefaultLogger().warning( NonNlsMessages.ComponentPrototypeMenuBuilder_toMenu_orphanedCategories( ids ) );
            return null;
        }

        final Collection<JMenuItem> menuItems = menuItemCollections_.get( menuDescriptor.getId() );
        if( menuItems != null )
        {
            for( final JMenuItem menuItem : menuItems )
            {
                menu.add( menuItem );
            }
        }

        for( final MenuDescriptor childMenuDescriptor : menuDescriptor.getChildMenuDescriptors() )
        {
            final JMenu childMenu = toMenu( childMenuDescriptor );
            if( childMenu != null )
            {
                menu.add( childMenu );
            }
        }

        return menu;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A menu descriptor.
     */
    @NotThreadSafe
    private static final class MenuDescriptor
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The collection of child menu descriptors. The key is the component
         * prototype category identifier with the child menu. The value is the
         * menu descriptor.
         */
        private final Map<String, MenuDescriptor> childMenuDescriptors_;

        /**
         * The component prototype category identifier associated with the menu
         * or {@code null} if the root menu.
         */
        private final String id_;

        /** The menu or {@code null} if the menu has not yet been set. */
        private JMenu menu_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MenuDescriptor} class.
         * 
         * @param id
         *        The component prototype category identifier associated with
         *        the menu or {@code null} if the root menu.
         */
        MenuDescriptor(
            /* @Nullable */
            final String id )
        {
            childMenuDescriptors_ = new LinkedHashMap<String, MenuDescriptor>();
            id_ = id;
            menu_ = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the child menu descriptor associated with the specified
         * component prototype category identifier.
         * 
         * <p>
         * The child menu descriptor will be created if it does not exist.
         * </p>
         * 
         * @param id
         *        The component prototype category identifier associated with
         *        the child menu; must not be {@code null}.
         * 
         * @return The child menu descriptor associated with the specified
         *         component prototype category identifier; never {@code null}.
         */
        /* @NonNull */
        private MenuDescriptor getChildMenuDescriptor(
            /* @NonNull */
            final String id )
        {
            assert id != null;

            MenuDescriptor childMenuDescriptor = childMenuDescriptors_.get( id );
            if( childMenuDescriptor == null )
            {
                childMenuDescriptor = new MenuDescriptor( id );
                childMenuDescriptors_.put( id, childMenuDescriptor );
            }

            return childMenuDescriptor;
        }

        /**
         * Gets the collection of child menu descriptors.
         * 
         * @return The collection of child menu descriptors; never {@code null}.
         *         The returned collection is not a copy and must not be
         *         modified by the caller.
         */
        /* @NonNull */
        Collection<MenuDescriptor> getChildMenuDescriptors()
        {
            return childMenuDescriptors_.values();
        }

        /**
         * Gets the descendant menu descriptor associated with the specified
         * component prototype category identifier path.
         * 
         * <p>
         * The descendant menu descriptor will be created if it does not exist.
         * </p>
         * 
         * @param idPath
         *        The component prototype category identifier path; must not be
         *        {@code null} and must not be empty.
         * 
         * @return The descendant menu descriptor associated with the specified
         *         component prototype category identifier path; never
         *         {@code null}.
         */
        /* @NonNull */
        MenuDescriptor getDescendantMenuDescriptor(
            /* @NonNull */
            final List<String> idPath )
        {
            assert idPath != null;
            assert !idPath.isEmpty();

            final MenuDescriptor menuDescriptor = getChildMenuDescriptor( idPath.get( 0 ) );
            if( idPath.size() == 1 )
            {
                return menuDescriptor;
            }

            return menuDescriptor.getDescendantMenuDescriptor( idPath.subList( 1, idPath.size() ) );
        }

        /**
         * Gets the component prototype category identifier associated with the
         * menu.
         * 
         * @return The component prototype category identifier associated with
         *         the menu or {@code null} if the root menu.
         */
        /* @Nullable */
        String getId()
        {
            return id_;
        }

        /**
         * Gets the menu.
         * 
         * @return The menu or {@code null} if the menu has not yet been set.
         */
        /* @Nullable */
        JMenu getMenu()
        {
            return menu_;
        }

        /**
         * Sets the menu.
         * 
         * <p>
         * This method must not be called more than once.
         * </p>
         * 
         * @param label
         *        The menu label; must not be {@code null}.
         * @param mnemonic
         *        The menu mnemonic.
         */
        void setMenu(
            /* @NonNull */
            final String label,
            final int mnemonic )
        {
            assert label != null;
            assert menu_ == null;

            menu_ = new JMenu( label );
            menu_.setMnemonic( mnemonic );
        }
    }
}

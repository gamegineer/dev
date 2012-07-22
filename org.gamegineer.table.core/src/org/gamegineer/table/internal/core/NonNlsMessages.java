/*
 * NonNlsMessages.java
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
 * Created on Oct 17, 2009 at 12:20:45 AM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A utility class to manage non-localized messages for the package.
 */
@ThreadSafe
final class NonNlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- AccordianContainerLayout -----------------------------------------

    /**
     * The offset in the y-direction is zero when the offset in the x-direction
     * is zero.
     */
    public static String AccordianContainerLayout_ctor_offsetY_zero;

    // --- Card -------------------------------------------------------------

    /** The orientation is illegal. */
    public static String Card_orientation_illegal;

    // --- CardPile ---------------------------------------------------------

    /**
     * The component collection contains a component created by a different
     * table environment.
     */
    public static String CardPile_addComponents_components_containsComponentCreatedByDifferentTableEnvironment;

    /** The component collection contains a {@code null} element. */
    public static String CardPile_addComponents_components_containsNullElement;

    /**
     * The component collection contains a component already contained in a
     * container.
     */
    public static String CardPile_addComponents_components_containsOwnedComponent;

    /** The component index is out of range. */
    public static String CardPile_getComponentFromIndex_index_outOfRange;

    /** The orientation is illegal. */
    public static String CardPile_orientation_illegal;

    /** The component is not contained in the container. */
    public static String CardPile_removeComponent_component_notOwned;

    // --- Component --------------------------------------------------------

    /** The component listener is already registered. */
    public static String Component_addComponentListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentBoundsChanged().
     */
    public static String Component_componentBoundsChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentOrientationChanged().
     */
    public static String Component_componentOrientationChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentSurfaceDesignChanged().
     */
    public static String Component_componentSurfaceDesignChanged_unexpectedException;

    /** The memento is for an unknown component type. */
    public static String Component_fromMemento_unknown;

    /** The component listener is not registered. */
    public static String Component_removeComponentListener_listener_notRegistered;

    // --- ComponentSurfaceDesignRegistry -----------------------------------

    /**
     * A component surface design is already registered for the specified
     * identifier.
     */
    public static String ComponentSurfaceDesignRegistry_registerComponentSurfaceDesign_componentSurfaceDesign_registered;

    /**
     * The component surface design is not registered for the specified
     * identifier.
     */
    public static String ComponentSurfaceDesignRegistry_unregisterComponentSurfaceDesign_componentSurfaceDesign_unregistered;

    // --- ComponentSurfaceDesignRegistryExtensionPointAdapter --------------

    /** The component surface design registry service is already bound. */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_bindComponentSurfaceDesignRegistry_bound;

    /** The extension registry service is already bound. */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while parsing the height attribute of a component
     * surface design configuration element.
     */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_createComponentSurfaceDesignRegistration_parseHeightError;

    /**
     * An error occurred while parsing the width attribute of a component
     * surface design configuration element.
     */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_createComponentSurfaceDesignRegistration_parseWidthError;

    /**
     * An error occurred while parsing the component surface design
     * configuration element.
     */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_registerComponentSurfaceDesign_parseError;

    /** The component surface design registry service is not bound. */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_unbindComponentSurfaceDesignRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- Container --------------------------------------------------------

    /** The container listener is already registered. */
    public static String Container_addContainerListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * IContainerListener.componentAdded().
     */
    public static String Container_componentAdded_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IContainerListener.componentRemoved().
     */
    public static String Container_componentRemoved_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IContainerListener.containerLayoutChanged().
     */
    public static String Container_containerLayoutChanged_unexpectedException;

    /** The component path does not exist in the container. */
    public static String Container_getComponent_path_notExists;

    /** The container listener is not registered. */
    public static String Container_removeContainerListener_listener_notRegistered;

    // --- MementoUtils -----------------------------------------------------

    /** The required attribute is absent. */
    public static String MementoUtils_attribute_absent;

    /** The attribute value is {@code null}. */
    public static String MementoUtils_attributeValue_null;

    /** The attribute value is of the wrong type. */
    public static String MementoUtils_attributeValue_wrongType;

    /** The memento is of the wrong type. */
    public static String MementoUtils_memento_wrongType;

    // --- StackedContainerLayout -------------------------------------------

    /** The components per stack level count is not positive. */
    public static String StackedContainerLayout_ctor_componentsPerStackLevel_notPositive;

    /** The stack level offset in the x-direction is not positive. */
    public static String StackedContainerLayout_ctor_stackLevelOffsetX_notPositive;

    /** The stack level offset in the y-direction is not positive. */
    public static String StackedContainerLayout_ctor_stackLevelOffsetY_notPositive;

    // --- Table ------------------------------------------------------------

    /** The component path does not exist in the table. */
    public static String Table_getComponent_path_notExists;

    // --- Tabletop ---------------------------------------------------------

    /**
     * The component collection contains a component created by a different
     * table environment.
     */
    public static String Tabletop_addComponents_components_containsComponentCreatedByDifferentTableEnvironment;

    /** The component collection contains a {@code null} element. */
    public static String Tabletop_addComponents_components_containsNullElement;

    /**
     * The component collection contains a component already contained in a
     * container.
     */
    public static String Tabletop_addComponents_components_containsOwnedComponent;

    /** The component index is out of range. */
    public static String Tabletop_getComponentFromIndex_index_outOfRange;

    /** The orientation is illegal. */
    public static String Tabletop_orientation_illegal;

    /** The component is not contained in the container. */
    public static String Tabletop_removeComponent_component_notOwned;

    // --- TableEnvironment -------------------------------------------------

    /** Failed to queue the event notification. */
    public static String TableEnvironment_addEventNotification_queueFailed;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NonNlsMessages.class.getName(), NonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NonNlsMessages} class.
     */
    private NonNlsMessages()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- ComponentSurfaceDesignRegistry -----------------------------------

    /**
     * Gets the formatted message indicating a component surface design is
     * already registered for the specified identifier.
     * 
     * @param componentSurfaceDesignId
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a component surface design is
     *         already registered for the specified identifier; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ComponentSurfaceDesignRegistry_registerComponentSurfaceDesign_componentSurfaceDesign_registered(
        /* @NonNull */
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        return bind( ComponentSurfaceDesignRegistry_registerComponentSurfaceDesign_componentSurfaceDesign_registered, componentSurfaceDesignId );
    }

    /**
     * Gets the formatted message indicating the component surface design is not
     * registered for the specified identifier.
     * 
     * @param componentSurfaceDesignId
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component surface design is
     *         not registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String ComponentSurfaceDesignRegistry_unregisterComponentSurfaceDesign_componentSurfaceDesign_unregistered(
        /* @NonNull */
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        return bind( ComponentSurfaceDesignRegistry_unregisterComponentSurfaceDesign_componentSurfaceDesign_unregistered, componentSurfaceDesignId );
    }

    // --- ComponentSurfaceDesignRegistryExtensionPointAdapter --------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * component surface design configuration element.
     * 
     * @param componentSurfaceDesignId
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the component surface design configuration element; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ComponentSurfaceDesignRegistryExtensionPointAdapter_registerComponentSurfaceDesign_parseError(
        /* @NonNull */
        final String componentSurfaceDesignId )
    {
        return bind( ComponentSurfaceDesignRegistryExtensionPointAdapter_registerComponentSurfaceDesign_parseError, componentSurfaceDesignId );
    }

    // --- MementoUtils -----------------------------------------------------

    /**
     * Gets the formatted message indicating the attribute is absent.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute is absent; never
     *         {@code null}.
     */
    /* @NonNull */
    static String MementoUtils_attribute_absent(
        /* @NonNull */
        final String attributeName )
    {
        return bind( MementoUtils_attribute_absent, attributeName );
    }

    /**
     * Gets the formatted message indicating the attribute value is {@code null}
     * .
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute value is
     *         {@code null}; never {@code null}.
     */
    /* @NonNull */
    static String MementoUtils_attributeValue_null(
        /* @NonNull */
        final String attributeName )
    {
        return bind( MementoUtils_attributeValue_null, attributeName );
    }

    /**
     * Gets the formatted message indicating the attribute value is of the wrong
     * type.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute value is of the
     *         wrong type; never {@code null}.
     */
    /* @NonNull */
    static String MementoUtils_attributeValue_wrongType(
        /* @NonNull */
        final String attributeName )
    {
        return bind( MementoUtils_attributeValue_wrongType, attributeName );
    }
}

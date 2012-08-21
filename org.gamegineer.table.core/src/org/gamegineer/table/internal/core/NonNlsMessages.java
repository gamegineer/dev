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
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ContainerLayoutId;

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

    /** The orientation is illegal. */
    public static String Component_orientation_illegal;

    /** The component listener is not registered. */
    public static String Component_removeComponentListener_listener_notRegistered;

    /** The surface designs collection contains a {@code null} surface design. */
    public static String Component_setSurfaceDesigns_surfaceDesigns_containsNullSurfaceDesign;

    // --- ComponentFactory -------------------------------------------------

    /**
     * The memento specifies an illegal component strategy for the specified
     * component type.
     */
    public static String ComponentFactory_createComponent_illegalComponentStrategy;

    /** The memento specifies an unknown component type. */
    public static String ComponentFactory_createComponent_unknownComponentType;

    // --- ComponentStrategyRegistry ----------------------------------------

    /** A component strategy is already registered for the specified identifier. */
    public static String ComponentStrategyRegistry_registerComponentStrategy_componentStrategy_registered;

    /** The component strategy is not registered for the specified identifier. */
    public static String ComponentStrategyRegistry_unregisterComponentStrategy_componentStrategy_unregistered;

    // --- ComponentStrategyRegistryExtensionPointAdapter -------------------

    /** The component strategy registry service is already bound. */
    public static String ComponentStrategyRegistryExtensionPointAdapter_bindComponentStrategyRegistry_bound;

    /** The extension registry service is already bound. */
    public static String ComponentStrategyRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while creating the component strategy of a component
     * strategy configuration element.
     */
    public static String ComponentStrategyRegistryExtensionPointAdapter_createComponentStrategyRegistration_createComponentStrategyError;

    /**
     * An error occurred while parsing the component strategy configuration
     * element.
     */
    public static String ComponentStrategyRegistryExtensionPointAdapter_registerComponentStrategy_parseError;

    /** The component strategy registry service is not bound. */
    public static String ComponentStrategyRegistryExtensionPointAdapter_unbindComponentStrategyRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String ComponentStrategyRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

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

    /**
     * The component collection contains a component created by a different
     * table environment.
     */
    public static String Container_addComponents_components_containsComponentCreatedByDifferentTableEnvironment;

    /** The component collection contains a {@code null} element. */
    public static String Container_addComponents_components_containsNullElement;

    /**
     * The component collection contains a component already contained in a
     * container.
     */
    public static String Container_addComponents_components_containsOwnedComponent;

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

    /** The component index is out of range. */
    public static String Container_getComponentFromIndex_index_outOfRange;

    /** The component path does not exist in the container. */
    public static String Container_getComponentFromPath_path_notExists;

    /** The component is not contained in the container. */
    public static String Container_removeComponent_component_notOwned;

    /** The container listener is not registered. */
    public static String Container_removeContainerListener_listener_notRegistered;

    // --- ContainerLayoutRegistry ------------------------------------------

    /** A container layout is already registered for the specified identifier. */
    public static String ContainerLayoutRegistry_registerContainerLayout_containerLayout_registered;

    /** The container layout is not registered for the specified identifier. */
    public static String ContainerLayoutRegistry_unregisterContainerLayout_containerLayout_unregistered;

    // --- ContainerLayoutRegistryExtensionPointAdapter ---------------------

    /** The container layout registry service is already bound. */
    public static String ContainerLayoutRegistryExtensionPointAdapter_bindContainerLayoutRegistry_bound;

    /** The extension registry service is already bound. */
    public static String ContainerLayoutRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while creating the container layout of a container
     * layout configuration element.
     */
    public static String ContainerLayoutRegistryExtensionPointAdapter_createContainerLayoutRegistration_createContainerLayoutError;

    /**
     * An error occurred while parsing the container layout configuration
     * element.
     */
    public static String ContainerLayoutRegistryExtensionPointAdapter_registerContainerLayout_parseError;

    /** The container layout registry service is not bound. */
    public static String ContainerLayoutRegistryExtensionPointAdapter_unbindContainerLayoutRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String ContainerLayoutRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- MementoUtils -----------------------------------------------------

    /** The required attribute is absent. */
    public static String MementoUtils_attribute_absent;

    /** The attribute value is {@code null}. */
    public static String MementoUtils_attributeValue_null;

    /** The attribute value is of the wrong type. */
    public static String MementoUtils_attributeValue_wrongType;

    /** The memento is of the wrong type. */
    public static String MementoUtils_memento_wrongType;

    // --- Table ------------------------------------------------------------

    /** The component path does not exist in the table. */
    public static String Table_getComponent_path_notExists;

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

    // --- ComponentStrategyRegistry ----------------------------------------

    /**
     * Gets the formatted message indicating a component strategy is already
     * registered for the specified identifier.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a component strategy is already
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String ComponentStrategyRegistry_registerComponentStrategy_componentStrategy_registered(
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyRegistry_registerComponentStrategy_componentStrategy_registered, componentStrategyId );
    }

    /**
     * Gets the formatted message indicating the component strategy is not
     * registered for the specified identifier.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component strategy is not
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String ComponentStrategyRegistry_unregisterComponentStrategy_componentStrategy_unregistered(
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyRegistry_unregisterComponentStrategy_componentStrategy_unregistered, componentStrategyId );
    }

    // --- ComponentStrategyRegistryExtensionPointAdapter -------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * component strategy configuration element.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the component strategy configuration element; never {@code null}.
     */
    /* @NonNull */
    static String ComponentStrategyRegistryExtensionPointAdapter_registerComponentStrategy_parseError(
        /* @NonNull */
        final String componentStrategyId )
    {
        return bind( ComponentStrategyRegistryExtensionPointAdapter_registerComponentStrategy_parseError, componentStrategyId );
    }

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

    // --- ContainerLayoutRegistry ------------------------------------------

    /**
     * Gets the formatted message indicating a container layout is already
     * registered for the specified identifier.
     * 
     * @param containerLayoutId
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a container layout is already
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String ContainerLayoutRegistry_registerContainerLayout_containerLayout_registered(
        /* @NonNull */
        final ContainerLayoutId containerLayoutId )
    {
        return bind( ContainerLayoutRegistry_registerContainerLayout_containerLayout_registered, containerLayoutId );
    }

    /**
     * Gets the formatted message indicating the container layout is not
     * registered for the specified identifier.
     * 
     * @param containerLayoutId
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the container layout is not
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String ContainerLayoutRegistry_unregisterContainerLayout_containerLayout_unregistered(
        /* @NonNull */
        final ContainerLayoutId containerLayoutId )
    {
        return bind( ContainerLayoutRegistry_unregisterContainerLayout_containerLayout_unregistered, containerLayoutId );
    }

    // --- ContainerLayoutRegistryExtensionPointAdapter ---------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * container layout configuration element.
     * 
     * @param containerLayoutId
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the container layout configuration element; never {@code null}.
     */
    /* @NonNull */
    static String ContainerLayoutRegistryExtensionPointAdapter_registerContainerLayout_parseError(
        /* @NonNull */
        final String containerLayoutId )
    {
        return bind( ContainerLayoutRegistryExtensionPointAdapter_registerContainerLayout_parseError, containerLayoutId );
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

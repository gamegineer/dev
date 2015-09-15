/*
 * NonNlsMessages.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.core.impl;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

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
    public static String Component_addComponentListener_listener_registered = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentBoundsChanged().
     */
    public static String Component_componentBoundsChanged_unexpectedException = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentOrientationChanged().
     */
    public static String Component_componentOrientationChanged_unexpectedException = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentSurfaceDesignChanged().
     */
    public static String Component_componentSurfaceDesignChanged_unexpectedException = ""; //$NON-NLS-1$

    /** The orientation is illegal. */
    public static String Component_orientation_illegal = ""; //$NON-NLS-1$

    /** The component listener is not registered. */
    public static String Component_removeComponentListener_listener_notRegistered = ""; //$NON-NLS-1$

    // --- ComponentFactory -------------------------------------------------

    /**
     * The memento specifies an illegal component strategy for the specified
     * component type.
     */
    public static String ComponentFactory_createComponent_illegalComponentStrategy = ""; //$NON-NLS-1$

    /** The memento specifies an unknown component type. */
    public static String ComponentFactory_createComponent_unknownComponentType = ""; //$NON-NLS-1$

    // --- ComponentStrategyRegistryExtensionPointAdapter -------------------

    /**
     * An error occurred while creating the component strategy of a component
     * strategy configuration element.
     */
    public static String ComponentStrategyRegistryExtensionPointAdapter_createObject_createComponentStrategyError = ""; //$NON-NLS-1$

    /** The component strategy identifier is missing. */
    public static String ComponentStrategyRegistryExtensionPointAdapter_createObject_missingId = ""; //$NON-NLS-1$

    // --- ComponentSurfaceDesignRegistryExtensionPointAdapter --------------

    /** The component surface design identifier is missing. */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_createObject_missingId = ""; //$NON-NLS-1$

    /**
     * An error occurred while parsing the height attribute of a component
     * surface design configuration element.
     */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_createObject_parseHeightError = ""; //$NON-NLS-1$

    /**
     * An error occurred while parsing the width attribute of a component
     * surface design configuration element.
     */
    public static String ComponentSurfaceDesignRegistryExtensionPointAdapter_createObject_parseWidthError = ""; //$NON-NLS-1$

    // --- Container --------------------------------------------------------

    /**
     * The component collection contains a component created by a different
     * table environment.
     */
    public static String Container_addComponents_components_containsComponentCreatedByDifferentTableEnvironment = ""; //$NON-NLS-1$

    /** The component collection contains a {@code null} element. */
    public static String Container_addComponents_components_containsNullElement = ""; //$NON-NLS-1$

    /**
     * The component collection contains a component already contained in a
     * container.
     */
    public static String Container_addComponents_components_containsOwnedComponent = ""; //$NON-NLS-1$

    /** The container listener is already registered. */
    public static String Container_addContainerListener_listener_registered = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * IContainerListener.componentAdded().
     */
    public static String Container_componentAdded_unexpectedException = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * IContainerListener.componentRemoved().
     */
    public static String Container_componentRemoved_unexpectedException = ""; //$NON-NLS-1$

    /**
     * An unexpected exception was thrown from
     * IContainerListener.containerLayoutChanged().
     */
    public static String Container_containerLayoutChanged_unexpectedException = ""; //$NON-NLS-1$

    /** The component index is out of range. */
    public static String Container_getComponentFromIndex_index_outOfRange = ""; //$NON-NLS-1$

    /** The component is not contained in the container. */
    public static String Container_removeComponent_component_notOwned = ""; //$NON-NLS-1$

    /** The container listener is not registered. */
    public static String Container_removeContainerListener_listener_notRegistered = ""; //$NON-NLS-1$

    // --- ContainerLayoutRegistryExtensionPointAdapter ---------------------

    /**
     * An error occurred while creating the container layout of a container
     * layout configuration element.
     */
    public static String ContainerLayoutRegistryExtensionPointAdapter_createObject_createContainerLayoutError = ""; //$NON-NLS-1$

    /** The container layout identifier is missing. */
    public static String ContainerLayoutRegistryExtensionPointAdapter_createObject_missingId = ""; //$NON-NLS-1$

    // --- DragContext ------------------------------------------------------

    /** A drag-and-drop operation is not active. */
    public static String DragContext_dragNotActive = ""; //$NON-NLS-1$

    // --- MementoUtils -----------------------------------------------------

    /** The required attribute is absent. */
    public static String MementoUtils_attribute_absent = ""; //$NON-NLS-1$

    /** The attribute value is {@code null}. */
    public static String MementoUtils_attributeValue_null = ""; //$NON-NLS-1$

    /** The attribute value is of the wrong type. */
    public static String MementoUtils_attributeValue_wrongType = ""; //$NON-NLS-1$

    /** The memento is of the wrong type. */
    public static String MementoUtils_memento_wrongType = ""; //$NON-NLS-1$

    // --- Table ------------------------------------------------------------

    /** The component has no container. */
    public static String Table_beginDrag_component_noContainer = ""; //$NON-NLS-1$

    /** The component does not exist in the table. */
    public static String Table_beginDrag_component_notExists = ""; //$NON-NLS-1$

    /** A drag-and-drop operation is active. */
    public static String Table_beginDrag_dragActive = ""; //$NON-NLS-1$

    // --- TableEnvironment -------------------------------------------------

    /** Failed to queue the event notification. */
    public static String TableEnvironment_addEventNotification_queueFailed = ""; //$NON-NLS-1$


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
    static String MementoUtils_attribute_absent(
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
    static String MementoUtils_attributeValue_null(
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
    static String MementoUtils_attributeValue_wrongType(
        final String attributeName )
    {
        return bind( MementoUtils_attributeValue_wrongType, attributeName );
    }
}

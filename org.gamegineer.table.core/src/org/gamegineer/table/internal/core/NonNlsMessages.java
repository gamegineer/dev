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
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.CardSurfaceDesignId;
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

    // --- Card -------------------------------------------------------------

    /** The component listener is already registered. */
    public static String Card_addComponentListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentBoundsChanged().
     */
    public static String Card_componentBoundsChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentOrientationChanged().
     */
    public static String Card_componentOrientationChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentSurfaceDesignChanged().
     */
    public static String Card_componentSurfaceDesignChanged_unexpectedException;

    /** The component listener is not registered. */
    public static String Card_removeComponentListener_listener_notRegistered;

    /** The face design size is not equal to the back design size. */
    public static String Card_setSurfaceDesigns_faceDesign_sizeNotEqual;

    // --- CardPile ---------------------------------------------------------

    /** The card pile listener is already registered. */
    public static String CardPile_addCardPileListener_listener_registered;

    /** The component listener is already registered. */
    public static String CardPile_addComponentListener_listener_registered;

    /**
     * The component collection contains a component created by a different
     * table.
     */
    public static String CardPile_addComponents_components_containsComponentCreatedByDifferentTable;

    /** The component collection contains a {@code null} element. */
    public static String CardPile_addComponents_components_containsNullElement;

    /**
     * The component collection contains a component already contained in a
     * container.
     */
    public static String CardPile_addComponents_components_containsOwnedComponent;

    /**
     * An unexpected exception was thrown from
     * ICardPileListener.cardPileLayoutChanged().
     */
    public static String CardPile_cardPileLayoutChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IContainerListener.componentAdded().
     */
    public static String CardPile_componentAdded_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentBoundsChanged().
     */
    public static String CardPile_componentBoundsChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentOrientationChanged().
     */
    public static String CardPile_componentOrientationChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IContainerListener.componentRemoved().
     */
    public static String CardPile_componentRemoved_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentListener.componentSurfaceDesignChanged().
     */
    public static String CardPile_componentSurfaceDesignChanged_unexpectedException;

    /** The component index is out of range. */
    public static String CardPile_getComponentFromIndex_index_outOfRange;

    /** The component is not contained in the container. */
    public static String CardPile_getComponentIndex_component_notOwned;

    /** An unknown layout is active. */
    public static String CardPile_getComponentOffsetAt_unknownLayout;

    /** The card pile listener is not registered. */
    public static String CardPile_removeCardPileListener_listener_notRegistered;

    /** The component listener is not registered. */
    public static String CardPile_removeComponentListener_listener_notRegistered;

    // --- CardPileBaseDesign -----------------------------------------------

    /** The card pile base design height must not be negative. */
    public static String CardPileBaseDesign_ctor_height_negative;

    /** The card pile base design width must not be negative. */
    public static String CardPileBaseDesign_ctor_width_negative;

    // --- CardPileBaseDesignRegistry ---------------------------------------

    /**
     * A card pile base design is already registered for the specified
     * identifier.
     */
    public static String CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered;

    /**
     * The card pile base design is not registered for the specified identifier.
     */
    public static String CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered;

    // --- CardPileBaseDesignRegistryExtensionPointAdapter ------------------

    /** The card pile base design registry service is already bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_bindCardPileBaseDesignRegistry_bound;

    /** The extension registry service is already bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while parsing the height attribute of a card pile base
     * design configuration element.
     */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesignRegistration_parseHeightError;

    /**
     * An error occurred while parsing the width attribute of a card pile base
     * design configuration element.
     */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesignRegistration_parseWidthError;

    /**
     * An error occurred while parsing the card pile base design configuration
     * element.
     */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError;

    /** The card pile base design registry service is not bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_unbindCardPileBaseDesignRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- CardSurfaceDesign ------------------------------------------------

    /** The card surface design height must not be negative. */
    public static String CardSurfaceDesign_ctor_height_negative;

    /** The card surface design width must not be negative. */
    public static String CardSurfaceDesign_ctor_width_negative;

    // --- CardSurfaceDesignRegistry ----------------------------------------

    /**
     * A card surface design is already registered for the specified identifier.
     */
    public static String CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered;

    /** The card surface design is not registered for the specified identifier. */
    public static String CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered;

    // --- CardSurfaceDesignRegistryExtensionPointAdapter -------------------

    /** The card surface design registry service is already bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_bindCardSurfaceDesignRegistry_bound;

    /** The extension registry service is already bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while parsing the height attribute of a card surface
     * design configuration element.
     */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesignRegistration_parseHeightError;

    /**
     * An error occurred while parsing the width attribute of a card surface
     * design configuration element.
     */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesignRegistration_parseWidthError;

    /**
     * An error occurred while parsing the card surface design configuration
     * element.
     */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_registerCardSurfaceDesign_parseError;

    /** The card surface design registry service is not bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_unbindCardSurfaceDesignRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- ComponentSurfaceDesign -------------------------------------------

    /** The component surface design height must not be negative. */
    public static String ComponentSurfaceDesign_ctor_height_negative;

    /** The component surface design width must not be negative. */
    public static String ComponentSurfaceDesign_ctor_width_negative;

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

    /** The card pile was created by a different table. */
    public static String Table_addCardPile_cardPile_createdByDifferentTable;

    /** The card pile is already contained in a table. */
    public static String Table_addCardPile_cardPile_owned;

    /** The table listener is already registered. */
    public static String Table_addTableListener_listener_registered;

    /** An unexpected exception was thrown from ITableListener.cardPileAdded(). */
    public static String Table_cardPileAdded_unexpectedException;

    /**
     * An unexpected exception was thrown from ITableListener.cardPileRemoved().
     */
    public static String Table_cardPileRemoved_unexpectedException;

    /** The card pile index is out of range. */
    public static String Table_getCardPileFromIndex_index_outOfRange;

    /** The card pile is not contained in the table. */
    public static String Table_getCardPileIndex_cardPile_notOwned;

    /** The card pile is not contained in the table. */
    public static String Table_removeCardPile_cardPile_notOwned;

    /** The table listener is not registered. */
    public static String Table_removeTableListener_listener_notRegistered;

    // --- TableContext -----------------------------------------------------

    /** Failed to queue the event notification. */
    public static String TableContext_addEventNotification_queueFailed;


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

    // --- CardPileBaseDesignRegistry ---------------------------------------

    /**
     * Gets the formatted message indicating a card pile base design is already
     * registered for the specified identifier.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a card pile base design is
     *         already registered for the specified identifier; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered, cardPileBaseDesignId );
    }

    /**
     * Gets the formatted message indicating the card pile base design is not
     * registered for the specified identifier.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the card pile base design is not
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered, cardPileBaseDesignId );
    }

    // --- CardPileBaseDesignRegistryExtensionPointAdapter ------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile base design configuration element.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile base design configuration element; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError(
        /* @NonNull */
        final String cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError, cardPileBaseDesignId );
    }

    // --- CardSurfaceDesignRegistry ----------------------------------------

    /**
     * Gets the formatted message indicating a card surface design is already
     * registered for the specified identifier.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a card surface design is already
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered, cardSurfaceDesignId );
    }

    /**
     * Gets the formatted message indicating the card surface design is not
     * registered for the specified identifier.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the card surface design is not
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered, cardSurfaceDesignId );
    }

    // --- CardSurfaceDesignRegistryExtensionPointAdapter ------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card surface design configuration element.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card surface design configuration element; never {@code null}
     *         .
     */
    /* @NonNull */
    static String CardSurfaceDesignRegistryExtensionPointAdapter_registerCardSurfaceDesign_parseError(
        /* @NonNull */
        final String cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignRegistryExtensionPointAdapter_registerCardSurfaceDesign_parseError, cardSurfaceDesignId );
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

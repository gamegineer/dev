/*
 * DeckPrototypeFactory.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Oct 25, 2012 at 9:41:24 PM.
 */

package org.gamegineer.cards.internal.ui.impl.prototypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.cards.core.CardOrientation;
import org.gamegineer.cards.core.CardSurfaceDesignIds;
import org.gamegineer.cards.core.CardsComponentStrategyIds;
import org.gamegineer.cards.internal.ui.impl.BundleConstants;
import org.gamegineer.table.core.ComponentStrategyRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.NoSuchComponentStrategyException;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;
import org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A factory for creating deck prototypes from the extension registry.
 */
@NotThreadSafe
public final class DeckPrototypeFactory
    implements IComponentPrototypeFactory, IExecutableExtension
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the configuration element parameter specifying the component
     * surface design identifier for the back of each card in the deck.
     */
    private static final String PARAM_NAME_BACK_DESIGN_ID = "backDesign"; //$NON-NLS-1$

    /**
     * The name of the configuration element parameter specifying if Jokers
     * should be included in the deck.
     */
    private static final String PARAM_NAME_INCLUDE_JOKERS = "includeJokers"; //$NON-NLS-1$

    /**
     * The identifier of the component surface design of the back of each card
     * in the deck.
     */
    @Nullable
    private ComponentSurfaceDesignId backDesignId_;

    /** Indicates Jokers should be included in the deck. */
    private boolean includeJokers_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DeckPrototypeFactory} class.
     */
    public DeckPrototypeFactory()
    {
        backDesignId_ = null;
        includeJokers_ = false;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card prototype.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * @param faceDesignId
     *        The identifier of the component surface design of the card face;
     *        must not be {@code null}.
     * 
     * @return A new card prototype; never {@code null}.
     * 
     * @throws org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException
     *         If the card prototype cannot be created.
     */
    private IComponent createCard(
        final ITableEnvironment tableEnvironment,
        final ComponentSurfaceDesignId faceDesignId )
        throws ComponentPrototypeFactoryException
    {
        try
        {
            final IComponent card = tableEnvironment.createComponent( ComponentStrategyRegistry.getComponentStrategy( CardsComponentStrategyIds.CARD ) );
            assert backDesignId_ != null;
            card.setSurfaceDesign( CardOrientation.BACK, ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( backDesignId_ ) );
            card.setSurfaceDesign( CardOrientation.FACE, ComponentSurfaceDesignRegistry.getComponentSurfaceDesign( faceDesignId ) );
            return card;
        }
        catch( final NoSuchComponentStrategyException e )
        {
            throw new ComponentPrototypeFactoryException( NonNlsMessages.DeckPrototypeFactory_createComponentPrototype_error, e );
        }
        catch( final NoSuchComponentSurfaceDesignException e )
        {
            throw new ComponentPrototypeFactoryException( NonNlsMessages.DeckPrototypeFactory_createComponentPrototype_error, e );
        }
    }

    /*
     * @see org.gamegineer.table.ui.prototype.IComponentPrototypeFactory#createComponentPrototype(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    public List<IComponent> createComponentPrototype(
        final ITableEnvironment tableEnvironment )
        throws ComponentPrototypeFactoryException
    {
        final ComponentSurfaceDesignId[] standardFaceDesignIds = new ComponentSurfaceDesignId[] {
            CardSurfaceDesignIds.FACE_CLUBS_ACE, //
            CardSurfaceDesignIds.FACE_CLUBS_TWO, //
            CardSurfaceDesignIds.FACE_CLUBS_THREE, //
            CardSurfaceDesignIds.FACE_CLUBS_FOUR, //
            CardSurfaceDesignIds.FACE_CLUBS_FIVE, //
            CardSurfaceDesignIds.FACE_CLUBS_SIX, //
            CardSurfaceDesignIds.FACE_CLUBS_SEVEN, //
            CardSurfaceDesignIds.FACE_CLUBS_EIGHT, //
            CardSurfaceDesignIds.FACE_CLUBS_NINE, //
            CardSurfaceDesignIds.FACE_CLUBS_TEN, //
            CardSurfaceDesignIds.FACE_CLUBS_JACK, //
            CardSurfaceDesignIds.FACE_CLUBS_QUEEN, //
            CardSurfaceDesignIds.FACE_CLUBS_KING, //
            CardSurfaceDesignIds.FACE_DIAMONDS_ACE, //
            CardSurfaceDesignIds.FACE_DIAMONDS_TWO, //
            CardSurfaceDesignIds.FACE_DIAMONDS_THREE, //
            CardSurfaceDesignIds.FACE_DIAMONDS_FOUR, //
            CardSurfaceDesignIds.FACE_DIAMONDS_FIVE, //
            CardSurfaceDesignIds.FACE_DIAMONDS_SIX, //
            CardSurfaceDesignIds.FACE_DIAMONDS_SEVEN, //
            CardSurfaceDesignIds.FACE_DIAMONDS_EIGHT, //
            CardSurfaceDesignIds.FACE_DIAMONDS_NINE, //
            CardSurfaceDesignIds.FACE_DIAMONDS_TEN, //
            CardSurfaceDesignIds.FACE_DIAMONDS_JACK, //
            CardSurfaceDesignIds.FACE_DIAMONDS_QUEEN, //
            CardSurfaceDesignIds.FACE_DIAMONDS_KING, //
            CardSurfaceDesignIds.FACE_HEARTS_ACE, //
            CardSurfaceDesignIds.FACE_HEARTS_TWO, //
            CardSurfaceDesignIds.FACE_HEARTS_THREE, //
            CardSurfaceDesignIds.FACE_HEARTS_FOUR, //
            CardSurfaceDesignIds.FACE_HEARTS_FIVE, //
            CardSurfaceDesignIds.FACE_HEARTS_SIX, //
            CardSurfaceDesignIds.FACE_HEARTS_SEVEN, //
            CardSurfaceDesignIds.FACE_HEARTS_EIGHT, //
            CardSurfaceDesignIds.FACE_HEARTS_NINE, //
            CardSurfaceDesignIds.FACE_HEARTS_TEN, //
            CardSurfaceDesignIds.FACE_HEARTS_JACK, //
            CardSurfaceDesignIds.FACE_HEARTS_QUEEN, //
            CardSurfaceDesignIds.FACE_HEARTS_KING, //
            CardSurfaceDesignIds.FACE_SPADES_ACE, //
            CardSurfaceDesignIds.FACE_SPADES_TWO, //
            CardSurfaceDesignIds.FACE_SPADES_THREE, //
            CardSurfaceDesignIds.FACE_SPADES_FOUR, //
            CardSurfaceDesignIds.FACE_SPADES_FIVE, //
            CardSurfaceDesignIds.FACE_SPADES_SIX, //
            CardSurfaceDesignIds.FACE_SPADES_SEVEN, //
            CardSurfaceDesignIds.FACE_SPADES_EIGHT, //
            CardSurfaceDesignIds.FACE_SPADES_NINE, //
            CardSurfaceDesignIds.FACE_SPADES_TEN, //
            CardSurfaceDesignIds.FACE_SPADES_JACK, //
            CardSurfaceDesignIds.FACE_SPADES_QUEEN, //
            CardSurfaceDesignIds.FACE_SPADES_KING
        };

        final List<IComponent> cards = new ArrayList<>( standardFaceDesignIds.length + 2 );

        for( final ComponentSurfaceDesignId faceDesignId : standardFaceDesignIds )
        {
            assert faceDesignId != null;
            cards.add( createCard( tableEnvironment, faceDesignId ) );
        }

        if( includeJokers_ )
        {
            cards.add( createCard( tableEnvironment, CardSurfaceDesignIds.FACE_SPECIAL_JOKER ) );
            cards.add( createCard( tableEnvironment, CardSurfaceDesignIds.FACE_SPECIAL_JOKER ) );
        }

        return cards;
    }

    /*
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
    public void setInitializationData(
        @Nullable
        @SuppressWarnings( "unused" )
        final IConfigurationElement config,
        @Nullable
        @SuppressWarnings( "unused" )
        final String propertyName,
        @Nullable
        final Object data )
        throws CoreException
    {
        @SuppressWarnings( "unchecked" )
        final Map<String, String> parameters = (Map<String, String>)data;
        if( parameters == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.DeckPrototypeFactory_setInitializationData_parametersNotSpecified ) );
        }

        final String encodedBackDesignId = parameters.get( PARAM_NAME_BACK_DESIGN_ID );
        if( encodedBackDesignId != null )
        {
            backDesignId_ = ComponentSurfaceDesignId.fromString( encodedBackDesignId );
        }
        else
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.DeckPrototypeFactory_setInitializationData_parameterNotSpecified( PARAM_NAME_BACK_DESIGN_ID ) ) );
        }

        final String encodedIncludeJokers = parameters.get( PARAM_NAME_INCLUDE_JOKERS );
        if( encodedIncludeJokers != null )
        {
            includeJokers_ = Boolean.parseBoolean( encodedIncludeJokers );
        }
    }
}

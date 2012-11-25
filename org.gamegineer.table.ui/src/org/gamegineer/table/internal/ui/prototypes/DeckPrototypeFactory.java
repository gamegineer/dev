/*
 * DeckPrototypeFactory.java
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
 * Created on Oct 25, 2012 at 9:41:24 PM.
 */

package org.gamegineer.table.internal.ui.prototypes;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Status;
import org.gamegineer.cards.core.CardOrientation;
import org.gamegineer.cards.core.CardsComponentStrategyIds;
import org.gamegineer.table.core.ComponentStrategyRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.NoSuchComponentStrategyException;
import org.gamegineer.table.core.NoSuchComponentSurfaceDesignException;
import org.gamegineer.table.internal.ui.BundleConstants;
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
    /* @NonNull */
    private IComponent createCard(
        /* @NonNull */
        final ITableEnvironment tableEnvironment,
        /* @NonNull */
        final ComponentSurfaceDesignId faceDesignId )
        throws ComponentPrototypeFactoryException
    {
        assert tableEnvironment != null;
        assert faceDesignId != null;
        try
        {
            final IComponent card = tableEnvironment.createComponent( ComponentStrategyRegistry.getComponentStrategy( CardsComponentStrategyIds.CARD ) );
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
        assertArgumentNotNull( tableEnvironment, "tableEnvironment" ); //$NON-NLS-1$

        final String[] encodedStandardFaceDesignIds = new String[] {
            "org.gamegineer.cards.cardSurfaces.clubs.ace", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.two", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.three", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.four", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.five", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.six", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.seven", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.eight", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.nine", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.ten", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.jack", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.queen", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.clubs.king", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.ace", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.two", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.three", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.four", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.five", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.six", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.seven", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.eight", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.nine", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.ten", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.jack", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.queen", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.diamonds.king", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.ace", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.two", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.three", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.four", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.five", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.six", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.seven", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.eight", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.nine", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.ten", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.jack", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.queen", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.hearts.king", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.ace", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.two", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.three", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.four", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.five", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.six", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.seven", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.eight", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.nine", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.ten", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.jack", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.queen", //$NON-NLS-1$
            "org.gamegineer.cards.cardSurfaces.spades.king", //$NON-NLS-1$
        };

        final List<IComponent> cards = new ArrayList<IComponent>( encodedStandardFaceDesignIds.length + 2 );

        for( final String encodedFaceDesignId : encodedStandardFaceDesignIds )
        {
            cards.add( createCard( tableEnvironment, ComponentSurfaceDesignId.fromString( encodedFaceDesignId ) ) );
        }

        if( includeJokers_ )
        {
            final ComponentSurfaceDesignId jokerFaceDesignId = ComponentSurfaceDesignId.fromString( "org.gamegineer.cards.cardSurfaces.special.joker" ); //$NON-NLS-1$
            cards.add( createCard( tableEnvironment, jokerFaceDesignId ) );
            cards.add( createCard( tableEnvironment, jokerFaceDesignId ) );
        }

        return cards;
    }

    /*
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
    public void setInitializationData(
        @SuppressWarnings( "unused" )
        final IConfigurationElement config,
        @SuppressWarnings( "unused" )
        final String propertyName,
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

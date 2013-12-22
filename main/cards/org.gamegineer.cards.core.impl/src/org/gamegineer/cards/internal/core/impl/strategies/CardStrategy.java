/*
 * CardStrategy.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Aug 1, 2012 at 8:14:04 PM.
 */

package org.gamegineer.cards.internal.core.impl.strategies;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.cards.core.CardOrientation;
import org.gamegineer.cards.core.CardSurfaceDesignIds;
import org.gamegineer.cards.core.CardsComponentStrategyIds;
import org.gamegineer.table.core.AbstractComponentStrategy;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.dnd.DefaultDragStrategy;
import org.gamegineer.table.core.dnd.IDragStrategy;
import org.gamegineer.table.core.dnd.IDragStrategyFactory;

/**
 * A component strategy that represents a card.
 */
@Immutable
final class CardStrategy
    extends AbstractComponentStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of supported card orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardOrientation.values( CardOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardStrategy} class.
     */
    CardStrategy()
    {
        super( CardsComponentStrategyIds.CARD );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getDefaultOrientation()
     */
    @Override
    public ComponentOrientation getDefaultOrientation()
    {
        return CardOrientation.FACE;
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentStrategy#getDefaultSurfaceDesignId()
     */
    @Override
    protected ComponentSurfaceDesignId getDefaultSurfaceDesignId()
    {
        return CardSurfaceDesignIds.DEFAULT;
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentStrategy#getExtension(java.lang.Class)
     */
    @Override
    public <T> T getExtension(
        final Class<T> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        if( type == IDragStrategyFactory.class )
        {
            return type.cast( DragStrategyFactory.INSTANCE );
        }

        return super.getExtension( type );
    }

    /*
     * @see org.gamegineer.table.core.IComponentStrategy#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The drag strategy for a card.
     */
    @Immutable
    static final class DragStrategy
        implements IDragStrategy
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The component from which the drag-and-drop operation will begin. */
        private final IComponent component_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DragStrategy} class.
         * 
         * @param component
         *        The component from which the drag-and-drop operation will
         *        begin; must not be {@code null}.
         */
        DragStrategy(
            /* @NonNull */
            final IComponent component )
        {
            assert component != null;

            component_ = component;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.dnd.IDragStrategy#canDrop(org.gamegineer.table.core.IContainer)
         */
        @Override
        public boolean canDrop(
            final IContainer dropContainer )
        {
            assertArgumentNotNull( dropContainer, "dropContainer" ); //$NON-NLS-1$

            return CardsComponentStrategyIds.CARD_PILE.equals( dropContainer.getStrategy().getId() );
        }

        /*
         * @see org.gamegineer.table.core.dnd.IDragStrategy#getDragComponents()
         */
        @Override
        public List<IComponent> getDragComponents()
        {
            final IContainer container = component_.getContainer();
            assert container != null;
            final List<IComponent> components = container.getComponents();
            final ComponentPath componentPath = component_.getPath();
            assert componentPath != null;
            return components.subList( componentPath.getIndex(), components.size() );
        }
    }

    /**
     * A factory for creating instances of {@link DragStrategy}.
     */
    @Immutable
    static final class DragStrategyFactory
        implements IDragStrategyFactory
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The singleton instance of this class. */
        static final DragStrategyFactory INSTANCE = new DragStrategyFactory();


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DragStrategyFactory} class.
         */
        private DragStrategyFactory()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.dnd.IDragStrategyFactory#createDragStrategy(org.gamegineer.table.core.IComponent, org.gamegineer.table.core.dnd.IDragStrategy)
         */
        @Override
        public IDragStrategy createDragStrategy(
            final IComponent component,
            final IDragStrategy successorDragStrategy )
        {
            assertArgumentNotNull( component, "component" ); //$NON-NLS-1$
            assertArgumentNotNull( successorDragStrategy, "successorDragStrategy" ); //$NON-NLS-1$

            return isContainedWithinCardPile( component ) //
                ? new DragStrategy( component ) //
                : new DefaultDragStrategy( component );
        }

        /**
         * Indicates the specified component (card) is contained within a card
         * pile.
         * 
         * @param component
         *        The component (card); must not be {@code null}.
         * 
         * @return {@code true} if the specified component (card) is contained
         *         within a card pile; otherwise {@code false}.
         */
        private static boolean isContainedWithinCardPile(
            /* @NonNull */
            final IComponent component )
        {
            assert component != null;

            final IContainer container = component.getContainer();
            return (container != null) && CardsComponentStrategyIds.CARD_PILE.equals( container.getStrategy().getId() );
        }
    }
}

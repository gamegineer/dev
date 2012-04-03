/*
 * NetworkTableUtils.java
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
 * Created on Jul 15, 2011 at 8:39:13 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.Loggers;

/**
 * A collection of useful methods for working with network tables.
 */
@ThreadSafe
public final class NetworkTableUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableUtils} class.
     */
    private NetworkTableUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Increments the state of the specified card pile associated with the
     * specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * @param cardPileIndex
     *        The card pile index.
     * @param cardPileIncrement
     *        The incremental change to the state of the card pile; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileIndex} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code table} or {@code cardPileIncrement} is {@code null}.
     */
    @SuppressWarnings( "boxing" )
    public static void incrementCardPileState(
        /* @NonNull */
        final ITable table,
        final int cardPileIndex,
        /* @NonNull */
        final CardPileIncrement cardPileIncrement )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardPileIncrement, "cardPileIncrement" ); //$NON-NLS-1$

        table.getLock().lock();
        try
        {
            final ICardPile cardPile = table.getCardPile( cardPileIndex );

            if( cardPileIncrement.getBaseDesign() != null )
            {
                cardPile.setBaseDesign( cardPileIncrement.getBaseDesign() );
            }

            if( cardPileIncrement.getBaseLocation() != null )
            {
                cardPile.setBaseLocation( cardPileIncrement.getBaseLocation() );
            }

            if( cardPileIncrement.getLayout() != null )
            {
                cardPile.setLayout( cardPileIncrement.getLayout() );
            }

            if( cardPileIncrement.getRemovedCardCount() != null )
            {
                final int removedCardCount = cardPileIncrement.getRemovedCardCount();
                if( removedCardCount == cardPile.getComponentCount() )
                {
                    cardPile.removeComponents();
                }
                else
                {
                    for( int index = 0; index < removedCardCount; ++index )
                    {
                        cardPile.removeComponent();
                    }
                }
            }

            if( cardPileIncrement.getAddedCardMementos() != null )
            {
                for( final Object cardMemento : cardPileIncrement.getAddedCardMementos() )
                {
                    final ICard card = table.createCard();

                    try
                    {
                        card.setMemento( cardMemento );
                    }
                    catch( final MementoException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_incrementCardPileState_setCardStateFailed, e );
                    }

                    cardPile.addComponent( card );
                }
            }
        }
        finally
        {
            table.getLock().unlock();
        }
    }

    /**
     * Increments the state of the specified card associated with the specified
     * table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * @param cardPileIndex
     *        The card pile index.
     * @param cardIndex
     *        The card index.
     * @param cardIncrement
     *        The incremental change to the state of the card; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileIndex} or {@code cardIndex} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code table} or {@code cardIncrement} is {@code null}.
     */
    public static void incrementCardState(
        /* @NonNull */
        final ITable table,
        final int cardPileIndex,
        final int cardIndex,
        /* @NonNull */
        final CardIncrement cardIncrement )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardIncrement, "cardIncrement" ); //$NON-NLS-1$

        table.getLock().lock();
        try
        {
            final ICardPile cardPile = table.getCardPile( cardPileIndex );
            final ICard card = (ICard)cardPile.getComponent( cardIndex ); // FIXME: remove cast

            if( (cardIncrement.getBackDesign() != null) && (cardIncrement.getFaceDesign() != null) )
            {
                card.setSurfaceDesigns( cardIncrement.getBackDesign(), cardIncrement.getFaceDesign() );
            }

            if( cardIncrement.getLocation() != null )
            {
                card.setLocation( cardIncrement.getLocation() );
            }

            if( cardIncrement.getOrientation() != null )
            {
                card.setOrientation( cardIncrement.getOrientation() );
            }
        }
        finally
        {
            table.getLock().unlock();
        }
    }

    /**
     * Increments the state of the specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * @param tableIncrement
     *        The incremental change to the state of the table; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} or {@code tableIncrement} is {@code null}.
     */
    @SuppressWarnings( "boxing" )
    public static void incrementTableState(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final TableIncrement tableIncrement )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$

        table.getLock().lock();
        try
        {
            if( tableIncrement.getRemovedCardPileIndexes() != null )
            {
                for( final Integer index : tableIncrement.getRemovedCardPileIndexes() )
                {
                    table.removeCardPile( table.getCardPile( index ) );
                }
            }

            if( tableIncrement.getAddedCardPileMementos() != null )
            {
                for( final Object cardPileMemento : tableIncrement.getAddedCardPileMementos() )
                {
                    final ICardPile cardPile = table.createCardPile();

                    try
                    {
                        cardPile.setMemento( cardPileMemento );
                    }
                    catch( final MementoException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_incrementTableState_setCardPileStateFailed, e );
                    }

                    table.addCardPile( cardPile );
                }
            }
        }
        finally
        {
            table.getLock().unlock();
        }
    }

    /**
     * Sets the state of the specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * @param tableMemento
     *        The memento containing the table state; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} or {@code tableMemento} is {@code null}.
     */
    public static void setTableState(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final Object tableMemento )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

        try
        {
            table.setMemento( tableMemento );
        }
        catch( final MementoException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.NetworkTableUtils_setTableState_failed, e );
        }
    }
}

/*
 * CardPileAsCardPileTest.java
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
 * Created on Jan 14, 2010 at 11:13:29 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.AbstractCardPileTestCase;
import org.gamegineer.table.core.CardPileListener;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.core.CardPile}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.core.ICardPile} interface.
 */
public final class CardPileAsCardPileTest
    extends AbstractCardPileTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileAsCardPileTest} class.
     */
    public CardPileAsCardPileTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#addComponentListener(org.gamegineer.table.core.IComponent, org.gamegineer.table.core.IComponentListener)
     */
    @Override
    protected void addComponentListener(
        final ICardPile component,
        final IComponentListener listener )
    {
        component.addCardPileListener( new CardPileListener()
        {
            @Override
            public void componentBoundsChanged(
                final ComponentEvent event )
            {
                listener.componentBoundsChanged( event );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITable)
     */
    @Override
    protected ICardPile createComponent(
        final ITable table )
    {
        return new CardPile( ((Table)table).getTableContext() );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createTable()
     */
    @Override
    protected ITable createTable()
    {
        return new Table();
    }
}

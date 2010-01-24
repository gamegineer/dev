/*
 * CardPileDesignUIs.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jan 23, 2010 at 9:10:23 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileDesigns;
import org.gamegineer.table.core.ICardPileDesign;

/**
 * A factory for creating various types of card pile design user interface types
 * suitable for testing.
 */
@ThreadSafe
public final class CardPileDesignUIs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileDesignUIs} class.
     */
    private CardPileDesignUIs()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card pile design user interface.
     * 
     * @param cardPileDesignUI
     *        The card pile design user interface to clone; must not be {@code
     *        null}.
     * 
     * @return A new card pile design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesignUI} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileDesignUI cloneCardPileDesignUI(
        /* @NonNull */
        final ICardPileDesignUI cardPileDesignUI )
    {
        assertArgumentNotNull( cardPileDesignUI, "cardPileDesignUI" ); //$NON-NLS-1$

        return CardPileDesignUIFactory.createCardPileDesignUI( cardPileDesignUI.getId(), cardPileDesignUI.getName(), cardPileDesignUI.getIcon() );
    }

    /**
     * Creates a new card pile design user interface for the specified card pile
     * design.
     * 
     * @param cardPileDesign
     *        The card pile design; must not be {@code null}.
     * 
     * @return A new card pile design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileDesignUI createCardPileDesignUI(
        /* @NonNull */
        final ICardPileDesign cardPileDesign )
    {
        assertArgumentNotNull( cardPileDesign, "cardPileDesign" ); //$NON-NLS-1$

        return CardPileDesignUIFactory.createCardPileDesignUI( cardPileDesign.getId(), cardPileDesign.getId().toString(), createDummy( Icon.class ) );
    }

    /**
     * Creates a new card pile design user interface with a unique identifier.
     * 
     * @return A new card pile design user interface; never {@code null}.
     */
    /* @NonNull */
    public static ICardPileDesignUI createUniqueCardPileDesignUI()
    {
        return createCardPileDesignUI( CardPileDesigns.createUniqueCardPileDesign() );
    }
}

/*
 * CardPileBaseDesignUIs.java
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
 * Created on Jan 23, 2010 at 9:10:23 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.easymock.EasyMock;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.ICardPileBaseDesign;

/**
 * A factory for creating various types of card pile base design user interface
 * types suitable for testing.
 */
@ThreadSafe
public final class CardPileBaseDesignUIs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignUIs} class.
     */
    private CardPileBaseDesignUIs()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card pile base design user interface.
     * 
     * @param cardPileBaseDesignUI
     *        The card pile base design user interface to clone; must not be
     *        {@code null}.
     * 
     * @return A new card pile base design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesignUI} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesignUI cloneCardPileBaseDesignUI(
        /* @NonNull */
        final ICardPileBaseDesignUI cardPileBaseDesignUI )
    {
        assertArgumentNotNull( cardPileBaseDesignUI, "cardPileBaseDesignUI" ); //$NON-NLS-1$

        return TableUIFactory.createCardPileBaseDesignUI( cardPileBaseDesignUI.getId(), cardPileBaseDesignUI.getName(), cardPileBaseDesignUI.getIcon() );
    }

    /**
     * Creates a new card pile base design user interface for the specified card
     * pile base design.
     * 
     * @param cardPileBaseDesign
     *        The card pile base design; must not be {@code null}.
     * 
     * @return A new card pile base design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardPileBaseDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesignUI createCardPileBaseDesignUI(
        /* @NonNull */
        final ICardPileBaseDesign cardPileBaseDesign )
    {
        assertArgumentNotNull( cardPileBaseDesign, "cardPileBaseDesign" ); //$NON-NLS-1$

        return TableUIFactory.createCardPileBaseDesignUI( cardPileBaseDesign.getId(), cardPileBaseDesign.getId().toString(), EasyMock.createMock( Icon.class ) );
    }

    /**
     * Creates a new card pile base design user interface with a unique
     * identifier.
     * 
     * @return A new card pile base design user interface; never {@code null}.
     */
    /* @NonNull */
    public static ICardPileBaseDesignUI createUniqueCardPileBaseDesignUI()
    {
        return createCardPileBaseDesignUI( CardPileBaseDesigns.createUniqueCardPileBaseDesign() );
    }
}

/*
 * CardDesignUIs.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Nov 20, 2009 at 11:55:33 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardDesigns;
import org.gamegineer.table.core.ICardDesign;

/**
 * A factory for creating various types of card design user interface types
 * suitable for testing.
 */
@ThreadSafe
public final class CardDesignUIs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardDesignUIs} class.
     */
    private CardDesignUIs()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card design user interface.
     * 
     * @param cardDesignUI
     *        The card design user interface to clone; must not be {@code null}.
     * 
     * @return A new card design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardDesignUI} is {@code null}.
     */
    /* @NonNull */
    public static ICardDesignUI cloneCardDesignUI(
        /* @NonNull */
        final ICardDesignUI cardDesignUI )
    {
        assertArgumentNotNull( cardDesignUI, "cardDesignUI" ); //$NON-NLS-1$

        return CardDesignUIFactory.createCardDesignUI( cardDesignUI.getId(), cardDesignUI.getName(), cardDesignUI.getIcon() );
    }

    /**
     * Creates a new card design user interface with a unique identifier.
     * 
     * @return A new card design user interface; never {@code null}.
     */
    /* @NonNull */
    public static ICardDesignUI createUniqueCardDesignUI()
    {
        final ICardDesign cardDesign = CardDesigns.createUniqueCardDesign();
        return CardDesignUIFactory.createCardDesignUI( cardDesign.getId(), cardDesign.getId().toString(), createDummy( Icon.class ) );
    }
}

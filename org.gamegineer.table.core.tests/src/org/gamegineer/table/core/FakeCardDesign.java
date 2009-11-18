/*
 * FakeCardDesign.java
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
 * Created on Nov 15, 2009 at 12:29:50 AM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import net.jcip.annotations.Immutable;

/**
 * Fake implementation of {@link org.gamegineer.table.core.ICardDesign}.
 */
@Immutable
public class FakeCardDesign
    implements ICardDesign
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card design identifier. */
    private final CardDesignId id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeCardDesign} class.
     * 
     * @param id
     *        The card design identifier; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    public FakeCardDesign(
        /* @NonNull */
        final CardDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardDesign#getId()
     */
    public CardDesignId getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.table.core.ICardDesign#getSize()
     */
    public Dimension getSize()
    {
        return new Dimension( 100, 100 );
    }
}

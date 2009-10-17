/*
 * AbstractAbstractCardChangeEventTestCase.java
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
 * Created on Oct 16, 2009 at 10:50:37 PM.
 */

package org.gamegineer.table.core;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.core.CardChangeEvent} class.
 * 
 * @param <T>
 *        The type of the card change event.
 */
public abstract class AbstractAbstractCardChangeEventTestCase<T extends CardChangeEvent>
    extends AbstractAbstractTableEventTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractCardChangeEventTestCase} class.
     */
    protected AbstractAbstractCardChangeEventTestCase()
    {
        super();
    }
}

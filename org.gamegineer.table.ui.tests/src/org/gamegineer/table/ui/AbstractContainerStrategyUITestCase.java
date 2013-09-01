/*
 * AbstractContainerStrategyUITestCase.java
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
 * Created on Sep 25, 2012 at 7:53:48 PM.
 */

package org.gamegineer.table.ui;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IContainerStrategyUI} interface.
 * 
 * @param <ContainerStrategyUIType>
 *        The type of the container strategy user interface.
 */
public abstract class AbstractContainerStrategyUITestCase<ContainerStrategyUIType extends IContainerStrategyUI>
    extends AbstractComponentStrategyUITestCase<ContainerStrategyUIType>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractContainerStrategyUITestCase} class.
     */
    protected AbstractContainerStrategyUITestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container strategy user interface under test in the fixture.
     * 
     * @return The container strategy user interface under test in the fixture;
     *         never {@code null}.
     */
    /* @NonNull */
    protected final ContainerStrategyUIType getContainerStrategyUI()
    {
        return getComponentStrategyUI();
    }
}

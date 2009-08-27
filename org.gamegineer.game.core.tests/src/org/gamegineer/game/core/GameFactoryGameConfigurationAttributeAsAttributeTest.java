/*
 * GameFactoryGameConfigurationAttributeAsAttributeTest.java
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
 * Created on Jul 18, 2008 at 12:00:18 AM.
 */

package org.gamegineer.game.core;

import java.util.Collections;
import java.util.List;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.common.core.services.component.util.attribute.IAttribute;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.IGameConfiguration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.GameFactory.GameConfigurationAttribute} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}
 * interface.
 */
public final class GameFactoryGameConfigurationAttributeAsAttributeTest
    extends AbstractAttributeTestCase<IGameConfiguration>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GameFactoryGameConfigurationAttributeAsAttributeTest} class.
     */
    public GameFactoryGameConfigurationAttributeAsAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#createAttribute()
     */
    @Override
    protected IAttribute<IGameConfiguration> createAttribute()
    {
        return GameFactory.GameConfigurationAttribute.INSTANCE;
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#getLegalValues()
     */
    @Override
    protected List<IGameConfiguration> getLegalValues()
    {
        return Collections.singletonList( Configurations.createUniqueGameConfiguration() );
    }
}

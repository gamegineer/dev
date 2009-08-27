/*
 * GameFactory.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jul 16, 2008 at 10:33:27 PM.
 */

package org.gamegineer.game.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;
import org.gamegineer.game.core.GameException;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.GameFactory.GameConfigurationAttribute;
import org.gamegineer.game.core.config.IGameConfiguration;

/**
 * A component factory for creating instances of {@code Game}.
 */
final class GameFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameFactory} class.
     */
    GameFactory()
    {
        SupportedClassNamesAttribute.INSTANCE.setValue( this, IGame.class.getName() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    @Override
    public Object createComponent(
        final IComponentCreationContext context )
        throws ComponentCreationException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IAttributeAccessor accessor = new ComponentCreationContextAttributeAccessor( context );
        final String className = ClassNameAttribute.INSTANCE.getValue( accessor );
        final IGameConfiguration gameConfig = GameConfigurationAttribute.INSTANCE.getValue( accessor );

        if( !IGame.class.getName().equals( className ) )
        {
            throw new IllegalArgumentException( Messages.GameFactory_createComponent_unsupportedType( className ) );
        }

        try
        {
            return Game.createGame( gameConfig );
        }
        catch( final GameException e )
        {
            throw new ComponentCreationException( e );
        }
    }
}

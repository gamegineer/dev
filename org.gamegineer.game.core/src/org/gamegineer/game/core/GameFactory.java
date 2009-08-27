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
 * Created on Jul 9, 2008 at 9:59:19 PM.
 */

package org.gamegineer.game.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;
import org.gamegineer.game.core.config.IGameConfiguration;

/**
 * A factory for creating games.
 * 
 * <p>
 * In order to be accessible to this factory, a game implementation must provide
 * an OSGi service that implements
 * {@link org.gamegineer.common.core.services.component.IComponentFactory} which
 * publishes the
 * {@link org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute}
 * attribute with a value that contains the element
 * {@code org.gamegineer.game.core.IGame}. The component creation context
 * passed to the factory will publish the
 * {@link GameFactory.GameConfigurationAttribute} attribute whose value contains
 * an instance of {@code org.gamegineer.game.core.config.IGameConfiguration}.
 * </p>
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class GameFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameFactory} class.
     */
    private GameFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game with a clean state.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}.
     * 
     * @return A new game with a clean state; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameConfig} is {@code null}.
     * @throws org.gamegineer.game.core.GameConfigurationException
     *         If a game that satisfies the requested configuration could not be
     *         created or {@code gameConfig} represents an illegal
     *         configuration.
     */
    /* @NonNull */
    public static IGame createGame(
        /* @NonNull */
        final IGameConfiguration gameConfig )
        throws GameConfigurationException
    {
        assertArgumentNotNull( gameConfig, "gameConfig" ); //$NON-NLS-1$

        final String className = IGame.class.getName();
        final IComponentSpecification specification = new ClassNameComponentSpecification( className );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, className );
        GameConfigurationAttribute.INSTANCE.setValue( builder, gameConfig );

        try
        {
            return (IGame)Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
        }
        catch( final ComponentException e )
        {
            throw new GameConfigurationException( Messages.GameFactory_createGame_unsupportedConfiguration, e );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * An attribute used to specify the game configuration when a client
     * requests a game instance to be created.
     */
    public static final class GameConfigurationAttribute
        extends AbstractAttribute<IGameConfiguration>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The singleton attribute instance. */
        public static final GameConfigurationAttribute INSTANCE = new GameConfigurationAttribute();

        /** The attribute name. */
        private static final String NAME = "org.gamegineer.game.core.GameFactory.gameConfiguration"; //$NON-NLS-1$


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code GameConfigurationAttribute}
         * class.
         */
        private GameConfigurationAttribute()
        {
            super( NAME, IGameConfiguration.class );
        }
    }
}

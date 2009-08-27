/*
 * GameServerFactory.java
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
 * Created on Dec 13, 2008 at 10:50:47 PM.
 */

package org.gamegineer.server.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;
import org.gamegineer.server.core.GameServerConfigurationException;
import org.gamegineer.server.core.IGameServer;
import org.gamegineer.server.core.GameServerFactory.GameServerConfigurationAttribute;
import org.gamegineer.server.core.config.IGameServerConfiguration;

/**
 * A component factory for creating instances of {@code GameServer}.
 */
final class GameServerFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerFactory} class.
     */
    GameServerFactory()
    {
        SupportedClassNamesAttribute.INSTANCE.setValue( this, IGameServer.class.getName() );
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
        final IGameServerConfiguration gameServerConfig = GameServerConfigurationAttribute.INSTANCE.getValue( accessor );

        if( !IGameServer.class.getName().equals( className ) )
        {
            throw new IllegalArgumentException( Messages.GameServerFactory_createComponent_unsupportedType( className ) );
        }

        try
        {
            return GameServer.createGameServer( gameServerConfig );
        }
        catch( final GameServerConfigurationException e )
        {
            throw new ComponentCreationException( e );
        }
    }
}

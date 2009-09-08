/*
 * CommandletFactory.java
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
 * Created on Oct 18, 2008 at 10:19:28 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.commandlets.connection.ConnectServerCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.connection.DisconnectServerCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.connection.GetServerCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.core.HelpCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.core.QuitCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.farm.GetLocalServersCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.farm.StartLocalServerCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.farm.StopLocalServerCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.server.CreateGameCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.server.GetGameCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.server.GetGameSystemsCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.server.GetGamesCommandlet;
import org.gamegineer.client.ui.console.commandlet.attributes.SupportedCommandletClassNamesAttribute;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;

/**
 * A component factory for creating instances of the base console commandlets.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class CommandletFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of commandlet class names which this factory can create. */
    private static final Collection<String> commandletClassNames_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CommandletFactory} class.
     */
    static
    {
        final List<String> classNames = new ArrayList<String>();

        // Core commandlets
        classNames.add( HelpCommandlet.class.getName() );
        classNames.add( QuitCommandlet.class.getName() );

        // Farm commandlets
        classNames.add( GetLocalServersCommandlet.class.getName() );
        classNames.add( StartLocalServerCommandlet.class.getName() );
        classNames.add( StopLocalServerCommandlet.class.getName() );

        // Connection commandlets
        classNames.add( ConnectServerCommandlet.class.getName() );
        classNames.add( DisconnectServerCommandlet.class.getName() );
        classNames.add( GetServerCommandlet.class.getName() );

        // Server commandlets
        classNames.add( CreateGameCommandlet.class.getName() );
        classNames.add( GetGameCommandlet.class.getName() );
        classNames.add( GetGameSystemsCommandlet.class.getName() );
        classNames.add( GetGamesCommandlet.class.getName() );

        commandletClassNames_ = Collections.unmodifiableList( classNames );
    }

    /**
     * Initializes a new instance of the {@code CommandletFactory} class.
     */
    CommandletFactory()
    {
        SupportedClassNamesAttribute.INSTANCE.setValue( this, commandletClassNames_ );
        SupportedCommandletClassNamesAttribute.INSTANCE.setValue( this, commandletClassNames_ );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    public Object createComponent(
        final IComponentCreationContext context )
        throws ComponentCreationException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IAttributeAccessor accessor = new ComponentCreationContextAttributeAccessor( context );
        final String className = ClassNameAttribute.INSTANCE.getValue( accessor );

        if( !commandletClassNames_.contains( className ) )
        {
            throw new IllegalArgumentException( Messages.CommandletFactory_createComponent_unsupportedType( className ) );
        }

        try
        {
            return Class.forName( className ).newInstance();
        }
        catch( final ClassNotFoundException e )
        {
            throw new ComponentCreationException( Messages.CommandletFactory_createComponent_failed( className ), e );
        }
        catch( final IllegalAccessException e )
        {
            throw new ComponentCreationException( Messages.CommandletFactory_createComponent_failed( className ), e );
        }
        catch( final InstantiationException e )
        {
            throw new ComponentCreationException( Messages.CommandletFactory_createComponent_failed( className ), e );
        }
    }
}

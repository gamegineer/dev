/*
 * HelpCommandlet.java
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
 * Created on Oct 24, 2008 at 11:19:01 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.internal.ui.console.Loggers;
import org.gamegineer.client.internal.ui.console.commandlet.CommandletAliasManager;
import org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandletContext;
import org.gamegineer.client.ui.console.commandlet.ICommandletHelp;
import org.gamegineer.client.ui.console.commandlet.attributes.SupportedCommandletClassNamesAttribute;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;
import org.gamegineer.common.core.services.component.util.attribute.ComponentFactoryAttributeAccessor;

/**
 * A commandlet that displays help about commandlets.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class HelpCommandlet
    implements ICommandlet, ICommandletHelp
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpCommandlet} class.
     */
    public HelpCommandlet()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Displays the help detailed description for the specified commandlet.
     * 
     * @param writer
     *        The console writer; must not be {@code null}.
     * @param name
     *        The commandlet name; must not be {@code null}.
     */
    private static void displayDetailedDescription(
        /* @NonNull */
        final PrintWriter writer,
        /* @NonNull */
        final String name )
    {
        assert writer != null;
        assert name != null;

        final Set<String> classNames = CommandletAliasManager.getCommandletClassNames( name );
        if( classNames.isEmpty() )
        {
            writer.println( Messages.HelpCommandlet_output_unknownCommandlet( name ) );
            return;
        }
        else if( classNames.size() > 1 )
        {
            writer.println( Messages.HelpCommandlet_output_ambiguousCommandlet( name, classNames ) );
            return;
        }

        final String className = classNames.iterator().next();
        final ICommandletHelp help = getCommandletHelp( className );
        final String description = (help != null) ? help.getDetailedDescription() : Messages.HelpCommandlet_output_noHelpDetailedDescriptionAvailable;
        writer.println( description );
    }

    /**
     * Displays the help synopsis for all registered commandlets.
     * 
     * @param writer
     *        The console writer; must not be {@code null}.
     */
    private static void displaySynopsis(
        /* @NonNull */
        final PrintWriter writer )
    {
        assert writer != null;

        for( final String className : getCommandletClassNames() )
        {
            writer.println( className );

            final ICommandletHelp help = getCommandletHelp( className );
            final String synopsis = (help != null) ? help.getSynopsis() : Messages.HelpCommandlet_output_noHelpSynopsisAvailable;
            writer.format( "\t%1$s%n", synopsis ); //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandlet#execute(org.gamegineer.client.ui.console.commandlet.ICommandletContext)
     */
    public void execute(
        final ICommandletContext context )
        throws CommandletException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final int argCount = context.getArguments().size();
        if( argCount > 1 )
        {
            throw new CommandletException( CommandletMessages.Commandlet_execute_tooManyArgs, CommandletMessages.Commandlet_output_tooManyArgs );
        }

        if( argCount == 0 )
        {
            displaySynopsis( context.getConsole().getDisplay().getWriter() );
        }
        else if( argCount == 1 )
        {
            displayDetailedDescription( context.getConsole().getDisplay().getWriter(), context.getArguments().get( 0 ) );
        }
    }

    /**
     * Gets a sorted collection of registered commandlet class names.
     * 
     * @return A sorted collection of registered commandlet class names; never
     *         {@code null}.
     */
    /* @NonNull */
    private static Set<String> getCommandletClassNames()
    {
        final Set<String> classNames = new TreeSet<String>();
        for( final IComponentFactory factory : Platform.getComponentService().getComponentFactories() )
        {
            final Iterable<String> supportedClassNames = SupportedCommandletClassNamesAttribute.INSTANCE.tryGetValue( new ComponentFactoryAttributeAccessor( factory ) );
            if( supportedClassNames != null )
            {
                for( final String className : supportedClassNames )
                {
                    classNames.add( className );
                }
            }
        }
        return classNames;
    }

    /**
     * Gets the commandlet help for the commandlet with the specified class
     * name.
     * 
     * @param className
     *        The commandlet class name; must not be {@code null}.
     * 
     * @return The commandlet help for the commandlet with the specified class
     *         name or {@code null} if no commandlet help could be obtained.
     */
    /* @Nullable */
    private static ICommandletHelp getCommandletHelp(
        /* @NonNull */
        final String className )
    {
        assert className != null;

        final IComponentSpecification specification = new ClassNameComponentSpecification( className );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, className );
        try
        {
            final ICommandlet commandlet = (ICommandlet)Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
            if( commandlet instanceof ICommandletHelp )
            {
                return (ICommandletHelp)commandlet;
            }
        }
        catch( final ComponentException e )
        {
            Loggers.DEFAULT.log( Level.WARNING, Messages.HelpCommandlet_getCommandletHelp_componentError( className ), e );
        }
        catch( final ClassCastException e )
        {
            Loggers.DEFAULT.log( Level.WARNING, Messages.HelpCommandlet_getCommandletHelp_notCommandlet( className ), e );
        }

        return null;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getDetailedDescription()
     */
    public String getDetailedDescription()
    {
        return Messages.HelpCommandlet_help_detailedDescription;
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.ICommandletHelp#getSynopsis()
     */
    public String getSynopsis()
    {
        return Messages.HelpCommandlet_help_synopsis;
    }
}

/*
 * CommandletParser.java
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
 * Created on Oct 23, 2008 at 11:18:37 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.internal.ui.console.Loggers;
import org.gamegineer.client.internal.ui.console.commandlets.core.NullCommandlet;
import org.gamegineer.client.internal.ui.console.commandlets.core.PrintLineCommandlet;
import org.gamegineer.client.ui.console.commandlet.ICommandlet;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.UnsupportedComponentSpecificationException;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;

/**
 * A commandlet parser.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class CommandletParser
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletParser} class.
     */
    public CommandletParser()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a commandlet executor from the specified commandlet tokens.
     * 
     * @param tokens
     *        The commandlet tokens; must not be {@code null}.
     * 
     * @return A new commandlet executor; never {@code null}.
     */
    /* @NonNull */
    private CommandletExecutor createCommandletExecutor(
        /* @NonNull */
        final CommandletTokenCollection tokens )
    {
        assert tokens != null;

        final IComponentSpecification specification = new ClassNameComponentSpecification( tokens.getName() );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, tokens.getName() );
        try
        {
            final ICommandlet commandlet = (ICommandlet)Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
            return new CommandletExecutor( commandlet, tokens.getArguments() );
        }
        catch( final UnsupportedComponentSpecificationException e )
        {
            return createPrintLineCommandletExecutor( Messages.CommandletParser_output_unknownCommandlet( tokens.getName() ) );
        }
        catch( final ComponentException e )
        {
            Loggers.DEFAULT.log( Level.WARNING, Messages.CommandletParser_createCommandletExecutor_componentError( tokens.getName() ), e );
            return createPrintLineCommandletExecutor( Messages.CommandletParser_output_createCommandletError( tokens.getName() ) );
        }
        catch( final ClassCastException e )
        {
            return createPrintLineCommandletExecutor( Messages.CommandletParser_output_notCommandlet( tokens.getName() ) );
        }
    }

    /**
     * Creates a commandlet executor that does nothing.
     * 
     * @return A commandlet executor that does nothing; never {@code null}.
     */
    /* @NonNull */
    private static CommandletExecutor createNullCommandletExecutor()
    {
        return new CommandletExecutor( new NullCommandlet(), Collections.<String>emptyList() );
    }

    /**
     * Creates a commandlet executor to print the specified line of text to the
     * console.
     * 
     * @param line
     *        The line of text to print to the console; must not be {@code null}.
     * 
     * @return A commandlet executor to print the specified line of text to the
     *         console; never {@code null};
     */
    /* @NonNull */
    private static CommandletExecutor createPrintLineCommandletExecutor(
        /* @NonNull */
        final String line )
    {
        assert line != null;

        return new CommandletExecutor( new PrintLineCommandlet(), Collections.singletonList( line ) );
    }

    /**
     * Parses the specified line and returns an appropriate commandlet executor.
     * 
     * @param line
     *        The line to parse; must not be {@code null}.
     * 
     * @return A new commandlet executor; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code line} is {@code null}.
     */
    /* @NonNull */
    public CommandletExecutor parse(
        /* @NonNull */
        final String line )
    {
        assertArgumentNotNull( line, "line" ); //$NON-NLS-1$

        if( line.isEmpty() )
        {
            return createNullCommandletExecutor();
        }

        final CommandletTokenCollection tokens;
        try
        {
            tokens = CommandletLexer.tokenize( line );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.DEFAULT.log( Level.WARNING, Messages.CommandletParser_parse_parseError, e );
            return createPrintLineCommandletExecutor( Messages.CommandletParser_output_malformedCommandlet );
        }

        return parse( tokens );
    }

    /**
     * Parses the specified tokens and returns an appropriate commandlet
     * executor.
     * 
     * @param tokens
     *        A collection of commandlet tokens; must not be {@code null}.
     * 
     * @return A new commandlet executor; never {@code null}.
     */
    /* @NonNull */
    private CommandletExecutor parse(
        /* @NonNull */
        final CommandletTokenCollection tokens )
    {
        assert tokens != null;

        final Set<String> classNames = CommandletAliasManager.getCommandletClassNames( tokens.getName() );
        if( classNames.isEmpty() )
        {
            return createPrintLineCommandletExecutor( Messages.CommandletParser_output_unknownCommandlet( tokens.getName() ) );
        }
        else if( classNames.size() > 1 )
        {
            return createPrintLineCommandletExecutor( Messages.CommandletParser_output_ambiguousCommandlet( tokens.getName(), classNames ) );
        }

        final String canonicalName = classNames.iterator().next();
        return createCommandletExecutor( new CommandletTokenCollection( canonicalName, tokens.getArguments() ) );
    }
}

/*
 * CommandletAliasManager.java
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
 * Created on Oct 22, 2008 at 10:23:39 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.ui.console.commandlet.attributes.SupportedCommandletClassNamesAttribute;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.core.services.component.util.attribute.ComponentFactoryAttributeAccessor;

/**
 * A manager of commandlet aliases.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class CommandletAliasManager
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A regular expression to match commandlet class names. */
    private static final Pattern COMMANDLET_CLASS_NAME_PATTERN = Pattern.compile( "^(?:.*\\.)?(\\w+)Commandlet$" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandletAliasManager} class.
     */
    private CommandletAliasManager()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets all commandlet class names that match the specified alias.
     * 
     * @param alias
     *        The commandlet alias; must not be {@code null}.
     * 
     * @return A set of all commandlet class names that match the specified
     *         alias; never {@code null}. The set will be empty if no
     *         commandlet is mapped to the specified alias.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code alias} is {@code null}.
     */
    /* @NonNull */
    public static Set<String> getCommandletClassNames(
        /* @NonNull */
        final String alias )
    {
        assertArgumentNotNull( alias, "alias" ); //$NON-NLS-1$

        final Set<String> classNames = new HashSet<String>();
        for( final IComponentFactory factory : Platform.getComponentService().getComponentFactories() )
        {
            final Iterable<String> supportedClassNames = SupportedCommandletClassNamesAttribute.INSTANCE.tryGetValue( new ComponentFactoryAttributeAccessor( factory ) );
            if( supportedClassNames != null )
            {
                for( final String className : supportedClassNames )
                {
                    if( isAliasForClassName( alias, className ) )
                    {
                        classNames.add( className );
                    }
                }
            }
        }

        return classNames;
    }

    /**
     * Indicates the specified commandlet alias is associated with the specified
     * class name.
     * 
     * @param alias
     *        The commandlet alias; must not be {@code null}.
     * @param className
     *        The commandlet class name; must not be {@code null}.
     * 
     * @return {@code true} if the specified commandlet alias is associated with
     *         the specified class name; otherwise {@code false}.
     */
    private static boolean isAliasForClassName(
        /* @NonNull */
        final String alias,
        /* @NonNull */
        final String className )
    {
        assert alias != null;
        assert className != null;

        if( alias.equals( className ) )
        {
            return true;
        }

        final Matcher matcher = COMMANDLET_CLASS_NAME_PATTERN.matcher( className );
        if( matcher.matches() )
        {
            if( alias.compareToIgnoreCase( matcher.group( 1 ) ) == 0 )
            {
                return true;
            }
        }

        return false;
    }
}

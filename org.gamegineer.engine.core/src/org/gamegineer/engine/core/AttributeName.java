/*
 * AttributeName.java
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
 * Created on Jun 25, 2008 at 10:40:35 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IState.Scope;

/**
 * The qualified name of an engine state attribute.
 * 
 * <p>
 * The qualified attribute name consists of two parts. The first is the engine
 * state scope in which the attribute exists. The second is the local name of
 * the attribute, which is unique within its enclosing scope.
 * </p>
 * 
 * <p>
 * An attribute name is suitable to be used as a key in a hash container.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class AttributeName
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The regex that specifies the format of an attribute name string. */
    private static final Pattern PATTERN = Pattern.compile( "^\\{(.+)\\}(.+)$" ); //$NON-NLS-1$

    /** The local name of the attribute. */
    private final String localName_;

    /** The attribute scope. */
    private final Scope scope_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeName} class.
     * 
     * @param scope
     *        The attribute scope; must not be {@code null}.
     * @param localName
     *        The local name of the attribute; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the local name is an empty string.
     * @throws java.lang.NullPointerException
     *         If {@code scope} or {@code localName} is {@code null}.
     */
    public AttributeName(
        /* @NonNull */
        final Scope scope,
        /* @NonNull */
        final String localName )
    {
        assertArgumentNotNull( scope, "scope" ); //$NON-NLS-1$
        assertArgumentNotNull( localName, "localName" ); //$NON-NLS-1$
        assertArgumentLegal( !localName.isEmpty(), "localName", Messages.AttributeName_localName_empty ); //$NON-NLS-1$

        scope_ = scope;
        localName_ = localName;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof AttributeName) )
        {
            return false;
        }

        final AttributeName other = (AttributeName)obj;
        return (scope_ == other.scope_) && localName_.equals( other.localName_ );
    }

    /**
     * Creates an attribute name from the specified string representation
     * described in the {@link #toString()} method.
     * 
     * @param name
     *        The string representation of the attribute name; must not be
     *        {@code null}.
     * 
     * @return An attribute name with the specified value; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code name} does not conform to the string representation
     *         described in {@link #toString()}.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public static AttributeName fromString(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        final Matcher matcher = PATTERN.matcher( name );
        assertArgumentLegal( matcher.matches(), "name", Messages.AttributeName_fromString_illegalFormat ); //$NON-NLS-1$

        final Scope scope = Scope.valueOf( Scope.class, matcher.group( 1 ) );
        final String localName = matcher.group( 2 );
        return new AttributeName( scope, localName );
    }

    /**
     * Gets the local name of the attribute.
     * 
     * @return The local name of the attribute; never {@code null}.
     */
    /* @NonNull */
    public String getLocalName()
    {
        return localName_;
    }

    /**
     * Gets the scope in which the attribute exists.
     * 
     * @return The scope in which the attribute exists; never {@code null}.
     */
    /* @NonNull */
    public Scope getScope()
    {
        return scope_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = result * 31 + scope_.hashCode();
        result = result * 31 + localName_.hashCode();
        return result;
    }

    /**
     * Gets a string representation of this attribute name.
     * 
     * <p>
     * The attribute name string representation is of the form:
     * </p>
     * 
     * <p>
     * {<i>{@literal <scope-name>}</i>}<i>{@literal <local-name>}</i>
     * </p>
     * 
     * <p>
     * where <i>scope-name</i> is the value of {@code Scope.toString}.
     * </p>
     * 
     * @return A string representation of this attribute name; never {@code
     *         null}.
     */
    @Override
    public String toString()
    {
        return String.format( "{%1$s}%2$s", scope_, localName_ ); //$NON-NLS-1$
    }
}

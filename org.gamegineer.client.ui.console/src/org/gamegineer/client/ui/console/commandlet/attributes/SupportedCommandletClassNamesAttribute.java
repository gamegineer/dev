/*
 * SupportedCommandletClassNamesAttribute.java
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
 * Created on Oct 18, 2008 at 9:22:55 PM.
 */

package org.gamegineer.client.ui.console.commandlet.attributes;

import java.util.Collections;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator;

/**
 * An attribute used to specify a collection of commandlet class names whose
 * creation is supported by a factory.
 */
public final class SupportedCommandletClassNamesAttribute
    extends AbstractAttribute<Iterable<String>>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton attribute instance. */
    public static final SupportedCommandletClassNamesAttribute INSTANCE = new SupportedCommandletClassNamesAttribute();

    /** The attribute name. */
    private static final String NAME = "org.gamegineer.client.ui.console.commandlet.attributes.supportedCommandletClassNames"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SupportedCommandletClassNamesAttribute} class.
     */
    private SupportedCommandletClassNamesAttribute()
    {
        super( NAME, Iterable.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute#isLegalValue(java.lang.Object)
     */
    @Override
    protected boolean isLegalValue(
        final Iterable<String> value )
    {
        if( !super.isLegalValue( value ) )
        {
            return false;
        }

        for( final String className : value )
        {
            if( className == null )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Sets the value of the attribute to the specified class name.
     * 
     * @param mutator
     *        The mutator used to write the attribute to the object; must not be
     *        {@code null}.
     * @param className
     *        The sole supported class name; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code className} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code mutator} is {@code null}.
     */
    public void setValue(
        /* @NonNull */
        final IAttributeMutator mutator,
        /* @NonNull */
        final String className )
    {
        setValue( mutator, Collections.singletonList( className ) );
    }
}

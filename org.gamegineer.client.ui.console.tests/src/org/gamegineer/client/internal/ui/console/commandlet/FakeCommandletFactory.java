/*
 * FakeCommandletFactory.java
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
 * Created on Oct 24, 2008 at 10:14:23 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashSet;
import java.util.Set;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.ui.console.commandlet.attributes.SupportedCommandletClassNamesAttribute;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;

/**
 * A fake commandlet component factory.
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
public final class FakeCommandletFactory
    extends AbstractComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of commandlet class names provided by this factory. */
    private final Set<String> m_commandletClassNames;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeCommandletFactory} class.
     * 
     * @param commandletClassNames
     *        The collection of commandlet class names provided by this factory;
     *        must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code commandletClassNames} is {@code null}.
     */
    public FakeCommandletFactory(
        /* @NonNull */
        final Set<String> commandletClassNames )
    {
        assertArgumentNotNull( commandletClassNames, "commandletClassNames" ); //$NON-NLS-1$

        m_commandletClassNames = new HashSet<String>( commandletClassNames );
        SupportedClassNamesAttribute.INSTANCE.setValue( this, m_commandletClassNames );
        SupportedCommandletClassNamesAttribute.INSTANCE.setValue( this, m_commandletClassNames );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    public Object createComponent(
        @SuppressWarnings( "unused" )
        final IComponentCreationContext context )
    {
        throw new UnsupportedOperationException();
    }
}

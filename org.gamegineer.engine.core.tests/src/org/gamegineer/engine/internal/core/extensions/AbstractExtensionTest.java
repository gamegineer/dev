/*
 * AbstractExtensionTest.java
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
 * Created on Apr 6, 2009 at 10:25:19 PM.
 */

package org.gamegineer.engine.internal.core.extensions;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.contexts.extension.FakeExtensionContext;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.AbstractExtension}
 * class.
 */
public final class AbstractExtensionTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractExtensionTest} class.
     */
    public AbstractExtensionTest()
    {
        super();
    }

    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * extension type.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Type_Null()
    {
        new AbstractExtension( null )
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code getExtensionContext} method throws an exception when
     * passed an illegal context that does not contain an extension context.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetExtensionContext_Context_Illegal()
    {
        AbstractExtension.getExtensionContext( new FakeEngineContext() );
    }

    /**
     * Ensures the {@code getExtensionContext} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testGetExtensionContext_Context_Null()
    {
        AbstractExtension.getExtensionContext( null );
    }

    /**
     * Ensures the {@code getExtensionContext} method does not return
     * {@code null}.
     */
    @Test
    public void testGetExtensionContext_ReturnValue_NonNull()
    {
        final IExtensionContext extensionContext = new FakeExtensionContext();
        final IEngineContext engineContext = new FakeEngineContext()
        {
            @Override
            public <T> T getContext(
                final Class<T> type )
            {
                if( type == IExtensionContext.class )
                {
                    return type.cast( extensionContext );
                }

                return super.getContext( type );
            }
        };

        assertNotNull( AbstractExtension.getExtensionContext( engineContext ) );
    }
}

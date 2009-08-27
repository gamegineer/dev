/*
 * MockExtension.java
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
 * Created on Apr 17, 2008 at 10:13:33 PM.
 */

package org.gamegineer.engine.core;

/**
 * Mock implementation of {@link org.gamegineer.engine.core.IExtension}.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class MockExtension
    implements IExtension
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates the extension has been started. */
    private boolean m_isStarted;

    /** The extension type. */
    private final Class<?> m_type;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockExtension} class.
     * 
     * @param type
     *        The extension type; must not be {@code null}.
     */
    public MockExtension(
        /* @NonNull */
        final Class<?> type )
    {
        assert type != null;

        m_isStarted = false;
        m_type = type;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.IExtension#getExtensionType()
     */
    public Class<?> getExtensionType()
    {
        return m_type;
    }

    /**
     * Indicates the extension has been started.
     * 
     * @return {@code true} if the extension has been started; otherwise
     *         {@code false} if the extension is stopped.
     */
    public boolean isStarted()
    {
        return m_isStarted;
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#start(org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public void start(
        final IEngineContext context )
        throws EngineException
    {
        assert context != null;

        m_isStarted = true;
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#stop(org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public void stop(
        final IEngineContext context )
        throws EngineException
    {
        assert context != null;

        m_isStarted = false;
    }
}

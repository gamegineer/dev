/*
 * ConsoleAdvisor.java
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
 * Created on Oct 10, 2008 at 11:39:42 PM.
 */

package org.gamegineer.client.ui.console;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.osgi.framework.Version;

/**
 * Implementation of {@link org.gamegineer.client.ui.console.IConsoleAdvisor}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@Immutable
public final class ConsoleAdvisor
    implements IConsoleAdvisor
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The application argument list. */
    private final List<String> m_appArgList;

    /** The application version. */
    private final Version m_appVersion;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConsoleAdvisor} class with an
     * empty application argument list and empty application version.
     */
    public ConsoleAdvisor()
    {
        this( Collections.<String>emptyList(), Version.emptyVersion );
    }

    /**
     * Initializes a new instance of the {@code ConsoleAdvisor} class from the
     * specified application argument list and application version.
     * 
     * @param appArgList
     *        The application argument list; must not be {@code null}.
     * @param appVersion
     *        The application version; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code appArgList} or {@code appVersion} is {@code null}.
     */
    public ConsoleAdvisor(
        /* @NonNull */
        final List<String> appArgList,
        /* @NonNull */
        final Version appVersion )
    {
        assertArgumentNotNull( appArgList, "appArgList" ); //$NON-NLS-1$
        assertArgumentNotNull( appVersion, "appVersion" ); //$NON-NLS-1$

        m_appArgList = Collections.unmodifiableList( new ArrayList<String>( appArgList ) );
        m_appVersion = appVersion;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.IConsoleAdvisor#getApplicationArguments()
     */
    public List<String> getApplicationArguments()
    {
        return m_appArgList;
    }

    /*
     * @see org.gamegineer.client.ui.console.IConsoleAdvisor#getApplicationVersion()
     */
    public Version getApplicationVersion()
    {
        return m_appVersion;
    }
}

/*
 * FarmAttributes.java
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
 * Created on May 18, 2009 at 9:59:13 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.farm;

import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.ui.console.commandlet.util.attribute.Attribute;
import org.gamegineer.server.core.IGameServer;

/**
 * The statelet attributes exposed by commandlets in the package.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class FarmAttributes
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game servers collection attribute. */
    public static final Attribute<Map<String, IGameServer>> GAME_SERVERS = new Attribute<Map<String, IGameServer>>( "org.gamegineer.client.internal.ui.console.commandlets.farm.gameServers" ) //$NON-NLS-1$
    {
        @Override
        protected Map<String, IGameServer> getDefaultValue()
        {
            return new HashMap<String, IGameServer>();
        }
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FarmAttributes} class.
     */
    private FarmAttributes()
    {
        super();
    }
}

/*
 * GameSystemUiRegistry.java
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
 * Created on Mar 2, 2009 at 11:32:01 PM.
 */

package org.gamegineer.game.internal.ui.services.systemuiregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.game.internal.ui.Activator;
import org.gamegineer.game.internal.ui.Loggers;
import org.gamegineer.game.internal.ui.Services;
import org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * Implementation of
 * {@link org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class GameSystemUiRegistry
    implements IGameSystemUiRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The extension point attribute specifying the game system user interface
     * class.
     */
    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    /** The extension point attribute specifying the game system identifier. */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The collection of game system user interfaces directly managed by this
     * object.
     */
    private final ConcurrentMap<String, IGameSystemUi> m_gameSystemUis;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiRegistry} class.
     */
    public GameSystemUiRegistry()
    {
        m_gameSystemUis = new ConcurrentHashMap<String, IGameSystemUi>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a collection of all foreign game system user interfaces not directly
     * managed by this object.
     * 
     * @return A collection of all foreign game system user interfaces not
     *         directly managed by this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IGameSystemUi> getForeignGameSystemUis()
    {
        final Collection<IGameSystemUi> gameSystemUis = new ArrayList<IGameSystemUi>();
        gameSystemUis.addAll( getForeignGameSystemUisFromServiceRegistry() );
        gameSystemUis.addAll( getForeignGameSystemUisFromExtensionRegistry() );
        return gameSystemUis;
    }

    /**
     * Gets a collection of all foreign game system user interfaces declared in
     * the extension registry.
     * 
     * @return A collection of all foreign game system user interfaces declared
     *         in the extension registry; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IGameSystemUi> getForeignGameSystemUisFromExtensionRegistry()
    {
        // TODO: Create proxies instead so we can lazy load the hosting game
        // system UI plug-ins.  (See section 17.3.2 of Clayberg-06.)

        final Collection<IGameSystemUi> gameSystemUis = new ArrayList<IGameSystemUi>();
        for( final IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_GAME_SYSTEM_UIS ) )
        {
            try
            {
                gameSystemUis.add( (IGameSystemUi)element.createExecutableExtension( ATTR_CLASS ) );
            }
            catch( final CoreException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.GameSystemUiRegistry_getForeignGameSystemUisFromExtensionRegistry_creationError( element.getAttribute( ATTR_ID ) ), e );
            }
        }
        return gameSystemUis;
    }

    /**
     * Gets a collection of all foreign game system user interfaces declared in
     * the service registry.
     * 
     * @return A collection of all foreign game system user interfaces declared
     *         in the service registry; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IGameSystemUi> getForeignGameSystemUisFromServiceRegistry()
    {
        return Services.getDefault().getGameSystemUis();
    }

    /*
     * @see org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry#getGameSystemUi(java.lang.String)
     */
    public IGameSystemUi getGameSystemUi(
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getGameSystemUiMap().get( id );
    }

    /**
     * Gets a map view of all game system user interfaces known by this object.
     * 
     * @return A map view of all game system user interfaces known by this
     *         object; never {@code null}.
     */
    /* @NonNull */
    private Map<String, IGameSystemUi> getGameSystemUiMap()
    {
        final Map<String, IGameSystemUi> gameSystemUis = new HashMap<String, IGameSystemUi>( m_gameSystemUis );
        for( final IGameSystemUi gameSystemUi : getForeignGameSystemUis() )
        {
            if( gameSystemUis.containsKey( gameSystemUi.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.GameSystemUiRegistry_getGameSystemUiMap_duplicateId( gameSystemUi.getId() ) );
            }
            else
            {
                gameSystemUis.put( gameSystemUi.getId(), gameSystemUi );
            }
        }
        return gameSystemUis;
    }

    /*
     * @see org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry#getGameSystemUis()
     */
    public Collection<IGameSystemUi> getGameSystemUis()
    {
        return new ArrayList<IGameSystemUi>( getGameSystemUiMap().values() );
    }

    /*
     * @see org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry#registerGameSystemUi(org.gamegineer.game.ui.system.IGameSystemUi)
     */
    public void registerGameSystemUi(
        final IGameSystemUi gameSystemUi )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$

        m_gameSystemUis.putIfAbsent( gameSystemUi.getId(), gameSystemUi );
    }

    /*
     * @see org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry#unregisterGameSystemUi(org.gamegineer.game.ui.system.IGameSystemUi)
     */
    public void unregisterGameSystemUi(
        final IGameSystemUi gameSystemUi )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$

        m_gameSystemUis.remove( gameSystemUi.getId(), gameSystemUi );
    }
}

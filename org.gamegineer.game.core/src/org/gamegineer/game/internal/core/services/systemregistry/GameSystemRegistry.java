/*
 * GameSystemRegistry.java
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
 * Created on Feb 16, 2009 at 10:29:18 PM.
 */

package org.gamegineer.game.internal.core.services.systemregistry;

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
import org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.internal.core.Activator;
import org.gamegineer.game.internal.core.Loggers;
import org.gamegineer.game.internal.core.Services;

/**
 * Implementation of
 * {@link org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class GameSystemRegistry
    implements IGameSystemRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension point attribute specifying the game system class. */
    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    /** The extension point attribute specifying the game system identifier. */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The collection of game systems directly managed by this object. */
    private final ConcurrentMap<String, IGameSystem> gameSystems_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemRegistry} class.
     */
    public GameSystemRegistry()
    {
        gameSystems_ = new ConcurrentHashMap<String, IGameSystem>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a collection of all foreign game systems not directly managed by
     * this object.
     * 
     * @return A collection of all foreign game systems not directly managed by
     *         this object; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IGameSystem> getForeignGameSystems()
    {
        final Collection<IGameSystem> gameSystems = new ArrayList<IGameSystem>();
        gameSystems.addAll( getForeignGameSystemsFromServiceRegistry() );
        gameSystems.addAll( getForeignGameSystemsFromExtensionRegistry() );
        return gameSystems;
    }

    /**
     * Gets a collection of all foreign game systems declared in the extension
     * registry.
     * 
     * @return A collection of all foreign game systems declared in the
     *         extension registry; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IGameSystem> getForeignGameSystemsFromExtensionRegistry()
    {
        // TODO: Create proxies instead so we can lazy load the hosting game
        // system plug-ins.  (See section 17.3.2 of Clayberg-06.)

        final Collection<IGameSystem> gameSystems = new ArrayList<IGameSystem>();
        for( final IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor( Activator.SYMBOLIC_NAME, Activator.EXTENSION_GAME_SYSTEMS ) )
        {
            try
            {
                gameSystems.add( (IGameSystem)element.createExecutableExtension( ATTR_CLASS ) );
            }
            catch( final CoreException e )
            {
                Loggers.DEFAULT.log( Level.SEVERE, Messages.GameSystemRegistry_getForeignGameSystemsFromExtensionRegistry_creationError( element.getAttribute( ATTR_ID ) ), e );
            }
        }
        return gameSystems;
    }

    /**
     * Gets a collection of all foreign game systems declared in the service
     * registry.
     * 
     * @return A collection of all foreign game systems declared in the service
     *         registry; never {@code null}.
     */
    /* @NonNull */
    private static Collection<IGameSystem> getForeignGameSystemsFromServiceRegistry()
    {
        return Services.getDefault().getGameSystems();
    }

    /*
     * @see org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry#getGameSystem(java.lang.String)
     */
    public IGameSystem getGameSystem(
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return getGameSystemMap().get( id );
    }

    /**
     * Gets a map view of all game systems known by this object.
     * 
     * @return A map view of all game systems known by this object; never
     *         {@code null}.
     */
    /* @NonNull */
    private Map<String, IGameSystem> getGameSystemMap()
    {
        final Map<String, IGameSystem> gameSystems = new HashMap<String, IGameSystem>( gameSystems_ );
        for( final IGameSystem gameSystem : getForeignGameSystems() )
        {
            if( gameSystems.containsKey( gameSystem.getId() ) )
            {
                Loggers.DEFAULT.warning( Messages.GameSystemRegistry_getGameSystemMap_duplicateId( gameSystem.getId() ) );
            }
            else
            {
                gameSystems.put( gameSystem.getId(), gameSystem );
            }
        }
        return gameSystems;
    }

    /*
     * @see org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry#getGameSystems()
     */
    public Collection<IGameSystem> getGameSystems()
    {
        return new ArrayList<IGameSystem>( getGameSystemMap().values() );
    }

    /*
     * @see org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry#registerGameSystem(org.gamegineer.game.core.system.IGameSystem)
     */
    public void registerGameSystem(
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        gameSystems_.putIfAbsent( gameSystem.getId(), gameSystem );
    }

    /*
     * @see org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry#unregisterGameSystem(org.gamegineer.game.core.system.IGameSystem)
     */
    public void unregisterGameSystem(
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        gameSystems_.remove( gameSystem.getId(), gameSystem );
    }
}

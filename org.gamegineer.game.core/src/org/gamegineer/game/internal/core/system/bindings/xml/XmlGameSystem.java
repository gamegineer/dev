/*
 * XmlGameSystem.java
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
 * Created on Nov 17, 2008 at 11:25:37 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.GameSystemBuilder;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * The XML binding for a game system.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@XmlAccessorType( XmlAccessType.NONE )
@XmlRootElement( name = XmlGameSystem.NAME_GAME_SYSTEM )
public final class XmlGameSystem
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local name of the game system element. */
    public static final String NAME_GAME_SYSTEM = "game-system"; //$NON-NLS-1$

    /** The local name of the game system identifier attribute. */
    public static final String NAME_ID = "id"; //$NON-NLS-1$

    /** The local name of the roles element. */
    public static final String NAME_ROLES = "roles"; //$NON-NLS-1$

    /** The local name of the stages element. */
    public static final String NAME_STAGES = "stages"; //$NON-NLS-1$

    /** The game system identifier. */
    @XmlAttribute( name = NAME_ID, required = true )
    private final String id_;

    /** The role list. */
    @XmlElement( name = XmlRole.NAME_ROLE, required = true, type = XmlRole.class )
    @XmlElementWrapper( name = NAME_ROLES, required = true )
    private final List<XmlRole> roles_;

    /** The stage list. */
    @XmlElement( name = XmlStage.NAME_STAGE, required = true, type = XmlStage.class )
    @XmlElementWrapper( name = NAME_STAGES, required = true )
    private final List<XmlStage> stages_;

    // FIXME: @XmlElementWrapper is not behaving properly.  Even though
    // required is true, the schema still says that the wrapper element is
    // optional.  Consider writing a custom collection class so we can avoid
    // using @XmlElementWrapper or determine if we're using JAXB incorrectly.
    // When fixed, search for the @Ignored tests associated with this issue.


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystem} class.
     */
    private XmlGameSystem()
    {
        id_ = null;
        roles_ = new ArrayList<XmlRole>();
        stages_ = new ArrayList<XmlStage>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game system based on the current state of this object.
     * 
     * @return A new game system; never {@code null}.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurs while creating the game system.
     */
    /* @NonNull */
    public IGameSystem toGameSystem()
        throws GameSystemException
    {
        // TODO: Bad smell.  See same comment in XmlStage.

        try
        {
            final GameSystemBuilder builder = new GameSystemBuilder();
            builder.setId( id_ );
            for( final XmlRole role : roles_ )
            {
                builder.addRole( role.toRole() );
            }
            for( final XmlStage stage : stages_ )
            {
                builder.addStage( stage.toStage() );
            }
            return builder.toGameSystem();
        }
        catch( final RuntimeException e )
        {
            throw new GameSystemException( Messages.XmlGameSystem_toGameSystem_builderError, e );
        }
    }
}

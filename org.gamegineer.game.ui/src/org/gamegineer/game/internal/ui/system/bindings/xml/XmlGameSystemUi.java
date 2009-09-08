/*
 * XmlGameSystemUi.java
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
 * Created on Feb 26, 2009 at 11:41:26 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.GameSystemUiBuilder;
import org.gamegineer.game.ui.system.GameSystemUiException;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * The XML binding for a game system user interface.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@XmlAccessorType( XmlAccessType.NONE )
@XmlRootElement( name = XmlGameSystemUi.NAME_GAME_SYSTEM_UI )
public final class XmlGameSystemUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local name of the game system user interface element. */
    public static final String NAME_GAME_SYSTEM_UI = "game-system-ui"; //$NON-NLS-1$

    /** The local name of the game system identifier attribute. */
    public static final String NAME_ID = "id"; //$NON-NLS-1$

    /** The local name of the game system name attribute. */
    public static final String NAME_NAME = "name"; //$NON-NLS-1$

    /** The local name of the roles element. */
    public static final String NAME_ROLES = "roles"; //$NON-NLS-1$

    /** The game system identifier. */
    @XmlAttribute( name = NAME_ID, required = true )
    private final String id_;

    /** The game system name. */
    @Localizable
    @XmlAttribute( name = NAME_NAME, required = true )
    private final String name_;

    /** The role list. */
    @XmlElement( name = XmlRoleUi.NAME_ROLE, required = true, type = XmlRoleUi.class )
    @XmlElementWrapper( name = NAME_ROLES, required = true )
    private final List<XmlRoleUi> roleUis_;

    // FIXME: @XmlElementWrapper is not behaving properly.  Even though
    // required is true, the schema still says that the wrapper element is
    // optional.  Consider writing a custom collection class so we can avoid
    // using @XmlElementWrapper or determine if we're using JAXB incorrectly.
    // When fixed, search for the @Ignored tests associated with this issue.


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlGameSystemUi} class.
     */
    private XmlGameSystemUi()
    {
        id_ = null;
        name_ = null;
        roleUis_ = new ArrayList<XmlRoleUi>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game system user interface based on the current state of
     * this object.
     * 
     * @return A new game system user interface; never {@code null}.
     * 
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the game system user interface.
     */
    /* @NonNull */
    public IGameSystemUi toGameSystemUi()
        throws GameSystemUiException
    {
        // TODO: Bad smell.  See same comment in XmlStage.

        try
        {
            final GameSystemUiBuilder builder = new GameSystemUiBuilder();
            builder.setId( id_ );
            builder.setName( name_ );
            for( final XmlRoleUi roleUi : roleUis_ )
            {
                builder.addRole( roleUi.toRoleUi() );
            }
            return builder.toGameSystemUi();
        }
        catch( final RuntimeException e )
        {
            throw new GameSystemUiException( Messages.XmlGameSystemUi_toGameSystemUi_builderError, e );
        }
    }
}

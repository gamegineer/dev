/*
 * XmlRoleUi.java
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
 * Created on Feb 27, 2009 at 9:01:52 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.GameSystemUiException;
import org.gamegineer.game.ui.system.IRoleUi;
import org.gamegineer.game.ui.system.RoleUiBuilder;

/**
 * The XML binding for a role user interface.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@XmlAccessorType( XmlAccessType.NONE )
public final class XmlRoleUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local name of the role identifier attribute. */
    public static final String NAME_ID = "id"; //$NON-NLS-1$

    /** The local name of the role name attribute. */
    public static final String NAME_NAME = "name"; //$NON-NLS-1$

    /** The local name of the role element. */
    public static final String NAME_ROLE = "role"; //$NON-NLS-1$

    /** The role identifier. */
    @XmlAttribute( name = NAME_ID, required = true )
    private final String id_;

    /** The role name. */
    @XmlAttribute( name = NAME_NAME, required = true )
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlRoleUi} class.
     */
    private XmlRoleUi()
    {
        id_ = null;
        name_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new role user interface based on the current state of this
     * object.
     * 
     * @return A new role user interface; never {@code null}.
     * 
     * @throws org.gamegineer.game.ui.system.GameSystemUiException
     *         If an error occurs while creating the role user interface.
     */
    /* @NonNull */
    public IRoleUi toRoleUi()
        throws GameSystemUiException
    {
        // TODO: Bad smell.  See same comment in XmlStage.

        try
        {
            final RoleUiBuilder builder = new RoleUiBuilder();
            builder.setId( id_ );
            builder.setName( name_ );
            return builder.toRoleUi();
        }
        catch( final RuntimeException e )
        {
            throw new GameSystemUiException( Messages.XmlRoleUi_toRoleUi_builderError, e );
        }
    }
}

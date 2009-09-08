/*
 * XmlRole.java
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
 * Created on Jan 17, 2009 at 10:09:05 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.RoleBuilder;

/**
 * The XML binding for a role.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@XmlAccessorType( XmlAccessType.NONE )
public final class XmlRole
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local name of the role identifier attribute. */
    public static final String NAME_ID = "id"; //$NON-NLS-1$

    /** The local name of the role element. */
    public static final String NAME_ROLE = "role"; //$NON-NLS-1$

    /** The role identifier. */
    @XmlAttribute( name = NAME_ID, required = true )
    private final String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlRole} class.
     */
    private XmlRole()
    {
        id_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new role based on the current state of this object.
     * 
     * @return A new role; never {@code null}.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurs while creating the role.
     */
    /* @NonNull */
    public IRole toRole()
        throws GameSystemException
    {
        // TODO: Bad smell.  See same comment in XmlStage.

        try
        {
            final RoleBuilder builder = new RoleBuilder();
            builder.setId( id_ );
            return builder.toRole();
        }
        catch( final RuntimeException e )
        {
            throw new GameSystemException( Messages.XmlRole_toRole_builderError, e );
        }
    }
}

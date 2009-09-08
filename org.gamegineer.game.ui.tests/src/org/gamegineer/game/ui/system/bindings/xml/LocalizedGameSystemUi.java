/*
 * LocalizedGameSystemUi.java
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
 * Created on Feb 28, 2009 at 9:46:30 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.gamegineer.game.ui.system.IRoleUi;

/**
 * A game system user interface decorator that replaces attribute values which
 * represent locale-neutral keys with the corresponding localized string.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@Immutable
public final class LocalizedGameSystemUi
    implements IGameSystemUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The string bundle used for localization. */
    private final IStringBundle bundle_;

    /** The decorated game system user interface. */
    private final IGameSystemUi gameSystemUi_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalizedGameSystemUi} class.
     * 
     * @param gameSystemUi
     *        The decorated game system user interface; must not be {@code null}
     *        .
     * @param bundle
     *        The string bundle used for localization; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUi} or {@code bundle} is {@code null}.
     */
    public LocalizedGameSystemUi(
        /* @NonNull */
        final IGameSystemUi gameSystemUi,
        /* @NonNull */
        final IStringBundle bundle )
    {
        assertArgumentNotNull( gameSystemUi, "gameSystemUi" ); //$NON-NLS-1$
        assertArgumentNotNull( bundle, "bundle" ); //$NON-NLS-1$

        gameSystemUi_ = gameSystemUi;
        bundle_ = bundle;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getId()
     */
    public String getId()
    {
        return gameSystemUi_.getId();
    }

    /*
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getName()
     */
    public String getName()
    {
        final String value = gameSystemUi_.getName();
        if( LocaleNeutralKey.isDecoratedKey( value ) )
        {
            final String localizedValue = bundle_.getString( LocaleNeutralKey.undecorateKey( value ) );
            if( localizedValue != null )
            {
                return localizedValue;
            }
        }

        return value;
    }

    /*
     * @see org.gamegineer.game.ui.system.IGameSystemUi#getRoles()
     */
    public List<IRoleUi> getRoles()
    {
        return gameSystemUi_.getRoles();
    }
}

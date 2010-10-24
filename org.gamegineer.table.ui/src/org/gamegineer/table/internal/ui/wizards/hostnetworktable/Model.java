/*
 * Model.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Oct 15, 2010 at 10:07:55 PM.
 */

package org.gamegineer.table.internal.ui.wizards.hostnetworktable;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.MultiValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

/**
 * The host network table wizard model.
 */
@NotThreadSafe
final class Model
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The confirmed password. */
    private String confirmedPassword_;

    /** The password. */
    private String password_;

    /** The player name. */
    private String playerName_;

    /** The port. */
    private int port_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Model} class.
     */
    Model()
    {
        confirmedPassword_ = ""; //$NON-NLS-1$
        password_ = ""; //$NON-NLS-1$
        playerName_ = ""; //$NON-NLS-1$
        port_ = 21112; // TODO: Refer to appropriate constant from network table model.
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the confirmed password.
     * 
     * @return The confirmed password; never {@code null}.
     */
    /* @NonNull */
    public String getConfirmedPassword()
    {
        return confirmedPassword_;
    }

    /**
     * Gets the password.
     * 
     * @return The password; never {@code null}.
     */
    /* @NonNull */
    public String getPassword()
    {
        return password_;
    }

    /**
     * Gets a validator for the password fields.
     * 
     * @param passwordValue
     *        The password observable value; must not be {@code null}.
     * @param confirmedPasswordValue
     *        The confirmed password observable value; must not be {@code null}.
     * 
     * @return A validator for the password fields; never {@code null}.
     */
    /* @NonNull */
    ValidationStatusProvider getPasswordValidationStatusProvider(
        /* @NonNull */
        final IObservableValue passwordValue,
        /* @NonNull */
        final IObservableValue confirmedPasswordValue )
    {
        assert passwordValue != null;
        assert confirmedPasswordValue != null;

        return new MultiValidator()
        {
            @Override
            protected IStatus validate()
            {
                final String password = (String)passwordValue.getValue();
                final String confirmedPassword = (String)confirmedPasswordValue.getValue();
                if( (password == null) || (confirmedPassword == null) || !password.equals( confirmedPassword ) )
                {
                    return ValidationStatus.error( Messages.Model_password_unconfirmed );
                }

                return ValidationStatus.ok();
            }
        };
    }

    /**
     * Gets the player name.
     * 
     * @return The player name; never {@code null}.
     */
    /* @NonNull */
    public String getPlayerName()
    {
        return playerName_;
    }

    /**
     * Gets a validator for the player name field.
     * 
     * @return A validator for the player name field; never {@code null}.
     */
    /* @NonNull */
    IValidator getPlayerNameValidator()
    {
        return new IValidator()
        {
            @Override
            public IStatus validate(
                final Object value )
            {
                final String playerName = (String)value;
                if( (playerName == null) || playerName.isEmpty() )
                {
                    return ValidationStatus.error( Messages.Model_playerName_empty );
                }

                return ValidationStatus.ok();
            }
        };
    }

    /**
     * Gets the port.
     * 
     * @return The port.
     */
    public int getPort()
    {
        return port_;
    }

    /**
     * Gets a validator for the port field.
     * 
     * @return A validator for the port field; never {@code null}.
     */
    /* @NonNull */
    IValidator getPortValidator()
    {
        return new IValidator()
        {
            @Override
            @SuppressWarnings( "boxing" )
            public IStatus validate(
                final Object value )
            {
                final int port = (Integer)value;
                if( (port < 1) || (port > 65535) )
                {
                    return ValidationStatus.error( Messages.Model_port_outOfRange );
                }

                return ValidationStatus.ok();
            }
        };
    }

    /**
     * Sets the confirmed password.
     * 
     * @param confirmedPassword
     *        The confirmed password; must not be {@code null}.
     */
    public void setConfirmedPassword(
        /* @NonNull */
        final String confirmedPassword )
    {
        assert confirmedPassword != null;

        confirmedPassword_ = confirmedPassword;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *        The password; must not be {@code null}.
     */
    public void setPassword(
        /* @NonNull */
        final String password )
    {
        assert password != null;

        password_ = password;
    }

    /**
     * Sets the player name.
     * 
     * @param playerName
     *        The player name; must not be {@code null}.
     */
    public void setPlayerName(
        /* @NonNull */
        final String playerName )
    {
        assert playerName != null;

        playerName_ = playerName;
    }

    /**
     * Sets the port.
     * 
     * @param port
     *        The port.
     */
    public void setPort(
        final int port )
    {
        port_ = port;
    }
}

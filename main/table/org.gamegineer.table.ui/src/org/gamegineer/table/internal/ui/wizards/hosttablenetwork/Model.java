/*
 * Model.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.wizards.hosttablenetwork;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.MultiValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.TableNetworkConstants;

/**
 * The host table network wizard model.
 */
@NotThreadSafe
final class Model
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The confirmed password. */
    private SecureString confirmedPassword_;

    /** The password. */
    private SecureString password_;

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
        confirmedPassword_ = new SecureString();
        password_ = new SecureString();
        playerName_ = ""; //$NON-NLS-1$
        port_ = TableNetworkConstants.DEFAULT_PORT;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disposes the resources used by the model.
     */
    public void dispose()
    {
        confirmedPassword_.dispose();
        password_.dispose();
    }

    /**
     * Gets the confirmed password.
     * 
     * @return The confirmed password; never {@code null}.
     */
    /* @NonNull */
    public SecureString getConfirmedPassword()
    {
        return confirmedPassword_;
    }

    /**
     * Gets the password.
     * 
     * @return The password; never {@code null}.
     */
    /* @NonNull */
    public SecureString getPassword()
    {
        return password_;
    }

    /**
     * Gets a validation status provider for the password fields.
     * 
     * @param passwordValue
     *        The password observable value; must not be {@code null}.
     * @param confirmedPasswordValue
     *        The confirmed password observable value; must not be {@code null}.
     * 
     * @return A validation status provider for the password fields; never
     *         {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "static-method" )
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
                final SecureString password = (SecureString)passwordValue.getValue();
                final SecureString confirmedPassword = (SecureString)confirmedPasswordValue.getValue();
                if( (password == null) || (confirmedPassword == null) || !password.equals( confirmedPassword ) )
                {
                    return ValidationStatus.error( NlsMessages.Model_password_unconfirmed );
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
    @SuppressWarnings( "static-method" )
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
                    return ValidationStatus.error( NlsMessages.Model_playerName_empty );
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
    @SuppressWarnings( "static-method" )
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
                    return ValidationStatus.error( NlsMessages.Model_port_outOfRange );
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
        final SecureString confirmedPassword )
    {
        assert confirmedPassword != null;

        confirmedPassword_.dispose();
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
        final SecureString password )
    {
        assert password != null;

        password_.dispose();
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

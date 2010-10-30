/*
 * HostNetworkTableWizard.java
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
 * Created on Oct 7, 2010 at 11:18:30 PM.
 */

package org.gamegineer.table.internal.ui.wizards.hostnetworktable;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.wizard.AbstractWizard;

/**
 * The host network table wizard.
 */
@NotThreadSafe
public final class HostNetworkTableWizard
    extends AbstractWizard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The wizard model. */
    private final Model model_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HostNetworkTableWizard} class.
     */
    public HostNetworkTableWizard()
    {
        model_ = new Model();

        setTitle( Messages.HostNetworkTableWizard_title );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizard#addPages()
     */
    @Override
    public void addPages()
    {
        addPage( new MainPage() );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.AbstractWizard#dispose()
     */
    @Override
    public void dispose()
    {
        model_.dispose();

        super.dispose();
    }

    /**
     * Gets the wizard model.
     * 
     * @return The wizard model; never {@code null}.
     */
    /* @NonNull */
    Model getModel()
    {
        return model_;
    }
}

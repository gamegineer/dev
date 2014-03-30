/*
 * AbstractWizardPage.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Sep 21, 2010 at 10:17:21 PM.
 */

package org.gamegineer.common.ui.wizard;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.dialog.AbstractDialogPage;
import org.gamegineer.common.ui.dialog.DialogMessage;

/**
 * Superclass for all implementations of {@link IWizardPage}.
 */
@NotThreadSafe
public abstract class AbstractWizardPage
    extends AbstractDialogPage
    implements IWizardPage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates the page is complete. */
    private boolean isComplete_;

    /** The unique name of the page within the wizard. */
    private final String name_;

    /** The previous page in the wizard sequence. */
    @Nullable
    private IWizardPage previousPage_;

    /** The wizard that hosts the page. */
    @Nullable
    private IWizard wizard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWizardPage} class.
     * 
     * @param name
     *        The unique name of the page within the wizard; must not be
     *        {@code null}.
     */
    protected AbstractWizardPage(
        final String name )
    {
        isComplete_ = true;
        name_ = name;
        previousPage_ = null;
        wizard_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#canMoveToNextPage()
     */
    @Override
    public final boolean canMoveToNextPage()
    {
        return isComplete() && (getNextPage() != null);
    }

    /**
     * Gets the container hosting the wizard that hosts the page.
     * 
     * @return The container hosting the wizard that hosts the page or
     *         {@code null} if the page has not yet been added to a wizard or
     *         the wizard has not yet been added to a container.
     */
    @Nullable
    protected final IWizardContainer getContainer()
    {
        return (wizard_ != null) ? wizard_.getContainer() : null;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#getName()
     */
    @Override
    public final String getName()
    {
        return name_;
    }

    /**
     * This implementation returns the next page as identified by the wizard.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizardPage#getNextPage()
     */
    @Nullable
    @Override
    public IWizardPage getNextPage()
    {
        if( wizard_ != null )
        {
            return wizard_.getNextPage( this );
        }

        return null;
    }

    /**
     * This implementation returns the cached previous page. If the cache is
     * empty, it returns the previous page as identified by the wizard.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizardPage#getPreviousPage()
     */
    @Nullable
    @Override
    public IWizardPage getPreviousPage()
    {
        if( previousPage_ != null )
        {
            return previousPage_;
        }

        if( wizard_ != null )
        {
            return wizard_.getPreviousPage( this );
        }

        return null;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#getWizard()
     */
    @Nullable
    @Override
    public final IWizard getWizard()
    {
        return wizard_;
    }

    /**
     * Indicates this page is the active page in the wizard.
     * 
     * @return {@code true} if this page is the active page in the wizard;
     *         otherwise {@code false}.
     */
    protected final boolean isActivePage()
    {
        final IWizardContainer container = getContainer();
        if( container == null )
        {
            return false;
        }

        return container.getActivePage() == this;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#isComplete()
     */
    @Override
    public final boolean isComplete()
    {
        return isComplete_;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#setComplete(boolean)
     */
    @Override
    public final void setComplete(
        final boolean isComplete )
    {
        isComplete_ = isComplete;

        if( isActivePage() )
        {
            final IWizardContainer container = getContainer();
            assert container != null;
            container.updateButtons();
        }
    }

    /**
     * This implementation extends the superclass implementation and updates the
     * wizard container banner if this page is active.
     * 
     * @see org.gamegineer.common.ui.dialog.AbstractDialogPage#setDescription(java.lang.String)
     */
    @Override
    protected void setDescription(
        @Nullable
        final String description )
    {
        super.setDescription( description );

        if( isActivePage() )
        {
            final IWizardContainer container = getContainer();
            assert container != null;
            container.updateBanner();
        }
    }

    /**
     * This implementation extends the superclass implementation and updates the
     * wizard container message if this page is active.
     * 
     * @see org.gamegineer.common.ui.dialog.AbstractDialogPage#setMessage(org.gamegineer.common.ui.dialog.DialogMessage)
     */
    @Override
    public void setMessage(
        @Nullable
        final DialogMessage message )
    {
        super.setMessage( message );

        if( isActivePage() )
        {
            final IWizardContainer container = getContainer();
            assert container != null;
            container.updateMessage();
        }
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#setPreviousPage(org.gamegineer.common.ui.wizard.IWizardPage)
     */
    @Override
    public final void setPreviousPage(
        @Nullable
        final IWizardPage page )
    {
        previousPage_ = page;
    }

    /**
     * This implementation extends the superclass implementation and updates the
     * wizard container banner if this page is active.
     * 
     * @see org.gamegineer.common.ui.dialog.AbstractDialogPage#setTitle(java.lang.String)
     */
    @Override
    protected void setTitle(
        @Nullable
        final String title )
    {
        super.setTitle( title );

        if( isActivePage() )
        {
            final IWizardContainer container = getContainer();
            assert container != null;
            container.updateBanner();
        }
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardPage#setWizard(org.gamegineer.common.ui.wizard.IWizard)
     */
    @Override
    public final void setWizard(
        final IWizard wizard )
    {
        wizard_ = wizard;
    }
}

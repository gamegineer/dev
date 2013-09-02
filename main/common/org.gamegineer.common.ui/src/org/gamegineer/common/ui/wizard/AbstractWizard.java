/*
 * AbstractWizard.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Sep 24, 2010 at 12:08:56 AM.
 */

package org.gamegineer.common.ui.wizard;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;

/**
 * Superclass for all implementations of {@link IWizard}.
 */
@NotThreadSafe
public abstract class AbstractWizard
    implements IWizard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container that hosts this wizard. */
    private IWizardContainer container_;

    /** Indicates the wizard needs Previous and Back buttons. */
    private boolean needsPreviousAndBackButtons_;

    /** Indicates the wizard needs a progress monitor. */
    private boolean needsProgressMonitor_;

    /** The collection of pages hosted by the wizard. */
    private final List<IWizardPage> pages_;

    /** The wizard title. */
    private String title_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWizard} class.
     */
    protected AbstractWizard()
    {
        container_ = null;
        needsPreviousAndBackButtons_ = false;
        needsProgressMonitor_ = false;
        pages_ = new ArrayList<>();
        title_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new page to the wizard.
     * 
     * <p>
     * The page is added to the end of the wizard page list.
     * </p>
     * 
     * @param page
     *        The new page; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code page} is {@code null}.
     */
    protected final void addPage(
        /* @NonNull */
        final IWizardPage page )
    {
        page.setWizard( this );
        pages_.add( page );
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#addPages()
     */
    @Override
    public void addPages()
    {
        // do nothing
    }

    /**
     * This implementation indicates the wizard can finish if all pages indicate
     * they are complete.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish()
    {
        for( final IWizardPage page : pages_ )
        {
            if( !page.isComplete() )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * This implementation creates the content for all pages hosted in the
     * wizard.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#create(java.awt.Container)
     */
    @Override
    public void create(
        final Container parent )
    {
        assertArgumentNotNull( parent, "parent" ); //$NON-NLS-1$

        for( final IWizardPage page : pages_ )
        {
            page.create( parent );
            assert page.getContent() != null;
        }
    }

    /**
     * This implementation disposes of all pages hosted by the wizard.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#dispose()
     */
    @Override
    public void dispose()
    {
        for( final IWizardPage page : pages_ )
        {
            page.dispose();
        }
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#getContainer()
     */
    @Override
    public final IWizardContainer getContainer()
    {
        return container_;
    }

    /**
     * This implementation returns the first page that was added to the wizard.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#getFirstPage()
     */
    @Override
    public IWizardPage getFirstPage()
    {
        if( pages_.isEmpty() )
        {
            return null;
        }

        return pages_.get( 0 );
    }

    /**
     * This implementation returns the page that was added to the wizard
     * immediately after the specified page.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#getNextPage(org.gamegineer.common.ui.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getNextPage(
        final IWizardPage page )
    {
        assertArgumentNotNull( page, "page" ); //$NON-NLS-1$

        final int index = pages_.indexOf( page );
        if( (index == -1) || (index == (pages_.size() - 1)) )
        {
            return null;
        }

        return pages_.get( index + 1 );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#getPage(java.lang.String)
     */
    @Override
    public final IWizardPage getPage(
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        for( final IWizardPage page : pages_ )
        {
            if( name.equals( page.getName() ) )
            {
                return page;
            }
        }

        return null;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#getPageCount()
     */
    @Override
    public final int getPageCount()
    {
        return pages_.size();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#getPages()
     */
    @Override
    public final Collection<IWizardPage> getPages()
    {
        return new ArrayList<>( pages_ );
    }

    /**
     * This implementation returns the page that was added to the wizard
     * immediately before the specified page.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#getPreviousPage(org.gamegineer.common.ui.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getPreviousPage(
        final IWizardPage page )
    {
        assertArgumentNotNull( page, "page" ); //$NON-NLS-1$

        final int index = pages_.indexOf( page );
        if( (index == -1) || (index == 0) )
        {
            return null;
        }

        return pages_.get( index - 1 );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#getTitle()
     */
    @Override
    public final String getTitle()
    {
        return title_;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#needsPreviousAndNextButtons()
     */
    @Override
    public final boolean needsPreviousAndNextButtons()
    {
        return needsPreviousAndBackButtons_ || (pages_.size() > 1);
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#needsProgressMonitor()
     */
    @Override
    public final boolean needsProgressMonitor()
    {
        return needsProgressMonitor_;
    }

    /**
     * This implementation does nothing and always returns {@code true}.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#performCancel()
     */
    @Override
    public boolean performCancel()
    {
        return true;
    }

    /**
     * This implementation does nothing and always returns {@code true}.
     * 
     * @see org.gamegineer.common.ui.wizard.IWizard#performFinish()
     */
    @Override
    public boolean performFinish()
    {
        return true;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizard#setContainer(org.gamegineer.common.ui.wizard.IWizardContainer)
     */
    @Override
    public final void setContainer(
        final IWizardContainer container )
    {
        container_ = container;
    }

    /**
     * Sets a flag indicating the wizard needs Previous and Back buttons even if
     * it only contains a single page.
     * 
     * <p>
     * This flag should be set by wizards where the first page adds additional
     * pages based on user input.
     * </p>
     * 
     * @param needsPreviousAndBackButtons
     *        {@code true} if the wizard needs Previous and Back buttons;
     *        otherwise {@code false}.
     */
    protected final void setNeedsPreviousAndBackButtons(
        final boolean needsPreviousAndBackButtons )
    {
        needsPreviousAndBackButtons_ = needsPreviousAndBackButtons;
    }

    /**
     * Sets a flag indicating the wizard needs a progress monitor.
     * 
     * @param needsProgressMonitor
     *        {@code true} if the wizard needs a progress monitor; otherwise
     *        {@code false}.
     */
    protected final void setNeedsProgressMonitor(
        final boolean needsProgressMonitor )
    {
        needsProgressMonitor_ = needsProgressMonitor;
    }

    /**
     * Sets the wizard title.
     * 
     * @param title
     *        The wizard title or {@code null} to clear the title.
     */
    protected final void setTitle(
        /* @Nullable */
        final String title )
    {
        title_ = title;

        if( container_ != null )
        {
            container_.updateShell();
        }
    }
}

/*
 * AbstractExtensionTestCase.java
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
 * Created on Apr 19, 2008 at 11:04:27 PM.
 */

package org.gamegineer.engine.core;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.IExtension} interface.
 */
public abstract class AbstractExtensionTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The extension under test in the fixture. */
    private IExtension extension_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractExtensionTestCase}
     * class.
     */
    protected AbstractExtensionTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an engine context suitable for the extension under test.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for the extension under test; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new FakeEngineContext();
    }

    /**
     * Creates the extension to be tested.
     * 
     * @return The extension to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract IExtension createExtension();

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        extension_ = createExtension();
        assertNotNull( extension_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        extension_ = null;
    }

    /**
     * Ensures the {@code getExtensionType} method does not return {@code null}.
     */
    @Test
    public void testGetExtensionType_ReturnValue_NonNull()
    {
        assertNotNull( extension_.getExtensionType() );
    }

    /**
     * Ensures the {@code start} method throws an exception when passed a
     * {@code null} engine context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testStart_EngineContext_Null()
        throws Exception
    {
        extension_.start( null );
    }

    /**
     * Ensures the {@code start} method throws an exception when called after
     * the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testStart_ExtensionStarted()
        throws Exception
    {
        final IEngineContext context = createEngineContext();
        extension_.start( context );
        extension_.start( context );
    }

    /**
     * Ensures the {@code stop} method throws an exception when passed a {@code
     * null} engine context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testStop_EngineContext_Null()
        throws Exception
    {
        extension_.stop( null );
    }

    /**
     * Ensures the {@code stop} method throws an exception when called before
     * the extension has been started.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testStop_ExtensionNotStarted()
        throws Exception
    {
        extension_.stop( createEngineContext() );
    }
}

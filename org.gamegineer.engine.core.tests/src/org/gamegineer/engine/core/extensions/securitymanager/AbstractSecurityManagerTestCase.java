/*
 * AbstractSecurityManagerTestCase.java
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
 * Created on Mar 30, 2009 at 11:10:32 PM.
 */

package org.gamegineer.engine.core.extensions.securitymanager;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.securitymanager.ISecurityManager}
 * interface.
 */
public abstract class AbstractSecurityManagerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The engine context for use in the fixture. */
    private IEngineContext m_context;

    /** The security manager under test in the fixture. */
    private ISecurityManager m_securityManager;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractSecurityManagerTestCase}
     * class.
     */
    protected AbstractSecurityManagerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an engine context suitable for testing the security manager.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context; never {@code null}.
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
     * Creates the security manager to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The security manager to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected abstract ISecurityManager createSecurityManager(
        /* @NonNull */
        IEngineContext context )
        throws Exception;

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
        m_context = createEngineContext();
        m_securityManager = createSecurityManager( m_context );
        assertNotNull( m_securityManager );
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
        m_securityManager = null;
        m_context = null;
    }

    /**
     * Ensures the {@code getUserPrincipal} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testGetUserPrincipal_Context_Null()
    {
        m_securityManager.getUserPrincipal( null );
    }

    /**
     * Ensures the {@code getUserPrincipal} method does not return {@code null}.
     */
    @Test
    public void testGetUserPrincipal_ReturnValue_NonNull()
    {
        assertNotNull( m_securityManager.getUserPrincipal( m_context ) );
    }
}

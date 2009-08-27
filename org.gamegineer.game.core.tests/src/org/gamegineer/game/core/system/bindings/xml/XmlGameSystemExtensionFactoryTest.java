/*
 * XmlGameSystemExtensionFactoryTest.java
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
 * Created on Feb 18, 2009 at 11:10:36 PM.
 */

package org.gamegineer.game.core.system.bindings.xml;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import org.eclipse.core.runtime.IConfigurationElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.system.bindings.xml.XmlGameSystemExtensionFactory}
 * class.
 */
public final class XmlGameSystemExtensionFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The XML game system extension factory under test in the fixture. */
    private XmlGameSystemExtensionFactory m_factory;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code XmlGameSystemExtensionFactoryTest} class.
     */
    public XmlGameSystemExtensionFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        m_factory = new XmlGameSystemExtensionFactory();
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
        m_factory = null;
    }

    /**
     * Ensures the {@code parsePath} method returns the correct value when
     * passed extension point data of type {@code Hashtable} in which an entry
     * for the path data is absent.
     */
    @Test
    public void testParsePath_Data_Hashtable_Absent()
    {
        final Map<String, String> data = new Hashtable<String, String>();
        data.put( "key", "value" ); //$NON-NLS-1$ //$NON-NLS-2$

        final String actualPath = XmlGameSystemExtensionFactoryFacade.parsePath( data );

        assertNull( actualPath );
    }

    /**
     * Ensures the {@code parsePath} method returns the correct value when
     * passed extension point data of type {@code Hashtable} in which an entry
     * for the path data is present.
     */
    @Test
    public void testParsePath_Data_Hashtable_Present()
    {
        final String expectedPath = "/my/path/file.xml"; //$NON-NLS-1$
        final Map<String, String> data = new Hashtable<String, String>();
        data.put( XmlGameSystemExtensionFactoryFacade.ATTR_PATH(), expectedPath );

        final String actualPath = XmlGameSystemExtensionFactoryFacade.parsePath( data );

        assertEquals( expectedPath, actualPath );
    }

    /**
     * Ensures the {@code parsePath} method returns the correct value when
     * passed {@code null} extension point data.
     */
    @Test
    public void testParsePath_Data_Null()
    {
        final String actualPath = XmlGameSystemExtensionFactoryFacade.parsePath( null );

        assertNull( actualPath );
    }

    /**
     * Ensures the {@code parsePath} method returns the correct value when
     * passed extension point data of type {@code String}.
     */
    @Test
    public void testParsePath_Data_String()
    {
        final String expectedPath = "/my/path/file.xml"; //$NON-NLS-1$
        final String data = expectedPath;

        final String actualPath = XmlGameSystemExtensionFactoryFacade.parsePath( data );

        assertEquals( expectedPath, actualPath );
    }

    /**
     * Ensures the {@code setInitializationData} method throws an exception when
     * passed a {@code null} configuration element.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetInitializationData_Config_Null()
        throws Exception
    {
        m_factory.setInitializationData( null, "propertyName", new Object() ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setInitializationData} method throws an exception when
     * passed a {@code null} property name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetInitializationData_PropertyName_Null()
        throws Exception
    {
        m_factory.setInitializationData( createDummy( IConfigurationElement.class ), null, new Object() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A class for transparently accessing inaccessible members of the
     * {@code XmlGameSystemExtensionFactory} class for testing purposes.
     */
    private static final class XmlGameSystemExtensionFactoryFacade
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code XmlGameSystemExtensionFactoryFacade} class.
         */
        private XmlGameSystemExtensionFactoryFacade()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Accessible facade for the {@code ATTR_PATH} class field.
         * 
         * @return The value of the {@code ATTR_PATH} class field; never
         *         {@code null}.
         */
        /* @NonNull */
        static String ATTR_PATH()
        {
            try
            {
                final Field field = XmlGameSystemExtensionFactory.class.getDeclaredField( "ATTR_PATH" ); //$NON-NLS-1$
                field.setAccessible( true );
                return (String)field.get( null );
            }
            catch( final Exception e )
            {
                throw new AssertionError( "failed to read 'ATTR_PATH'" ); //$NON-NLS-1$
            }
        }

        /**
         * Accessible facade for the {@code parsePath} class method.
         * 
         * @param data
         *        The extension point data; may be {@code null}.
         * 
         * @return The bundle path of the game system file or {@code null} if
         *         the data could not be parsed.
         */
        /* @Nullable */
        static String parsePath(
            /* @Nullable */
            final Object data )
        {
            try
            {
                final Method method = XmlGameSystemExtensionFactory.class.getDeclaredMethod( "parsePath", Object.class ); //$NON-NLS-1$
                method.setAccessible( true );
                return (String)method.invoke( null, data );
            }
            catch( final Exception e )
            {
                throw new AssertionError( "failed to invoke 'parsePath'" ); //$NON-NLS-1$
            }
        }
    }
}

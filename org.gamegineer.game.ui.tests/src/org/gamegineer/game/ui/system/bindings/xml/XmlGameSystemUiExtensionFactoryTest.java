/*
 * XmlGameSystemUiExtensionFactoryTest.java
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
 * Created on Mar 4, 2009 at 12:16:20 AM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

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
 * {@link org.gamegineer.game.ui.system.bindings.xml.XmlGameSystemUiExtensionFactory}
 * class.
 */
public final class XmlGameSystemUiExtensionFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The XML game system user interface extension factory under test in the
     * fixture.
     */
    private XmlGameSystemUiExtensionFactory factory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * XmlGameSystemUiExtensionFactoryTest} class.
     */
    public XmlGameSystemUiExtensionFactoryTest()
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
        factory_ = new XmlGameSystemUiExtensionFactory();
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
        factory_ = null;
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

        final String actualPath = XmlGameSystemUiExtensionFactoryFacade.parsePath( data );

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
        data.put( XmlGameSystemUiExtensionFactoryFacade.ATTR_PATH(), expectedPath );

        final String actualPath = XmlGameSystemUiExtensionFactoryFacade.parsePath( data );

        assertEquals( expectedPath, actualPath );
    }

    /**
     * Ensures the {@code parsePath} method returns the correct value when
     * passed {@code null} extension point data.
     */
    @Test
    public void testParsePath_Data_Null()
    {
        final String actualPath = XmlGameSystemUiExtensionFactoryFacade.parsePath( null );

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

        final String actualPath = XmlGameSystemUiExtensionFactoryFacade.parsePath( data );

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
        factory_.setInitializationData( null, "propertyName", new Object() ); //$NON-NLS-1$
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
        factory_.setInitializationData( createDummy( IConfigurationElement.class ), null, new Object() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A class for transparently accessing inaccessible members of the {@code
     * XmlGameSystemUiExtensionFactory} class for testing purposes.
     */
    private static final class XmlGameSystemUiExtensionFactoryFacade
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * XmlGameSystemUiExtensionFactoryFacade} class.
         */
        private XmlGameSystemUiExtensionFactoryFacade()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Accessible facade for the {@code ATTR_PATH} class field.
         * 
         * @return The value of the {@code ATTR_PATH} class field; never {@code
         *         null}.
         */
        /* @NonNull */
        static String ATTR_PATH()
        {
            try
            {
                final Field field = XmlGameSystemUiExtensionFactory.class.getDeclaredField( "ATTR_PATH" ); //$NON-NLS-1$
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
                final Method method = XmlGameSystemUiExtensionFactory.class.getDeclaredMethod( "parsePath", Object.class ); //$NON-NLS-1$
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

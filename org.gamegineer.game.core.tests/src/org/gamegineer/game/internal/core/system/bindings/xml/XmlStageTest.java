/*
 * XmlStageTest.java
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
 * Created on Nov 17, 2008 at 11:46:07 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import static org.gamegineer.game.core.system.Assert.assertStageEquals;
import static org.junit.Assert.assertEquals;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.NonValidatingStageBuilder;
import org.gamegineer.game.core.system.bindings.xml.XmlGameSystems;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.bindings.xml.XmlStage} class.
 */
public final class XmlStageTest
    extends AbstractJaxbTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage builder for use in the fixture. */
    private NonValidatingStageBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlStageTest} class.
     */
    public XmlStageTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a reader for the specified stage.
     * 
     * @param stage
     *        The stage; must not be {@code null}.
     * 
     * @return A reader for the specified stage; never {@code null}.
     */
    /* @NonNull */
    private static Reader createStageReader(
        /* @NonNull */
        final IStage stage )
    {
        assert stage != null;

        return new StringReader( XmlGameSystems.toXml( FakeRootElement.NAME_ROOT, stage ) );
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#getRootElementType()
     */
    @Override
    protected Class<?> getRootElementType()
    {
        return FakeRootElement.class;
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        builder_ = new NonValidatingStageBuilder( GameSystems.createUniqueStageList( 1 ).get( 0 ) );

        super.setUp();
    }

    /*
     * @see org.gamegineer.game.internal.core.system.xml.AbstractJaxbTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        builder_ = null;
    }

    /**
     * Ensures the {@code toStage} method throws an exception when the stage is
     * malformed but valid according to the JAXB schema.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test( expected = GameSystemException.class )
    public void testToStage_Fail_Malformed()
        throws Exception
    {
        builder_.setCardinality( -1 );
        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createStageReader( builder_.toStage() ) );
        final XmlStage xmlStage = root.getStage();

        xmlStage.toStage();
    }

    /**
     * Ensures the {@code toStage} method creates the expected stage when given
     * a well-formed stage.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testToStage_Success()
        throws Exception
    {
        final IStage expectedStage = builder_.toStage();
        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createStageReader( expectedStage ) );
        final XmlStage xmlStage = root.getStage();

        final IStage actualStage = xmlStage.toStage();

        assertStageEquals( expectedStage, actualStage );
    }

    /**
     * Ensures a stage fails to be unmarshalled when it does not have an
     * identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoId()
        throws Exception
    {
        builder_.setId( null );

        getUnmarshaller().unmarshal( createStageReader( builder_.toStage() ) );
    }

    /**
     * Ensures a stage fails to be unmarshalled when it does not have a
     * strategy.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnmarshalException.class )
    public void testUnmarshal_Fail_NoStrategy()
        throws Exception
    {
        builder_.setStrategy( null );

        getUnmarshaller().unmarshal( createStageReader( builder_.toStage() ) );
    }

    /**
     * Ensures a stage is successfully unmarshalled when it does not have a
     * cardinality.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal_Success_NoCardinality()
        throws Exception
    {
        builder_.setCardinality( null );

        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createStageReader( builder_.toStage() ) );
        final IStage actualStage = root.getStage().toStage();

        assertEquals( 0, actualStage.getCardinality() );
    }

    /**
     * Ensures a stage is successfully unmarshalled when it is completely
     * specified.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnmarshal_Success_Complete()
        throws Exception
    {
        final IStage expectedStage = builder_.toStage();

        final FakeRootElement root = (FakeRootElement)getUnmarshaller().unmarshal( createStageReader( expectedStage ) );
        final IStage actualStage = root.getStage().toStage();

        assertStageEquals( expectedStage, actualStage );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake element that serves as the root element for documents under test
     * in the fixture.
     */
    @XmlAccessorType( XmlAccessType.NONE )
    @XmlRootElement( name = "root" )
    private static final class FakeRootElement
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The local name of the root element. */
        static final String NAME_ROOT = "root"; //$NON-NLS-1$

        /** The stage. */
        @XmlElement( name = XmlStage.NAME_STAGE, required = true )
        private final XmlStage stage_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeRootElement} class.
         */
        private FakeRootElement()
        {
            stage_ = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Gets the stage.
         * 
         * @return The stage; never {@code null}.
         */
        /* @NonNull */
        XmlStage getStage()
        {
            return stage_;
        }
    }
}

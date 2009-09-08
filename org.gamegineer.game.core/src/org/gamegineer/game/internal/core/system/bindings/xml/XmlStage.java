/*
 * XmlStage.java
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
 * Created on Nov 17, 2008 at 11:23:53 PM.
 */

package org.gamegineer.game.internal.core.system.bindings.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.UnsupportedComponentSpecificationException;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;
import org.gamegineer.game.core.system.GameSystemException;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;
import org.gamegineer.game.core.system.StageBuilder;

/**
 * The XML binding for a stage.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
@XmlAccessorType( XmlAccessType.NONE )
public final class XmlStage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local name of the cardinality attribute. */
    public static final String NAME_CARDINALITY = "cardinality"; //$NON-NLS-1$

    /** The local name of the stage identifier attribute. */
    public static final String NAME_ID = "id"; //$NON-NLS-1$

    /** The local name of the stage element. */
    public static final String NAME_STAGE = "stage"; //$NON-NLS-1$

    /** The local name of the stages element. */
    public static final String NAME_STAGES = "stages"; //$NON-NLS-1$

    /** The local name of the strategy attribute. */
    public static final String NAME_STRATEGY = "strategy"; //$NON-NLS-1$

    /**
     * The stage cardinality.
     * 
     * <p>
     * Note that this field must be a reference type because it is optional in
     * the schema.
     * </p>
     */
    @XmlAttribute( name = NAME_CARDINALITY, required = false )
    private final Integer cardinality_;

    /** The stage identifier. */
    @XmlAttribute( name = NAME_ID, required = true )
    private final String id_;

    /** The list of child stages. */
    @XmlElement( name = NAME_STAGE, required = true, type = XmlStage.class )
    @XmlElementWrapper( name = NAME_STAGES, required = false )
    private final List<XmlStage> stages_;

    /** The name of the class that implements the stage strategy. */
    @XmlAttribute( name = NAME_STRATEGY, required = true )
    private final String strategyClassName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XmlStage} class.
     */
    private XmlStage()
    {
        cardinality_ = Integer.valueOf( 0 );
        id_ = null;
        stages_ = new ArrayList<XmlStage>();
        strategyClassName_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the stage strategy class identified by the
     * current stage strategy class name.
     * 
     * @return A new stage strategy; never {@code null}.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If the strategy class name was not specified in the XML, the
     *         strategy class is unknown, or an error occurred while
     *         instantiating the strategy class.
     */
    /* @NonNull */
    private IStageStrategy createStrategy()
        throws GameSystemException
    {
        IStageStrategy strategy = createStrategyFromClasspath();
        if( strategy != null )
        {
            return strategy;
        }

        strategy = createStrategyFromComponentService();
        if( strategy != null )
        {
            return strategy;
        }

        throw new GameSystemException( Messages.XmlStage_createStrategy_unknownClass( strategyClassName_ ) );
    }

    /**
     * Creates a new instance of the stage strategy class identified by the
     * current stage strategy class name using the bundle's classpath.
     * 
     * @return A new stage strategy or {@code null} if the strategy class is not
     *         available from the classpath.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurred while instantiating the strategy class.
     */
    /* @Nullable */
    private IStageStrategy createStrategyFromClasspath()
        throws GameSystemException
    {
        try
        {
            return (IStageStrategy)Class.forName( strategyClassName_ ).newInstance();
        }
        catch( final ClassNotFoundException e )
        {
            return null;
        }
        catch( final InstantiationException e )
        {
            throw new GameSystemException( Messages.XmlStage_createStrategy_instantiationError( strategyClassName_ ), e );
        }
        catch( final IllegalAccessException e )
        {
            throw new GameSystemException( Messages.XmlStage_createStrategy_accessError( strategyClassName_ ), e );
        }
        catch( final ClassCastException e )
        {
            throw new GameSystemException( Messages.XmlStage_createStrategy_notStrategy( strategyClassName_ ), e );
        }
    }

    /**
     * Creates a new instance of the stage strategy class identified by the
     * current stage strategy class name using the component service.
     * 
     * @return A new stage strategy or {@code null} if the strategy class is not
     *         available from the component service.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurred while instantiating the strategy class.
     */
    /* @Nullable */
    private IStageStrategy createStrategyFromComponentService()
        throws GameSystemException
    {
        final IComponentSpecification specification = new ClassNameComponentSpecification( strategyClassName_ );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, strategyClassName_ );

        try
        {
            return (IStageStrategy)Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
        }
        catch( final UnsupportedComponentSpecificationException e )
        {
            return null;
        }
        catch( final ComponentException e )
        {
            throw new GameSystemException( Messages.XmlStage_createStrategy_instantiationError( strategyClassName_ ), e );
        }
        catch( final ClassCastException e )
        {
            throw new GameSystemException( Messages.XmlStage_createStrategy_notStrategy( strategyClassName_ ), e );
        }
    }

    /**
     * Creates a new stage based on the current state of this object.
     * 
     * @return A new stage; never {@code null}.
     * 
     * @throws org.gamegineer.game.core.system.GameSystemException
     *         If an error occurs while creating the stage.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    public IStage toStage()
        throws GameSystemException
    {
        // XXX: Is catching unchecked exceptions in this case a bad smell?  Should we
        // have wrappers around the standard builders that throw checked exceptions
        // to be used when the input source is unknown?  Compare to how one might use
        // Integer.parseInt() in scenarios where the input source is known (programmer)
        // and unknown (user).

        final IStageStrategy strategy = createStrategy();

        try
        {
            final StageBuilder builder = new StageBuilder();
            builder.setId( id_ );
            builder.setStrategy( strategy );
            builder.setCardinality( cardinality_ );
            for( final XmlStage stage : stages_ )
            {
                builder.addStage( stage.toStage() );
            }
            return builder.toStage();
        }
        catch( final RuntimeException e )
        {
            throw new GameSystemException( Messages.XmlStage_toStage_builderError, e );
        }
    }
}

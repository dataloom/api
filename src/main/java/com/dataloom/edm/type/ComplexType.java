package com.dataloom.edm.type;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.olingo.commons.api.edm.FullQualifiedName;

import com.dataloom.authorization.securable.AbstractSchemaAssociatedSecurableType;
import com.dataloom.authorization.securable.SecurableObjectType;
import com.dataloom.client.serialization.SerializationConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

public class ComplexType extends AbstractSchemaAssociatedSecurableType {

    private final LinkedHashSet<UUID> properties;
    private final Optional<UUID>      baseType;
    private final SecurableObjectType category;
    private transient int             h = 0;

    @JsonCreator
    public ComplexType(
            @JsonProperty( SerializationConstants.ID_FIELD ) Optional<UUID> id,
            @JsonProperty( SerializationConstants.TYPE_FIELD ) FullQualifiedName type,
            @JsonProperty( SerializationConstants.TITLE_FIELD ) String title,
            @JsonProperty( SerializationConstants.DESCRIPTION_FIELD ) Optional<String> description,
            @JsonProperty( SerializationConstants.SCHEMAS ) Set<FullQualifiedName> schemas,
            @JsonProperty( SerializationConstants.PROPERTIES_FIELD ) LinkedHashSet<UUID> properties,
            @JsonProperty( SerializationConstants.PARENT_TYPE_FIELD ) Optional<UUID> baseType,
            @JsonProperty( SerializationConstants.CATEGORY ) SecurableObjectType category ) {
        super( id, type, title, description, schemas );
        this.properties = checkNotNull( properties, "Entity set properties cannot be null" );
        this.baseType = baseType;
        this.category = category;
    }

    public ComplexType(
            UUID id,
            FullQualifiedName type,
            String title,
            Optional<String> description,
            Set<FullQualifiedName> schemas,
            LinkedHashSet<UUID> properties,
            Optional<UUID> baseType,
            SecurableObjectType category ) {
        this( Optional.of( id ), type, title, description, schemas, properties, baseType, category );
    }

    public ComplexType(
            FullQualifiedName type,
            String title,
            String description,
            Set<FullQualifiedName> schemas,
            LinkedHashSet<UUID> properties,
            Optional<UUID> baseType,
            SecurableObjectType category ) {
        this( Optional.absent(), type, title, Optional.of( description ), schemas, properties, baseType, category );
    }

    @JsonProperty( SerializationConstants.PARENT_TYPE_FIELD )
    public Optional<UUID> getBaseType() {
        return baseType;
    }

    @JsonProperty( SerializationConstants.PROPERTIES_FIELD )
    public Set<UUID> getProperties() {
        return Collections.unmodifiableSet( properties );
    }

    public void addPropertyTypes( Set<UUID> propertyTypeIds ) {
        properties.addAll( checkNotNull( propertyTypeIds, "Property type ids cannot be null." ) );
    }

    public void removePropertyTypes( Set<UUID> propertyTypeIds ) {
        properties.removeAll( checkNotNull( propertyTypeIds, "Property type ids cannot be null." ) );
    }

    @Override
    @JsonProperty( SerializationConstants.CATEGORY )
    public SecurableObjectType getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        if ( h == 0 ) {
            int result = super.hashCode();
            result = prime * result + ( ( baseType == null ) ? 0 : baseType.hashCode() );
            result = prime * result + ( ( category == null ) ? 0 : category.hashCode() );
            result = prime * result + ( ( properties == null ) ? 0 : properties.hashCode() );
            h = result;
        }
        return h;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( !super.equals( obj ) ) return false;
        if ( getClass() != obj.getClass() ) return false;
        ComplexType other = (ComplexType) obj;
        if ( baseType == null ) {
            if ( other.baseType != null ) return false;
        } else if ( !baseType.equals( other.baseType ) ) return false;
        if ( category != other.category ) return false;
        if ( properties == null ) {
            if ( other.properties != null ) return false;
        } else if ( !properties.equals( other.properties ) ) return false;
        return true;
    }

}

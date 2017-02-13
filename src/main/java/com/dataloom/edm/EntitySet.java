/*
 * Copyright 2017 Kryptnostic, Inc. (dba Loom)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.dataloom.edm;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;

import com.dataloom.authorization.securable.AbstractSecurableObject;
import org.apache.commons.lang3.StringUtils;

import com.dataloom.authorization.securable.SecurableObjectType;
import com.dataloom.client.serialization.SerializationConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class EntitySet extends AbstractSecurableObject {
    private final UUID   entityTypeId;
    private final String name;

    /**
     * Creates an entity set with provided parameters and will automatically generate a UUID if not provided.
     * 
     * @param id An optional UUID for the entity set.
     * @param name The name of the entity set.
     * @param title The friendly name for the entity set.
     * @param description A description of the entity set.
     */
    @JsonCreator
    public EntitySet(
            @JsonProperty( SerializationConstants.ID_FIELD ) Optional<UUID> id,
            @JsonProperty( SerializationConstants.ENTITY_TYPE_ID_FIELD ) UUID entityTypeId,
            @JsonProperty( SerializationConstants.NAME_FIELD ) String name,
            @JsonProperty( SerializationConstants.TITLE_FIELD ) String title,
            @JsonProperty( SerializationConstants.DESCRIPTION_FIELD ) Optional<String> description ) {
        super( id, title, description );
        checkArgument( StringUtils.isNotBlank( name ), "Entity set name cannot be blank." );
        checkArgument( StringUtils.isNotBlank( title ), "Entity set title cannot be blank." );
        this.name = name;
        this.entityTypeId = checkNotNull( entityTypeId );
    }

    public EntitySet(
            UUID id,
            UUID entityTypeId,
            String name,
            String title,
            Optional<String> description ) {
        this( Optional.of( id ), entityTypeId, name, title, description );
    }

    public EntitySet(
            UUID entityTypeId,
            String name,
            String title,
            Optional<String> description ) {
        this( Optional.absent(), entityTypeId, name, title, description );
    }

    public UUID getEntityTypeId() {
        return entityTypeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( entityTypeId == null ) ? 0 : entityTypeId.hashCode() );
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( !super.equals( obj ) ) {
            return false;
        }
        if ( !( obj instanceof EntitySet ) ) {
            return false;
        }
        EntitySet other = (EntitySet) obj;
        if ( entityTypeId == null ) {
            if ( other.entityTypeId != null ) {
                return false;
            }
        } else if ( !entityTypeId.equals( other.entityTypeId ) ) {
            return false;
        }
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        } else if ( !name.equals( other.name ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntitySet [entityTypeId=" + entityTypeId + ", name=" + name + ", id=" + id
                + ", title=" + title + ", description=" + description + "]";
    }

    @Override
    public SecurableObjectType getCategory() {
        return SecurableObjectType.EntitySet;
    }
}
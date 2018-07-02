/*
 * Copyright (C) 2018. OpenLattice, Inc.
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
 * You can contact the owner of the copyright at support@openlattice.com
 *
 *
 */

package com.openlattice.graph.query;

import static com.openlattice.client.serialization.SerializationConstants.ENTITY_SET_ID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public class EntitySetQuery implements EntityQuery {
    private final UUID entitySetId;

    @JsonCreator
    public EntitySetQuery(
            @JsonProperty( ENTITY_SET_ID ) UUID entitySetId ) {
        this.entitySetId = entitySetId;
    }

    @Override public boolean equals( Object o ) {
        if ( this == o ) { return true; }
        if ( !( o instanceof EntitySetQuery ) ) { return false; }
        EntitySetQuery that = (EntitySetQuery) o;
        return Objects.equals( entitySetId, that.entitySetId );
    }

    @Override public int hashCode() {

        return Objects.hash( entitySetId );
    }

    @JsonProperty( ENTITY_SET_ID )
    public UUID getEntitySetId() {
        return entitySetId;
    }
}
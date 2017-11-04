package com.dataloom.edm.set;

import com.dataloom.client.serialization.SerializationConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntitySetPropertyMetadata {

    private String  title;
    private String  description;
    private boolean defaultShow;

    @JsonCreator
    public EntitySetPropertyMetadata(
            @JsonProperty( SerializationConstants.TITLE_FIELD ) String title,
            @JsonProperty( SerializationConstants.DESCRIPTION_FIELD ) String description,
            @JsonProperty( SerializationConstants.DEFAULT_SHOW ) boolean defaultShow ) {
        this.title = title;
        this.description = description;
        this.defaultShow = defaultShow;
    }

    @JsonProperty( SerializationConstants.TITLE_FIELD )
    public String getTitle() {
        return title;
    }

    @JsonProperty( SerializationConstants.DESCRIPTION_FIELD )
    public String getDescription() {
        return description;
    }

    @JsonProperty( SerializationConstants.DEFAULT_SHOW )
    public boolean getDefaultShow() {
        return defaultShow;
    }
    
    @JsonIgnore
    public void setTitle( String title ) {
        this.title = title;
    }
    
    @JsonIgnore
    public void setDescription( String description ) {
        this.description = description;
    }
    
    @JsonIgnore
    public void setDefaultShow( boolean defaultShow ) {
        this.defaultShow = defaultShow;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( defaultShow ? 1231 : 1237 );
        result = prime * result + ( ( description == null ) ? 0 : description.hashCode() );
        result = prime * result + ( ( title == null ) ? 0 : title.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        EntitySetPropertyMetadata other = (EntitySetPropertyMetadata) obj;
        if ( defaultShow != other.defaultShow ) return false;
        if ( description == null ) {
            if ( other.description != null ) return false;
        } else if ( !description.equals( other.description ) ) return false;
        if ( title == null ) {
            if ( other.title != null ) return false;
        } else if ( !title.equals( other.title ) ) return false;
        return true;
    }

}
package com.dataloom.exceptions;

import java.net.HttpURLConnection;

import com.dataloom.client.serialization.SerializationConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(
    shape = JsonFormat.Shape.OBJECT )
public enum LoomExceptions {
    RESOURCE_NOT_FOUND_EXCEPTION( "resourceNotFound", HttpURLConnection.HTTP_NOT_FOUND ),
    ILLEGAL_ARGUMENT_EXCEPTION( "illegalArgument", HttpURLConnection.HTTP_BAD_REQUEST ),
    ILLEGAL_STATE_EXCEPTION( "illegalState", HttpURLConnection.HTTP_INTERNAL_ERROR ),
    TYPE_EXISTS_EXCEPTION( "typeExists", HttpURLConnection.HTTP_CONFLICT ),
    FORBIDDEN_EXCEPTION( "forbidden", HttpURLConnection.HTTP_FORBIDDEN ),
    TOKEN_REFRESH_EXCEPTION( "tokenNeedsRefresh", HttpURLConnection.HTTP_UNAUTHORIZED ),
    AUTH0_TOKEN_EXCEPTION( "tokenError", HttpURLConnection.HTTP_UNAUTHORIZED ),
    OTHER_EXCEPTION( "otherError", HttpURLConnection.HTTP_INTERNAL_ERROR );

    private String type;
    private int    code;

    private LoomExceptions( String type, int code ) {
        this.type = type;
        this.code = code;
    }

    @JsonProperty( SerializationConstants.TYPE_FIELD )
    public String getType() {
        return type;
    }

    @JsonProperty( SerializationConstants.HTTP_STATUS_CODE )
    public int getCode() {
        return code;
    }
}
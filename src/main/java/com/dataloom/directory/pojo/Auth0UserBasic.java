package com.dataloom.directory.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

import java.util.*;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Auth0UserBasic {
    public static final String USER_ID_FIELD       = "user_id";
    public static final String EMAIL_FIELD         = "email";
    public static final String NICKNAME_FIELD      = "nickname";
    public static final String USERNAME_FIELD      = "username";
    public static final String APP_METADATA_FIELD  = "app_metadata";
    public static final String ROLES_FIELD         = "roles";
    public static final String ORGANIZATIONS_FIELD = "organization";

    private final String      userId;
    private final String      email;
    private final String      nickname;
    private final String      username;
    private final Set<String> roles;
    private final Set<String> organizations;

    @SuppressWarnings( "unchecked" )
    @JsonCreator
    @JsonIgnoreProperties(
            ignoreUnknown = true )
    public Auth0UserBasic(
            @JsonProperty( USER_ID_FIELD ) String userId,
            @JsonProperty( EMAIL_FIELD ) String email,
            @JsonProperty( NICKNAME_FIELD ) String nickname,
            @JsonProperty( APP_METADATA_FIELD ) Map<String, Object> appMetadata ) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        if ( this.email != null ) {
            this.username = this.email;
        } else if ( this.nickname != null ) {
            this.username = this.nickname;
        } else {
            this.username = this.userId;
        }
        this.roles = Sets.newHashSet(
                ( (List<String>) MoreObjects.firstNonNull( appMetadata, new HashMap<>() ).getOrDefault( "roles",
                        new ArrayList<String>() ) ) );
        this.organizations = Sets
                .newHashSet( ( (List<String>) MoreObjects.firstNonNull( appMetadata, new HashMap<>() ).getOrDefault(
                        "roles",
                        new ArrayList<String>() ) ) );
    }

    @JsonProperty( USER_ID_FIELD )
    public String getUserId() {
        return userId;
    }

    @JsonProperty( EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( NICKNAME_FIELD )
    public String getNickname() {
        return nickname;
    }

    @JsonProperty( USERNAME_FIELD )
    public String getUsername() {
        return username;
    }

    @JsonProperty( ROLES_FIELD )
    public Set<String> getRoles() {
        return Collections.unmodifiableSet( roles );
    }

    @JsonProperty( ORGANIZATIONS_FIELD )
    public Set<String> getOrganizations() {
        return Collections.unmodifiableSet( organizations );
    }
}

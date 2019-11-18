package com.openlattice.organizations

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.openlattice.apps.AppTypeSetting
import com.openlattice.authorization.AclKey
import com.openlattice.authorization.Principal
import com.openlattice.authorization.PrincipalType
import com.openlattice.client.serialization.SerializationConstants
import com.openlattice.notifications.sms.SmsEntitySetInformation
import com.openlattice.organization.OrganizationPrincipal
import com.openlattice.organization.roles.Role
import java.util.*

/**
 * Organization data object
 * These are mutable for convenience, but the only way to persistently mutate is through entry processor.
 * If you mutate these or pass around values do so at your own risk.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */

data class Organization(
        val securablePrincipal: OrganizationPrincipal,
        @JsonProperty(SerializationConstants.EMAILS_FIELD) val autoApprovedEmails: MutableSet<String>,
        @JsonProperty(SerializationConstants.MEMBERS_FIELD) val members: MutableSet<Principal>,
        @JsonProperty(SerializationConstants.ROLES) val roles: MutableSet<Role>,
        @JsonProperty(SerializationConstants.SMS_ENTITY_SET_INFO)
        val smsEntitySetInfo: MutableSet<SmsEntitySetInformation>,
        @JsonProperty(SerializationConstants.PARTITIONS) val partitions: MutableList<Int> = mutableListOf(),
        @JsonProperty(SerializationConstants.APPS) val apps: MutableSet<UUID> = mutableSetOf(),
        val appConfigs: Map<UUID, Map<UUID, AppTypeSetting>> = mutableMapOf(),
        @JsonProperty(SerializationConstants.AUTO_ENROLLMENTS) val autoEnrollments: MutableSet<String> = mutableSetOf(),
        @JsonProperty(SerializationConstants.GRANTS) val grants: MutableMap<UUID, Grant> = mutableMapOf()
) {

    val id: UUID
        @JsonProperty(SerializationConstants.ID_FIELD)
        get() = securablePrincipal.id
    val principal: Principal
        @JsonProperty(SerializationConstants.PRINCIPAL)
        get() = securablePrincipal.principal

    val title: String
        @JsonProperty(SerializationConstants.TITLE_FIELD)
        get() = securablePrincipal.title
    val description: String
        @JsonProperty(SerializationConstants.DESCRIPTION_FIELD)
        get() = securablePrincipal.description


    @JsonCreator
    constructor(
            @JsonProperty(SerializationConstants.ID_FIELD) id: Optional<UUID>,
            @JsonProperty(SerializationConstants.PRINCIPAL) principal: Principal,
            @JsonProperty(SerializationConstants.TITLE_FIELD) title: String,
            @JsonProperty(SerializationConstants.DESCRIPTION_FIELD) description: Optional<String>,
            @JsonProperty(SerializationConstants.EMAILS_FIELD) autoApprovedEmails: MutableSet<String>,
            @JsonProperty(SerializationConstants.MEMBERS_FIELD) members: MutableSet<Principal>,
            @JsonProperty(SerializationConstants.ROLES) roles: MutableSet<Role>,
            @JsonProperty(SerializationConstants.APPS) apps: MutableSet<UUID>,
            @JsonProperty(SerializationConstants.SMS_ENTITY_SET_INFO)
            smsEntitySetInfo: Optional<MutableSet<SmsEntitySetInformation>>,
            @JsonProperty(SerializationConstants.PARTITIONS) partitions: Optional<MutableList<Int>>,
            @JsonProperty(SerializationConstants.AUTO_ENROLLMENTS) autoEnrollments: MutableSet<String> = mutableSetOf(),
            @JsonProperty(SerializationConstants.GRANTS) grants: MutableMap<UUID, Grant> = mutableMapOf()
    ) : this(
            OrganizationPrincipal(id, principal, title, description),
            autoApprovedEmails,
            members,
            roles,
            smsEntitySetInfo.orElse(mutableSetOf<SmsEntitySetInformation>()),
            partitions.orElse(mutableListOf()),
            apps
    )


    init {
        check(securablePrincipal.principalType == PrincipalType.ORGANIZATION) { "Organization principal must be of PrincipalType.ORGANIZATION" }
    }

    constructor(
            principal: OrganizationPrincipal,
            autoApprovedEmails: MutableSet<String>,
            members: MutableSet<Principal>,
            roles: MutableSet<Role>
    ) : this(
            principal,
            autoApprovedEmails,
            members,
            roles,
            mutableSetOf()
    )

    constructor(
            id: Optional<UUID>,
            principal: Principal,
            title: String,
            description: Optional<String>,
            autoApprovedEmails: MutableSet<String>,
            members: MutableSet<Principal>,
            roles: MutableSet<Role>
    ) : this(
            id,
            principal,
            title,
            description,
            autoApprovedEmails,
            members,
            roles,
            mutableSetOf<UUID>(),
            Optional.empty(),
            Optional.empty()
    )

    constructor(
            id: Optional<UUID>,
            principal: Principal,
            title: String,
            description: Optional<String>,
            autoApprovedEmails: MutableSet<String>,
            members: MutableSet<Principal>,
            roles: MutableSet<Role>,
            partitions: MutableList<Int>
    ) : this(
            id,
            principal,
            title,
            description,
            autoApprovedEmails,
            members,
            roles,
            mutableSetOf(),
            Optional.empty(),
            Optional.of(partitions)
    )

    @JsonIgnore
    fun getAclKey(): AclKey {
        return securablePrincipal.aclKey
    }


}
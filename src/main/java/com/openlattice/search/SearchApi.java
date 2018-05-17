/*
 * Copyright (C) 2018. OpenLattice, Inc.
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
 * You can contact the owner of the copyright at support@openlattice.com
 */

package com.openlattice.search;

import com.openlattice.search.requests.AdvancedSearch;
import com.openlattice.search.requests.FQNSearchTerm;
import com.openlattice.search.requests.Search;
import com.openlattice.search.requests.SearchResult;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.openlattice.data.requests.NeighborEntityDetails;
import com.openlattice.edm.EntitySet;
import com.openlattice.search.requests.DataSearchResult;
import com.openlattice.search.requests.SearchTerm;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchApi {
    /*
     * These determine the service routing for the LB
     */
    String SERVICE    = "/datastore";
    String CONTROLLER = "/search";
    String BASE       = SERVICE + CONTROLLER;

    /*
     * Normal params
     */
    String POPULAR           = "/popular";
    String ORGANIZATIONS     = "/organizations";
    String ENTITY_SETS       = "/entity_sets";
    String ENTITY_TYPES      = "/entity_types";
    String ASSOCIATION_TYPES = "/association_types";
    String PROPERTY_TYPES    = "/property_types";
    String APP               = "/app";
    String APP_TYPES         = "/app_types";
    String ADVANCED          = "/advanced";
    String HOME              = "/home";
    String FQN               = "/fqn";
    String NEIGHBORS         = "/neighbors";
    String EDM               = "/edm";
    String INDEX             = "/index";
    String KEYWORD           = "kw";
    String ENTITY_TYPE_ID    = "eid";
    String PROPERTY_TYPE_ID  = "pid";

    String ENTITY_SET_ID = "entitySetId";
    String NUM_RESULTS   = "numResults";
    String ENTITY_ID     = "entityId";
    String START         = "start";

    String ENTITY_SET_ID_PATH = "/{" + ENTITY_SET_ID + "}";
    String NUM_RESULTS_PATH   = "/{" + NUM_RESULTS + "}";
    String ENTITY_ID_PATH     = "/{" + ENTITY_ID + "}";
    String START_PATH         = "/{" + START + "}";

    /**
     * The query, entityType, and propertyTypes params are all optional, but at least one must be specified otherwise an
     * error will be thrown. All specified params are required to be present in each entity set returned. If entityType
     * and propertyTypes are both specified, the propertyTypes param will be ignored.
     *
     * @param search A JSON object that contains between three and five parameters. Required parameters are "start" and
     *               "maxHits, which specify the hit number to start returning results on for paging and the maximum number
     *               of hits to return. Optional parameters are "query" (specifies the keywords used to perform the
     *               search), "eid" (UUID of the entity type of the entity sets that will be returned), and "pid" (a set of
     *               UUIDs of property types belonging to the entity sets that will be returned). All three of these
     *               parameters are optional, but at least one must be specified otherwise an error will be thrown. If eid
     *               and pid are both specified, the pid param will be ignored.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE )
    SearchResult executeEntitySetKeywordQuery( @Body Search search );

    @GET( BASE + POPULAR )
    Iterable<EntitySet> getPopularEntitySet();

    /**
     * Executes a search over all existing entity sets to populate the home page. The path parameters instruct which
     * page to return and how large the page should be.
     *
     * @param start   The first result number to return
     * @param maxHits The total number of results to return
     * @return
     */
    @GET( BASE + ENTITY_SETS + START_PATH + NUM_RESULTS_PATH )
    SearchResult getEntitySets(
            @Path( START ) int start,
            @Path( NUM_RESULTS ) int maxHits );

    /**
     * Executes a search over the data of a given entity set to find rows that match the search term
     *
     * @param entitySetId The id of the entity set the search will be executed within
     * @param searchTerm  A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                    returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                    "searchTerm", which is the search term results will match on.
     * @return A data search result object, containing the total number of hits for the given query, and the hits
     * themselves
     */
    @POST( BASE + ENTITY_SET_ID_PATH )
    DataSearchResult executeEntitySetDataQuery(
            @Path( ENTITY_SET_ID ) UUID entitySetId,
            @Body SearchTerm searchTerm );

    /**
     * Executes a search over the data of a given entity set to find rows matching the specified property type values
     *
     * @param entitySetId The id of the entity set the search will be executed within
     * @param search      A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                    returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                    "searchFields", which is a map from property type ids to search terms to match on those property
     *                    types.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + ADVANCED + ENTITY_SET_ID_PATH )
    DataSearchResult executeAdvancedEntitySetDataQuery(
            @Path( ENTITY_SET_ID ) UUID entitySetId,
            @Body AdvancedSearch search );

    /**
     * Executes a search over all organizations to find ones that match the given search term
     *
     * @param searchTerm A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                   "searchTerm", which is the search term results will match on.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + ORGANIZATIONS )
    SearchResult executeOrganizationSearch( @Body SearchTerm searchTerm );

    /**
     * Executes a search over all entity types to find ones that match the given search term
     *
     * @param searchTerm A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                   "searchTerm", which is the search term results will match on.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + ENTITY_TYPES )
    SearchResult executeEntityTypeSearch( @Body SearchTerm searchTerm );

    /**
     * Executes a search over all association types to find ones that match the given search term
     *
     * @param searchTerm A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                   "searchTerm", which is the search term results will match on.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + ASSOCIATION_TYPES )
    SearchResult executeAssociationTypeSearch( @Body SearchTerm searchTerm );

    /**
     * Executes a search over all property types to find ones that match the given search term
     *
     * @param searchTerm A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                   "searchTerm", which is the search term results will match on.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + PROPERTY_TYPES )
    SearchResult executePropertyTypeSearch( @Body SearchTerm searchTerm );

    /**
     * Executes a search over all apps to find ones that match the given search term
     *
     * @param searchTerm A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                   "searchTerm", which is the search term results will match on.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + APP )
    SearchResult executeAppSearch( @Body SearchTerm searchTerm );

    /**
     * Executes a search over all app types to find ones that match the given search term
     *
     * @param searchTerm A JSON object that contains three parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return, and
     *                   "searchTerm", which is the search term results will match on.
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + APP_TYPES )
    SearchResult executeAppTypeSearch( @Body SearchTerm searchTerm );

    /**
     * Executes a search over all entity types to find ones that match the given name and namespace, including partial
     * matches
     *
     * @param searchTerm A JSON object that contains four parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return,
     *                   "name", which is the partial name to match, and "namespace" which is the partial namespace to match
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + ENTITY_TYPES + FQN )
    SearchResult executeFQNEntityTypeSearch( @Body FQNSearchTerm searchTerm );

    /**
     * Executes a search over all property types to find ones that match the given name and namespace, including partial
     * matches
     *
     * @param searchTerm A JSON object that contains four parameters: "start", which specifies the hit number to start
     *                   returning results on for paging, "maxHits", which specifies the maximum number of hits to return,
     *                   "name", which is the partial name to match, and "namespace" which is the partial namespace to match
     * @return A search result object, containing the total number of hits for the given query, and the hits themselves
     */
    @POST( BASE + PROPERTY_TYPES + FQN )
    SearchResult executeFQNPropertyTypeSearch( @Body FQNSearchTerm searchTerm );

    /**
     * Executes a search for all neighbors of an entity that are connected by an association
     *
     * @param entitySetId the entity set id of the entity
     * @param entityId    the entity key id of the entity
     * @return A list of objects containing information about the neighbor and association
     */
    @GET( BASE + ENTITY_SET_ID_PATH + ENTITY_ID_PATH )
    List<NeighborEntityDetails> executeEntityNeighborSearch(
            @Path( ENTITY_SET_ID ) UUID entitySetId,
            @Path( ENTITY_ID ) UUID entityId );

    /**
     * Executes a search for all neighbors of multiple entities of the same entity set that are connected by an
     * association
     *
     * @param entitySetId the entity set id of the entities
     * @param entityIds   the entity key ids of the entities
     * @return A map from each entity id to a list of objects containing information about the neighbors and
     * associations of that entity
     */
    @POST( BASE + ENTITY_SET_ID_PATH + NEIGHBORS )
    Map<UUID, List<NeighborEntityDetails>> executeEntityNeighborSearchBulk(
            @Path( ENTITY_SET_ID ) UUID entitySetId,
            @Body Set<UUID> entityIds );

    @GET( BASE + EDM + INDEX )
    Void triggerEdmIndex();

    @GET( BASE + ENTITY_SETS + INDEX + ENTITY_SET_ID_PATH )
    Void trigerEntitySetDataIndex( @Path( ENTITY_SET_ID ) UUID entitySetId );

    @GET( BASE + ENTITY_SETS + INDEX )
    Void triggerAllEntitySetDataIndex();

}
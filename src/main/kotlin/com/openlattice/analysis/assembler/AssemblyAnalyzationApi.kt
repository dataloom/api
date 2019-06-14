/*
 * Copyright (C) 2019. OpenLattice, Inc.
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

package com.openlattice.analysis.assembler

import retrofit2.http.Body
import retrofit2.http.POST


const val SERVICE = "/datastore"
const val CONTROLLER = "/assembly-analysis"
const val BASE = SERVICE + CONTROLLER

const val SIMPLE_AGGREGATION = "/simple-aggregation"

interface AssemblyAnalyzationApi {

    @POST(BASE + SIMPLE_AGGREGATION)
    fun getSimpleAssemblyAggregates(@Body assemblyAggregationFilter: AssemblyAggregationFilter): Iterable<Map<String, Any>>

}
package com.diva.models.database.collection.playlist

import io.github.juevigrace.diva.core.models.Option

data class MixMetadataEntity(
    val collectionId: String,
    val algorithmType: Option<String> = Option.None,
    val timeWindowHours: Option<Int> = Option.None,
    val contentWeight: Option<Double> = Option.None,
    val freshnessWeight: Option<Double> = Option.None,
    val minEngagementScore: Option<Int> = Option.None,
    val excludedTags: Option<String> = Option.None,
    val autoRefresh: Option<Boolean> = Option.None,
    val refreshIntervalSeconds: Option<Int> = Option.None,
)

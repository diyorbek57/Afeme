package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.BuildingMaterial

data class BuildingMaterialResponse(
    val data: List<BuildingMaterial>,
    val message: String,
    val status: Boolean
)
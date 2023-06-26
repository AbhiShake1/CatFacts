package com.app.catfacts.models.dataModels

data class CatsModel(
    val status: Status,
    val _id: String,
    val user: String,
    val text: String,
    val __v: Int,
    val source: String,
    val updatedAt: String,
    val type: String,
    val createdAt: String,
    val deleted: Boolean,
    val used: Boolean
) {
    data class Status(
        val verified: Boolean,
        val sentCount: Int,
        val feedback: String = ""
    )
}

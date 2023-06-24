package com.box.view.screens.admin

enum class ExpansionStatus {
    EXPANDED,
    COLLAPSED,
    NOT_EXPANDABLE,
}

data class AdminTreeTile(
    val id: Long,
    val level: Int,
    val expansionStatus: ExpansionStatus,
    val attributes: Map<String, String>
)
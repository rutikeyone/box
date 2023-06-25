package com.box.view.screens.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.R
import com.box.data.repository.AccountsRepository
import com.box.domain.entity.AccountEntity
import com.box.domain.entity.AccountFullDataEntity
import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.BoxEntity
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.Resources
import com.box.view.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AdminViewModel @AssistedInject constructor(
    @Assisted private val resources: Resources,
    private val accountsRepository: AccountsRepository,
) : BaseViewModel(), AdminTileAdapter.Listener {

    private val _items = MutableLiveData<List<AdminTreeTile>>()
    val items = _items.share()

    private val expandedIdentifiers = mutableSetOf(getRootId())
    private val expandedItemStateFlow = MutableStateFlow(ExpansionsState(expandedIdentifiers))

    override fun onItemToggled(item: AdminTreeTile) {
        if(item.expansionStatus == ExpansionStatus.NOT_EXPANDABLE) return

        if(item.expansionStatus == ExpansionStatus.EXPANDED) {
            expandedIdentifiers.remove(item.id)
        } else {
            expandedIdentifiers.add(item.id)
        }
        expandedItemStateFlow.value = ExpansionsState(expandedIdentifiers)
    }

    init {
        viewModelScope.launch {
            combine(accountsRepository.getAllData(), expandedItemStateFlow) { allData, expansionsState ->
                val rootNode = toNode(allData, expansionsState.identifiers)
                flatNodes(rootNode)
            }.collect {
                _items.value = it
            }
        }
    }

    private fun toNode(accountsDataList: List<AccountFullDataEntity>, expandedIdentifiers: Set<Long>): Node {
        val rootExpansion = getExpansionStatus(getRootId(), accountsDataList.isNotEmpty(), expandedIdentifiers)
        return Node(
            id = getRootId(),
            attributes = mapOf(resources.getString(R.string.all_accounts) to ""),
            expansionStatus = rootExpansion,
            nodes = if(rootExpansion != ExpansionStatus.EXPANDED) emptyList() else accountsDataList.map { accountData ->
                val accountExpansionStatus = getExpansionStatus(getAccountId(accountData), accountData.boxesAndSettings.isNotEmpty(), expandedIdentifiers)
                Node(
                    id = getAccountId(accountData),
                    attributes = accountToMap(accountData.account),
                    expansionStatus = accountExpansionStatus,
                    nodes = if(accountExpansionStatus != ExpansionStatus.EXPANDED) emptyList() else accountData.boxesAndSettings.map { boxAndSettingsEntity ->
                        val boxExpansionStatus = getExpansionStatus(getBoxId(boxAndSettingsEntity), true, expandedIdentifiers)
                        Node(
                            id = getBoxId(boxAndSettingsEntity),
                            attributes = boxToMap(boxAndSettingsEntity.box),
                            expansionStatus = boxExpansionStatus,
                            nodes = if(boxExpansionStatus != ExpansionStatus.EXPANDED) emptyList() else listOf(
                                Node(
                                    id = getSettingsIds(boxAndSettingsEntity, accountData),
                                    attributes = settingsToMap(boxAndSettingsEntity.isActive),
                                    expansionStatus = ExpansionStatus.NOT_EXPANDABLE,
                                    nodes = emptyList(),
                                )
                            )
                        )
                    }
                )
            }
        )
    }

    private fun flatNodes(root: Node): List<AdminTreeTile> {
        val items = mutableListOf<AdminTreeTile>()
        val level = 0
        doFlatNodes(root, level, items)
        return items
    }

    private fun doFlatNodes(node: Node, level: Int, items: MutableList<AdminTreeTile>) {
        val item = AdminTreeTile(
            id = node.id,
            level = level,
            expansionStatus = node.expansionStatus,
            attributes = node.attributes,
        )
        items.add(item)
        for(child in node.nodes) {
            doFlatNodes(child, level + 1, items)
        }
    }

    private fun getRootId() = 1L

    private fun getAccountId(accountFullDataEntity: AccountFullDataEntity): Long = accountFullDataEntity.account.id or ACCOUNT_MASK

    private fun getBoxId(boxAndSettingsEntity: BoxAndSettingsEntity): Long = boxAndSettingsEntity.box.id or BOX_MASK

    private fun getSettingsIds(boxAndSettingsEntity: BoxAndSettingsEntity, accountFullDataEntity: AccountFullDataEntity): Long =
        boxAndSettingsEntity.box.id or SETTING_MASK or (accountFullDataEntity.account.id shl 32)

    private fun getExpansionStatus(id: Long, hasChildren: Boolean, expandedIds: Set<Long>): ExpansionStatus {
        if(!hasChildren) return ExpansionStatus.NOT_EXPANDABLE
        return if(expandedIds.contains(id)) {
            ExpansionStatus.EXPANDED
        } else {
            ExpansionStatus.COLLAPSED
        }
    }

    private fun accountToMap(accountEntity: AccountEntity): Map<String, String> {
        return mapOf(
            resources.getString(R.string.account_id) to accountEntity.id.toString(),
            resources.getString(R.string.account_email) to accountEntity.email,
            resources.getString(R.string.username) to accountEntity.username
        )
    }

    private fun boxToMap(boxEntity: BoxEntity): Map<String, String> {
        return mapOf(
            resources.getString(R.string.box_id) to boxEntity.id.toString(),
            resources.getString(R.string.box_name) to boxEntity.colorName,
            resources.getString(R.string.color_value) to String.format("#%06X", (0xFFFFFF and boxEntity.colorValue))
        )
    }

    private fun settingsToMap(isActive: Boolean): Map<String, String> {
        val isActiveValue = resources.getString(if (isActive) R.string.yes else R.string.no)
        return mapOf(resources.getString(R.string.settings_is_active) to isActiveValue)
    }

    private class Node(
        val id: Long,
        val attributes: Map<String, String>,
        val expansionStatus: ExpansionStatus,
        val nodes: List<Node>
    )

    private class ExpansionsState(
        val identifiers: Set<Long>
    )

    private companion object {
        const val  ACCOUNT_MASK = 0x2L shl 60
        const val  BOX_MASK = 0x3L shl 60
        const val  SETTING_MASK = 0x4L shl 60
    }

    @AssistedFactory
    interface Factory {
        fun create(resources: Resources): AdminViewModel
    }

}
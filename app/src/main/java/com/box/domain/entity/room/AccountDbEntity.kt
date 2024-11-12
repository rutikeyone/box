package com.box.domain.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.box.data.repository.SecureUtilsRepository
import com.box.domain.entity.AccountEntity
import com.box.domain.entity.SignUpDataEntity

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)
data class AccountDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt", defaultValue = "") val salt: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "phone") val phone: String?,
) {
    fun toAccount(): AccountEntity = AccountEntity(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )

    companion object {
        fun fromSignUpData(signUpDataEntity: SignUpDataEntity, secureUtilsRepository: SecureUtilsRepository): AccountDbEntity {
            val salt = secureUtilsRepository.generateSalt()
            val hash = secureUtilsRepository.passwordToHash(signUpDataEntity.password, salt)

            return AccountDbEntity(
                id = 0,
                email = signUpDataEntity.email,
                username = signUpDataEntity.username,
                hash = secureUtilsRepository.bytesToString(hash),
                salt = secureUtilsRepository.bytesToString(salt),
                createdAt = System.currentTimeMillis(),
                phone = null,
            )
        }
    }
}
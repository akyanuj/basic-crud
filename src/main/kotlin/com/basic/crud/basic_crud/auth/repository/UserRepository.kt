package com.basic.crud.basic_crud.auth.repository

import com.basic.crud.basic_crud.auth.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

//UserEntity - Which table/entity does this repository manage?
//Long - What is the type of the Primary Key?
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): UserEntity?

    fun deleteByEmail(email: String)

}
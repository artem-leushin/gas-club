package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.entity.local.VkSessionDB
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.entity.VkSession
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersDBTest {

  private val userDomain = User(1, "asd", "dsa", "asas", "www")
  private val userDB = UserDB(1, "asd", "dsa", "asas", "www")

  private val vkSessionDomain = VkSession(1, "asd", "dsa", "asas")
  private val vkSessionDB = VkSessionDB(1, "asd", "dsa", "asas")

  @Test
  fun `user maps between db and domain`() {
    assertEquals(userDomain.toDB(), userDB)
    assertEquals(userDB.toDomain(), userDomain)
  }

  @Test
  fun `vk session maps between db and domain`() {
    assertEquals(vkSessionDomain.toDB(), vkSessionDB)
    assertEquals(vkSessionDB.toDomain(), vkSessionDomain)
  }
}


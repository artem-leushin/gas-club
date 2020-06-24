package com.musicgear.gas.data.mappers

import com.musicgear.gas.data.entity.remote.AlbumR
import com.musicgear.gas.data.entity.remote.PhotoAttachmentR
import com.musicgear.gas.data.entity.remote.PhotoCommentR
import com.musicgear.gas.data.entity.remote.PhotoR
import com.musicgear.gas.data.entity.remote.SizeR
import com.musicgear.gas.data.entity.remote.UserR
import com.musicgear.gas.domain.entity.AttachmentType
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.entity.Comment
import com.musicgear.gas.domain.entity.InstrumentPhoto
import com.musicgear.gas.domain.entity.PhotoAttachment
import com.musicgear.gas.domain.entity.Size
import com.musicgear.gas.domain.entity.SizeType
import com.musicgear.gas.domain.entity.User
import org.junit.Assert
import org.junit.Test
import org.threeten.bp.LocalDateTime

class MappersRTest {

  private val userD = User(1, "asd", "dsa", "asas", "www")
  private val userR = UserR(1, "asd", "dsa", "asas", "www")

  private val sizeD = Size("asd", 11, 22, SizeType.PROPORTIONAL_130)
  private val sizeR = SizeR("asd", 11, 22, SizeType.PROPORTIONAL_130)

  private val albumD = Category(1, "asd", "dsa", 11, "www", listOf(sizeD))
  private val albumR = AlbumR(1, "asd", "dsa", 11, "www", listOf(sizeR))

  private val photoD = InstrumentPhoto(1, 11, 22, "", LocalDateTime.MIN, listOf(sizeD))
  private val photoR = PhotoR(1, 11, 22, "", LocalDateTime.MIN, listOf(sizeR))

  private val attachmentD = PhotoAttachment(AttachmentType.AUDIO, photoD)
  private val attachmentR = PhotoAttachmentR(AttachmentType.AUDIO, photoR)

  private val commentD = Comment(1, 11, "asdasd", listOf(attachmentD))
  private val commentR = PhotoCommentR(1, 11, "asdasd", listOf(attachmentR))

  @Test
  fun `user maps between api and domain`() {
    Assert.assertEquals(userR.toDomain(), userD)
    Assert.assertEquals(UserR().toDomain(), User.EMPTY)
  }

  @Test
  fun `photo sizes map between api and domain`() {
    Assert.assertEquals(sizeR.toDomain(), sizeD)
    Assert.assertEquals(SizeR().toDomain(), Size.EMPTY)
  }

  @Test
  fun `album maps between api and domain`() {
    Assert.assertEquals(albumR.toDomain(), albumD)
    Assert.assertEquals(AlbumR().toDomain(), Category())
  }

  @Test
  fun `photos map between api and domain`() {
    Assert.assertEquals(photoR.toDomain(), photoD)
    Assert.assertEquals(PhotoR().toDomain(), InstrumentPhoto.EMPTY)
  }

  @Test
  fun `photo attachments map between api and domain`() {
    Assert.assertEquals(attachmentR.toDomain(), attachmentD)
    Assert.assertEquals(PhotoAttachmentR().toDomain(), PhotoAttachment())
  }

  @Test
  fun `comments map between api and domain`() {
    Assert.assertEquals(commentR.toDomain(), commentD)
    Assert.assertEquals(PhotoCommentR().toDomain(), Comment())
  }
}
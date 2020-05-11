package com.musicgear.gas.data

import com.musicgear.gas.domain.entity.SizeType
import org.junit.Test

class ExampleUnitTest {

  val sizes = listOf(
    SizeType.PROPORTIONAL_75,
    SizeType.PROPORTIONAL_130,
    SizeType.PROPORTIONAL_604,
    SizeType.RATIO_130,
    SizeType.RATIO_200,
    SizeType.RATIO_320,
    SizeType.RATIO_510,
    SizeType.PROPORTIONAL_807,
    SizeType.PROPORTIONAL_X1024,
    SizeType.PROPORTIONAL_X2560
  )

  @Test
  fun `sizes priority is correct`() {

  }
}

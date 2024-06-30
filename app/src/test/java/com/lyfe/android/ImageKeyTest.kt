package com.lyfe.android

import com.lyfe.android.core.model.ImageKey
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageKeyTest {

	@Test
	fun validateRemoveLeadingSlashFunction() {
		val key = ImageKey("url")
		val keyWithPrefixedSplash = ImageKey("/url")

		assertEquals(key.removeLeadingSlash().startsWith("/"), false)
		assertEquals(keyWithPrefixedSplash.removeLeadingSlash().startsWith("/"), false)
	}
}
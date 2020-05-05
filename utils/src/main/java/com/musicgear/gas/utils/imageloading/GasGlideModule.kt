package com.musicgear.gas.utils.imageloading

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import java.io.InputStream

@GlideModule
class GasGlideModule : AppGlideModule(), KoinComponent {
  override fun applyOptions(context: Context, builder: GlideBuilder) {
    val memoryCacheSizeBytes = 1024 * 1024 * 10 // 10mb
    val diskCacheSizeBytes = 1024 * 1024 * 50 // 50 MB

    builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
      .setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
  }

  override fun isManifestParsingEnabled(): Boolean {
    return false
  }
}

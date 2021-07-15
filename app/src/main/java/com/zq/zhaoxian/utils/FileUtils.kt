package com.zq.zhaoxian.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File


/**
 * @program: owner_app
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-15 14:53
 **/
object FileUtils {

    public fun getImageContentUri(context: Context, path: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ",
            arrayOf(path),
            null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri: Uri = Uri.parse("content://media/external/images/media")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (File(path).exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, path)
                context.contentResolver
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    /**
     * 获取app缓存路径
     *
     * @param context Application Context
     * @return 缓存路径
     */
    public fun getCacheDir(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            val file = context.externalCacheDir
            if (null == file) {
                context.cacheDir.path
            } else {
                file.path
            }
        } else {
            //外部存储不可用
            context.cacheDir.path
        }
    }

    /**
     * 获取app文件路径
     *
     * @param context Application Context
     * @return app文件路径
     */
    fun getFilesDir(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            val file = context.getExternalFilesDir(null)
            if (null == file) {
                context.filesDir.path
            } else {
                file.path
            }
        } else {
            //外部存储不可用
            context.filesDir.path
        }
    }


}
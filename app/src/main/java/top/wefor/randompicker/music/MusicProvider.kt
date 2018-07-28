package top.wefor.randompicker.music

import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import java.io.File

/**
 * Created on 2018/7/26.
 * @author ice
 * @GitHub https://github.com/XunMengWinter
 */
class MusicProvider(context: Context) {

    val mMusicList = getMusicList(context, Environment.getExternalStorageDirectory())

    /**
     * 极速遍历本地音乐
     */
    private fun getMusicList(context: Context, rootDir: File): ArrayList<MusicBean> {
        val list: ArrayList<MusicBean> = arrayListOf()
        val cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.DATA + " like ?",
                arrayOf(rootDir.getPath() + "%"),
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if (cursor == null) return list;
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            // 如果不是音乐
            val isMusic = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC))
            if (isMusic != null && isMusic == "") continue

            val title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
            val artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

            val music = MusicBean()
            music.id = (cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)))
            music.title = (title)
            music.artist = (artist)
            music.filePath = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)))
            music.length = (cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)))
            music.imageFilePath = (getAlbumImage(context, cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))).orEmpty()
            list.add(music);
        }
        cursor.close()
        // id title singer data time image
        return list
    }

    private fun getAlbumImage(context: Context, albumId: Int): String? {
        var path: String? = ""
        var cursor: Cursor? = null
        try {
            cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART),
                    MediaStore.Audio.Albums._ID + "=?",
                    arrayOf<String>(albumId.toString()),
                    null)
            if (cursor.moveToFirst()) {
                // do whatever you need to do
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
            }
        } catch (e: Exception) {
        } finally {
            cursor?.close()
        }
        return path
    }

}
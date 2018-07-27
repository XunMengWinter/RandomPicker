package top.wefor.randompicker.music

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File

/**
 * Created on 2018/7/26.
 * @author ice
 */
/**
 * 100ms
 */
class MusicProvider(context: Context) {

    val mMusicList = getMusicList(context, Environment.getExternalStorageDirectory())
    val mContext = context

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
            music.image = (getAlbumImage(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)))).orEmpty()
            list.add(music);
        }
        cursor.close()
        // id title singer data time image
        return list
    }


    private fun getAlbumImage(albumId: Int): String? {
        var result: String? = ""
        var cursor: Cursor? = null
        try {
            cursor = mContext.getContentResolver().query(
                    Uri.parse("content://media/external/audio/albums/$albumId"), arrayOf("album_art"), null, null, null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                result = cursor.getString(0)
                break
            }
        } catch (e: Exception) {

        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }

        return result
    }

}
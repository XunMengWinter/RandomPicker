package top.wefor.randompicker.music

/**
 * Created on 2018/7/26.
 * @author ice
 * @GitHub https://github.com/XunMengWinter
 */
data class MusicBean(var id: Int = 0, var title: String = "", var artist: String = ""
                     , var filePath: String = "", var length: Int = 0, internal var imageFilePath: String = "") {

    override fun toString(): String {
        return "[Music(title = $title, artist = $artist)]"
    }
}
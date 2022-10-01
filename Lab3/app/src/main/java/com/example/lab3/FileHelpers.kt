package com.example.lab3

import android.content.Context
import java.io.*

object FileHelpers {

    @JvmStatic
    fun read(fileName: String): String? {
        return try {
            val fis = FileInputStream(File(fileName))
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            sb.toString()
        } catch (fileNotFound: FileNotFoundException) {
            null
        } catch (ioException: IOException) {
            null
        }
    }

    @JvmStatic
    fun create(fileName: String, jsonString: String?) {
        val file = File(fileName)
        file.createNewFile()
        
        val fos = FileOutputStream(file)
        if (jsonString != null) {
            fos.write(jsonString.toByteArray())
        }
        fos.close()
    }

    @JvmStatic
    fun isFilePresent(context: Context, fileName: String): Boolean {
        val path: String = "${context.filesDir.absolutePath}/$fileName"
        val file = File(path)
        return file.exists()
    }
}
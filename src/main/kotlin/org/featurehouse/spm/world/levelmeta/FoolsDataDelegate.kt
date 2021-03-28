package org.featurehouse.spm.world.levelmeta

import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

class FoolsDataDelegate(val file: File) {
    companion object {
        private val LOGGER = LogManager.getLogger("Fools Data Delegate")
        val FALSE = byteArrayOf(29, -91, 7, 30)
        val TRUE  = byteArrayOf(-31, 8, 8, -33)
    }

    @Throws(IOException::class)
    /**
     * @return true if created. false if existed.
     */
    private fun checkExist() : Boolean {
        val b = file.createNewFile()
        if (b) {
            file.writeBytes(FALSE)
            LOGGER.info("Created file ${file.absolutePath}")
        }
        return b
    }

    fun write(initialized: Boolean) {
        try {
            checkExist()
            file.writeBytes(if (initialized) TRUE else FALSE)
        } catch (e: IOException) {
            LOGGER.error(e)
            throw RuntimeException(e)
        }
    }

    fun read() : Boolean {
        try {
            checkExist()
            val bytes: ByteArray = file.readBytes()
            return when {
                bytes.contentEquals(TRUE) -> true
                bytes.contentEquals(FALSE) -> false
                else -> {
                    LOGGER.warn("Illegal bytes read: {} , treated as false", bytes.contentToString())
                    false
                }
            }
        } catch (e: IOException) {
            LOGGER.error(e)
            throw RuntimeException(e)
        }
    }
}
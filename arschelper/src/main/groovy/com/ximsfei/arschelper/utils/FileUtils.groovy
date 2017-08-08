package com.ximsfei.arschelper.utils

class FileUtils {
    def static byte[] readFile(File file) throws IOException {
        if (!file.exists()) {
            throw new RuntimeException(file + ": file not found")
        }

        if (!file.isFile()) {
            throw new RuntimeException(file + ": not a file")
        }

        if (!file.canRead()) {
            throw new RuntimeException(file + ": file not readable")
        }

        long longLength = file.length()
        int length = (int) longLength
        if (length != longLength) {
            throw new RuntimeException(file + ": file too long")
        }
        readInputStream(new FileInputStream(file))
    }

    def static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available())

        InputStream inputStream = null
        try {
            inputStream = new BufferedInputStream(is)
            byte[] buffer = new byte[8192]
            int bytesRead
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead)
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (Exception e) {
                    // ignored.
                }
            }
        }

        baos.toByteArray()
    }

    def static copyToFile(InputStream inputStream, File destFile) {
        copyToFile(inputStream, destFile, false)
    }

    def static copyToFile(InputStream inputStream, File destFile, boolean append) {
        if (!append && destFile.exists()) {
            destFile.delete()
        }
        if (!destFile.exists()) {
            destFile.getParentFile().mkdirs()
            destFile.createNewFile()
        }
        FileOutputStream out = new FileOutputStream(destFile, append)
        try {
            byte[] buffer = new byte[4096]
            int bytesRead
            while ((bytesRead = inputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, bytesRead)
            }
        } finally {
            out.flush()
            out.close()
        }
    }
}

package com.ximsfei.arschelper.utils

class IOUtil {
    static void close(Closeable... closeables) throws IOException {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close()
                }
            }
        }
    }

    static void closeQuietly(Closeable... closeables) {
        try {
            close(closeables)
        } catch (IOException e) {
            // do nothing
        }
    }

}

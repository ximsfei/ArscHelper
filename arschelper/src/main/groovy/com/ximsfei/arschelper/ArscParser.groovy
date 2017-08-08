package com.ximsfei.arschelper

import com.ximsfei.arschelper.arsc.Chunk
import com.ximsfei.arschelper.utils.FileUtils
import com.ximsfei.arschelper.utils.Log

import java.nio.ByteBuffer
import java.nio.ByteOrder

class ArscParser {
    List<Chunk> chunks = new ArrayList<>()

    ArscParser(File file) {
        init(FileUtils.readFile(file))
    }

    ArscParser(InputStream is) {
        init(FileUtils.readInputStream(is))
    }

    def init(byte[] bytes) {
        ByteBuffer data = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        while (data.hasRemaining()) {
            chunks.add(Chunk.create(data))
        }
        Log.d("chunk size", chunks.size() as String)
    }
}

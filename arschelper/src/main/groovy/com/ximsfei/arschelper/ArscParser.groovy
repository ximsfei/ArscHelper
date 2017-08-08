package com.ximsfei.arschelper

import com.ximsfei.arschelper.arsc.ChunkHeader
import com.ximsfei.arschelper.arsc.StringPool
import com.ximsfei.arschelper.arsc.Table
import com.ximsfei.arschelper.utils.FileUtils

import java.nio.ByteBuffer
import java.nio.ByteOrder

class ArscParser {
    Table table
    StringPool stringPool

    ArscParser(File file) {
        init(FileUtils.readFile(file))
    }

    ArscParser(InputStream is) {
        init(FileUtils.readInputStream(is))
    }

    def init(byte[] bytes) {
        int offset
        ByteBuffer data = ByteBuffer.wrap(bytes)
        data.order(ByteOrder.LITTLE_ENDIAN);
        table = Table.parse(data)
        offset = table.header.headSize
        data.position(offset)
        stringPool = StringPool.parse(data)
        offset += stringPool.header.size
        data.position(offset)
        ChunkHeader.parse(data)
    }
}

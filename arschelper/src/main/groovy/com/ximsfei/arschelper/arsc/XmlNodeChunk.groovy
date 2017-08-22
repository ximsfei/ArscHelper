package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

abstract class XmlNodeChunk extends Chunk {
    final int lineNumber
    final int comment
    XmlNodeChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        lineNumber = buffer.getInt()
        comment = buffer.getInt()
    }
}

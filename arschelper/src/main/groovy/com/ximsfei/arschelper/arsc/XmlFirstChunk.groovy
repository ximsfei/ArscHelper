package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlFirstChunk extends XmlNodeChunk {
    XmlFirstChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
    }

    @Override
    Chunk.Type getType() {
        Type.XML_FIRST_CHUNK
    }
}

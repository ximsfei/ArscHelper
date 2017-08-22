package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlEndElementChunk extends XmlNodeChunk {
    final int namespace
    final int name

    XmlEndElementChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        namespace = buffer.getInt()
        name = buffer.getInt()
    }

    @Override
    Chunk.Type getType() {
        Type.XML_END_ELEMENT
    }
}

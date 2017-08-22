package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlCdataChunk extends XmlNodeChunk {
    final int rawValue
    final ResourceValue resourceValue
    XmlCdataChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        rawValue = buffer.getInt()
        resourceValue = ResourceValue.create(buffer)
    }

    @Override
    Chunk.Type getType() {
        Type.XML_CDATA
    }
}

package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlEndNamespaceChunk extends XmlNamespaceChunk {
    XmlEndNamespaceChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
    }

    @Override
    Chunk.Type getType() {
        Type.XML_END_NAMESPACE
    }
}

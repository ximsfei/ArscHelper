package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

abstract class XmlNamespaceChunk extends XmlNodeChunk {
    final int prefix
    final int uri

    XmlNamespaceChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        prefix = buffer.getInt()
        uri = buffer.getInt()
    }
}

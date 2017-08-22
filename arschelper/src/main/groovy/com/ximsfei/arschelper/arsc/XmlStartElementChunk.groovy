package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlStartElementChunk extends XmlNodeChunk {
    private final int namespace
    private final int name
    // short
    private final int attributeStart
    private final int attributeSize
    private final int attributeCount
    // Index (1-based) of the "id" attribute. 0 if none.
    private final int idIndex
    // Index (1-based) of the "class" attribute. 0 if none.
    private final int classIndex
    // Index (1-based) of the "style" attribute. 0 if none.
    private final int styleIndex

    List<XmlAttribute> attributes = new ArrayList<>()

    XmlStartElementChunk(ByteBuffer buffer, Chunk parent) {
        super(buffer, parent)
        namespace = buffer.getInt()
        name = buffer.getInt()
        attributeStart = buffer.getShort() & 0xFFFF
        attributeSize = buffer.getShort() & 0xFFFF
        attributeCount = buffer.getShort() & 0xFFFF
        idIndex = buffer.getShort() & 0xFFFF - 1
        classIndex = buffer.getShort() & 0xFFFF - 1
        styleIndex = buffer.getShort() & 0xFFFF - 1
    }

    @Override
    def init(ByteBuffer buffer) {
        int offset = this.offset + headerSize + attributeStart
        int endOffset = offset + XmlAttribute.SIZE * attributeCount
        buffer.mark()
        buffer.position(offset)

        while (offset < endOffset) {
            attributes.add(XmlAttribute.create(buffer, this))
            offset += XmlAttribute.SIZE
        }

        buffer.reset()
    }

    @Override
    Type getType() {
        Type.XML_START_ELEMENT
    }
}

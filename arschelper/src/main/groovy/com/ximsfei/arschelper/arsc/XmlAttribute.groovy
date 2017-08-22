package com.ximsfei.arschelper.arsc

import java.nio.ByteBuffer

class XmlAttribute {
    static final int SIZE = 12 + ResourceValue.SIZE
    int namespace
    int name
    int rawValue
    ResourceValue resourceValue
    XmlNodeChunk parent

    XmlAttribute(int namespace, int name, int rawValue, ResourceValue typedValue, XmlNodeChunk parent) {
        this.namespace = namespace
        this.name = name
        this.rawValue = rawValue
        this.resourceValue = typedValue
        this.parent = parent
    }

    static XmlAttribute create(ByteBuffer buffer, XmlNodeChunk parent) {
        int namespace = buffer.getInt()
        int name = buffer.getInt()
        int rawValue = buffer.getInt()
        ResourceValue typedValue = ResourceValue.create(buffer)
        new XmlAttribute(namespace, name, rawValue, typedValue, parent)
    }
}

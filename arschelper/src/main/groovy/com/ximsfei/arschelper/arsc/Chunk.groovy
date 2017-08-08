package com.ximsfei.arschelper.arsc

import com.ximsfei.arschelper.utils.SizeOf

import java.nio.ByteBuffer

import static com.ximsfei.arschelper.arsc.Chunk.Type

/**
 * Created by ximsfei on 2017/8/8.
 */
abstract class Chunk {
    static final int SIZE = SizeOf.USHORT + SizeOf.USHORT + SizeOf.UINT
    enum Type {
        NULL(0x0000),
        STRING_POOL(0x0001),
        TABLE(0x0002),
        XML(0x0003),
        XML_FIRST_CHUNK(0x0100),
        XML_END_NAMESPACE(0x0101),
        XML_START_ELEMENT(0x0102),
        XML_END_ELEMENT(0x0103),
        XML_CDATA(0x0104),
        XML_LAST_CHUNK(0x017f),
        XML_RESOURCE_MAP(0x0180),
        TABLE_PACKAGE(0x0200),
        TABLE_TYPE(0x0201),
        TABLE_TYPE_SPEC(0x0202),
        TABLE_LIBRARY(0x0203);

        final short type
        static final Map<Short, Type> TYPES_MAP
        static {
            TYPES_MAP = new HashMap<>()
            for (Type type : values()) {
                TYPES_MAP.put(type.type, type)
            }
        }

        Type(int type) {
            this.type = type
        }

        static Type get(short type) {
            TYPES_MAP.get(type)
        }
    }

    final short headerSize
    final int size
    final int offset
    final Chunk parent

    Chunk(ByteBuffer buffer, Chunk parent) {
        this.parent = parent
        offset = buffer.position() - SizeOf.USHORT
        headerSize = (buffer.getShort() & 0xFFFF)
        size = buffer.getInt()
    }

    def init(ByteBuffer buffer) {}

    def seekToEndOfChunk(ByteBuffer buffer) {
        buffer.position(offset + size)
    }

    abstract Type getType()

    static Chunk create(ByteBuffer buffer) {
        create(buffer, null)
    }

    static Chunk create(ByteBuffer buffer, Chunk parent) {
        Chunk chunk
        short t = buffer.getShort()
        Type type = Type.get(t)
        switch (type) {
            case Type.STRING_POOL:
                chunk = new StringPoolChunk(buffer, parent)
                break;
            case Type.TABLE:
                chunk = new TableChunk(buffer, parent)
                break
            case Type.TABLE_PACKAGE:
                chunk = new TablePackageChunk(buffer, parent)
                break;
            case Type.TABLE_TYPE:
                chunk = new TableTypeChunk(buffer, parent)
                break;
            case Type.TABLE_TYPE_SPEC:
                chunk = new TableTypeSpecChunk(buffer, parent)
                break;
            default:
                chunk = new UnknownChunk(buffer, parent)
                break;
        }
        chunk.init(buffer);
        chunk.seekToEndOfChunk(buffer);
        chunk
    }
}
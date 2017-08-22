package com.ximsfei.arschelper.utils

final class UnsignedBytes {
    static int toInt(byte value) {
        return value & 255
    }

    static byte checkedCast(long value) {
        if (value >> 8 != 0L) {
            throw new IllegalArgumentException("Out of range: " + value)
        } else {
            return (byte) ((int) value)
        }
    }

    static byte saturatedCast(long value) {
        if (value > (long) toInt((byte) -1)) {
            return -1
        } else {
            return value < 0L ? 0 : (byte) ((int) value)
        }
    }

    static int compare(byte a, byte b) {
        return toInt(a) - toInt(b)
    }
}
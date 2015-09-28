package com.zsx.tools;

/**
 * BOMs in byte length ordering:
 * 00 00 FE FF    = UTF-32, big-endian
 * FF FE 00 00    = UTF-32, little-endian
 * EF BB BF       = UTF-8,
 * FE FF          = UTF-16, big-endian
 * FF FE          = UTF-16, little-endian
 * <p/>
 * Win2k Notepad:
 * Unicode format = UTF-16LE
 ***/

import java.io.*;

/**
 * 用于去除Bom
 */
public class Lib_UnicodeInputStream extends InputStream {
    PushbackInputStream internalIn;
    boolean isInited = false;
    String defaultEnc;
    String encoding;

    private static final int BOM_SIZE = 4;

    public Lib_UnicodeInputStream(InputStream in, String defaultEnc) {
        internalIn = new PushbackInputStream(in, BOM_SIZE);
        this.defaultEnc = defaultEnc;
    }

    /**
     * @return 返回默认编码
     */

    public String _getDefaultEncoding() {
        return defaultEnc;
    }

    /**
     * @return 返回inputStream编码
     */
    public String _getEncoding() {
        if (!isInited) {
            try {
                init();
            } catch (IOException ex) {
                IllegalStateException ise = new IllegalStateException("Init method failed.");
                ise.initCause(ise);
                throw ise;
            }
        }
        return encoding;
    }

    protected void init() throws IOException {
        if (isInited)
            return;

        byte bom[] = new byte[BOM_SIZE];
        int n, unread;
        n = internalIn.read(bom, 0, bom.length);

        if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00) && (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
            encoding = "UTF-32BE";
            unread = n - 4;
        } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) && (bom[2] == (byte) 0x00)
                && (bom[3] == (byte) 0x00)) {
            encoding = "UTF-32LE";
            unread = n - 4;
        } else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) && (bom[2] == (byte) 0xBF)) {
            encoding = "UTF-8";
            unread = n - 3;
        } else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
            encoding = "UTF-16BE";
            unread = n - 2;
        } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
            encoding = "UTF-16LE";
            unread = n - 2;
        } else {
            // Unicode BOM mark not found, unread all bytes
            encoding = defaultEnc;
            unread = n;
        }
        if (unread > 0)
            internalIn.unread(bom, (n - unread), unread);

        isInited = true;
    }

    @Override
    public void close() throws IOException {
        isInited = true;
        internalIn.close();
    }

    @Override
    public int read() throws IOException {
        init();
        isInited = true;
        return internalIn.read();
    }
}

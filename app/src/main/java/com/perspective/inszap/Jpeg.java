package com.perspective.inszap;

import android.util.Log;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.ImgWMF;
import com.lowagie.text.Jpeg2000;
import com.lowagie.text.Utilities;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.BmpImage;
import com.lowagie.text.pdf.codec.GifImage;
import com.lowagie.text.pdf.codec.JBIG2Image;
import com.lowagie.text.pdf.codec.PngImage;
import com.lowagie.text.pdf.codec.TiffImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Jpeg extends Image {

    /** sequence that is used in all Jpeg files */
    public static final byte JFIF_ID[] = {
            0x4A, 0x46, 0x49, 0x46, 0x00
    };
    /** Marker value */
    public static final int M_APPE = 0xEE;
    /** Marker value */
    public static final int M_APP0 = 0xE0;
    /** Marker value */
    public static final int M_APP2 = 0xE2;
    /** This is a type of marker. */
    public static final int VALID_MARKER = 0;
    /** This is a type of marker. */
    public static final int UNSUPPORTED_MARKER = 1;
    public static final int[] VALID_MARKERS = {
            0xC0, 0xC1, 0xC2
    };
    /** This is a type of marker. */
    public static final int NOPARAM_MARKER = 2;
    /** Jpeg markers without additional parameters. */
    public static final int[] NOPARAM_MARKERS = {
            0xD0, 0xD1, 0xD2, 0xD3, 0xD4, 0xD5, 0xD6, 0xD7, 0xD8, 0x01
    };
    /** Unsupported Jpeg markers. */
    public static final int[] UNSUPPORTED_MARKERS = {
            0xC3, 0xC5, 0xC6, 0xC7, 0xC8, 0xC9, 0xCA, 0xCB, 0xCD, 0xCE, 0xCF
    };
    /** This is a type of marker. */
    public static final int NOT_A_MARKER = -1;

    private byte[][] icc;

    protected Jpeg(Image image)  {
        super(image);
    }

    public Jpeg(final byte[] img) throws BadElementException, IOException {
        super((URL) null);
        rawData = img;
        originalData = img;
        processParameters();
    }

    private void processParameters() throws BadElementException, IOException {
        type = JPEG;
        originalType = ORIGINAL_JPEG;
        InputStream is = null;
        try {
            String errorID;
            if (rawData == null) {
                is = url.openStream();
                errorID = url.toString();
            } else {
                is = new java.io.ByteArrayInputStream(rawData);
                errorID = "Byte array";
            }
            if (is.read() != 0xFF || is.read() != 0xD8) {
                throw new BadElementException(errorID + " is not a valid JPEG-file.");
            }
            boolean firstPass = true;
            int len;
            while (true) {
                int v = is.read();
                if (v < 0) {
                    throw new IOException("Premature EOF while reading JPG.");
                }
                if (v == 0xFF) {
                    int marker = is.read();
                    if (firstPass && marker == M_APP0) {
                        firstPass = false;
                        len = getShort(is);
                        if (len < 16) {
                            Utilities.skip(is, len - 2);
                            continue;
                        }
                        byte bcomp[] = new byte[JFIF_ID.length];
                        int r = is.read(bcomp);
                        if (r != bcomp.length) {
                            throw new BadElementException(errorID + " corrupted JFIF marker.");
                        }
                        boolean found = true;
                        for (int k = 0; k < bcomp.length; ++k) {
                            if (bcomp[k] != JFIF_ID[k]) {
                                found = false;
                                break;
                            }
                        }
                        if (!found) {
                            Utilities.skip(is, len - 2 - bcomp.length);
                            continue;
                        }
                        Utilities.skip(is, 2);
                        int units = is.read();
                        int dx = getShort(is);
                        int dy = getShort(is);
                        if (units == 1) {
                            dpiX = dx;
                            dpiY = dy;
                        } else if (units == 2) {
                            dpiX = (int) (dx * 2.54f + 0.5f);
                            dpiY = (int) (dy * 2.54f + 0.5f);
                        }
                        Utilities.skip(is, len - 2 - bcomp.length - 7);
                        continue;
                    }
                    if (marker == M_APPE) {
                        len = getShort(is) - 2;
                        byte[] byteappe = new byte[len];
                        for (int k = 0; k < len; ++k) {
                            byteappe[k] = (byte) is.read();
                        }
                        if (byteappe.length >= 12) {
                            String appe = new String(byteappe, 0, 5, "ISO-8859-1");
                            if (appe.equals("Adobe")) {
                                invert = true;
                            }
                        }
                        continue;
                    }
                    if (marker == M_APP2) {
                        len = getShort(is) - 2;
                        byte[] byteapp2 = new byte[len];
                        for (int k = 0; k < len; ++k) {
                            byteapp2[k] = (byte) is.read();
                        }
                        if (byteapp2.length >= 14) {
                            String app2 = new String(byteapp2, 0, 11, "ISO-8859-1");
                            if (app2.equals("ICC_PROFILE")) {
                                // int order = byteapp2[12] & 0xff;
                                // int count = byteapp2[13] & 0xff;
                                // if (icc == null)
                                // icc = new byte[count][];
                                // icc[order - 1] = byteapp2;
                            }
                        }
                        continue;
                    }
                    firstPass = false;
                    int markertype = marker(marker);
                    if (markertype == VALID_MARKER) {
                        Utilities.skip(is, 2);
                        if (is.read() != 0x08) {
                            throw new BadElementException(errorID + " must have 8 bits per component.");
                        }
                        scaledHeight = getShort(is);
                        setTop(scaledHeight);
                        scaledWidth = getShort(is);
                        setRight(scaledWidth);
                        colorspace = is.read();
                        bpc = 8;
                        break;
                    } else if (markertype == UNSUPPORTED_MARKER) {
                        throw new BadElementException(errorID + ": unsupported JPEG marker: " + marker);
                    } else if (markertype != NOPARAM_MARKER) {
                        Utilities.skip(is, getShort(is) - 2);
                    }
                }
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
        plainWidth = getWidth();
        plainHeight = getHeight();
        if (icc != null) {
            int total = 0;
            for (int k = 0; k < icc.length; ++k) {
                if (icc[k] == null) {
                    icc = null;
                    return;
                }
                total += icc[k].length - 14;
            }
            byte[] ficc = new byte[total];
            total = 0;
            for (int k = 0; k < icc.length; ++k) {
                System.arraycopy(icc[k], 14, ficc, total, icc[k].length - 14);
                total += icc[k].length - 14;
            }
            icc = null;
        }
    }

    private static final int getShort(final InputStream is) throws IOException {
        return (is.read() << 8) + is.read();
    }

    private static final int marker(final int marker) {
        for (int i = 0; i < VALID_MARKERS.length; i++) {
            if (marker == VALID_MARKERS[i]) {
                return VALID_MARKER;
            }
        }
        for (int i = 0; i < NOPARAM_MARKERS.length; i++) {
            if (marker == NOPARAM_MARKERS[i]) {
                return NOPARAM_MARKER;
            }
        }
        for (int i = 0; i < UNSUPPORTED_MARKERS.length; i++) {
            if (marker == UNSUPPORTED_MARKERS[i]) {
                return UNSUPPORTED_MARKER;
            }
        }
        return NOT_A_MARKER;
    }

    public static Image getInstance(byte[] imgb) throws BadElementException,
            MalformedURLException, IOException {
        java.io.InputStream is = null;
        try {
            is = new java.io.ByteArrayInputStream(imgb);
            int c1 = is.read();
            int c2 = is.read();
            int c3 = is.read();
            int c4 = is.read();
            is.close();

            is = null;
            Log.v("datos",c1 + " " + c2 + " " + c3 + " " + c4);
            if (c1 == 'G' && c2 == 'I' && c3 == 'F') {
                GifImage gif = new GifImage(imgb);
                return gif.getImage(1);
            }
            if (c1 == 0xFF && c2 == 0xD8) {
                return new Jpeg(imgb);
            }
            if (c1 == 0x00 && c2 == 0x00 && c3 == 0x00 && c4 == 0x0c) {
                return new Jpeg2000(imgb);
            }
            if (c1 == 0xff && c2 == 0x4f && c3 == 0xff && c4 == 0x51) {
                return new Jpeg2000(imgb);
            }
            if (c1 == PngImage.PNGID[0] && c2 == PngImage.PNGID[1]
                    && c3 == PngImage.PNGID[2] && c4 == PngImage.PNGID[3]) {
                return PngImage.getImage(imgb);
            }
            if (c1 == 0xD7 && c2 == 0xCD) {
                return new ImgWMF(imgb);
            }
            if (c1 == 'B' && c2 == 'M') {
                return BmpImage.getImage(imgb);
            }
            if ((c1 == 'M' && c2 == 'M' && c3 == 0 && c4 == 42)
                    || (c1 == 'I' && c2 == 'I' && c3 == 42 && c4 == 0)) {
                RandomAccessFileOrArray ra = null;
                try {
                    ra = new RandomAccessFileOrArray(imgb);
                    Image img = TiffImage.getTiffImage(ra, 1);
                    if (img.getOriginalData() == null)
                        img.setOriginalData(imgb);
                    return img;
                } finally {
                    if (ra != null)
                        ra.close();
                }

            }
            if ( c1 == 0x97 && c2 == 'J' && c3 == 'B' && c4 == '2' ) {
                is = new java.io.ByteArrayInputStream(imgb);
                is.skip(4);
                int c5 = is.read();
                int c6 = is.read();
                int c7 = is.read();
                int c8 = is.read();
                if ( c5 == '\r' && c6 == '\n' && c7 == 0x1a && c8 == '\n' ) {
                    int file_header_flags = is.read();
                    int number_of_pages = -1;
                    if ( (file_header_flags & 0x2) == 0x2 ) {
                        number_of_pages = (is.read() << 24) | (is.read() << 16) | (is.read() << 8) | is.read();
                    }
                    is.close();
                    // a jbig2 file with a file header.  the header is the only way we know here.
                    // embedded jbig2s don't have a header, have to create them by explicit use of Jbig2Image?
                    // nkerr, 2008-12-05  see also the getInstance(URL)
                    RandomAccessFileOrArray ra = null;
                    try {
                        ra = new RandomAccessFileOrArray(imgb);
                        Image img = JBIG2Image.getJbig2Image(ra, 1);
                        if (img.getOriginalData() == null)
                            img.setOriginalData(imgb);
                        return img;
                    } finally {
                        if (ra != null)
                            ra.close();
                    }
                }
            }
            throw new IOException(
                    "The byte array is not a recognized imageformat.");
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}


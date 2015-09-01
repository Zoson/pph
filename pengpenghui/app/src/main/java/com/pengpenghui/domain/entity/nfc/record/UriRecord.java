package com.pengpenghui.domain.entity.nfc.record;

import android.net.Uri;
import android.nfc.NdefRecord;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.primitives.Bytes;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * A parsed record containing a Uri.
 * Created by Zen9 on 2015/8/31.
 */
public class UriRecord implements ParsedNdefRecord {

    private static final String TAG = "UriRecord";

    public static final String RECORD_TYPE = "UriRecord";

    /**
     * NFC Forum "URI Record Type Definition"
     *
     * This is a mapping of "URI Identifier Codes" to URI string prefixes,
     * per section 3.2.2 of the NFC Forum URI Record Type Definition document.
     */
    private static final BiMap<Byte, String> URI_PREFIX_MAP = ImmutableBiMap.<Byte, String>builder()
            .put((byte) 0x00, "")
            .put((byte) 0x01, "http://www.")
            .put((byte) 0x02, "https://www.")
            .put((byte) 0x03, "http://")
            .put((byte) 0x04, "https://")
            .put((byte) 0x05, "tel:")
            .put((byte) 0x06, "mailto:")
            .put((byte) 0x07, "ftp://anonymous:anonymous@")
            .put((byte) 0x08, "ftp://ftp.")
            .put((byte) 0x09, "ftps://")
            .put((byte) 0x0A, "sftp://")
            .put((byte) 0x0B, "smb://")
            .put((byte) 0x0C, "nfs://")
            .put((byte) 0x0D, "ftp://")
            .put((byte) 0x0E, "dav://")
            .put((byte) 0x0F, "news:")
            .put((byte) 0x10, "telnet://")
            .put((byte) 0x11, "imap:")
            .put((byte) 0x12, "rtsp://")
            .put((byte) 0x13, "urn:")
            .put((byte) 0x14, "pop:")
            .put((byte) 0x15, "sip:")
            .put((byte) 0x16, "sips:")
            .put((byte) 0x17, "tftp:")
            .put((byte) 0x18, "btspp://")
            .put((byte) 0x19, "btl2cap://")
            .put((byte) 0x1A, "btgoep://")
            .put((byte) 0x1B, "tcpobex://")
            .put((byte) 0x1C, "irdaobex://")
            .put((byte) 0x1D, "file://")
            .put((byte) 0x1E, "urn:epc:id:")
            .put((byte) 0x1F, "urn:epc:tag:")
            .put((byte) 0x20, "urn:epc:pat:")
            .put((byte) 0x21, "urn:epc:raw:")
            .put((byte) 0x22, "urn:epc:")
            .put((byte) 0x23, "urn:nfc:")
            .build();

    private final Uri mUri;

    private UriRecord(Uri uri) {
        //检查uri不为null时， 直接返回uri
        this.mUri = Preconditions.checkNotNull(uri);
    }

    public String getActualPayload() {
        return mUri.toString();
    }

    public Uri getUri() {
        return mUri;
    }

    /**
     * Convert {@link NdefRecord} into a {@link Uri}.
     * This will handle both TNF_WELL_KNOWN / RTD_URI and TNF_ABSOLUTE_URI.
     *
     * @throws IllegalArgumentException if the NdefRecord is not a record
     *         containing a URI.
     */
    public static UriRecord parse(NdefRecord record) {
        //获取TNF类型值，该值指示Record类型域的类别
        short tnf = record.getTnf();

        if (tnf == NdefRecord.TNF_WELL_KNOWN){
            return parseWellKnown(record);
        }
        else if (tnf == NdefRecord.TNF_ABSOLUTE_URI){
            return parseAbsolute(record);
        }
        throw new IllegalArgumentException("Unknown TNF " + tnf);
    }

    /** Parse and absolute URI record */
    private static UriRecord parseAbsolute(NdefRecord record) {
        byte[] payload = record.getPayload();
        Uri uri = Uri.parse(new String(payload, Charset.forName("UTF-8")));
        return new UriRecord(uri);
    }

    /** Parse an well known URI record */
    private static UriRecord parseWellKnown(NdefRecord record) {

        Preconditions.checkArgument(Arrays.equals(record.getType(), NdefRecord.RTD_URI));

        //获取记录payload内容
        byte[] payload = record.getPayload();

        //payload[0]包含URI的标识符代码，即网址前缀。详情查看书本格式
        String prefix = URI_PREFIX_MAP.get(payload[0]);

        //完整的Uri
        byte[] fullUri =
                Bytes.concat(prefix.getBytes(Charset.forName("UTF-8")), Arrays.copyOfRange(payload, 1,
                        payload.length));

        Uri uri = Uri.parse(new String(fullUri, Charset.forName("UTF-8")));

        return new UriRecord(uri);
    }

    /**
     * 判断记录是否为uriRecord
     * @param record
     * @return
     */
    public static boolean isUri(NdefRecord record) {
        try {
            parse(record);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static final byte[] EMPTY = new byte[0];
}


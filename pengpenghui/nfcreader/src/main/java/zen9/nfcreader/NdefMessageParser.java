package zen9.nfcreader;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import java.util.ArrayList;
import java.util.List;

import zen9.nfcreader.record.*;


/**
 * Created by Zen9 on 2015/8/31.
 */
public class NdefMessageParser {

    // Utility class
    private NdefMessageParser() {

    }

    /**
     * Parse an NdefMessage
     * 将一条NDEF消息转换成记录列表
     */
    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        /**
         * NdefRecord[] NdefMessage.getRecords()：
         * Get the NDEF Records inside this NDEF Message.
         */
        return getListRecords(message.getRecords());
    }

    public static List<ParsedNdefRecord> getListRecords(NdefRecord[] records) {

        List<ParsedNdefRecord> elements = new ArrayList<ParsedNdefRecord>();

        for (final NdefRecord record : records)
        {
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record));
            }
            else if (TextRecord.isText(record)){
                elements.add(TextRecord.parse(record));
            }
            else {
                elements.add(new ParsedNdefRecord(){
                    @Override
                    public String getActualPayload(){
                        return new String(record.getPayload());
                    }
                });
            }
        }
        return elements;
    }
}


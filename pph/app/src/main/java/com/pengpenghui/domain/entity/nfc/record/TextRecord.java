package com.pengpenghui.domain.entity.nfc.record;

import android.nfc.NdefRecord;

import com.google.common.base.Preconditions;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

//Guava类库中提供了一个作参数检查的工具类

/**
 * An NFC Text Record
 * Created by Zen9 on 2015/8/31.
 */
public class TextRecord implements ParsedNdefRecord {

    /** ISO/IANA language code */
    private final String mLanguageCode;

    private final String mText;

    private TextRecord(String languageCode, String text) {
        mLanguageCode = Preconditions.checkNotNull(languageCode);
        mText = Preconditions.checkNotNull(text);
    }

    public String getActualPayload() {
        return mText;
    }

    public String getText() {
        return mText;
    }

    /**
     * Returns the ISO/IANA language code associated with this text element.
     */
    public String getLanguageCode() {
        return mLanguageCode;
    }

    // TODO: deal with text fields which span（跨越） multiple NdefRecords
    public static TextRecord parse(NdefRecord record) {

        //记录格式验证
        Preconditions.checkArgument(record.getTnf() == NdefRecord.TNF_WELL_KNOWN);

        //记录类型验证
        Preconditions.checkArgument(Arrays.equals(record.getType(), NdefRecord.RTD_TEXT));
        try {
            byte[] payload = record.getPayload();  //获取记录payload内容

            //获取记录第一个字节
            Byte statusByte = payload[0];

            //获取状态字节编码
            String textEncoding = ((statusByte & 0200) == 0) ? "UTF-8" : "UTF-16";

            //获取语言码长度
            int languageCodeLength = statusByte & 0077;

            //获取语言码
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

            /**
             * 获取payload实际的文本数据
             * public String (byte[] data, int offset, int byteCount, String charsetName)
             */
            String text =new String(payload, languageCodeLength + 1,
                    payload.length - languageCodeLength - 1, textEncoding);

            return new TextRecord(languageCode, text);
        } catch (UnsupportedEncodingException e) {
            // should never happen unless we get a malformed tag.
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean isText(NdefRecord record) {
        try {
            parse(record);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}


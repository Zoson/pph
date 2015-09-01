package zen9.nfcreader.record;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zen9 on 2015/8/31.
 */
public interface ParsedNdefRecord {

    public String getActualPayload();
}

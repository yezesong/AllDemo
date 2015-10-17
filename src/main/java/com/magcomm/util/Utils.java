package com.magcomm.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by lenovo on 15-10-17.
 */
public class Utils {
    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }
}

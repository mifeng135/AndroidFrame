package com.mifeng.mf.Common;

import android.content.Intent;

/**
 * Created by Administrator on 2019/9/20.
 */

public interface MFIActivityEventListener {

    void onActivityResult(int requestCode, int resultCode, Intent data);
}

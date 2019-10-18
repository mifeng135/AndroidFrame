package com.mifeng.mf.Common;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/8/19.
 */

public class MFHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String localHPUUId = (String) MFUtils.getInstance().getLocalData("hp-uuid", MFUtils.Preferences.STRING);
        String localToken = (String) MFUtils.getInstance().getLocalData("token", MFUtils.Preferences.STRING);
        if (localHPUUId != "") {
            builder.addHeader("hp-uuid",localHPUUId);
        }
        if (localToken != "") {
            builder.addHeader("access-token",localToken);
        }
        Request newRequest = builder.build();
        Response response = chain.proceed(newRequest);

        Headers header =  response.headers();

        String responseHpUuid = header.get("hp-uuid");
        String responseToken = header.get("access-token");
        if (responseHpUuid != null && responseHpUuid != localHPUUId) {
            MFUtils.getInstance().saveLocalData("hp-uuid",responseHpUuid);
        }
        if (responseToken != null && localToken != responseToken) {
            MFUtils.getInstance().saveLocalData("token",responseToken);
        }
        return response;
    }
}

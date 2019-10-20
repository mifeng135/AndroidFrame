package com.mifeng.mf.Common;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.mifeng.mf.Constant.MFAppConfig;
import com.mifeng.mf.Constant.MFBeanFactory;
import com.mifeng.mf.Constant.MFMsgFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mifeng.mf.Constant.MFMsgDefine.G_HIDE_NET_LOADING;
import static com.mifeng.mf.Constant.MFMsgDefine.G_SHOW_NET_LOADING;

/**
 * Created by Administrator on 2019/8/19.
 */

public class MFNetWorkLogic {
    private static final int netTime = 20000;

    private static volatile MFNetWorkLogic mInstance;
    private OkHttpClient mOkHttpClient;

    public static MFNetWorkLogic getInstance() {
        if (mInstance == null) {
            synchronized (MFNetWorkLogic.class) {
                if (mInstance == null) {
                    mInstance = new MFNetWorkLogic();
                }
            }
        }
        return mInstance;
    }


    MFNetWorkLogic() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(netTime, TimeUnit.SECONDS)
                .readTimeout(netTime, TimeUnit.SECONDS)
                .writeTimeout(netTime, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .addInterceptor(new MFHttpInterceptor())
                .build();
    }

    /**
     * cancel request
     * @param cmd recv cmd
     */
    public void cancalRequestWithCmd(String cmd) {
        List<Call> callList = mOkHttpClient.dispatcher().queuedCalls();
        for (int i = 0; i < callList.size(); i++) {
            Call call = callList.get(i);
            if (call.request().tag().equals(cmd)) {
                call.cancel();
            }
        }
    }


    public String assignParamForGet(Map<String, Object> param) {
        String paramString = "";
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue;
            if (entry.getValue() instanceof Map) {
                mapValue = JSON.toJSONString(entry.getValue());
            }else if (entry.getValue() instanceof Integer) {
                mapValue =  entry.getValue() + "";
            }else {
                mapValue = (String) entry.getValue();
            }
            if (paramString.length() == 0) {
                paramString = paramString + "?" + mapKey + "=" + mapValue;
            } else {
                paramString = paramString + "&" + mapKey + "=" + mapValue;
            }
        }
        return paramString;
    }

    public void requestData(String msgCmd, Map param, String method) {

        String baseUrl = MFAppConfig.httpUrl;

        Map sendMsgFactory = MFMsgFactory.getInstance().getSendMsgFactory();
        Map recvMsgFactory = MFMsgFactory.getInstance().getRecvMsgFactory();


        String sendUrl = (String) sendMsgFactory.get(msgCmd);
        final String recvCmd = (String) recvMsgFactory.get(sendUrl);

        baseUrl = baseUrl + sendUrl;

        Request request;
        if (method.equals("get")) {
            baseUrl += assignParamForGet(param);
            request = new Request.Builder()
                    .get()
                    .url(baseUrl)
                    .tag(recvCmd)
                    .build();
        } else {
            String paramString = JSON.toJSONString(param);
            MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
            RequestBody requestBody = RequestBody.create(mediaType, paramString);

            request = new Request.Builder()
                    .post(requestBody)
                    .url(baseUrl)
                    .tag(recvCmd)
                    .build();
        }


        MFEvent.getInstance().postEvent(G_SHOW_NET_LOADING, "", "");

        Call requestCall = mOkHttpClient.newCall(request);
        requestCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MFEvent.getInstance().postEvent(G_HIDE_NET_LOADING, "", "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MFEvent.getInstance().postEvent(G_HIDE_NET_LOADING, "", "");
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    MFEvent.getInstance().postEvent(recvCmd, JSON.parseObject(string, MFBeanFactory.getInstance().getBeanByCmd(recvCmd)), param);
                }
            }
        });
    }

    public void requestWithSpread(List<Map> spreadParam) {
        String baseUrl = MFAppConfig.httpUrl;

        String recvCmd = "";
        if (spreadParam.size() <= 0) {
            return;
        }
        MFEvent.getInstance().postEvent(G_SHOW_NET_LOADING, "", "");


        ArrayList<Object> result = new ArrayList<>(Collections.nCopies(spreadParam.size(), new Object()));
        final int[] recvRefCount = {0};

        Map sendMsgFactory = MFMsgFactory.getInstance().getSendMsgFactory();
        Map recvMsgFactory = MFMsgFactory.getInstance().getRecvMsgFactory();


        for (int i = 0; i < spreadParam.size(); i++) {
            Map map = spreadParam.get(i);
            String sendCmd = (String)map.get("sendCmd");
            String sendType = (String)map.get("sendType");
            Map param = (Map)map.get("param");
            String sendUrl = (String) sendMsgFactory.get(sendCmd);
            String recvUrl = (String) recvMsgFactory.get(sendUrl);
            if (recvCmd.length() <= 0) {
                recvCmd = recvUrl;
            }
            String requestUrl = baseUrl + sendUrl;

            Request request;

            if (sendType.equals("get")) {
                requestUrl += assignParamForGet(param);
                request = new Request.Builder()
                        .get()
                        .url(requestUrl)
                        .tag(recvUrl +"_" + i)
                        .build();
            }else {
                String paramString = JSON.toJSONString(param);
                MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
                RequestBody requestBody = RequestBody.create(mediaType, paramString);

                request = new Request.Builder()
                        .post(requestBody)
                        .url(requestUrl)
                        .tag(recvUrl +"_" + i)
                        .build();
            }
            Call requestCall = mOkHttpClient.newCall(request);

            String finalRecvCmd = recvCmd;

            requestCall.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    recvRefCount[0] = recvRefCount[0] + 1;
                    MFEvent.getInstance().postEvent(G_HIDE_NET_LOADING, "", "");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();

                        String tag = ((String)response.request().tag());
                        String cmd = tag.split("_")[0];
                        int index = Integer.parseInt(tag.split("_")[1]);
                        synchronized (result) {
                            result.set(index,JSON.parseObject(string, MFBeanFactory.getInstance().getBeanByCmd(cmd)));
                        }
                        recvRefCount[0] = recvRefCount[0] + 1;
                        if (result.size() == recvRefCount[0]) {
                            MFEvent.getInstance().postEvent(finalRecvCmd, result, "");
                            MFEvent.getInstance().postEvent(G_HIDE_NET_LOADING, "", "");
                        }
                    }
                }
            });

        }
    }

    public void upload(String msgCmd, String fileName, String filePath) {

        String baseUrl = MFAppConfig.httpUrl;

        Map sendMsgFactory = MFMsgFactory.getInstance().getSendMsgFactory();
        Map recvMsgFactory = MFMsgFactory.getInstance().getRecvMsgFactory();

        String sendUrl = (String) sendMsgFactory.get(msgCmd);
        final String recvCmd = (String) recvMsgFactory.get(sendUrl);

        baseUrl = baseUrl + sendUrl;
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .url(baseUrl)
                .post(requestBody)
                .build();

        Call requestCall = mOkHttpClient.newCall(request);
        requestCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MFEvent.getInstance().postEvent(G_HIDE_NET_LOADING, "", "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MFEvent.getInstance().postEvent(G_HIDE_NET_LOADING, "", "");
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    MFEvent.getInstance().postEvent(recvCmd, string, "");
                }
            }
        });
    }
}



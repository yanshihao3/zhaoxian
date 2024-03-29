package com.zq.base.network;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @program: zhaoxian
 * @description:
 * @author: 闫世豪
 * @create: 2022-02-16 14:02
 **/
public class UploadFileRequestBody extends RequestBody {

    //实际的待包装请求体
    private final RequestBody requestBody;
    //进度回调接口
    private final ProgressRequestListener progressListener;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    //每个RequestBody对应一个tag，存放在map中，保证计算的时候不会出现重复
    private String tag;

    /**
     * 构造函数，赋值
     *
     * @param file
     * @param progressListener 回调接口
     * @param tag              标记
     */
    public UploadFileRequestBody(File file, ProgressRequestListener progressListener, String tag) {
        this.requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        this.progressListener = progressListener;
        this.tag = tag;
    }

    public UploadFileRequestBody(RequestBody requestBody, ProgressRequestListener progressListener, String tag) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
        this.tag = tag;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();

    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }


    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调

            }
        };
    }
}



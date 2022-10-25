package me.goldze.mvvmhabit.http.upload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 文件上传进度
 * @param <T>
 * 用法:
 *  FileRequestBody.RetrofitCallback callback = new FileRequestBody.RetrofitCallback() {
 *             @Override
 *             public void onLoading(long total, long progress) {
 *                 //此处进行进度更新
 *             }
 *         };
 *
 * RequestBody requestFile = RequestBody.create(MediaType.parse("video/mp4"), file);
 * FileRequestBody<T> fileRequestBody = new FileRequestBody<T>(requestFile, callback);
 * MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), fileRequestBody);
 *
 * 对比正常操作：
 * RequestBody requestFile = RequestBody.create(MediaType.parse("video/mp4"), file);
 * MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), requestFile);
 */
public class FileRequestBody<T> extends RequestBody {
    /**
     * 实际请求体
     */
    private RequestBody requestBody;
    /**
     * 上传回调接口
     */
    private RetrofitCallback callback;
    /**
     * 包装完成的BufferedSink
     */
    private BufferedSink bufferedSink;

    public FileRequestBody(RequestBody requestBody, RetrofitCallback callback) {
        super();
        this.requestBody = requestBody;
        this.callback = callback;
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
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
                //回调 或者利用Rxjava发送
                callback.onLoading(contentLength, bytesWritten);
            }
        };
    }

    public abstract static class RetrofitCallback {
        //用于进度的回调
        public abstract void onLoading(long total, long progress);
    }
}
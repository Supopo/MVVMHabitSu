package com.goldze.mvvmhabit.data;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.goldze.mvvmhabit.data.source.HttpDataSource;
import com.goldze.mvvmhabit.data.source.http.service.CustomObserver;
import com.goldze.mvvmhabit.data.source.http.service.DemoApiService;
import com.goldze.mvvmhabit.entity.DemoEntity;
import com.goldze.mvvmhabit.utils.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * 一个简单的网络请求仓库
 */
public class SimpleRepository implements HttpDataSource {
    private static final SimpleRepository instance = new SimpleRepository();
    private final DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);

    private SimpleRepository() {
    }

    public static SimpleRepository getInstance() {
        return instance;
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoGet() {
        return apiService.demoGet();
    }

    @Override
    public Observable<Object> login() {
        return null;
    }

    @Override
    public Observable<DemoEntity> loadMore() {
        return null;
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoPost(String catalog) {
        return null;
    }

    public void demoGet2(MutableLiveData<Boolean> disDialog, MutableLiveData<DemoEntity> dataResult) {
        SimpleRepository.getInstance().demoGet()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //请求前的执行 主线程中
                    }
                })
                .subscribe(new CustomObserver<BaseResponse<DemoEntity>>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposable = d;
                    }

                    @Override
                    protected void onFinish() {
                        disDialog.postValue(true);
                        disposable.dispose();
                    }


                    @Override
                    public void onSuccess(BaseResponse<DemoEntity> data) {
                        dataResult.postValue(data.getResult());
                    }

                });
    }
}

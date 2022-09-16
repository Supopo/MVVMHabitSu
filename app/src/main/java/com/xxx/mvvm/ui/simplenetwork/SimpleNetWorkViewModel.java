package com.xxx.mvvm.ui.simplenetwork;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xxx.mvvm.data.SimpleRepository;
import com.xxx.mvvm.data.source.http.service.CustomObserver;
import com.xxx.mvvm.entity.DemoEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.RxUtils;

public class SimpleNetWorkViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> disDialog = new MutableLiveData<>();
    public MutableLiveData<DemoEntity> dataResult = new MutableLiveData<>();

    public SimpleNetWorkViewModel(@NonNull Application application) {
        super(application);
    }

    //写法1
    public void demoGet() {
        SimpleRepository.getInstance().demoGet()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //请求前的执行 主线程中
                    }
                })
                .subscribe(new CustomObserver<BaseResponse<DemoEntity>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
                        addSubscribe(d);
                    }

                    @Override
                    protected void onFinish() {
                        disDialog.postValue(true);
                    }


                    @Override
                    public void onSuccess(BaseResponse<DemoEntity> response) {
                        if (response.getResult() != null && !response.getResult().getItems().isEmpty()) {
                            dataResult.postValue(response.getResult());
                        }
                    }

                });
    }


    //写法2
    public void demoGet2() {
        SimpleRepository.getInstance().demoGet2(disDialog, dataResult);
    }

}

package com.xxx.mvvmhabit.ui.simplenetwork;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.xxx.mvvmhabit.BR;
import com.xxx.mvvmhabit.R;
import com.xxx.mvvmhabit.data.SimpleRepository;
import com.xxx.mvvmhabit.data.source.http.service.CustomObserver;
import com.xxx.mvvmhabit.entity.DemoEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class SimpleNetWorkViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> disDialog = new MutableLiveData<>();
    public MutableLiveData<DemoEntity> dataResult = new MutableLiveData<>();
    public SingleLiveEvent<SimpleNetWorkItemViewModel> deleteItemLiveData = new SingleLiveEvent<>();


    public SimpleNetWorkViewModel(@NonNull Application application) {
        super(application);
    }

    //给RecyclerView添加ObservableList
    public ObservableList<SimpleNetWorkItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<SimpleNetWorkItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_simple_network);

    /**
     * 删除条目
     *
     * @param netWorkItemViewModel
     */
    public void deleteItem(SimpleNetWorkItemViewModel netWorkItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(netWorkItemViewModel);
    }

    /**
     * 获取条目下标
     *
     * @param netWorkItemViewModel
     * @return
     */
    public int getItemPosition(SimpleNetWorkItemViewModel netWorkItemViewModel) {
        return observableList.indexOf(netWorkItemViewModel);
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
                            initListData(response.getResult());
                        }
                    }

                });
    }

    public void initListData(DemoEntity demoEntity) {
        //清除列表
        observableList.clear();
        //请求成功
        for (DemoEntity.ItemsEntity entity : demoEntity.getItems()) {
            SimpleNetWorkItemViewModel itemViewModel = new SimpleNetWorkItemViewModel(SimpleNetWorkViewModel.this, entity);
            //双向绑定动态添加Item
            observableList.add(itemViewModel);
        }
    }

    //写法2
    public void demoGet2() {
        SimpleRepository.getInstance().demoGet2(disDialog, dataResult);
    }

}

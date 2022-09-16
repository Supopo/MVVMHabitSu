package com.xxx.mvvm.ui.simplenetwork;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.xxx.mvvm.R;
import com.xxx.mvvm.databinding.ItemSimpleNetworkBinding;
import com.xxx.mvvm.entity.DemoEntity;

import java.util.List;

public class SimpleAdapter extends BaseQuickAdapter<DemoEntity.ItemsEntity, DataBindingHolder<ItemSimpleNetworkBinding>> {

    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<ItemSimpleNetworkBinding> holder, int i, @Nullable DemoEntity.ItemsEntity item) {
        if (item == null)
            return;

        // 获取 Binding
        ItemSimpleNetworkBinding binding = holder.getBinding();
        binding.setViewModel(item);
        binding.executePendingBindings();
    }

    //局部刷新
    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<ItemSimpleNetworkBinding> holder, int position, @Nullable DemoEntity.ItemsEntity item, @NonNull List<?> payloads) {
        super.onBindViewHolder(holder, position, item, payloads);
        //判单payloads
        if (!payloads.isEmpty()) {
            String tag = (String) payloads.get(0);
        }
    }

    @NonNull
    @Override
    protected DataBindingHolder<ItemSimpleNetworkBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return new DataBindingHolder<>(R.layout.item_simple_network, viewGroup);
    }
}

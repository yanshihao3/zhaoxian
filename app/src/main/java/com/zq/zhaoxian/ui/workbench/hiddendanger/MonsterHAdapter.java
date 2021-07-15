package com.zq.zhaoxian.ui.workbench.hiddendanger;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.zq.zhaoxian.R;
import com.zq.zhaoxian.view.recycle.BaseHFormAdapter;

import java.util.List;


public class MonsterHAdapter extends BaseHFormAdapter<DangerModel.Info> {

    public MonsterHAdapter(Context context) {
        super(context);
    }

    @Override
    protected String getRowData(DangerModel.Info model, int index) {
        String result = "";
        switch (index) {
            case 0:
                result = model.getZQIOT__ProjectName__CST();
                break;
            case 1:
                result = model.getZQIOT__DangerInfo__CST();
                break;
            case 2:
                result = model.getZQIOT__AddrDetail__CST();
                break;
            case 3:
                result = model.getZQIOT__step__CST();
                break;
            case 4:
                result = model.getZQIOT__condition__CST();
                break;
        }

        return result;
    }

    public MonsterHAdapter(Context context, List<DangerModel.Info> list) {
        super(context, list);
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item,
                viewGroup, false);

        return view;
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ItemHolder(view);

        return viewHolder;
    }

    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tvItem.setText(content);
        if (columnIndex==4){
            holder.tvItem.setTextColor(Color.parseColor("#73C74C"));
        }
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}

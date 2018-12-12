package com.dikai.chenghunjiclient.citypicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Lucio on 2016/7/31.
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Object> list;

    public List<Object> getList() {
        return list;
    }

    private CityViewHolder lastHolder;
    public CityListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city_select_list, parent, false);
        CityViewHolder holder = new CityViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {

        if(list.get(position) instanceof Province) {
            Province province = (Province) list.get(position);
            holder.name.setText(province.getRegionName());
        }else if(list.get(position) instanceof City){
            City city = (City) list.get(position);
            holder.name.setText(city.getRegionName());
        }else {
            Country country = (Country) list.get(position);
            holder.name.setText(country.getRegionName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends Object> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
        int size = list.size();
        list = new ArrayList<>();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }


    @Override
    public void onViewDetachedFromWindow(CityViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder == lastHolder) {
            lastHolder = null;
        }
    }


    @Override
    public void onClick(View v) {
        CityViewHolder holder = (CityViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(list.get(position) instanceof Province) {
            Province province = (Province) list.get(position);
            EventBus.getDefault().post(new EventBusBean(Constants.SELECT_PROVINCE,province));
            ((SelectCityActivity)context).setFragment(province);
        }else if(list.get(position) instanceof City){
            City city = (City) list.get(position);
            EventBus.getDefault().post(new EventBusBean(Constants.SELECT_CITY,city));
            ((SelectCityActivity)context).setFragment(city);
        }else {
            Country country = (Country) list.get(position);
            ((SelectCityActivity)context).sendCity(country);
        }
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private LinearLayout mLayout;

        public CityViewHolder(View itemView) {
            super(itemView);

            name = ((TextView) itemView.findViewById(R.id.item_city_list_text));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_city_list_layout);

        }
    }
}


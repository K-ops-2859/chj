package com.dikai.chenghunjiclient.citypicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dikai.chenghunjiclient.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CityListAdapter mAdapter;

    public SelectCityFragment() {

    }

    public static SelectCityFragment newInstance(Object object) {
        Bundle args = new Bundle();
        args.putSerializable("city", (Serializable) object);
        SelectCityFragment fragment = new SelectCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_city, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.city_select_recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("city:","there3");
        mAdapter = new CityListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        Object object = getArguments().getSerializable("city");
        if(object instanceof String){
            List<Province> list = new ArrayList<>();
            try {
                list.addAll(DBManager.getInstance(getContext().getApplicationContext()).queryProvinces());
                mAdapter.addAll(list);
//                Log.e("city:","there4" + list.toString());
            }catch (Exception e){
                Log.e("?????",e.toString());
            }
        }else if(object instanceof Province){
            try {
                Province province = (Province) object;
                List<City> list = new ArrayList<>();
                list.addAll(DBManager.getInstance(getContext().getApplicationContext()).queryCity(province.getId()));
                mAdapter.addAll(list);
            }catch (Exception e){
                Log.e("?????1",e.toString());
            }
        }else if(object instanceof City){
            try {
                City city = (City) object;
                List<Country> list = new ArrayList<>();
                list.add(new Country(city.getId(),city.getRegionId(),city.getRegionName()));
                list.addAll(DBManager.getInstance(getContext().getApplicationContext()).queryCountry(city.getId()));
                mAdapter.addAll(list);
            }catch (Exception e){
                Log.e("?????3",e.toString());
            }
        }
    }
}

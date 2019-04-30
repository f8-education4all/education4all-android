package com.f82019.education4all;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.f82019.education4all.adapters.AchivmentAdapter;
import com.f82019.education4all.models.Archivment;

import java.util.ArrayList;
import java.util.List;


public class Learn2WriteFragment extends Fragment {

    ListView listView;


    public Learn2WriteFragment() {
        // Required empty public constructor
    }

    public static Learn2WriteFragment newInstance() {
        Learn2WriteFragment fragment = new Learn2WriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn2_write, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getView().findViewById(R.id.frag_learn2_write_listview);

        List<Archivment> archivments = new ArrayList<>();
        archivments.add(new Archivment("The digits", "Learn to write all the 10 digits [0-9]", null, 0));

        AchivmentAdapter adapter = new AchivmentAdapter(getContext(), android.R.layout.simple_list_item_2, archivments);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Learn2WriteActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

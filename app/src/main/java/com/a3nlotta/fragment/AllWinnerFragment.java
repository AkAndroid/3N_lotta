package com.a3nlotta.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3nlotta.R;
import com.a3nlotta.adapter.WinnersListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllWinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllWinnerFragment extends Fragment {

    @BindView(R.id.rvWinners)
    RecyclerView rvWinners;
    public AllWinnerFragment() {
    }

    public static AllWinnerFragment newInstance() {
        AllWinnerFragment fragment = new AllWinnerFragment();
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
        View view = inflater.inflate(R.layout.fragment_all_winner, container, false);
        ButterKnife.bind(this, view);

        rvWinners.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWinners.setAdapter(new WinnersListAdapter());
        return view;
    }
}
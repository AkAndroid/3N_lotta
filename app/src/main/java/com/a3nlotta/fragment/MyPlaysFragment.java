package com.a3nlotta.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.activity.MyPlaysActivity;
import com.a3nlotta.adapter.MyPalysAdapter;
import com.a3nlotta.model.mayplay.MyPlaysModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MyPlaysFragment extends Fragment {

    @BindView(R.id.rvMyPlays)
    RecyclerView rvMyPlays;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private static final String PLAY_TYPE = "play_type";
    
    private String mPlayType;

    public MyPlaysFragment() {
        
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param playType Parameter Monthly/Weekly.
     * @return A new instance of fragment MyPlaysFragment.
     */
    public static MyPlaysFragment newInstance(String playType) {
        MyPlaysFragment fragment = new MyPlaysFragment();
        Bundle args = new Bundle();
        args.putString(PLAY_TYPE, playType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayType = getArguments().getString(PLAY_TYPE);
          
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_plays, container, false);
        ButterKnife.bind(this, view);

        getMyPlay(mPlayType);
        return view;
    }

    private void getMyPlay(String mPlayType) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",mPlayType);
            AlertDialog dialog = Utilities.getProgressDialog(getContext(), getString(R.string.loading));
            dialog.show();
            WebAPI.call(getContext(), jsonObject, Constants.MY_PLAYS, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            MyPlaysModel myPlaysModel = new Gson().fromJson(response.toString(),MyPlaysModel.class);
                            if(myPlaysModel!=null && myPlaysModel.getData()!=null && myPlaysModel.getData().size()>0){
                                tvNoData.setVisibility(View.GONE);
                                rvMyPlays.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                                rvMyPlays.setAdapter(new MyPalysAdapter(getContext(),myPlaysModel.getData()));
                            }else{
                                tvNoData.setVisibility(View.VISIBLE);
                            }

                        }else{
                            Toasty.error(getContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(getContext(), Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
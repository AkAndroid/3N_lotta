package com.a3nlotta.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.a3nlotta.R;
import com.a3nlotta.adapter.MonthDrawTicketAdapter;
import com.a3nlotta.adapter.WinnerViewPagerAdapter;
import com.a3nlotta.model.home.HomeModel;
import com.a3nlotta.model.home.tickets.TicketData;
import com.a3nlotta.model.home.tickets.TicketModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MonthDrawTicketActivity extends AppCompatActivity {

    private MonthDrawTicketAdapter adapter;
    private Integer lastPage;

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @BindView(R.id.rvMonthTickets)
    RecyclerView rvMonthTickets;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.searchView)
    SearchView searchView;

    int pageNo=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_draw_ticket);
        ButterKnife.bind(this);

        GridLayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(this,3);
        rvMonthTickets.setLayoutManager(mLayoutManager);

        rvMonthTickets.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if(pageNo<=lastPage) {
                                getData(false);
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pageNo=1;
                getData(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                    getData(true);
                return false;
            }
        });

        adapter = new MonthDrawTicketAdapter(this);
        rvMonthTickets.setAdapter(adapter);
        getData(false);
    }

    private void getData(boolean fromQuery) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page",pageNo);
            jsonObject.put("search_input",searchView.getQuery());

            progressBar.setVisibility(View.VISIBLE);
            WebAPI.call(this, jsonObject, Constants.MONTH_DRAW_TICKET, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        if(response.getBoolean("success")){
                            pageNo=pageNo+1;
                            TicketModel ticketModel = new Gson().fromJson(response.toString(),TicketModel.class);
                            if(ticketModel.getData()!=null) {
                                lastPage=ticketModel.getData().getLastPage();
                                if(fromQuery){
                                    adapter.clearData();
                                }
                                adapter.setData(ticketModel.getData().getData());
                            }
                        }else{
                            Toasty.error(MonthDrawTicketActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(MonthDrawTicketActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
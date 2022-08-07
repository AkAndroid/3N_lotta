package com.a3nlotta.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.activity.PrivacyPolicyActivity;
import com.a3nlotta.adapter.FAQAdapter;
import com.a3nlotta.adapter.PrivacyPolicyAdapter;
import com.a3nlotta.model.privacypolicy.PrivacyPolicyModel;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQFragment extends Fragment {

    @BindView(R.id.rvFaq)
    RecyclerView rvFaq;

    public static FAQFragment newInstance() {
        FAQFragment fragment = new FAQFragment();
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
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        ButterKnife.bind(this, view);

        getFAQ();

        return view;
    }

    private void getFAQ() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lang","en");
            AlertDialog dialog = Utilities.getProgressDialog(getContext(), getString(R.string.loading));
            dialog.show();
            WebAPI.call(getContext(), jsonObject, Constants.FAQ, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            PrivacyPolicyModel privacyPolicyModel = new Gson().fromJson(response.toString(),PrivacyPolicyModel.class);
                            if(privacyPolicyModel!=null && privacyPolicyModel.getData()!=null){
                                rvFaq.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                                rvFaq.setAdapter(new FAQAdapter(getContext(),privacyPolicyModel.getData()));
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
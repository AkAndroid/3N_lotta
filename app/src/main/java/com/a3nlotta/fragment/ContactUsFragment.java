package com.a3nlotta.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.a3nlotta.R;
import com.a3nlotta.activity.LandingActivity;
import com.a3nlotta.activity.LoginActivity;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUsFragment extends Fragment {

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etMobileNo)
    TextInputEditText etMobileNo;
    @BindView(R.id.etMessage)
    TextInputEditText etMessage;
    @BindView(R.id.rgCommunication)
    RadioGroup rgCommunication;

    @OnClick(R.id.btSubmit)
    public void onSubmitClick(){
        if(TextUtils.isEmpty(etName.getText())){
            Toasty.error(getContext(),getString(R.string.enter_name)).show();
        }else if(TextUtils.isEmpty(etEmail.getText())){
            Toasty.error(getContext(),getString(R.string.enter_email)).show();
        }else if(TextUtils.isEmpty(etMobileNo.getText())){
            Toasty.error(getContext(),getString(R.string.enter_mobile)).show();
        }else if(TextUtils.isEmpty(etMessage.getText())){
            Toasty.error(getContext(),getString(R.string.enter_message)).show();
        }else{
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",etName.getText());
                jsonObject.put("email_id",etEmail.getText());
                jsonObject.put("mobile_no",etMobileNo.getText());
                jsonObject.put("type",rgCommunication.getCheckedRadioButtonId() == R.id.rbEmail?"Email":"Phone");
                jsonObject.put("message",etMessage.getText());

                AlertDialog dialog = Utilities.getProgressDialog(getContext(), getString(R.string.request_submiting));
                dialog.show();
                WebAPI.call(getContext(), jsonObject, Constants.CONTACT_US, Request.Method.POST, new VolleyManager() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.cancel();
                        try {
                            if(response.getBoolean("success")){

                                //Toasty.success(getContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                                etName.setText("");
                                etEmail.setText("");
                                etMobileNo.setText("");
                                etMessage.setText("");
                                Utilities.showCustomAlert(getContext(),getString(R.string.thank_you_contact_us),false,true,null);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
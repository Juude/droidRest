package net.juude.droidrest.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.juude.droidrest.R;
import net.juude.droidrest.utils.Toaster;

import org.json.JSONObject;


/**
 * Created by juude on 15/5/5.
 */
public class VolleyFragment extends Fragment implements View.OnClickListener {
    private  CacheableStringRequest mStringRequest;
    private  JsonObjectRequest mJsonObjectRequest;
    private  FastJsonRequest mFastJsonRequest;

    private TextView mResultView;
    private ProgressDialog mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_volley, null);
        v.findViewById(R.id.string_request).setOnClickListener(this);
        v.findViewById(R.id.json_request).setOnClickListener(this);
        v.findViewById(R.id.fastjson_request).setOnClickListener(this);
        mResultView = (TextView) v.findViewById(R.id.result);
        return v;
    }

    private void showProgress(String text) {
        if(mProgressBar == null) {
            mProgressBar = new ProgressDialog(getActivity());
        }
        mProgressBar.setMessage(text);
        mProgressBar.show();

    }

    private void hideProgress(){
        mProgressBar.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.string_request:
                createStringRequest();
                break;
            case R.id.json_request:
                createJsonRequest();
                break;
            case R.id.fastjson_request:
                createFastJsonRequest();
                break;
        }
    }

    private void createStringRequest() {
        showProgress("正在加载");
        CharSequence url = ((EditText) getView().findViewById(R.id.edit_url)).getText();
        mResultView.setText(null);
        mStringRequest = new CacheableStringRequest(url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mResultView.setText(response);
                Toaster.show(getActivity(), "加载成功");
                hideProgress();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toaster.show(getActivity(), "加载失败");
                        hideProgress();
                    }
                }
        );
        ApiManager.addRequest(mStringRequest);
    }

    private void createJsonRequest() {
        showProgress("正在加载");
        mResultView.setText(null);
        mJsonObjectRequest = new JsonObjectRequest("https://api.github.com/users/whatever", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mResultView.setText(response.toString());
                Toaster.show(getActivity(), "加载成功");
                hideProgress();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toaster.show(getActivity(), "加载失败");
                        hideProgress();
                    }
                }
        );
        ApiManager.addRequest(mJsonObjectRequest);
    }

    private void createFastJsonRequest() {
        showProgress("正在加载");
        mResultView.setText(null);
        mFastJsonRequest = new FastJsonRequest("https://api.github.com/users", new Response.Listener<JSON>() {
                @Override
                public void onResponse(JSON response) {
                    mResultView.setText(response.toString());
                    Toaster.show(getActivity(), "加载成功" + response.getClass());
                    hideProgress();
                }
            },
            new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toaster.show(getActivity(), "加载失败");
                        hideProgress();
                    }
            }
        );
        ApiManager.addRequest(mFastJsonRequest);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mStringRequest != null) {
            mStringRequest.cancel();
        }
        if(mJsonObjectRequest != null) {
            mJsonObjectRequest.cancel();
        }
        if(mFastJsonRequest != null) {
            mFastJsonRequest.cancel();
        }
    }
}

package com.magcomm.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magcomm.demos.R;

/**
 * Created by lenovo on 15-10-17.
 */
public class MenuFragment extends Fragment {
    private static final String TAG = "MenuFragment";
    private View mMainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment_screen_second,
                (ViewGroup) getActivity().findViewById(R.id.pager), false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup parent = (ViewGroup) mMainView.getParent();
        if (parent != null) {
            parent.removeAllViewsInLayout();
            Log.v(TAG, "fragment2-->移除已存在的View");
        }
        return mMainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}

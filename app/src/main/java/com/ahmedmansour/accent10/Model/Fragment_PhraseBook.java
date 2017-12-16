package com.ahmedmansour.accent10.Model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedmansour.accent10.R;

/**
 * Created by Ahmed Mansour on 10/25/2017.
 */

public class Fragment_PhraseBook extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_phrasebook_main, container, false);
        return view;
    }
}

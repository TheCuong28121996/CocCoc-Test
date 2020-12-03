package com.example.cococ.utils;

import android.view.View;

public interface ItemClickListener<D> {
    void onClick(View view, int position, D data);
}

package com.juborajsarker.mcctechnicaltest.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class StatefulRecyclerView extends RecyclerView {

    public static final String SAVED_SUPER_STATE = "super-state";
    public static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";

    public Parcelable mLayoutManagerSavedState;

    public StatefulRecyclerView(Context context) {
        super(context);
    }

    public StatefulRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatefulRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_SUPER_STATE, super.onSaveInstanceState());
        bundle.putParcelable(SAVED_LAYOUT_MANAGER, this.getLayoutManager().onSaveInstanceState());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mLayoutManagerSavedState = bundle.getParcelable(SAVED_LAYOUT_MANAGER);
            state = bundle.getParcelable(SAVED_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }


    public void restorePosition() {
        if (mLayoutManagerSavedState != null) {
            this.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
            mLayoutManagerSavedState = null;
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        restorePosition();
    }
}

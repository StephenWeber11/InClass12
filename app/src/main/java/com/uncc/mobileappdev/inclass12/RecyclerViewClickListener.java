package com.uncc.mobileappdev.inclass12;

import android.view.View;

/**
 * Created by Stephen on 4/19/2018.
 */

public interface RecyclerViewClickListener {
    public void recyclerViewListClicked(View v, int position);
    public void removeItem(View v, int position);
}

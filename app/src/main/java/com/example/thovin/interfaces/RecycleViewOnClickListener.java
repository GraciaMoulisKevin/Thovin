package com.example.thovin.interfaces;

public interface RecycleViewOnClickListener {

    String TAG_MENU = "menu";
    String TAG_PRODUCT = "product";
    String TAG_ORDER = "order";
    String TAG_RESTAURANT = "restaurant";

    /**
     * On item click
     * @param position The list position of the item
     * @param tag A specific tag (can be null). Useful to make difference between different type of
     *            item clicked. See the list of tag above.
     */
    void onItemClick(int position, String tag);
}

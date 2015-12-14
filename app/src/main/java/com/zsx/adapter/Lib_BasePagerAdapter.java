package com.zsx.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhusx on 2015/11/24.
 */
public abstract class Lib_BasePagerAdapter<T> extends PagerAdapter {
    private List<T> mList;
    private LayoutInflater mInflater;
    static final int IGNORE_ITEM_VIEW_TYPE = AdapterView.ITEM_VIEW_TYPE_IGNORE;
    private final RecycleBin recycleBin;

    public Lib_BasePagerAdapter(Context context) {
        this(context, new ArrayList<T>(), new RecycleBin());
    }

    public Lib_BasePagerAdapter(Context context, List<T> list) {
        this(context, list, new RecycleBin());
    }

    public Lib_BasePagerAdapter(Context context, boolean isCache) {
        this(context, new ArrayList<T>(), null);
    }

    public Lib_BasePagerAdapter(Context context, List<T> list, boolean isCache) {
        this(context, list, null);
    }

    Lib_BasePagerAdapter(Context context, List<T> list, RecycleBin recycleBin) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        this.recycleBin = recycleBin;
        if (recycleBin != null) {
            recycleBin.setViewTypeCount(getViewTypeCount());
        }
    }

    @Override
    public void notifyDataSetChanged() {
        recycleBin.scrapActiveViews();
        super.notifyDataSetChanged();
    }

    public abstract View getView(LayoutInflater inflater, int position, T t, View convertView, ViewGroup container);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if (recycleBin != null) {
            int viewType = getItemViewType(position);
            if (viewType != IGNORE_ITEM_VIEW_TYPE) {
                view = recycleBin.getScrapView(position, viewType);
            }
        }
        view = getView(mInflater, position, mList.get(position), view, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        if (recycleBin != null) {
            int viewType = getItemViewType(position);
            if (viewType != IGNORE_ITEM_VIEW_TYPE) {
                recycleBin.addScrapView(view, position, viewType);
            }
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

class RecycleBin {
    /**
     * Views that were on screen at the start of layout. This array is populated at the start of
     * layout, and at the end of layout all view in activeViews are moved to scrapViews.
     * Views in activeViews represent a contiguous range of Views, with position of the first
     * view store in mFirstActivePosition.
     */
    private View[] activeViews = new View[0];
    private int[] activeViewTypes = new int[0];

    /**
     * Unsorted views that can be used by the adapter as a convert view.
     */
    private SparseArray<View>[] scrapViews;

    private int viewTypeCount;

    private SparseArray<View> currentScrapViews;

    public void setViewTypeCount(int viewTypeCount) {
        if (viewTypeCount < 1) {
            throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
        }
        //noinspection unchecked
        SparseArray<View>[] scrapViews = new SparseArray[viewTypeCount];
        for (int i = 0; i < viewTypeCount; i++) {
            scrapViews[i] = new SparseArray<View>();
        }
        this.viewTypeCount = viewTypeCount;
        currentScrapViews = scrapViews[0];
        this.scrapViews = scrapViews;
    }

    protected boolean shouldRecycleViewType(int viewType) {
        return viewType >= 0;
    }

    /**
     * @return A view from the ScrapViews collection. These are unordered.
     */
    View getScrapView(int position, int viewType) {
        if (viewTypeCount == 1) {
            return retrieveFromScrap(currentScrapViews, position);
        } else if (viewType >= 0 && viewType < scrapViews.length) {
            return retrieveFromScrap(scrapViews[viewType], position);
        }
        return null;
    }

    /**
     * Put a view into the ScrapViews list. These views are unordered.
     *
     * @param scrap The view to add
     */
    void addScrapView(View scrap, int position, int viewType) {
        if (viewTypeCount == 1) {
            currentScrapViews.put(position, scrap);
        } else {
            scrapViews[viewType].put(position, scrap);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            scrap.setAccessibilityDelegate(null);
        }
    }

    /**
     * Move all views remaining in activeViews to scrapViews.
     */
    void scrapActiveViews() {
        final View[] activeViews = this.activeViews;
        final int[] activeViewTypes = this.activeViewTypes;
        final boolean multipleScraps = viewTypeCount > 1;

        SparseArray<View> scrapViews = currentScrapViews;
        final int count = activeViews.length;
        for (int i = count - 1; i >= 0; i--) {
            final View victim = activeViews[i];
            if (victim != null) {
                int whichScrap = activeViewTypes[i];

                activeViews[i] = null;
                activeViewTypes[i] = -1;

                if (!shouldRecycleViewType(whichScrap)) {
                    continue;
                }

                if (multipleScraps) {
                    scrapViews = this.scrapViews[whichScrap];
                }
                scrapViews.put(i, victim);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    victim.setAccessibilityDelegate(null);
                }
            }
        }

        pruneScrapViews();
    }

    /**
     * Makes sure that the size of scrapViews does not exceed the size of activeViews.
     * (This can happen if an adapter does not recycle its views).
     */
    private void pruneScrapViews() {
        final int maxViews = activeViews.length;
        final int viewTypeCount = this.viewTypeCount;
        final SparseArray<View>[] scrapViews = this.scrapViews;
        for (int i = 0; i < viewTypeCount; ++i) {
            final SparseArray<View> scrapPile = scrapViews[i];
            int size = scrapPile.size();
            final int extras = size - maxViews;
            size--;
            for (int j = 0; j < extras; j++) {
                scrapPile.remove(scrapPile.keyAt(size--));
            }
        }
    }

    static View retrieveFromScrap(SparseArray<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size > 0) {
            // See if we still have a view for this position.
            for (int i = 0; i < size; i++) {
                int fromPosition = scrapViews.keyAt(i);
                View view = scrapViews.get(fromPosition);
                if (fromPosition == position) {
                    scrapViews.remove(fromPosition);
                    return view;
                }
            }
            int index = size - 1;
            View r = scrapViews.valueAt(index);
            scrapViews.remove(scrapViews.keyAt(index));
            return r;
        } else {
            return null;
        }
    }
}

package com.zsx.widget.v7;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 *  ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack)
 *  itemTouchHelper.attachToRecyclerView(recyclerView)
 *
 *  View.setOnTouchListener(new View.OnTouchListener() {
 *      public boolean onTouch(View v, MotionEvent event) {
 *            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
 *               itemTouchHelper.startDrag(viewHolder);
 *            }
 *            return false;
 *      }
 *  });
 *
 * 还未完成
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/6/27 13:40
 */
class Lib_ItemTouchHelper extends ItemTouchHelper.Callback {
    private Paint p = new Paint();

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        int from, to;
        from = viewHolder.getAdapterPosition();
        to = target.getAdapterPosition();
        if (recyclerView.getAdapter() instanceof Lib_BaseRecyclerAdapter) {
            ((Lib_BaseRecyclerAdapter) recyclerView.getAdapter())._moveItemToUpdate(from, to);
        }
        return true;
    }

    RecyclerView recyclerView;

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (recyclerView != null) {
            if (recyclerView.getAdapter() != null) {
                if (recyclerView.getAdapter() instanceof Lib_BaseRecyclerAdapter) {
                    ((Lib_BaseRecyclerAdapter) recyclerView.getAdapter())._removeItemToUpdate(viewHolder.getAdapterPosition());
                }
            }
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(final Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (this.recyclerView == null) {
            this.recyclerView = recyclerView;
        }
        // Fade out the view as it is swiped out of the parent's bounds
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            View itemView = viewHolder.itemView;
//            Bitmap icon;
//            if (dX > 0) {
//                icon = BitmapFactory.decodeResource(recyclerView.getContext().getResources(), R.drawable.home_topic);
//                // Set color for right swipe
//                p.setColor(ContextCompat.getColor(recyclerView.getContext(), R.color.lib_red));
//                // Draw Rect with varying right side, equal to displacement dX
//                c.drawRect((float) itemView.getLeft() + _DensityUtil.dip2px(recyclerView.getContext(), 0), (float) itemView.getTop(), dX + _DensityUtil.dip2px(recyclerView.getContext(), 0),
//                        (float) itemView.getBottom(), p);
//                // Set the image icon for right swipe
//                c.drawBitmap(icon, (float) itemView.getLeft() + _DensityUtil.dip2px(recyclerView.getContext(), 16), (float) itemView.getTop() +
//                        ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight()) / 2, p);
//                icon.recycle();
//            }
//        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            __onItemSelected(viewHolder, actionState);
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        __onItemClear(recyclerView, viewHolder);
    }

    protected void __onItemSelected(RecyclerView.ViewHolder viewHolder, int actionState) {
    }

    protected void __onItemClear(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    }
}

package com.zsx.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * 
 * 下拉刷新ListView
 * 
 * @description onRefreshComplete() Activity中调用, 通知ListView 刷新完成<br/>
 */
public class Lib_Widget_ListView extends ListView implements OnScrollListener {

	public Lib_Widget_ListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public Lib_Widget_ListView(Context context) {
		super(context);
		init();
	}

	public Lib_Widget_ListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		setBackgroundDrawable(null);
		setCacheColorHint(Color.TRANSPARENT);
		setHorizontalFadingEdgeEnabled(false);
		setVerticalFadingEdgeEnabled(false);
	}

	private LinearLayout headViewLayout;

	private enum HeadStatus {
		RELEASE_To_REFRESH, // 松开刷新
		PULL_To_REFRESH, // 下拉刷新
		REFRESHING, // 正在刷新
		DONE // 默认

	}

	private enum FootStatus {
		DONE, // 默认
		LOADING, // 正在加载更多
		NO_DATA, // 没有数据
		ERROR// 加载数据失败
	}

	private int scrollTotalItemCount;// 当前滑动位置显示的Item总数
	private HeadStatus headStatus = null;
	private FootStatus footStatus = null;
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	private XListViewHeader headView;
	private XListViewFooter footView;
	private XListViewEmpty emptyView;
	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;
	private int headContentHeight;
	private int startY;
	/** 控制滑动下拉惯性 启动下拉刷新 */
	private boolean isUserTouch;
	private boolean isBack;
	private OnRefreshListener onRefreshListener;
	private OnLoadingMoreListener onLoadMoreListener;
	private OnScrollListener onScrollListener;
	private int delayTime = 300;

	public void _setHeadView(OnRefreshListener refreshListener) {
		if (onRefreshListener == null) {
			initHeadView();
			this.onRefreshListener = refreshListener;
		} else {
			throw new IllegalArgumentException("headView inited!!!");
		}
	}

	public void _setFootView(OnLoadingMoreListener loadMoreListener) {
		if (onLoadMoreListener == null) {
			initFootView();
			this.onLoadMoreListener = loadMoreListener;
		} else {
			throw new IllegalArgumentException("footView inited!!!");
		}
	}

	private void initHeadView() {
		if (getAdapter() != null) {
			throw new RuntimeException("setHeadView() 必须在 setAdapter() 以前!!");
		}
		if (headView != null) {
			return;
		}
		headViewLayout = new LinearLayout(getContext());
		headViewLayout.setOrientation(LinearLayout.VERTICAL);
		headViewLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		super.addHeaderView(headViewLayout, null, false);
		this.headView = new XListViewHeader(getContext());
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
		addHeaderView(headView);
		super.setOnScrollListener(this);
		headStatus = HeadStatus.DONE;
	}

	public void _setHeadViewLayoutParams(LayoutParams lp) {
		if (headViewLayout != null) {
			headViewLayout.setLayoutParams(lp);
		}
	}

	public void _setFootViewLayoutParams(LayoutParams lp) {
		if (footView != null) {
			footView.setLayoutParams(lp);
		}
	}

	private void initFootView() {
		if (getAdapter() != null) {
			throw new RuntimeException("_setFootView() 必须在 setAdapter() 以前!!");
		}
		if (footView != null) {
			return;
		}
		footView = new XListViewFooter(getContext());
		footView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setViewPadding(footView, isShowFootView);
		footView.setOnClickListener(onLoadMoreClickListener);
		addFooterView(footView);
		footStatus = FootStatus.DONE;
		super.setOnScrollListener(this);
	}

	private void setViewPadding(View view, boolean isShow) {
		measureView(view);
		int headContentHeight = view.getMeasuredHeight();
		if (isShow) {
			view.setPadding(0, 0, 0, 0);
		} else {
			view.setPadding(0, -1 * headContentHeight, 0, 0);
		}
	}

	private OnClickListener onLoadMoreClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (footStatus == null) {
				return;
			}
			if (headStatus != null && headStatus != HeadStatus.DONE) {
				return;
			}
			if (footStatus == FootStatus.NO_DATA) {
				return;
			}
			if (footStatus == FootStatus.DONE || footStatus == FootStatus.ERROR) {
				ListAdapter adapter = getAdapter();
				if (adapter != null) {
					final int count = getAdapterCount();
					if (onLoadMoreListener != null) {
						if (onLoadMoreListener.hasMore(count)) {
							changeFootViewByState(FootStatus.LOADING);
							onLoadMoreListener.loadMoreData(
									Lib_Widget_ListView.this, count);
						} else {
							changeFootViewByState(FootStatus.NO_DATA);
						}
					}
				}
			}
		}
	};

	@Override
	public void addHeaderView(View v) {
		if (headViewLayout != null) {
			headViewLayout.addView(v, new LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
		} else {
			super.addHeaderView(v);
		}
	}

	@Override
	public void addHeaderView(View v, Object data, boolean isSelectable) {
		if (headViewLayout != null) {
			headViewLayout.addView(v);
		} else {
			super.addHeaderView(v, data, isSelectable);
		}
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		this.onScrollListener = l;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (headStatus == HeadStatus.REFRESHING) {
			changeHeaderViewByState(HeadStatus.DONE);
		}
		if (footStatus == FootStatus.LOADING) {
			changeFootViewByState(FootStatus.DONE);
		}
		if (emptyView != null) {
			emptyView._setDefault();
		}
		super.setAdapter(adapter);
	}

	/**
	 * 是否正在加载数据
	 * 
	 * @return
	 */
	public boolean _isLoading() {
		return headStatus == HeadStatus.REFRESHING
				|| footStatus == FootStatus.LOADING;
	}

	/**
	 * 添加了HeadView 和FootView 之后getAdapter.getCount 会比实际的大2
	 * 
	 * @return
	 */
	int getAdapterCount() {
		ListAdapter adapter = getAdapter();
		if (adapter == null) {
			return 0;
		}
		return adapter.getCount() - getHeaderViewsCount()
				- getFooterViewsCount();
	}

	public void onScroll(AbsListView view, int firstVisiableItem,
			int visibleItemCount, int totalItemCount) {
		this.scrollTotalItemCount = visibleItemCount + firstVisiableItem;
		if (onScrollListener != null) {
			onScrollListener.onScroll(view, firstVisiableItem,
					visibleItemCount, totalItemCount);
		}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		isUserTouch = (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
		if (onScrollListener != null) {
			onScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	/**
	 * 隐藏FootView
	 * 
	 * @param isShow
	 */
	public void _setShowFootView(boolean isShow) {
		isShowFootView = isShow;
		if (footView != null) {
			setViewPadding(footView, isShow);
		}
	}

	private boolean isShowFootView = true;

	/**
	 * 设置HeadView 背景色
	 * 
	 * @param colorID
	 */
	public void _setHeadViewBackgroupResource(int colorID) {
		headViewLayout.setBackgroundResource(colorID);
		if (headView != null) {
			headView.setBackgroundResource(colorID);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isUserTouch = true;
			if (getFirstVisiblePosition() == 0 && !isRecored) {
				isRecored = true;
				startY = (int) event.getY();
				// Log.v(TAG, "在down时候记录当前位置‘");
			}
			break;

		case MotionEvent.ACTION_UP:
			if (footStatus != FootStatus.LOADING
					&& headStatus != HeadStatus.REFRESHING) {
				if (headStatus == null || headStatus == HeadStatus.DONE) {
					if (event.getY() - startY < 40) {
						ListAdapter adapter = getAdapter();
						if (adapter != null) {
							if (scrollTotalItemCount == adapter.getCount()) {
								final int count = getAdapterCount();
								if (onLoadMoreListener != null) {
									if (onLoadMoreListener.hasMore(count)) {
										changeFootViewByState(FootStatus.LOADING);
										onLoadMoreListener.loadMoreData(this,
												count);
									} else {
										changeFootViewByState(FootStatus.NO_DATA);
									}
								}
							}
						}
					}
				}
				if (headStatus == HeadStatus.DONE) {
					// 什么都不做
				}
				if (headStatus == HeadStatus.PULL_To_REFRESH) {
					changeHeaderViewByState(HeadStatus.DONE);
					// Log.v(TAG, "由下拉刷新状态，到done状态");
				}
				if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
					changeHeaderViewByState(HeadStatus.REFRESHING);
					// Log.v(TAG, "由松开刷新状态，到done状态");
					if (onRefreshListener != null) {
						onRefreshListener.onRefresh(this);
					}
				}
			}
			isRecored = false;
			isBack = false;
			break;

		case MotionEvent.ACTION_MOVE:
			if (isUserTouch) {
				int tempY = (int) event.getY();

				if (!isRecored && getFirstVisiblePosition() == 0) {
					// Log.v(TAG, "在move时候记录下位置");
					isRecored = true;
					startY = tempY;
				}
				if (headStatus != HeadStatus.REFRESHING && isRecored
						&& footStatus != FootStatus.LOADING) {

					// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

					// 可以松手去刷新了
					if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
						setSelection(0);
						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							changeHeaderViewByState(HeadStatus.PULL_To_REFRESH);
							// Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
						}
						// 一下子推到顶了
						else if (tempY - startY <= 0) {
							changeHeaderViewByState(HeadStatus.DONE);
							// Log.v(TAG, "由松开刷新状态转变到done状态");
						}
						// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
						else {
							// 不用进行特别的操作，只用更新paddingTop的值就行了
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (headStatus == HeadStatus.PULL_To_REFRESH) {
						setSelection(0);
						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headContentHeight) {
							isBack = true;
							changeHeaderViewByState(HeadStatus.RELEASE_To_REFRESH);
							// Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
						}
						// 上推到顶了
						else if (tempY - startY <= 0) {
							changeHeaderViewByState(HeadStatus.DONE);
							// Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
						}
					}

					// done状态下
					if (headStatus == HeadStatus.DONE) {
						if (tempY - startY > 0) {
							changeHeaderViewByState(HeadStatus.PULL_To_REFRESH);
						}
					}

					// 更新headView的size
					if (headStatus == HeadStatus.PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);
					}

					// 更新headView的paddingTop
					if (headStatus == HeadStatus.RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			if (headStatus != HeadStatus.REFRESHING && isRecored
					&& footStatus != FootStatus.LOADING) {
				changeHeaderViewByState(HeadStatus.DONE);
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private void changeFootViewByState(FootStatus state) {
		this.footStatus = state;
		switch (state) {
		case DONE:
			footView._onDone();
			break;
		case LOADING:
			footView._onDoneToLoadMore();
			break;
		case NO_DATA:
			footView._onNoData();
			break;
		case ERROR:
			footView._onLoadMoreToError();
			break;
		}
	}

	private void changeHeaderViewByState(HeadStatus state) {
		this.headStatus = state;
		switch (state) {
		case RELEASE_To_REFRESH:
			headView.onDownReleaseToRefresh();
			// Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				headView.onDownPullToRefresh(isBack);
				isBack = false;
			} else {
				headView.onDownPullToRefresh(isBack);
			}
			// Log.v(TAG, "当前状态，下拉刷新");
			break;

		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			headView.onDownToRefreshing();
			// Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			headView.onDoneToRefresh();
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			// Log.v(TAG, "当前状态，done");
			break;
		}
	}

	/**
	 * 刷新Listener
	 */
	public interface OnRefreshListener {
		/**
		 * 回调开始刷新
		 * 
		 * @return 是否成功加载到数据
		 */
		void onRefresh(Lib_Widget_ListView listView);
	}

	/**
	 * 加载更多Listener
	 */
	public interface OnLoadingMoreListener {
		/**
		 * 该方法会在在新线程中执行
		 * 
		 * @param itemCount
		 *            当前显示的总数
		 * @return 是否成功加载到数据
		 */
		void loadMoreData(Lib_Widget_ListView listView, int itemCount);

		/**
		 * 
		 * @param itemCount
		 *            adapter的Count
		 * @return
		 */
		boolean hasMore(int itemCount);
	}

	/**
	 * 通知刷新完成
	 */
	public void _setLoadingComplete() {
		if (headStatus == HeadStatus.REFRESHING) {
			headView.onDoneToRefresh();
			postDelayed(new Runnable() {

				@Override
				public void run() {
					changeHeaderViewByState(HeadStatus.DONE);
				}
			}, delayTime);
		}
		if (footStatus == FootStatus.LOADING) {
			changeFootViewByState(FootStatus.DONE);
		}
		if (footStatus == FootStatus.NO_DATA) {
			changeFootViewByState(FootStatus.DONE);
		}
		if (emptyView != null) {
			if (getAdapter().isEmpty()) {
				emptyView._setNoData();
			} else {
				emptyView._setDefault();
			}
		}
	}

	/**
	 * 通知刷新失败
	 * 
	 */
	public void _setLoadingError(String message) {
		if (headStatus == HeadStatus.REFRESHING) {
			Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
			changeHeaderViewByState(HeadStatus.DONE);
		}
		if (footStatus == FootStatus.LOADING) {
			changeFootViewByState(FootStatus.ERROR);
		}
		if (emptyView != null) {
			emptyView._setError();
		}
	}

	/**
	 * 加载更多数据
	 */
	public void _startLoadMoreData() {
		if (getAdapter() == null) {
			throw new IllegalStateException("getAdapter() is null");
		}
		if (onLoadMoreListener == null) {
			throw new IllegalStateException("no setFootView()");
		}
		if (emptyView != null) {
			emptyView.setEnabled(false);
			if (getAdapterCount() == 0) {
				emptyView._setLoading();
			}
		}
		changeFootViewByState(FootStatus.LOADING);
		onLoadMoreListener.loadMoreData(this, getAdapterCount());
	}

	/**
	 * @param delayTiem
	 *            加载完成之后 提示成功的View 持续显示的时间
	 */
	public void _setDelayTime(int delayTiem) {
		this.delayTime = delayTiem;
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean removeHeaderView(View v) {
		headViewLayout.removeView(v);
		return super.removeHeaderView(v);
	}

//	/**
//	 *
//	 * @param adapter
//	 * @param loadData
//	 * @param data
//	 * @param hasPullHeadView
//	 *            是否可以下拉刷新
//	 */
//	public <Result, P> void _setAdapter(
//			final Lib_BaseAdapter<Result> adapter,
//			final Lib_BaseHttpRequestData<Lib_HttpResult<List<Result>>, P> loadData,
//			final Lib_OnAutoLoadData<Result, P> data, boolean hasPullHeadView,
//			boolean hasLoadMoreFootView, boolean hasEmptyView,
//			final P... addParams) {
//		if (loadData == null) {
//			throw new IllegalArgumentException(
//					"Lib_HttpLoadData must not null!!!");
//		}
//		if (data == null) {
//			throw new IllegalArgumentException(
//					"OnAutoLoadData must not null!!!");
//		}
//		if (adapter == null) {
//			throw new IllegalArgumentException(
//					"Lib_BaseAdapter must not null!!!");
//		}
//		Lib_OnHttpLoadingListener<Lib_HttpResult<List<Result>>, P> listener = new Lib_OnHttpLoadingListener<Lib_HttpResult<List<Result>>, P>() {
//
//			@Override
//			public void onLoadStart(int id, RequestData<P> requestData) {
//				if (emptyView != null) {
//					if (getAdapterCount() == 0) {
//						emptyView._setLoading();
//					}
//				}
//			}
//
//			@Override
//			public void onLoadError(
//					int id,
//					final Lib_BaseHttpRequestData<Lib_HttpResult<List<Result>>, P> loadData,
//					RequestData<P> requestData,
//					Lib_HttpResult<List<Result>> result, boolean isAPIError,
//					String error_message) {
//				if (isAPIError) {
//					_setLoadingError("加载失败");
//					if (result != null) {
//						if (result.getTotalCount() == Lib_HttpResult.TOTAL_COUNT_DEFAULT) {
//							result.setTotalCount(0);
//						}
//					}
//					if (getAdapter().isEmpty()) {
//						if (emptyView != null) {
//							emptyView._setNoData();
//						}
//					}
//				} else {
//					_setLoadingError(error_message);
//				}
//			}
//
//			@Override
//			public void onLoadComplete(int id, RequestData<P> requestData,
//					Lib_HttpResult<List<Result>> b) {
//				if (b.getData().size() == 0) {
//					b.setTotalCount(Lib_HttpResult.TOTAL_COUNT_ERROR);
//					if (getAdapter().isEmpty()) {
//						if (emptyView != null) {
//							emptyView._setNoData();
//						}
//					} else {
//						if (footView != null) {
//							changeFootViewByState(FootStatus.NO_DATA);
//						}
//					}
//				} else {
//					if (requestData.isRefresh) {
//						adapter._setItemsToUpdate(b.getData());
//					} else {
//						int position = getFirstVisiblePosition();
//						adapter._addItemToUpdate(b.getData());
//						setSelection(position);
//					}
//				}
//				_setLoadingComplete();
//			}
//		};
//		if (hasPullHeadView) {
//			_setHeadView(new OnRefreshListener() {
//
//				@Override
//				public void onRefresh(Lib_Widget_ListView listView) {
//					if (loadData._isLoading()) {
//						return;
//					}
//					loadData._refreshData(data.onLoadData(
//							loadData._getRequestID(), 0, null, addParams));
//				}
//			});
//		}
//		if (hasLoadMoreFootView) {
//			_setFootView(new OnLoadingMoreListener() {
//
//				@Override
//				public void loadMoreData(Lib_Widget_ListView listView,
//						int itemCount) {
//					if (loadData._isLoading()) {
//						return;
//					}
//					if (itemCount == 0) {
//						loadData._loadData(data.onLoadData(
//								loadData._getRequestID(), itemCount, null,
//								addParams));
//					} else {
//						loadData._loadData(data.onLoadData(
//								loadData._getRequestID(), itemCount,
//								(Result) adapter.getItem(itemCount - 1),
//								addParams));
//					}
//				}
//
//				@Override
//				public boolean hasMore(int itemCount) {
//					Lib_HttpResult<List<Result>> result = loadData
//							._getLastData();
//					if (result == null) {
//						return true;
//					} else {
//						switch (result.getTotalCount()) {
//						case Lib_HttpResult.TOTAL_COUNT_DEFAULT:
//							return true;
//						case Lib_HttpResult.TOTAL_COUNT_ERROR:
//							return false;
//						case 0:
//							return false;
//						default:
//							return result.getTotalCount() > itemCount;
//						}
//					}
//				}
//			});
//		}
//		if (hasEmptyView) {
//			if (getEmptyView() == null) {
//				ViewParent parent = getParent();
//				if (parent instanceof ViewGroup) {
//					final ViewGroup viewGroup = (ViewGroup) parent;
//					if (emptyView == null) {
//						emptyView = new XListViewEmpty(getContext());
//					}
//					getViewTreeObserver().addOnGlobalLayoutListener(
//							new OnGlobalLayoutListener() {
//
//								@SuppressWarnings("deprecation")
//								@Override
//								public void onGlobalLayout() {
//									getViewTreeObserver()
//											.removeGlobalOnLayoutListener(this);
//									viewGroup.addView(
//											emptyView,
//											viewGroup
//													.indexOfChild(Lib_Widget_ListView.this) + 1,
//											new ViewGroup.LayoutParams(
//													getWidth() > 0 ? getWidth()
//															: ViewGroup.LayoutParams.MATCH_PARENT,
//													getHeight() > 0 ? getHeight()
//															: ViewGroup.LayoutParams.MATCH_PARENT));
//									setEmptyView(emptyView);
//									emptyView
//											.setOnClickListener(new OnClickListener() {
//
//												@Override
//												public void onClick(View v) {
//													emptyView._setLoading();
//													loadData._refreshData(data.onLoadData(
//															loadData._getRequestID(),
//															0, null, addParams));
//												}
//											});
//								}
//							});
//				} else {
//					if (LogUtil.DEBUG) {
//						LogUtil.e(this,
//								"getParent() no instanceof ViewGroup!!!");
//					}
//				}
//			}
//		}
//		setAdapter(adapter);
//		loadData._setOnLoadingListener(listener);
//	}
}

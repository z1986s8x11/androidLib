package org.zsx.android.api.util;

import android.os.AsyncTask;
import android.os.Bundle;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class AsyncTask_Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.util_asynctask);
	}

	/**
	 * AsyncTask说明
	 */
	public void newAsyncTask() {
		/**
		 * Task的实例必须在UI thread中创建 execute方法必须在UI thread中调用
		 * 该task只能被执行一次，否则多次调用时将会出现异常
		 */

		/**
		 * 泛型<Params, Progress, Result> Params 启动任务执行的输入参数，比如HTTP请求的URL Progress
		 * 后台任务执行的百分比 Result 后台执行任务最终返回的结果，比如String
		 */
		new AsyncTask<String, Integer, Boolean>() {
			/**
			 * 该方法将在执行实际的后台操作前被UI thread调用。 可以在该方法中做一些准备工作，如在界面上显示一个进度条
			 */
			protected void onPreExecute() {
			};

			/**
			 * 将在onPreExecute 方法执行后马上执行， 该方法运行在后台线程中。 这里将主要负责执行那些很耗时的后台计算工作。
			 * 可以调用 publishProgress方法来更新实时的任务进度 参数为泛型的Params 返回泛型的Result
			 */
			@Override
			protected Boolean doInBackground(String... params) {
				int progress = 0;
				// 将回调onProgressUpdate();
				publishProgress(progress);
				return false;
			}

			/**
			 * 在publishProgress方法被调用后， UI thread将调用这个方法从而在界面上展示任务的进展情况，
			 * 例如通过一个进度条进行展示 参数为泛型的Progress
			 */
			protected void onProgressUpdate(Integer... values) {
			};

			/**
			 * 在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用，
			 * 后台的计算结果将通过该方法传递到UI thread 参数为泛型的Result
			 */
			protected void onPostExecute(Boolean result) {
			};
		}.execute("参数");
	}
}

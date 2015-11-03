package org.zsx.android.api.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class Service_Service extends Service {
	private zMyBinder mBinder;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public IBinder onBind(Intent arg0) {
		if (mBinder == null) {
			mBinder = new zMyBinder();
		}
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	class zMyBinder extends Binder {

	}
}

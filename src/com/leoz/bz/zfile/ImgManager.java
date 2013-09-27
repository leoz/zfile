package com.leoz.bz.zfile;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.leoz.bz.zclient.ZCltManager;

public enum ImgManager {
	INSTANCE;

	private static final String TAG = "[LeoZ] ImgManager"; /// TODO: FIX ME
	
	private static final int THREAD_POOL_SIZE = 5;

	private final ExecutorService pool;
	private Map<ImgKey, ImageView> imageMap = Collections
			.synchronizedMap(new WeakHashMap<ImgKey, ImageView>());
	private Bitmap placeholder;

	ImgManager() {
		pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	public void setPlaceholder(Bitmap bmp) {
		placeholder = bmp;
	}

	public void loadBitmap(final ImgKey key, final ImageView imageView) {
		
		Log.d(TAG, "######### loadBitmap: " + key.toString()); /// FIX ME							

		imageMap.put(key, imageView);

		//Get image from cache
		Bitmap bitmap = ImgCache.INSTANCE.getThumbnail(key);
		
		if (setBitmap(key, imageView, bitmap) != true) {
			runThread(key, imageView);			
		}
		else {
			Log.d(TAG, "image loaded from cache: " + key.toString());
		}
	}
	
	private void runThread(final ImgKey key, final ImageView view) {
		pool.submit(new Runnable() {
			public void run() {
				ZCltManager.INSTANCE.invokeScanFile(key.mName, key.mSize);
			}
		});
	}

	private boolean setBitmap(final ImgKey key, final ImageView v, Bitmap b) {
		
		boolean r = false;
		
		if (b != null) {
			v.setImageBitmap(b);
			imageMap.remove(key);
			r = true;
		} else {
			v.setImageBitmap(placeholder);
		}
		
		return r;	
	}
}


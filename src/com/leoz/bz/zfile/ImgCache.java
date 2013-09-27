package com.leoz.bz.zfile;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public enum ImgCache {
	INSTANCE;

	private final Map<ImgKey, Bitmap> mCache;

	ImgCache() {
		
		mCache = new HashMap<ImgKey, Bitmap>();
	}

	public Bitmap getThumbnail(ImgKey key) {
		
		if (mCache.containsKey(key)) {
			return mCache.get(key);
		}

		return null;
	}
	
	public void putThumbnail(final ImgKey key, Bitmap b) {
		
		if (key != null && b != null) {
			mCache.put(key, b);			
		}
	}
}


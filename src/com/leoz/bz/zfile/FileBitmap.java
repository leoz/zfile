package com.leoz.bz.zfile;

import java.io.IOException;

import com.leoz.bz.zbase.ZFile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public enum FileBitmap {
	INSTANCE;

///	private static final String TAG = "FileBitmap"; /// TODO: FIX ME

    private boolean mInit = true;
    
    private int mMaxSize = 0;
	
	public void setContext(Context c) {
		
		if (mInit == true) {
			
			mInit = false;
			
			mMaxSize = getViewMaxSize(c);
			
			FileInfo.INSTANCE.init(c);
	    	FileIcon.INSTANCE.init(c);
	    	ImgManager.INSTANCE.setPlaceholder(FileIcon.INSTANCE.getImageIcon());
		}		
	}

	public void setFileBitmap (final ZFile f, final ImageView view) {
		
		int size = getViewSize(view);
		
		if (size <= 0) {
			size = mMaxSize;
		}
		
///		Log.v(TAG, "######### setFileBitmap:" + size); /// FIX ME							

		FileType t = FileInfo.INSTANCE.getFileType (f);
		
		if (t == FileType.FTYPE_IMAGE) {
			String url = null;
			try {
				url = f.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (url != null) {
				ImgManager.INSTANCE.loadBitmap(new ImgKey(url, size), view);					
			}			
		}
		else {
	        // Set icon
			Bitmap b = FileIcon.INSTANCE.getFileBitmap (t);
			if (t == FileType.FTYPE_DIRECTORY) {
				if (!f.canExecute() || !f.canRead()) {
					b = FileIcon.INSTANCE.getDirRed();				
				}				
			}
			
			view.setImageBitmap(b);			
		}
	}
	
	private int getViewSize(final ImageView view) {
		
		int size = 0;
		
		int w = 0;
		int h = 0;

		if (view != null) {
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
			if (params != null) {
				w = params.width;
				h = params.height;
			}
			else {
				w = view.getWidth();
				h = view.getHeight();
			}
		}
		
		if (w > h) {
			size = w;
		}
		else {
			size = h;				
		}
		
		return size;
	}
	
	private int getViewMaxSize(Context c) {
		
		int size = 0;
		
		if (c != null) {
			
			Display display = ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			
			Point p = new Point();
			display.getSize(p);
			
			int w = p.x;
			int h = p.y;
			
			if (w > h) {
				size = w;
			}
			else {
				size = h;				
			}
		}
		return size;
	}
}

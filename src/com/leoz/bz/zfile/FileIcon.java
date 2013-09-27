package com.leoz.bz.zfile;

import com.leoz.bz.zfile.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public enum FileIcon {
	INSTANCE;

    private Bitmap mIconDir;
    private Bitmap mIconDirRed;
    private Bitmap mIconImage;
    private Bitmap mIconVideo;
    private Bitmap mIconAudio;
    private Bitmap mIconText;
    private Bitmap mIconFile;

    private boolean mInit = true;

    public void init(Context c) {
		
		if (mInit) {
			
			mInit = false;
			
	    	mIconDir = BitmapFactory.decodeResource(c.getResources(), R.drawable.folder);
	    	mIconDirRed = BitmapFactory.decodeResource(c.getResources(), R.drawable.folder_red);
	    	mIconImage = BitmapFactory.decodeResource(c.getResources(), R.drawable.image);
	    	mIconVideo = BitmapFactory.decodeResource(c.getResources(), R.drawable.video);
	    	mIconAudio = BitmapFactory.decodeResource(c.getResources(), R.drawable.audio);
	    	mIconText = BitmapFactory.decodeResource(c.getResources(), R.drawable.text);
	    	mIconFile = BitmapFactory.decodeResource(c.getResources(), R.drawable.file);
		}		
	}
    
    public Bitmap getImageIcon () {
    	return mIconImage;
    }
	
    public Bitmap getDirRed () {
    	return mIconDirRed;
    }
    
	public Bitmap getFileBitmap (FileType t) {

		Bitmap b = null;
		
		switch (t) {
		case FTYPE_DIRECTORY:
			b = mIconDir;				
			break;
		case FTYPE_IMAGE:
			b = mIconImage;				
			break;
		case FTYPE_VIDEO:
			b = mIconVideo;
			break;
		case FTYPE_AUDIO:
			b = mIconAudio;
			break;
		case FTYPE_TEXT:
			b = mIconText;
			break;
		default:
			b = mIconFile;	
		}
		
		return b;
	}
}

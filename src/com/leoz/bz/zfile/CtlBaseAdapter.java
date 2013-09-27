package com.leoz.bz.zfile;

import com.leoz.bz.zbase.ZFile;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public abstract class CtlBaseAdapter extends BaseAdapter {
    public Context mContext;
    public ZFile[] mFiles;

    public CtlBaseAdapter(Context c, ZFile f) {
    	mContext = c;
    	FileBitmap.INSTANCE.setContext(c);
    	setData (f);   		
    }
    
    public int getCount() {
        return mFiles.length;
    }

    public Object getItem(int position) {
        return mFiles[position];
    }

    public long getItemId(int position) {
        return position;
    }
    
	public void setData(ZFile f) {
        mFiles = FileInfo.INSTANCE.getDir(f);    		
	}
	
	public void clearData() {
		mFiles = null;
		notifyDataSetChanged();
	}
	
	public void setViewBitmap (int position, final ImageView view) {
		FileBitmap.INSTANCE.setFileBitmap(mFiles[position], view);		
	}
}

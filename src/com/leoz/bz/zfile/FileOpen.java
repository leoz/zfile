package com.leoz.bz.zfile;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import com.leoz.bz.zbase.ZFile;

public class FileOpen {
	
	private static Activity mActivity = null;
	
    private static OnDirSelectedListener dirSelectedListener;
    private static OnImageSelectedListener imageSelectedListener;
	
	public interface OnDirSelectedListener {
	    public void onDirSelected(ZFile f, int context);
	}
	
	public interface OnImageSelectedListener {
	    public void onImageSelected(ZFile f);
	}
	
	public static void setActivity(Activity a) {
		
		mActivity = a;
		
		try {
			dirSelectedListener = (OnDirSelectedListener) mActivity;
			imageSelectedListener = (OnImageSelectedListener) mActivity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(mActivity.toString()
					+ " must implement listeners");
		}

	}
	
	public static void fileSelected (ZFile f, int context) {
		
		if (f.isDirectory()) {
			
			if (f.canExecute() && f.canRead()) {
				// Broadcast selection
				dirSelectedListener.onDirSelected(f, context);		
			}
			else {
				/// TODO: FIX ME!!!
				Toast.makeText(mActivity, "Cannot open directory: " + f.getName() + "\n" + "Insufficient privileges", Toast.LENGTH_SHORT).show();							
			}
		}
		else {
			openFile (f);
		}
	}
	
    private static void openFile(ZFile f) {
		/// TODO: FIX ME!!!
		//Toast.makeText(getActivity(), "File Clicked: "+ f.getName(), Toast.LENGTH_SHORT).show();
		
		openFileDefault(f);
    }
	
    public static void openFileDefault(ZFile f) {
    	
    	FileType t = FileInfo.INSTANCE.getFileType(f);
    	
    	if (t == FileType.FTYPE_IMAGE) {
			// Broadcast image selection
			imageSelectedListener.onImageSelected(f);		
    	}
    	else if (t == FileType.FTYPE_VIDEO) {
    		
    		/// Show video file    		
    	}
    	else {
    		
    		/// Call default application
    		
	    	Intent intent = new Intent(Intent.ACTION_VIEW);
	    	
	    	String mime = FileInfo.INSTANCE.getMimeType(f);
	    	
	        intent.setDataAndType(f.getUri(), mime);
	
	    	PackageManager pm = mActivity.getPackageManager();
	    	List<ResolveInfo> apps = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	
	    	if (apps.size() > 0) {
	    		mActivity.startActivity(intent);	
	    	}
	    	else {
				/// TODO: FIX ME!!!
				Toast.makeText(mActivity, "Cannot open file: " + f.getName() + "\n" + "File type is not supported", Toast.LENGTH_SHORT).show();							
	    	}    		
    	}
    }

}

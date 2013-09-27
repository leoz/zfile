package com.leoz.bz.zfile;

import android.content.Context;
import android.webkit.MimeTypeMap;

import com.leoz.bz.zbase.ZFile;

public enum FileInfo {
	INSTANCE;
	
	/// private static final String TAG = "FileInfo"; /// TODO: FIX ME
	
	private MimeTypeMap mMap;
    
    private boolean init = true;
    	
	public void init(Context c) {
		
		if (init == true) {
			
			init = false;
			
			mMap = MimeTypeMap.getSingleton();	    	
		}		
	}
	
	public ZFile[] getDir(ZFile f) {
		
		if (f == null) {
	    	ZFile[] ff = new ZFile[1];
	        ff[0] = new ZFile (ZFile.PARENT);
	        return ff;
		}
		else {
			return f.listDir();
		}
	}	
	
	public ZFile[] getDirByType(ZFile f, FileType type) {
		return setDirByType(getDir(f), type);
	}	
	
	private ZFile[] setDirByType(ZFile[] files, FileType type) {
		int n = 0;
		for (ZFile c : files) if (getFileType(c) == type) n++;
		ZFile[] ff = new ZFile[n];
		n = 0;
		for (ZFile c : files) if (getFileType(c) == type) ff[n++] = c;
		return ff;
	}

	public String getMimeType (ZFile f) {
        String extension = (MimeTypeMap.getFileExtensionFromUrl(f.getAbsolutePath())).toLowerCase();
    	/// Log.v(TAG, "getMimeType: Extension is - " + extension); /// TODO: FIX ME
        String m = mMap.getMimeTypeFromExtension(extension);
    	/// Log.v(TAG, "getMimeType: MIME type is - " + m); /// TODO: FIX ME
        return m;
	}
	
	public String getFileSize (ZFile f) {
		String s = null;
		if (!f.isDirectory()) {
			long len = f.length();
			
			if (len < 1024) {
				s = String.valueOf(len) + " B";
			}
			else {
				len = len / 1024;
				if (len < 1024) {
					s = String.valueOf(len) + " KB";					
				}
				else {
					len = len / 1024;					
					if (len < 1024) {
						s = String.valueOf(len) + " MB";					
					}
					else {
						len = len / 1024;					
						s = String.valueOf(len) + " GB";					
					}
				}
			}			
		}
		else {
			ZFile[] tmp = f.listFiles();
			if (tmp != null && tmp.length > 0) {
				s = tmp.length + " files";
			}
			else {
				s = "Empty";
			}
		}
		return s;
	}
	
	public String getFileTypeFullText (ZFile f) {
		String t = getFileTypeText (f);
		if (!f.isDirectory()) {
			String m = getMimeType (f);
			if (m != null) {
				m = m.substring(m.indexOf('/')+1);
				m = m.toUpperCase();
				t = m + " " + t;
			}			
		}
		return t;		
	}
	
	private String getFileTypeText (ZFile f) {
		String t = null;
		
		switch (getFileType(f)) {
		case FTYPE_DIRECTORY:
			t = "File folder";	
			break;
		case FTYPE_IMAGE:
			t = "Image";	
			break;
		case FTYPE_VIDEO:
			t = "Movie";	
			break;
		case FTYPE_AUDIO:
			t = "Sound";	
			break;
		case FTYPE_TEXT:
			t = "Text";	
			break;
		default:
			t = "";	
		}
		return t;
	}
		
	public FileType getFileType (ZFile f) {
		FileType t = FileType.FTYPE_FILE;
		
		if (f.isDirectory()) {
			t = FileType.FTYPE_DIRECTORY;
		}
		else {
			/// TODO: Log.v(TAG, "getFileType: is File"); /// FIX ME
			String m = getMimeType (f);
			
			if (m != null) {
				if (m.startsWith("image")) {
					t = FileType.FTYPE_IMAGE;
				}else if (m.startsWith("video")) {
					t = FileType.FTYPE_VIDEO;					
				}else if (m.startsWith("audio")) {
					t = FileType.FTYPE_AUDIO;					
				}else if (m.startsWith("text")) {
					t = FileType.FTYPE_TEXT;					
				}
			}			
		}
		
		/// TODO: Log.v(TAG, "getFileType: result: " + t); /// FIX ME
		return t;
	} 
}

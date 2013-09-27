package com.leoz.bz.zfile;

public class ImgKey {
	public String mName;
	public int mSize;
	
	public ImgKey (String name, int size) {
		mName = name;
		mSize = size;
	}

	@Override
	public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o.getClass() != getClass())
            return false;

        ImgKey k = (ImgKey) o;
        return (k.mSize == mSize && k.mName.equals(mName));
    }

	@Override
	public int hashCode() {
		
		int result = 13;
		
		result = 31 * result + mName.hashCode();
		result = 31 * result + mSize;
		
		return result;
	}

	@Override
	public String toString() {
		return "[" + mSize + ":" + mName + "]";
	}
}

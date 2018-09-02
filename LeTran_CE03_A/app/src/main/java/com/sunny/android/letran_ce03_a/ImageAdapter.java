
	// Name: Tran Le
	// JAv2 - 1809
	// File name: ImageAdapter.java

package com.sunny.android.letran_ce03_a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

	public class ImageAdapter extends BaseAdapter {

	// Member variables
	private static final String FILE_CONTAINER_NAME = "capture_images";
	private static final long BASE_ID = 0x00001;
	private Context iContext;
	private ArrayList<String> images;

	private static final String TAG = "ImageAdapter";

	// Constructor
	public ImageAdapter(Context _context, ArrayList<String> _images) {
		iContext = _context;
		images = _images;
	}

	@Override
	public int getCount() {
		if (images != null) {
			return images.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (images != null && 0 <= position || position < images.size()) {
			return images.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return BASE_ID + position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(iContext).inflate(R.layout.gridcell_custom, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder)convertView.getTag();
		}

		if (images != null) {
			Bitmap bmp = null;
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 2;
			bmp = BitmapFactory.decodeFile(images.get(position), opt);

			if (bmp != null) {
				vh.ivw_Image.setImageBitmap(bmp);
			}
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView ivw_Image;

		ViewHolder(View _layout) {
			ivw_Image = (ImageView)_layout.findViewById(R.id.ivw_ImageHolder);
		}
	}
}

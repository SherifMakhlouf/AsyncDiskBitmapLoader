package com.lilmak.asyncdiskbitmaploader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.lilmak.asyncdiskbitmaploader.R;
import com.lilmak.asyncdiskbitmaploader.util.AsyncDrawable;
import com.lilmak.asyncdiskbitmaploader.util.BitmapWorkerTask;
import com.lilmak.asyncdiskbitmaploader.util.FileBitmapWorkerTask;

public class AsyncDiskBitmapLoader {

	private static Bitmap holder;
	private LruCache<Long, Bitmap> lruCache;
	Context context;

	/**
	 * 
	 * @param cxt
	 *            Application Context
	 * @param cache
	 *            Your cache if null it will use a default LRU Cache
	 * @param holderBitmap
	 *            A holder that will be set to the imageView until the image is
	 *            loaded if null a default bitmap will be used
	 */
	public AsyncDiskBitmapLoader(Context cxt, LruCache<Long, Bitmap> cache,
			Bitmap holderBitmap) {
		if (cache != null)
			cache = lruCache;
		context = cxt;
		if (holderBitmap == null)
			holder = BitmapFactory.decodeResource(cxt.getResources(),
					R.drawable.ic_launcher);
	}

	/**
	 * Loads bitmap from File
	 * 
	 * @param path
	 *            bitmap path
	 * @param cached
	 *            True if the bitmap should be cached
	 * @param imageView
	 *            imageView to load bitmap in
	 */
	public void loadFromFile(String path, boolean cached, ImageView imageView) {
		if (FileBitmapWorkerTask.cancelPotentialWork(path, imageView)) {
			final BitmapWorkerTask task = new FileBitmapWorkerTask(imageView,null);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(
					context.getResources(), holder, task);
			imageView.setImageDrawable(asyncDrawable);
			((FileBitmapWorkerTask) task).execute(path);
		}
	}

	/**
	 * Loads bitmap from File with given options
	 * @param path
	 * 		bitmap path
	 * @param cached
	 * 		 True if the bitmap should be cached
	 * @param imageView
	 *  	imageView to load bitmap in
	 * @param options
	 * 		
	 */
	public void loadFromFile(String path, boolean cached, ImageView imageView,BitmapFactory.Options options) {
		if (FileBitmapWorkerTask.cancelPotentialWork(path, imageView)) {
			final BitmapWorkerTask task = new FileBitmapWorkerTask(imageView,options);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(
					context.getResources(), holder, task);
			imageView.setImageDrawable(asyncDrawable);
			((FileBitmapWorkerTask) task).execute(path);
		}
	}

	/**
	 * Sets a holder bitmap
	 * 
	 * @param holderBitmap
	 */
	public void setHolder(Bitmap holderBitmap) {
		holder = holderBitmap;
	}
}


	// Name: Tran Le
	// JAV2 - 1809
	// File name: MainActivity.java

package com.sunny.android.letran_ce03_a;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sunny.android.letran_ce03_a.fragments.GridViewFragment;
import com.sunny.android.letran_ce03_a.interfaces.ViewImageInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewImageInterface {

	// Member variables
	private static final int REQUEST_CAMERA = 0x00010;
	private static final int REQUEST_VIEW_IMAGE = 0x00101;
	private static final String AUTHORITY = "com.sunny.android.imageprovider";
	private static final String IMAGE_FOLDER = "capture_images";
	private int imageId = 0;

	private static final String TAG = "MainActivity";

	private File storage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		storage = getExternalFilesDir(IMAGE_FOLDER);

		getSupportFragmentManager().beginTransaction().add(R.id.gridFragmentHolder,
				GridViewFragment.newInstance(returnFiles())).commit();
	}

	// Function to setup menu on action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_take_picture, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Function to create handler for option menu items events
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_btn_capture) {
			Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputUri());
			photoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			startActivityForResult(photoIntent, REQUEST_CAMERA);
		}
		return super.onOptionsItemSelected(item);
	}

	private Uri getOutputUri() {
	File imageFile = getImageFile();

		if (imageFile == null) {
			return null;
		}

		return FileProvider.getUriForFile(this, AUTHORITY, imageFile.getAbsoluteFile());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			getSupportFragmentManager().beginTransaction().replace(R.id.gridFragmentHolder,
					GridViewFragment.newInstance(returnFiles())).commit();
		}
	}

	private File getImageFile() {
		String fileName = "Image" + imageId + ".jpg";
		File protectedStorage = getExternalFilesDir(IMAGE_FOLDER);
		String[] nameOfFiles = storage.list();

		for (String name: nameOfFiles) {
			if (name.equals(fileName)) {
				imageId++;
				fileName = "Image" + imageId + ".jpg";
			}
		}
		File imageFile = new File(protectedStorage, fileName);

		try {
			// To suppress the warning, log it out
			boolean canCreate = imageFile.createNewFile();
			Log.i(TAG, "getImageFile: "+canCreate);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageFile;
	}

	private ArrayList<String> returnFiles() {
		File[] allFiles = storage.listFiles();
		ArrayList<String> paths = new ArrayList<>();

		for (int i = 0; i < allFiles.length; i++) {
			String path = Uri.fromFile(allFiles[i]).getPath();
			Log.i(TAG, "returnFiles: "+path);
			paths.add(path);
		}

		return paths;
	}

	@Override
	public void viewImage(int position) {
		Intent viewIntent = new Intent(Intent.ACTION_VIEW);
		viewIntent.setDataAndType(FileProvider.getUriForFile(this, AUTHORITY, storage.listFiles()[position]), "image/*");
		viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivityForResult(viewIntent, REQUEST_VIEW_IMAGE);
	}
}

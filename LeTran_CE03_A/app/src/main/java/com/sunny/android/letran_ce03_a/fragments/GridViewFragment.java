
	// Name: Tran Le
	// JAV2 - 1809
	// File name: GridViewFragment.java

package com.sunny.android.letran_ce03_a.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.sunny.android.letran_ce03_a.ImageAdapter;
import com.sunny.android.letran_ce03_a.R;
import com.sunny.android.letran_ce03_a.interfaces.ViewImageInterface;

import java.io.File;
import java.util.ArrayList;

public class GridViewFragment extends Fragment {

	private static final String TAG = "GridViewFragment";
	private ViewImageInterface listener;

	// Member variable
	private static final String KEY_IMAGE_PATHS = "KEY_IMAGE_PATH";

	public GridViewFragment() {
		// Default empty constructor
	}

	// Function to create new instance of fragment
	public static GridViewFragment newInstance(ArrayList<String> paths) {
		Bundle args = new Bundle();

		args.putStringArrayList(KEY_IMAGE_PATHS, paths);

		GridViewFragment fragment = new GridViewFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof ViewImageInterface) {
			listener = (ViewImageInterface)context;
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_gridview, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getView() != null && getArguments() != null) {
			ArrayList<String> imagePaths = getArguments().getStringArrayList(KEY_IMAGE_PATHS);

			if (imagePaths != null) {
				GridView gridView = (GridView) getView().findViewById(R.id.gvw_GridView);
				ConstraintLayout empty_state = (ConstraintLayout) getView().findViewById(R.id.empty_state);
				if (imagePaths.size() > 0) {
					ImageAdapter adapter = new ImageAdapter(getActivity(), imagePaths);
					gridView.setAdapter(adapter);
					empty_state.setVisibility(View.GONE);
					gridView.setVisibility(View.VISIBLE);
				} else {
					empty_state.setVisibility(View.VISIBLE);
					gridView.setVisibility(View.INVISIBLE);
				}

				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if (listener != null) {
							listener.viewImage(position);
						}
					}
				});
			}
		}
	}
}

package com.karthek.android.s.ainfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthek.android.s.ainfo.databinding.BuildInfoFragmentBinding;
import com.karthek.android.s.ainfo.state.BuildInfoViewModel;

public class BuildInfoFragment extends Fragment {

	private BuildInfoFragmentBinding binding;
	private BuildInfoViewModel mViewModel;

	public static BuildInfoFragment newInstance() {
		return new BuildInfoFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		binding = BuildInfoFragmentBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = new ViewModelProvider(this).get(BuildInfoViewModel.class);
		// TODO: Use the ViewModel
		binding.brand.setText(Build.BRAND);
		binding.device.setText(Build.DEVICE);
		binding.manufacturer.setText(Build.MANUFACTURER);
		binding.model.setText(Build.MODEL);
		binding.board.setText(Build.BOARD);
		binding.hardware.setText(Build.HARDWARE);
		binding.product.setText(Build.PRODUCT);

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
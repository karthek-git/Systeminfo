package com.karthek.android.s.ainfo;

import static com.karthek.android.s.ainfo.SApplication.props;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.karthek.android.s.ainfo.databinding.FragmentSocBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocFragment extends Fragment {


	private FragmentSocBinding fragmentSocBinding;

	public SocFragment() {
	}

	public static SocFragment newInstance() {
		return new SocFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		fragmentSocBinding = FragmentSocBinding.inflate(inflater, container, false);
		return fragmentSocBinding.getRoot();
	}

	@Override
	public void onStart() {
		super.onStart();
		fragmentSocBinding.socManufacturer.setText(props.getSocManufacturer());
		fragmentSocBinding.socModel.setText(props.getSocModel());
		fragmentSocBinding.coresNo.setText(String.valueOf(Runtime.getRuntime().availableProcessors()));
		props.getGPU(getActivity(), fragmentSocBinding.dsp);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			fragmentSocBinding.supportedAbis.setText(appString(Build.SUPPORTED_ABIS));
			fragmentSocBinding.supported32bitAbis.setText(appString(Build.SUPPORTED_32_BIT_ABIS));
			fragmentSocBinding.supported64bitAbis.setText(appString(Build.SUPPORTED_64_BIT_ABIS));
		}
		fragmentSocBinding.radioVersion.setText(Build.getRadioVersion());
	}

	private String appString(String[] strings) {
		String string = strings[0];
		for (int s = 1; s < strings.length; s++) {
			string = string.concat("\n\n" + strings[s]);
		}
		return string;
	}


}
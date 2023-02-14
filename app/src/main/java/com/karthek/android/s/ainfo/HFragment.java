package com.karthek.android.s.ainfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HFragment extends ListFragment {

	public static HFragment newInstance() {
		return new HFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_h, container, false);
	}

	@Override
	public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
				.setReorderingAllowed(true);
		switch (position) {
			case 0:
				fragmentTransaction.replace(R.id.fragment, BuildInfoFragment.newInstance())
						.addToBackStack("sysinfo")
						.commit();
				break;
			case 1:
				fragmentTransaction.replace(R.id.fragment, SocFragment.newInstance())
						.addToBackStack("socinfo")
						.commit();
		}
	}
}
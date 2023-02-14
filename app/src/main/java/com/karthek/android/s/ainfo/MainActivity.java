package com.karthek.android.s.ainfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.karthek.android.s.ainfo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);
		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.fragment, HFragment.newInstance())
				.commit();
	}
}
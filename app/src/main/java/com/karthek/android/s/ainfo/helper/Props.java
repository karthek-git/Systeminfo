package com.karthek.android.s.ainfo.helper;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Props {

	private final String unknown = "Unknown";
	private String SocManufacturer;
	private String SocModel;
	private String GPU;

	public Props() {
	}

	public String getSocManufacturer() {
		if (SocManufacturer != null) {
			return SocManufacturer;
		} else {
			switch (Build.HARDWARE) {
				case "qcom":
					return "Qualcomm";
			}
		}
		return unknown;
	}

	public String getProp(String prop) {
		String p = null;

		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);

			p = (String) get.invoke(c, prop);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (p == null) {
			Process process = null;
			BufferedReader bufferedReader = null;

			try {
				process = new ProcessBuilder().command("/system/bin/getprop", prop).start();
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				p = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				Log.v("err", "unable to get" + p);
			} finally {
				try {
					if (bufferedReader != null)
						bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (process != null) {
					process.destroy();
				}
			}
		}

		return p;
	}

	public String getSocModel() {
		if (SocModel != null) {
			return SocModel;
		} else {

		}
		return unknown;
	}

	public void getGPU(Context context, TextView view) {
		ViewGroup viewGroup= (ViewGroup) view.getParent();
		if (GPU != null) {
		} else {
			GPU = unknown;
			GLSurfaceView glSurfaceView = new GLSurfaceView(context);
			glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
				@Override
				public void onSurfaceCreated(GL10 gl, EGLConfig config) {
					GPU = gl.glGetString(GL10.GL_RENDERER);
					view.setText(GPU);
					Log.v("gl","called");
				}

				@Override
				public void onSurfaceChanged(GL10 gl, int width, int height) {

				}

				@Override
				public void onDrawFrame(GL10 gl) {

				}
			});
			viewGroup.addView(glSurfaceView);
		}
	}
}

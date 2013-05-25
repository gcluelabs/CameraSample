package com.example.camerasample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class CameraSample extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		CameraView mCamera = new CameraView(this);
		setContentView(mCamera);
	}
}

/**
 * CameraView
 */
class CameraView extends SurfaceView implements SurfaceHolder.Callback {
	private Camera mCamera;

	public CameraView(Context context) {
		super(context);
		getHolder().addCallback(this);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i("CAMERA", "surfaceChaged");

		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(width, height);
		mCamera.setParameters(parameters);

		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceCreated");

		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceDestroyed");
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
}
package com.gclue.CameraSample;

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

		// Notification Barを消す
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Title Barを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		CameraView mCamera = new CameraView(this);
		setContentView(mCamera);
	}
}

/**
 * CameraView
 */
class CameraView extends SurfaceView implements SurfaceHolder.Callback {
	/**
	 * Cameraのインスタンスを格納する変数
	 */
	private Camera mCamera;

	public CameraView(Context context) {
		super(context);
		getHolder().addCallback(this);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * Surfaceに変化があった場合に呼ばれる
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i("CAMERA", "surfaceChaged");

		// 画面設定
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(width, height);
		mCamera.setParameters(parameters);

		// プレビュー表示を開始
		mCamera.startPreview();
	}

	/**
	 * Surfaceが生成された際に呼ばれる
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceCreated");

		// カメラをOpen
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
		}
	}

	/**
	 * Surfaceが破棄された場合に呼ばれる
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceDestroyed");

		// カメラをClose
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
}
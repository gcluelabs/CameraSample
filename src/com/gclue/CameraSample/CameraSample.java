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

		// Notification Bar������
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Title Bar������
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
	 * Camera�̃C���X�^���X���i�[����ϐ�
	 */
	private Camera mCamera;

	public CameraView(Context context) {
		super(context);
		getHolder().addCallback(this);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * Surface�ɕω����������ꍇ�ɌĂ΂��
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i("CAMERA", "surfaceChaged");

		// ��ʐݒ�
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(width, height);
		mCamera.setParameters(parameters);

		// �v���r���[�\�����J�n
		mCamera.startPreview();
	}

	/**
	 * Surface���������ꂽ�ۂɌĂ΂��
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceCreated");

		// �J������Open
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
		}
	}

	/**
	 * Surface���j�����ꂽ�ꍇ�ɌĂ΂��
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("CAMERA", "surfaceDestroyed");

		// �J������Close
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
}
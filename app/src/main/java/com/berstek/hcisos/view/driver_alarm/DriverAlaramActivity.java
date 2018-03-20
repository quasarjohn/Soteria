package com.berstek.hcisos.view.driver_alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;

import com.berstek.hcisos.R;
import com.berstek.hcisos.presentor.ClassificationPresentor;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class DriverAlaramActivity extends AppCompatActivity
    implements CameraBridgeViewBase.CvCameraViewListener2,
    ClassificationPresentor.ClassificationPresentorCallback {

  private JavaCameraView cam;
  private Mat mat;

  private ClassificationPresentor classificationPresentor;

  private TextView classificationTxt, speedTxt;

  private boolean classifying = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_driver_alaram);


    getSupportActionBar().hide();


    classificationTxt = findViewById(R.id.classificationTxt);
    speedTxt = findViewById(R.id.speedTxt);

    classificationPresentor = new ClassificationPresentor(this);
    classificationPresentor.setClassificationPresentorCallback(this);

    cam = findViewById(R.id.my_java_camera);

    cam.setCameraIndex(0);
    cam.setVisibility(SurfaceView.VISIBLE);
    cam.setCvCameraViewListener(this);
    cam.setMaxFrameSize(640, 480);

  }

  @Override
  public void onCameraViewStarted(int width, int height) {

    mat = new Mat(height, width, CvType.CV_8U);

  }

  @Override
  public void onCameraViewStopped() {

  }

  @Override
  public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
    mat = inputFrame.rgba();
    Core.flip(mat, mat, 1);

    if (!classifying)
      classificationThread.start();
    return mat;
  }


  private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) {
      switch (status) {
        case LoaderCallbackInterface.SUCCESS: {
          cam.enableView();
        }
        break;
        default: {
          super.onManagerConnected(status);
        }
        break;
      }
    }
  };

  @Override
  public void onResume() {
    super.onResume();
    if (!OpenCVLoader.initDebug()) {
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
    } else {
      mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }
  }


  @Override
  public void onImageClassified(String label, double probability) {
    runOnUiThread(() -> {
      classificationTxt.setText(label);
      classifying = false;
    });
  }

  @Override
  public void onClassificationStarted() {
    classifying = true;
  }

  private Thread classificationThread = new Thread(new Runnable() {
    @Override
    public void run() {
      classificationPresentor.classify(mat);
    }
  });

}

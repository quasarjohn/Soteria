package com.berstek.hcisos.presentor;

import android.app.Activity;
import android.graphics.Bitmap;

import com.berstek.hcisos.firebase_da.DA;
import com.berstek.hcisos.tensorflow.ImageClassifier;
import com.berstek.hcisos.tensorflow.ImageClassifierQuantizedMobileNet;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.IOException;

public class ClassificationPresentor {

  private ClassificationPresentorCallback classificationPresentorCallback;

  private static final int INPUT_SIZE = 224;
  private static final int IMAGE_MEAN = 128;
  private static final float IMAGE_STD = 128;


  private Bitmap bitmap;

  private ImageClassifier classifier;

  private Activity activity;

  public ClassificationPresentor(Activity activity) {
    this.activity = activity;
  }

  public void classify(Mat mat) {
    try {
      if (classifier == null)
        classifier = new ImageClassifierQuantizedMobileNet(activity);

      classificationPresentorCallback.onClassificationStarted();

      Mat mat1 = mat.clone();
      bitmap = Bitmap.createBitmap(mat1.cols(), mat1.rows(), Bitmap.Config.ARGB_8888);
      Utils.matToBitmap(mat1, bitmap);
      bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

      String classification = classifier.classifyFrame(bitmap);
      classificationPresentorCallback.onImageClassified(classification, 0);


    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public interface ClassificationPresentorCallback {
    void onImageClassified(String label, double probability);

    void onClassificationStarted();
  }

  public void setClassificationPresentorCallback(ClassificationPresentorCallback classificationPresentorCallback) {
    this.classificationPresentorCallback = classificationPresentorCallback;
  }
}

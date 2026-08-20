package com.example.englishrebuild;

import android.graphics.Bitmap;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public final class OcrEngine {
    private OcrEngine(){}

    public interface Callback {
        void onSuccess(String text);
        void onError(Exception error);
    }

    public static void recognize(Bitmap bitmap, Callback callback) {
        if (bitmap == null) {
            callback.onError(new IllegalArgumentException("No image selected"));
            return;
        }
        InputImage image=InputImage.fromBitmap(bitmap,0);
        TextRecognizer recognizer=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        recognizer.process(image)
            .addOnSuccessListener(result -> {
                callback.onSuccess(result.getText());
                recognizer.close();
            })
            .addOnFailureListener(error -> {
                callback.onError(error);
                recognizer.close();
            });
    }
}

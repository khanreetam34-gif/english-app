package com.example.englishrebuild;

import android.net.Uri;

public final class OcrService {
    private OcrService(){}

    public static Result prepareImage(Uri uri) {
        if (uri == null) return new Result(false, "", "No image selected");
        return new Result(true, uri.toString(), "Image selected; OCR engine not configured");
    }

    public static final class Result {
        public final boolean ready;
        public final String source;
        public final String message;
        Result(boolean ready,String source,String message) {
            this.ready=ready; this.source=source; this.message=message;
        }
    }
}

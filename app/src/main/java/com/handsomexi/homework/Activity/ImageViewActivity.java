package com.handsomexi.homework.Activity;

import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.handsomexi.homework.R;

import java.io.File;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ImageViewActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        String path = getIntent().getStringExtra("path");
        if(path == null || path.trim().isEmpty())
            return;
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) findViewById(R.id.image_imageview);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
        imageView.setMinScale(0.1f);
        imageView.setMaxScale(5.0f);
        imageView.setImage(ImageSource.uri(Uri.fromFile(new File(path))),new ImageViewState(1.0f, new PointF(0, 0), 0));


    }

}

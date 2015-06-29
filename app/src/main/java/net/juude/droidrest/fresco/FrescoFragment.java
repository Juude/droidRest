package net.juude.droidrest.fresco;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.juude.droidrest.R;

/**
 * Created by juude on 15-6-29.
 */
public class FrescoFragment extends Fragment{
    private ImageView mImageView1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fresco, null);
        mImageView1 = (ImageView) v.findViewById(R.id.image1);
        fetchImage();
        return v;
    }


    private void fetchImage() {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShkbESeC8Ni6J1H-E8dpQxMh4TsyU39fHB_-xFc3vaKjYb8kxQRA"))
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, new Object());
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(final @Nullable Bitmap bitmap) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView1.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, AsyncTask.THREAD_POOL_EXECUTOR);
    }


}


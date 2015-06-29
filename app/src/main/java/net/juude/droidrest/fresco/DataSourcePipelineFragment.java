package net.juude.droidrest.fresco;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.juude.droidrest.R;
import net.juude.droidrest.RestApplication;

/**
 * Created by juude on 15-6-29.
 */
public class DataSourcePipelineFragment extends Fragment{
    private static final String TAG = "DataSourcePipelineFragment";
    private ImageView mPipeImageView;
    private CloseableImage mCloseableImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data_source_pipline, null);
        mPipeImageView = (ImageView) v.findViewById(R.id.pipeline_data_source);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchImage();
    }

    private void fetchImage() {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShkbESeC8Ni6J1H-E8dpQxMh4TsyU39fHB_-xFc3vaKjYb8kxQRA"))
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        DataSource<CloseableReference<CloseableImage>> cacheImage = imagePipeline.fetchImageFromBitmapCache(imageRequest, new Object());
//        if(cacheImage!= null && cacheImage.getResult()!= null && (mCloseableImage = cacheImage.getResult().get()) != null) {
//            showCloseableImage();
//            return;
//        }

        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, new Object());
        dataSource.subscribe(new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                final CloseableImage closeableImage = dataSource.getResult().get();
                mCloseableImage = closeableImage;
                RestApplication.getRefWatcher().watch(mCloseableImage);
                showCloseableImage();
                dataSource.getResult().close();
                Log.d(TAG, "success");
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                Log.d(TAG, "onFailureImpl");

            }
        }, AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void showCloseableImage() {
        if(mCloseableImage instanceof CloseableStaticBitmap) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPipeImageView.setImageBitmap(((CloseableStaticBitmap)mCloseableImage).getUnderlyingBitmap());
                }
            });
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        //mPipeImageView.setImageDrawable(null);
//        if(mCloseableImage != null && !mCloseableImage.isClosed()) {
//            mCloseableImage.close();
//        }
    }

}


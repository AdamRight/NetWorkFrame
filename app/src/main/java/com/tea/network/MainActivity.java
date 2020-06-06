package com.tea.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tea.network.file.FileStorageManager;
import com.tea.network.http.DownloadCallback;
import com.tea.network.http.HttpManager;
import com.tea.network.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView downImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downImageview = findViewById(R.id.down_iv);

        File file = FileStorageManager.getInstance().getFileByName("com");
        Logger.debug("eee","-----:"+file);

        HttpManager.getInstance().asyncRequest("https://adamright.github.io/img/4.png", new DownloadCallback() {
            @Override
            public void success(File file) {
                Logger.debug("eee","-----success:"+file);
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        downImageview.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.debug("eee","-----fail:"+errorMessage);

            }

            @Override
            public void progress(int progress) {
                Logger.debug("eee","-----progress:"+progress);
            }
        });
    }
}

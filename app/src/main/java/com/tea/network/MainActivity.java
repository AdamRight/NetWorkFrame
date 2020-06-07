package com.tea.network;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tea.network.download.DownloadManager;
import com.tea.network.file.FileStorageManager;
import com.tea.network.http.DownloadCallback;
import com.tea.network.net.service.MoocApiProvider;
import com.tea.network.net.service.MoocRequest;
import com.tea.network.net.service.MoocResponse;
import com.tea.network.utils.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView downImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downImageview = findViewById(R.id.down_iv);

        File file = FileStorageManager.getInstance().getFileByName("com");
        Logger.debug("eee","-----:"+file);

       /* HttpManager.getInstance().asyncRequest("https://adamright.github.io/img/4.png", new DownloadCallback() {
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
        });*/

        DownloadManager.getInstance().download("https://adamright.github.io/img/4.png", new DownloadCallback() {
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

            }

            @Override
            public void progress(int progress) {

            }
        });



        Map<String, String> map = new HashMap<>();
        map.put("username", "nate");
        map.put("userage", "12");
        MoocApiProvider.helloWorld("http://192.168.1.12:8080/web/HelloServlet", map, new MoocResponse<Person>() {

            @Override
            public void success(MoocRequest request, Person data) {
                Logger.debug("nate", data.toString());
            }

            @Override
            public void fail(int errorCode, String errorMsg) {
            }
        });

    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        MainActivity.this.startActivity(intent);
    }

    public class Person {


        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Person(){

        }

        public Person(String name,int age){
            this.name=name;
            this.age=age;
        }
    }
}

package com.team.crop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

public class OutpuActivity extends AppCompatActivity {

    private ImageView mResultImg;
    private TextView img_result;
    //String postUrl = "http://192.168.43.58:5000/";
    String postUrl = "http://3.22.124.24:80/";
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outpu);

        Intent intent = getIntent();
        String image_path= intent.getStringExtra("imagePath");
        Uri fileUri = Uri.parse(image_path);

        mResultImg = (ImageView) findViewById(R.id.output_img);

        mResultImg.setImageURI(fileUri);
        //img_result = (TextView) this.findViewById ( R.id.img_result );
        //img_result.setText ( image_path );
        try {
            InputStream inputStream = getContentResolver ().openInputStream ( fileUri );
            bitmap = BitmapFactory.decodeStream ( inputStream );
            imageToString(bitmap);
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace ( );
        }

    }

    private void imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream (  );
        bitmap.compress ( Bitmap.CompressFormat.JPEG, 100, outputStream );
        byte[] byteArray = outputStream.toByteArray ();

        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "androidFlask.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        postRequest(postUrl, postBodyImage);
        //String encodedImage = Base64.encodeToString ( imageBytes, Base64.DEFAULT);
        //JSONObject postDataParams = new JSONObject();
       // postDataParams.put(encodedImage);
        //return encodedImage;
    }

    void  simplePost(String postUrl){
        OkHttpClient client = new OkHttpClient.Builder ().connectionSpecs ( Arrays.asList ( ConnectionSpec.CLEARTEXT ) ).build ();

        Request request = new Request.Builder()
                .url(postUrl)
                .build();
        client.newCall ( request ).enqueue ( new Callback ( ) {
            @Override
            public
            void onFailure ( @NotNull Call call , @NotNull IOException e ){

            }

            @Override
            public
            void onResponse ( @NotNull Call call , @NotNull Response response ) throws IOException{

            }
        } );
    }

    void postRequest(String postUrl, RequestBody postBody) {


        OkHttpClient client = new OkHttpClient.Builder ().connectionSpecs ( Arrays.asList ( ConnectionSpec.CLEARTEXT ) ).build ();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback () {
            @Override
            public void onFailure (Call call, IOException e ) { e.printStackTrace();
                /*// Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView img_result = findViewById(R.id.img_result);
                        img_result.setText("Failed to Connect to Server");
                    }
                });*/
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                OutpuActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView img_result = findViewById(R.id.img_result);
                        try {
                            img_result.setText(response.body().string());
                            simplePost ("http://3.22.124.24:80/Exit");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}

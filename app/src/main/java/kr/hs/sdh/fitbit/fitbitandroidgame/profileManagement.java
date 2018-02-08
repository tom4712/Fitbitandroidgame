package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class profileManagement extends AppCompatActivity {

    Button Picture;
    ImageView Result;

    private Uri photoUri;
    private String currentPhotoPath;
    String mImageCaptureName;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        Picture = findViewById(R.id.Picture);
        Result = findViewById(R.id.Result);

        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder photoOrGallery = new AlertDialog.Builder(context);
                photoOrGallery.setTitle("프로필");
                photoOrGallery.setCancelable(false)
                        .setPositiveButton("사진찍기",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        selectPhoto();
                                    }
                                })
                        .setNegativeButton("갤러리",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    selectGallery();
                                    }
                                });
                AlertDialog alertDialog = photoOrGallery.create();
                alertDialog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            switch (requestCode){
                case 1987:
                    sendPicture(data.getData());
                    break;
                case 1897:
                    getPictureForPhoto();
                    break;
            }
        }
    }

    private void selectPhoto(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {

                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(i, 1897);
                }
            }
        }
    }

    private File createImageFile() throws IOException{
        File dir = new File(Environment.getExternalStorageDirectory() + "/path/");

        if(dir.exists())
            dir.mkdir();

        mImageCaptureName = "profileImage";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()
                + "/path/" + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;
    }

    private void selectGallery(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i,1987);
    }

    private void sendPicture(Uri imgUri){
        String imagePath = getRealPathFromURL(imgUri);
        ExifInterface exif = null;

        try{
            exif = new ExifInterface(imagePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;
        if(exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        }
        else
            exifDegree = 0;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Result.setImageBitmap(rotate(bitmap, exifDegree));
    }

    private void getPictureForPhoto(){
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(currentPhotoPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;
        if(exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        }
        else
            exifDegree = 0;
        Result.setImageBitmap(rotate(bitmap, exifDegree));
    }

    private int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;

        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;

        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree){

        Matrix matrix = new Matrix();

        matrix.postRotate(degree);

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURL(Uri contentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if(cursor.moveToFirst())
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return  cursor.getString(column_index);
    }
}

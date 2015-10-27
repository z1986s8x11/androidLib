package com.zsx.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.zsx.util.Lib_Util_File;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhusx on 2015/10/26.
 */
public class Lib_SelectPhoto {
    public static final int ActivityCameraRequestCode = 0x8A1;
    public static final int ActivityPhotoRequestCode = 0x8B2;
    private final int ActivityClopPhotoRequestCode = 0x8C3;
    private Activity activity;
    private Fragment fragment;
    private Context context;
    private boolean isClop = false;
    private int photoWidth, photoHeight;
    private File dirFile;
    private File saveFile;

    public Lib_SelectPhoto(Activity activity, String dir, int width, int height) {
        this(activity, dir);
        this.photoWidth = width;
        this.photoHeight = height;
        this.isClop = true;
    }

    public Lib_SelectPhoto(Fragment fragment, String dir, int width, int height) {
        this(fragment, dir);
        this.photoWidth = width;
        this.photoHeight = height;
        this.isClop = true;
    }

    public Lib_SelectPhoto(Activity activity, String dir) {
        this.activity = activity;
        this.context = activity;
        this.dirFile = new File(dir);
    }

    public Lib_SelectPhoto(Fragment fragment, String dir) {
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.dirFile = new File(dir);
    }

    private void startActivityForResult(Intent intent, int requestId) {
        if (fragment == null) {
            activity.startActivityForResult(intent, requestId);
        } else {
            fragment.startActivityForResult(intent, requestId);
        }
    }

    /**
     * 从相册选取
     */
    public void _startSelectPhoto() {
        saveFile = new File(dirFile, System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        startActivityForResult(intent, ActivityPhotoRequestCode);
    }

    /**
     * 照像获取图片
     */
    public void _startTakePhoto() {
        saveFile = new File(dirFile, System.currentTimeMillis() + ".jpg");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (saveFile.exists()) {
                saveFile.delete();
            }
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
            startActivityForResult(intent, ActivityCameraRequestCode);
        } else {
            Toast.makeText(context, "请插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    public interface onDataListener {
        void onPhoto(File saveFile);
    }

    public void _onActivityResult(int requestCode, int resultCode, Intent data,
                                  onDataListener listener) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ActivityCameraRequestCode:
                    if (resultCode == Activity.RESULT_OK) {
                        if (isClop) {
                            gotoClop(Uri.fromFile(saveFile), photoWidth, photoHeight);
                        } else {
                            listener.onPhoto(saveFile);
                        }
                    }
                    break;
                case ActivityPhotoRequestCode:
                    if (resultCode == Activity.RESULT_OK) {
                        Uri uri = data.getData();
                        if (isClop) {
                            gotoClop(Uri.fromFile(new File(getPath(context, uri))), photoWidth, photoHeight);
                        } else {
                            File file = new File(getPath(context, uri));
                            listener.onPhoto(file);
                        }
                    }
                    break;
                case ActivityClopPhotoRequestCode:
                    if (resultCode == Activity.RESULT_OK) {
                        Bitmap bm = data.getParcelableExtra("data");
                        if (bm != null) {
                            if (saveFile.exists()) {
                                saveFile.delete();
                            }
                            boolean isSuccess = Lib_Util_File.saveToFile(bm, saveFile.getPath());
                            if (!bm.isRecycled()) {
                                bm.recycle();
                            }
                            if (isSuccess) {
                                listener.onPhoto(saveFile);
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void gotoClop(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ActivityClopPhotoRequestCode);
    }

    @SuppressLint("NewApi")
    private String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // Whether the Uri authority is ExternalStorageProvider.
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // Whether the Uri authority is DownloadsProvider.
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // Whether the Uri authority is MediaProvider.
            else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Whether the Uri authority is Google Photos.
            if ("com.google.android.apps.photos.content".equals(uri.getAuthority())) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}

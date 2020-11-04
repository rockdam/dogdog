package com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.interfaces.SelectedPictureView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SelectedPictureResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SendImagData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

public class SelectedPicture extends BaseActivity implements SelectedPictureView {

    TextView seletedGallery,changeDefualtImage,takeaPicture;

    private Uri filePath;
    private static final int GALLERY_REQUEST_CODE = 0;
    SelectedPictureService selectedPictureService;
    SendImagData sendImagData;
    ImageView closeAddchangedogs;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    PermissionListener permissionlistener;
    int counter;
    Uri uri;

    public void takePhoto() throws IOException {
        counter++; //this is an int
        String imageFileName = "JPEG_" + counter; //make a better file name
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,
                ".jpg",
                storageDir
        );

        uri = Uri.fromFile(image);
        getBaseContext().sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri) );
        // 이래야 갤러리에 보임 .
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_picture);
        seletedGallery=findViewById(R.id.seleted_gallery_selectedpicture);
        takeaPicture=findViewById(R.id.take_a_picture_selectedpicture);
        changeDefualtImage=findViewById(R.id.change_defualt_image_selectedpicture);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

//        출처: https://docko.tistory.com/entry/androidosFileUriExposedException-filestorageemulated0-exposed-beyond-app-through-IntentgetData [장똘]
// uri 노출 해결 이게 쉽다. 사진
        sendImagData=new SendImagData();
        setWindow();
        closeAddchangedogs=findViewById(R.id.close_addchangedogs);

        closeAddchangedogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        changeDefualtImage.setOnClickListener(view -> {

            String defaultUrl="https://firebasestorage.googleapis.com/v0/b/dogdog-1d2f8.appspot.com/o/default_profile_image.png?alt=media&token=9052b50b-dd25-46b1-ba22-f1581a1231f5";
            sendImagData.setImgUrl(defaultUrl);
            selectedPictureService=new SelectedPictureService(SelectedPicture.this,sendImagData);
            selectedPictureService.refreshHomeView();

            System.out.println("g");


        });

        seletedGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setActivityTitle("사진 편집")
//
//
//
//                        .setCropMenuCropButtonTitle("적용")
//                        .setCropShape(CropImageView.CropShape.OVAL)
//                        .start(SelectedPicture.this);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        //TedPermission 라이브러리 -> 카메라 권한 획득
        takeaPicture.setOnClickListener(view -> {

            permissionlistener = new PermissionListener() {

                @Override

                public void onPermissionGranted() {
                    try {
                        takePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                Toast.makeText(SelectedPicture.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {

//                Toast.makeText(SelectedPicture.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }



            };
            TedPermission.with(this)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
                    .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
                    .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                    .check();
        });






    }


    private void setWindow() {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setGravity(Gravity.BOTTOM);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//          바깥쪽 클릭 방지
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
//
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.RESULT_OK)
        {

            Log.e("여기나와?","몰러유");
            cropImage(uri);

        }else if(requestCode == GALLERY_REQUEST_CODE && resultCode == this.RESULT_OK)//사진 촬영일 때
        {

            cropImage(data.getData());

        }
        else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) { //사진 촬영 아닐 때
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();

                Log.e("여기나와?","ㅇ"+result.getUri());
                getBaseContext().sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result.getUri()) );
                filePath=result.getUri();

                uploadFile();
//                finish();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private void cropImage(Uri uri) {
//        int height = (int) UIUtils.dip2px(160);
//        int width = (int) UIUtils.dip2px(160);
        Intent intent = CropImage.activity(uri)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("사진 편집")
//                .setMinCropWindowSize(width, height)
//                .setMaxCropResultSize(width, height)
                .getIntent(SelectedPicture.this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("업로드중...");
//            progressDialog.show();

            showDogDogLoadingDialog();
            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://dogdog-1d2f8.appspot.com").child("images/" + filename);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {




                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {







                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    String downloadPhotoUrl=uri.toString();
                                    Log.e("Url",downloadPhotoUrl);
//                                    progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기

                                    sendImagData.setImgUrl(downloadPhotoUrl);

                                    selectedPictureService=new SelectedPictureService(SelectedPicture.this,sendImagData);
                                    selectedPictureService.refreshHomeView();
//                                    Intent resultIntent = new Intent();
//                                    resultIntent.putExtra("Url",downloadPhotoUrl);
//                                    setResult(RESULT_OK,resultIntent);
//                                    이 코드가 사실상 혼자만들어도 db 서버랑하면 아무의미가 없어 .; 진짜 잠깐 받을 때나 쓰는코드
                                    hideDogDogLoadingDialog();


                                }
                            });







                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
                            hideDogDogLoadingDialog();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();





    }

    @Override
    public void refresh(SelectedPictureResponse result) {
        if(result.getIsSuccess())
        {
            finish();

        }else{

            Toast.makeText(this,"서버와의 연결이 실패하였습니다",Toast.LENGTH_SHORT).show();
        }


    }
}


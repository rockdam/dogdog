package com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.interfaces.SelectedPictureView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SelectedPictureResponse;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.models.SendImagData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

public class SelectedPicture extends BaseActivity implements SelectedPictureView {

    TextView seletedGallery,changeDefualtImage;

    private Uri filePath;
    private static final int REQUEST_CODE = 0;
    SelectedPictureService selectedPictureService;
    SendImagData sendImagData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_picture);
        seletedGallery=findViewById(R.id.seleted_gallery_selectedpicture);

        changeDefualtImage=findViewById(R.id.change_defualt_image_selectedpicture);

        sendImagData=new SendImagData();
        setWindow();


        changeDefualtImage.setOnClickListener(view -> {

            String defaultUrl="https://firebasestorage.googleapis.com/v0/b/dogdog-1d2f8.appspot.com/o/default_profile_image.png?alt=media&token=9052b50b-dd25-46b1-ba22-f1581a1231f5";
            sendImagData.setImgUrl(defaultUrl);
            selectedPictureService=new SelectedPictureService(SelectedPicture.this,sendImagData);
            selectedPictureService.refreshHomeView();


        });

        seletedGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("사진 편집")

                        .setCropMenuCropButtonTitle("적용")
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(SelectedPicture.this);
            }
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
        if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();

                filePath=result.getUri();
                uploadFile();
//                finish();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

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
                                    progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기

                                    sendImagData.setImgUrl(downloadPhotoUrl);

                                    selectedPictureService=new SelectedPictureService(SelectedPicture.this,sendImagData);
                                    selectedPictureService.refreshHomeView();
//                                    Intent resultIntent = new Intent();
//                                    resultIntent.putExtra("Url",downloadPhotoUrl);
//                                    setResult(RESULT_OK,resultIntent);
//                                    이 코드가 사실상 혼자만들어도 db 서버랑하면 아무의미가 없어 .; 진짜 잠깐 받을 때나 쓰는코드


                                }
                            });







                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
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
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
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


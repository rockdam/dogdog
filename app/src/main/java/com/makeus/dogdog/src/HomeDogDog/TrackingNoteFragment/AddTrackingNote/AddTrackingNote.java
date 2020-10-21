package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
//
//import com.esafirm.imagepicker.features.ImagePicker;
//import com.esafirm.imagepicker.model.Image;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lumyjuwon.richwysiwygeditor.RichWysiwyg;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.interfaces.FinishCallback;
import com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote.models.DayHistory;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.SelectedPicture;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.SelectedPicture.SelectedPictureService;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;
import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;



// 지워진 이미지 확인 하는 법 체크 해놨다가 보낼 때 갯수랑 비교하면 될 듯?
public class AddTrackingNote extends BaseActivity implements FinishCallback {

    private RichWysiwyg wysiwyg;
    private Uri filePath;
    String downloadPhotoUrl;
    DayHistory dayHistory;
    AddTrackingNoteService addTrackingNoteService;
    int limitPictureUpload=0; // 근데 이거 할려면 지워지면 알아차려야되는데 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking_note);
        String why= getIntent().getStringExtra("date");

        wysiwyg = findViewById(R.id.richwysiwygeditor);

        wysiwyg.getContent()
                .setEditorFontSize(24)

                .setEditorPadding(4, 0, 4, 0);

        wysiwyg.getHeadlineEditText().setHint("제목을 입력해주세요");

        wysiwyg.getCancelButton().setText("취소");

        wysiwyg.getConfirmButton().setText("등록");
//        wysiwyg.getConfirmButton().setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                // Handle this
//                Log.i("Rich Wysiwyg Headline", wysiwyg.getHeadlineEditText().getText().toString());
//                if(wysiwyg.getContent().getHtml() != null)
//                    Log.i("Rich Wysiwyg", wysiwyg.getContent().getHtml());
//            }
//        });
        wysiwyg.getInsertImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("사진 편집")

                        .setCropMenuCropButtonTitle("적용")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .start(AddTrackingNote.this);

            }
        });
        wysiwyg.getConfirmButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Log.e("html",wysiwyg.getContent().getHtml());
                dayHistory=new DayHistory();
                dayHistory.setDate(why);
                Log.e("내 아이디는 ",""+sSharedPreferences.getInt("dogIdx",1));
                dayHistory.setDogIdx(sSharedPreferences.getInt("dogIdx",1));
                dayHistory.setContent( wysiwyg.getContent().getHtml());

                SharedPreferences.Editor editor=sSharedPreferences.edit();
                editor.putString("html2",wysiwyg.getContent().getHtml());

                editor.apply();

                addTrackingNoteService=new AddTrackingNoteService(AddTrackingNote.this);
                addTrackingNoteService.createWalkingNoteHistory(dayHistory);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> images = ImagePicker.getImages(data);
            insertImages(images);
        }

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
        super.onActivityResult(requestCode, resultCode, data);
    }


    //아 웹뷰니깐 아예 올리고 여기서 추가하면 되네 .
    //대신 로딩 바는 필요 .
    private void insertImages(List<Image> images) { // 얘는 여러개 되는건데 한번에 한장씩 하자 .. 위에 메소드로
        if (images == null) return;

        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0, l = images.size(); i < l; i++) {
            stringBuffer.append(images.get(i).getPath()).append("\n");
            // Handle this

//
//            filePath= Uri.parse("file://\"" + images.get(i).getPath());
//            uploadFile();
//            wysiwyg.getContent().insertImage("file://" + images.get(i).getPath(), "alt");

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


                                    downloadPhotoUrl=uri.toString();
                                    Log.e("Url",downloadPhotoUrl);
                                    progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                                    wysiwyg.getContent().insertImage(downloadPhotoUrl, "alt"); // 파이어베이스에서 받은 URL 보여주기
//                                    sendImagData.setImgUrl(downloadPhotoUrl);
//
//                                    selectedPictureService=new SelectedPictureService(SelectedPicture.this,sendImagData);
//                                    selectedPictureService.refreshHomeView();
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
    public void finishActivity() {
        finish();
    }
}
//button.setOnTouchListener(new OnTouchListener() {

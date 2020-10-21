package com.makeus.dogdog.src.HomeDogDog.TrackingNoteFragment.AddTrackingNote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
//
//import com.esafirm.imagepicker.features.ImagePicker;
//import com.esafirm.imagepicker.model.Image;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.lumyjuwon.richwysiwygeditor.RichWysiwyg;
import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;

import java.util.List;

public class AddTrackingNote extends AppCompatActivity {

    private RichWysiwyg wysiwyg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking_note);


        wysiwyg = findViewById(R.id.richwysiwygeditor);

        wysiwyg.getContent()
                .setEditorFontSize(24)

                .setEditorPadding(4, 0, 4, 0);

        wysiwyg.getHeadlineEditText().setHint("제목을 입력해주세요");

        wysiwyg.getCancelButton().setText("취소");

        wysiwyg.getConfirmButton().setText("등록");
        wysiwyg.getConfirmButton().setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                // Handle this
                Log.i("Rich Wysiwyg Headline", wysiwyg.getHeadlineEditText().getText().toString());
                if(wysiwyg.getContent().getHtml() != null)
                    Log.i("Rich Wysiwyg", wysiwyg.getContent().getHtml());
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> images = ImagePicker.getImages(data);
            insertImages(images);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void insertImages(List<Image> images) {
        if (images == null) return;

        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0, l = images.size(); i < l; i++) {
            stringBuffer.append(images.get(i).getPath()).append("\n");
            // Handle this
            wysiwyg.getContent().insertImage("file://" + images.get(i).getPath(), "alt");
        }
    }
}
//button.setOnTouchListener(new OnTouchListener() {

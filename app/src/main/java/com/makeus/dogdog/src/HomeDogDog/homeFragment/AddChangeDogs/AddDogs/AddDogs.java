package com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.BaseActivity;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.interfaces.UpdateDogView;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AddDogs.models.AddDogInfo;
import com.makeus.dogdog.src.HomeDogDog.homeFragment.AddChangeDogs.AdddogsService;
import com.makeus.dogdog.src.joinmember.step6.SearchBreedsDialog;
import com.makeus.dogdog.src.joinmember.step6.Step6Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDogs extends BaseActivity implements UpdateDogView {

    EditText nameEdt, birthdayEdt, weightEdt;
    TextView breedsDogs, mComplete;
    RadioButton mMale, mFemale, mYes, mNo;
    RadioGroup mRgGender, mIsDefaultCheck;
    ImageView nameClearEdt, birthdayClearEdt, weightClearEdt;
    String mDogName, mBirthday, mGender, mBreeds;
    float mWeight;
    String mSettingDefaultProfile;

    int mDogIdx;
    int mBreedsIdx;
    SearchBreedsDialog mSearchBreedsDialog;
    DogsService mAddDogsService;
    String mKg;
    AddDogInfo addDogsInfo;
    TextView mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dogs);
        nameEdt = findViewById(R.id.name_edt_addDogs);
        mComplete = findViewById(R.id.complete_button_addDogs);
        birthdayEdt = findViewById(R.id.birthday_edt_addDogs);
        weightEdt = findViewById(R.id.weight_addDogs);
        mMale = findViewById(R.id.male_addDogs);
        mFemale = findViewById(R.id.female_addDogs);
        mCancel=findViewById(R.id.cancel_adddogs);

        mRgGender = findViewById(R.id.rgGender_addDogs);
        mYes = findViewById(R.id.yes_addDogs);
        mIsDefaultCheck = findViewById(R.id.isDefault_check_addDogs);
        mNo = findViewById(R.id.no_addDogs);
        breedsDogs = findViewById(R.id.dog_breeds_addDogs);
        nameClearEdt = findViewById(R.id.name_edtclear_addDogs);
        birthdayClearEdt = findViewById(R.id.birthday_edtclear_addDogs);
        weightClearEdt = findViewById(R.id.weight_edtclear_addDogs);
        mAddDogsService = new DogsService(this);
        mDogIdx = getIntent().getIntExtra("dogIdx", 1);
        mSettingDefaultProfile = "Y";
        mGender = "female";
        nameEdtSet();
        birthDaySet();
        setWeightEdt();
        addDogsInfo = new AddDogInfo();

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mIsDefaultCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.yes_addDogs) {
                    mSettingDefaultProfile = "Y";


                }
                if (i == R.id.no_addDogs) {
                    mSettingDefaultProfile = "N";

                }
            }
        });
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("이름", nameEdt.getText().toString());
                Log.e("몸무게", weightEdt.getText().toString());
                if (nameEdt.getText() != null && nameEdt.getText().toString().length() < 2) {

                    Toast.makeText(getBaseContext(), "이름을 2글자 이상 입력해주세요 :)", Toast.LENGTH_SHORT).show();
                } else if (!validationDate(birthdayEdt.getText().toString())) {
                    Toast.makeText(getBaseContext(), "유효한 날짜 형식이 아닙니다.", Toast.LENGTH_SHORT).show();

                } else if (checkCorrectWeightValue() || mWeight <= 0) {


                    Toast.makeText(getBaseContext(), "정확한 몸무게를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {

                    addDogsInfo.setName(nameEdt.getText().toString());
                    addDogsInfo.setBirth(birthdayEdt.getText().toString());
                    addDogsInfo.setGender(mGender);
                    addDogsInfo.setWeight(mWeight);
                    addDogsInfo.setBreedIdx(mBreedsIdx);
                    addDogsInfo.setIsDisplayed(mSettingDefaultProfile);
                    mAddDogsService.addDogService(addDogsInfo);
                }
            }
        });
        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.male_addDogs) {
                    mGender = "male";


                }
                if (i == R.id.female_addDogs) {
                    mGender = "female";

                }
            }
        });
        breedsDogs.setOnClickListener(view -> {

            mSearchBreedsDialog = new SearchBreedsDialog(this);
            mSearchBreedsDialog.setDialogListener((breed, breedIdx) -> {
                        breedsDogs.setText(breed);
                        mBreedsIdx = breedIdx;
                    }
//


            );

            mSearchBreedsDialog.show();


        });
    }

    boolean checkCorrectWeightValue() {

        boolean returnValue;
        String check = weightEdt.getText().toString();
        int idx = check.indexOf("k");
        if (idx == -1) {
            returnValue = true;
        } else {

            String checkdf=check.substring(0, idx);


            if (isValidKg(check.substring(0, idx))) {
                mWeight = Float.parseFloat(check.substring(0, idx));
                returnValue=false;
            }else{
                returnValue=true;

            }
        }

        return returnValue;
    }

    public static boolean isValidKg(final String kg) {

        Pattern pattern;
        Matcher matcher;

        final String KG_PATTERN = "^(0|[1-9]\\d*)(\\.\\d+)?$";
        pattern = Pattern.compile(KG_PATTERN);
        matcher = pattern.matcher(kg);

        return matcher.matches();

    }

    // 유효한 날짜인지 체크
    public boolean validationDate(String checkDate) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            dateFormat.setLenient(false);
            dateFormat.parse(checkDate);
            return true;

        } catch (ParseException e) {
            return false;
        }

    }

    void birthDaySet() {


        birthdayClearEdt.setOnClickListener(view -> {

            birthdayEdt.setText("");

        });
        birthdayEdt.addTextChangedListener(new TextWatcher() {

            private int _beforeLenght = 0;
            private int _afterLenght = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _beforeLenght = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Length)");
                    return;
                }

                char inputChar = s.charAt(s.length() - 1);
                if (inputChar != '-' && (inputChar < '0' || inputChar > '9')) {
                    birthdayEdt.getText().delete(s.length() - 1, s.length());
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Number)");
                    return;
                }

                _afterLenght = s.length();

                // 삭제 중
                if (_beforeLenght > _afterLenght) {
                    // 삭제 중에 마지막에 -는 자동으로 지우기
                    if (s.toString().endsWith("-")) {
                        birthdayEdt.setText(s.toString().substring(0, s.length() - 1));
                    }
                }
                // 입력 중
                else if (_beforeLenght < _afterLenght) {
                    if (_afterLenght == 5 && s.toString().indexOf("-") < 0) {
                        birthdayEdt.setText(s.toString().subSequence(0, 4) + "-" + s.toString().substring(4, s.length()));
                    } else if (_afterLenght == 7) {
                        birthdayEdt.setText(s.toString().subSequence(0, 7) + "-" + s.toString().substring(7, s.length()));
                    }
                }
                birthdayEdt.setSelection(birthdayEdt.length());
//
//                if(s.length() == 19) {
//                    btnInput.setBackground(
//                            ContextCompat.getDrawable(getContext(), R.drawable.btn_active));
//                } else {
//                    btnInput.setBackground(
//                            ContextCompat.getDrawable(getContext(), R.drawable.btn_inactive));
//                }
//                글자가 19개 일 때 버튼이 활성화 되는 코드
//
//                출처: https://devvkkid.tistory.com/111 [개발자입니까?]
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });

    }

    void nameEdtSet() {

        nameClearEdt.setOnClickListener(view -> {

            nameEdt.setText("");

        });
        nameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                mDogName = nameEdt.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                mDogName = nameEdt.getText().toString();
                if (nameEdt.getText().toString().length() == 1) {

                    nameEdt.getBackground().setColorFilter(getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);


//                    https://stackoverflow.com/questions/28303112/changing-tint-color-of-android-edittext-programmatically

                } else {
                    nameEdt.getBackground().setColorFilter(getResources().getColor(R.color.startwalkingGray),
                            PorterDuff.Mode.SRC_ATOP);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                mEdit_Input_Text_joinmember.getBackground().setColorFilter(getResources().getColor(R.color.editTextUnderLine),
//                        PorterDuff.Mode.SRC_ATOP);
//                warningText.setTextColor(ContextCompat.getColor(getBaseContext(),
//                        R.color.startwalkingGray));
//                warningImage.setImageResource(R.drawable.warning_off);

            }
        });


    }

    void setWeightEdt() {

        weightClearEdt.setOnClickListener(view -> {

            weightEdt.setText("");

        });
        weightEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mKg = weightEdt.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (isValidKg(editable.toString())) {
//                    mWeight = Float.parseFloat(mKg);
                    mKg += "kg";

                    weightEdt.setText(mKg);
                    weightEdt.setSelection(weightEdt.getText().length() - 2);


                }


            }
        });

    }

    @Override
    public void refresh(boolean isSuccess) {
        if (isSuccess) {
            finish();
        } else {

            Toast.makeText(getBaseContext(), "빈 공간을 채워주세요", Toast.LENGTH_SHORT);
        }
    }
}
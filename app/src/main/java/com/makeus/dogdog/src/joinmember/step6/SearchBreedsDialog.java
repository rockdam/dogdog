package com.makeus.dogdog.src.joinmember.step6;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makeus.dogdog.R;
import com.makeus.dogdog.src.joinmember.step6.interfaces.PassValueDialog;
import com.makeus.dogdog.src.joinmember.step6.models.SearchBreedsResult;
import com.makeus.dogdog.src.joinmember.step6.models.SearchBreedsResponse;

import java.util.ArrayList;

public class SearchBreedsDialog extends Dialog implements View.OnClickListener, PassValueDialog {



    ListView mListview;
    ArrayList<Breeds> mArraylist;
    private SearchBreedsDialogListener customDialogListener;

    SearchBreedsService mSearchBreedsService;

    ArrayAdapter<Breeds> mAdapter;
    public SearchBreedsDialog(@NonNull Context context) {
        super(context);
    }



    @Override
    public void passValueListView(SearchBreedsResponse searchBreedsListResponse) {
        //리스트로 온 경우
        for(SearchBreedsResult searchBreedsResult : searchBreedsListResponse.getSearchBreedsResult())
        {
            mArraylist.add(new Breeds(searchBreedsResult.getBreed(),searchBreedsResult.getBreedIdx()));


        }
        mAdapter=new ArrayAdapter<Breeds>(getContext(),android.R.layout.simple_list_item_1,mArraylist){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                // Replace text with my own
                view.setText(getItem(position).getBreed());
                return view;
            }
        };
        mListview.setAdapter(mAdapter);

        // 7. ListView 객체의 특정 아이템 클릭시 처리를 추가합니다.
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Breeds breeds=(Breeds)adapterView.getItemAtPosition(position);
                customDialogListener.onItemClickClicked(breeds.getBreed(),breeds.getBreedIdx());
                dismiss();
//                // 8. 클릭한 아이템의 문자열을 가져와서
//                String selected_item = (String)adapterView.getItemAtPosition(position);
//
//                // 9. 해당 아이템을 ArrayList 객체에서 제거하고
//                list.remove(selected_item);
//
//                // 10. 어댑터 객체에 변경 내용을 반영시켜줘야 에러가 발생하지 않습니다.
//                adapter.notifyDataSetChanged();
            }
        });
    }

    //인터페이스 설정
    interface SearchBreedsDialogListener{
        void onItemClickClicked(String breed, Integer breedIdx);

    }

    //호출할 리스너 초기화
    public void setDialogListener(SearchBreedsDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }


//    출처: https://bottlecok.tistory.com/38 [잡캐의 IT 꿀팁]
//    해당 패턴은 옵져버 패턴 입니다 .
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbreeds_dialog);
        mArraylist=new ArrayList<>();
        mListview=findViewById(R.id.listview_breeds_customdialog);
        mSearchBreedsService=new SearchBreedsService(this); //서비스 등록
        mSearchBreedsService.searchBreeds();
    }

    @Override
    public void onClick(View view) {

    }
}

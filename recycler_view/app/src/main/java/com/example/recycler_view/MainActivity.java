package com.example.recycler_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //activity_main.xml에서 recyclerview 인스턴스화
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);

        //리사이클러뷰에 레이아웃 매니저 설정
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //리사이클러뷰에 어댑터 설정
        mArrayList = new ArrayList<>();
        mAdapter = new CustomAdapter(mArrayList); //근데 왜 빈 어레이리스트를 넣어주냐
        mRecyclerView.setAdapter(mAdapter);


        //리사이클러뷰에 디바이더 아이템 데코레이션? 설정
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        //insert 버튼에 액티비티 달아주기
        Button buttonInsert = (Button) findViewById(R.id.button_main_insert);
        Button buttonDelete = (Button) findViewById(R.id.button_main_delete);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                Dictionary data = new Dictionary(count + "", "Apple" + count, "사과" + count);
                //mArrayList.add(0, data); //recyclerview 의 첫줄에 삽입
                mArrayList.add(data); // recyclerview의 마지막줄에 삽입

                //mAdapter.notifyDataSetChanged(); //화면이 다 바뀐다
                //mAdapter.notifyItemChanged(1); //특정 뷰홀더만 바꾼다 이거 희한하게 동작하네!
                mAdapter.notifyItemInserted(count); //특정 뷰홀더 위치에 삽입한다. 이게 함수를 덜 불러내는데(성능상 좋겠지 당연히?)

                //mAdapter.notifyItemMoved(0, count); //negative delta라는 에러가 뜨면서 앱이 강제종료된다
                //mAdapter.notifyItemMoved(0, count-1);
                //mAdapter.notifyItemRangeChanged(0, count); //itemChanged랑 하는 짓 생김새는 비슷함


                if (count > 0) {
                    buttonDelete.setVisibility(View.VISIBLE);
                } else if (count < 1) {
                    buttonDelete.setVisibility(View.GONE);
                }
            }
        });

        //리스트 삭제 버튼도 만들어보자.....
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;

                if (mArrayList.size() > 0) {
                    mArrayList.remove(mArrayList.size() - 1);
                }
                if (mArrayList.size() <= 0) {
                    buttonDelete.setVisibility(View.GONE);
                    count = 0;
                }
                //mAdapter.notifyDataSetChanged(); //전체 갱신 하면서 bind create 다시 호출
                mAdapter.notifyItemChanged(count); //삭제된 데이터를 지우면서 bind create 호출 x
            }
        });
    }
}
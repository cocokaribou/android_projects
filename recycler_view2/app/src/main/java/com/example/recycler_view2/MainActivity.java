package com.example.recycler_view2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Dictionary> mArrayList; //데이터 vo
    private CustomAdapter mAdapter; //어댑터
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("⚠️MainActivity", this.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list); //activity_main.xml

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();

        mAdapter = new CustomAdapter(this, mArrayList);
        recyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        // 화면 아래쪽 버튼을 클릭하면
        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 레이아웃 파일 edit_box.xml을 불러와서 화면에 보여준다
                // 빌더?
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.edit_box, null, false);
                builder.setView(view);

                final Button ButtonSubmit = (Button)view.findViewById(R.id.button_dialog_submit);
                final EditText editTextId = (EditText)view.findViewById(R.id.edittext_dialog_id);
                final EditText editTextEnglish = (EditText)view.findViewById(R.id.edittext_dialog_english);
                final EditText editTextKorean = (EditText)view.findViewById(R.id.edittext_dialog_korean);

                ButtonSubmit.setText("추가");

                final AlertDialog dialog = builder.create();

                // 다이얼로그 안에 있는 추가버튼을 클릭하면
                ButtonSubmit.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        // 사용자가 입력한 내용을 가져와서
                        String strID = editTextId.getText().toString();
                        String strEnglish = editTextEnglish.getText().toString();
                        String strKorean = editTextKorean.getText().toString();

                        // ArrayList에 추가하고
                        Dictionary dic = new Dictionary(strID, strEnglish, strKorean);

                        mArrayList.add(0, dic); //첫번째 줄에 삽입

                        // Adapter에서 RecyclerView에 반영한다
                        mAdapter.notifyItemInserted(0);

                        dialog.dismiss();
                    }
                });
                dialog.show();
           }
        });
    }
}
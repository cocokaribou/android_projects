package com.example.recycler_view2;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Dictionary> mList;
    private Context mContext;



    // 수정하는 동작
    // 컨텍스트 메뉴를 사용하려면 RecyclerView.ViewHolder를 상속받은 클래스에서
    // OnCreateContextMenuListener를 구현
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        protected TextView id;
        protected TextView english;
        protected TextView korean;

        public CustomViewHolder(@NonNull View itemView) { //뷰 홀더 xml 정보를 전달받는다
            super(itemView); //RecyclerView.ViewHolder 생성
            this.id = (TextView)itemView.findViewById(R.id.id_listitem);
            this.english = (TextView)itemView.findViewById(R.id.english_listitem);
            this.korean = (TextView)itemView.findViewById(R.id.korean_listitem);

            // OnCreateContextMenuListener 현재 클래스에서 구현한다고 설
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // 컨텍스트 메뉴를 생성하고 메뉴 항목선택시 호출되는 리스너. ID 1001, 1002로 어떤 메뉴 선택했는지 리스너에서 구분

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }


        //리스너를 밖에서 구현중
        // 컨텍스트 메뉴에서 항목 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case 1001 : //편집
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용
                        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_box, null, false);
                        builder.setView(view);

                        final Button ButtonSubmit = (Button)view.findViewById(R.id.button_dialog_submit);
                        final EditText editTextID = (EditText)view.findViewById(R.id.edittext_dialog_id);
                        final EditText editTextEnglish = (EditText)view.findViewById(R.id.edittext_dialog_english);
                        final EditText editTextKorean = (EditText)view.findViewById(R.id.edittext_dialog_korean);

                        // 해당 줄에 입력되어있던 데이터를 불러와서 다이얼로그에 보여줌
                        editTextID.setText(mList.get(getAdapterPosition()).getId());
                        editTextEnglish.setText(mList.get(getAdapterPosition()).getEnglish());
                        editTextKorean.setText(mList.get(getAdapterPosition()).getKorean());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener(){
                            //편집 버튼을 누르면 현재 UI에 입력돼있는 내용으로
                           public void onClick(View v){
                               String strID = editTextID.getText().toString();
                               String strEnglish = editTextEnglish.getText().toString();
                               String strKorean = editTextKorean.getText().toString();

                               Dictionary dic = new Dictionary(strID, strEnglish, strKorean);

                               //ArrayList에 있는 데이터 변경
                               mList.set(getAdapterPosition(), dic);

                               //어댑터에서 RecyclerView에 반영하도록 한다
                               notifyItemChanged(getAdapterPosition());

                               dialog.dismiss();
                           }
                        });
                        dialog.show();

                        break;

                    case 1002 : //삭제
                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),mList.size());
                        break;
                }
                return true;
            }
        };
    }

    public CustomAdapter(ArrayList<Dictionary> list){
        //adapter의 필드에 vo(Dictionary) 객체를 담
        this.mList = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("tag1", "create");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }
    //viewHolder를 생성(inflate)


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Log.d("tag2", "bind");
        holder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        holder.id.setGravity(Gravity.CENTER);
        holder.english.setGravity(Gravity.CENTER);
        holder.korean.setGravity(Gravity.CENTER);

        holder.id.setText(mList.get(position).getId()); //get(position)은 특정 뷰홀더에 표시되는 데이터만 arraylist에서 집는거군
        holder.english.setText(mList.get(position).getEnglish());
        holder.korean.setText(mList.get(position).getKorean());
    }
    //viewHolder의 데이터를 그려줌

    @Override
    public int getItemCount() {
        return (null!=mList?mList.size() : 0);
    }
}

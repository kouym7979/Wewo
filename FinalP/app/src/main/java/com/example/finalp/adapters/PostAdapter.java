package com.example.finalp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalp.NoticeBoardActivity;
import com.example.finalp.Notice_B.Post;
import com.example.finalp.Post_Comment;
import com.example.finalp.Post_write;
import com.example.finalp.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> datas;//뒷부분 추가
    private Context mcontext;


    public interface  EventListener<QuerySnapshot>{
        void onItemClicked(int position);
    }



    public PostAdapter(Context mcontext,List<Post> datas) {//어댑터에 대한 생성자
        this.datas = datas;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override//밑에 메소드들은 그냥 implement method한거입니다. 해야한대요
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Context context =parent.getContext();
       // LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //View view = inflater.inflate(R.layout.item_post,parent,false);
        //PostViewHolder
        //View view=inflater.inflate(R.layout.item_post,parent,false);
        //context=parent.getContext();
        //return new PostViewHolder(view);
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data=datas.get(position);//Post라는 모델객체를 하나 만든 이유
        holder.p_nickname.setText(datas.get(position).getP_nickname());
        holder.title.setText(datas.get(position).getTitle());//각각 데이터에 들어있는 제목 내용들이 각각 하나고 여러개가 아니기때문에
        holder.contents.setText(datas.get(position).getContents());//리스트로 만들어 주기 위해서
        final int posi=holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context=v.getContext();
                if(posi!=RecyclerView.NO_POSITION){
                    Intent intent=new Intent(v.getContext(),Post_Comment.class);
                    intent.putExtra("title",datas.get(posi).getTitle());
                    intent.putExtra("content",datas.get(posi).getContents());
                    intent.putExtra("nickname",datas.get(posi).getP_nickname());
                    //intent.putExtra("title",datas.get(pos).title);
                    mcontext.startActivity(intent);
                }
            }
        });

        //예를들면 첫째줄에 데이터에 위치를 각각 0번째 1번째...으로 받아서 그 위치마다 0번째 데이터위치에
        //0번째 제목, 0번째 내용 이런식으로 묶어서 리스트로 만들기 위해서 모델객체를 선언, holder가 그런 것을 지정해줌
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView contents;
        private TextView p_nickname;

        public PostViewHolder(@NonNull final View itemView) {//포스트 뷰홀더의 생성자
            super(itemView);

            title=itemView.findViewById(R.id.post_title);
            contents=itemView.findViewById(R.id.post_contents);
            p_nickname=itemView.findViewById(R.id.post_writer);


           //Intent intent = new Intent(View.,)
                   // int pos=getAdapterPosition();//몇 번째의 게시글을 클릭했는지 알기위해

        }


    }
}

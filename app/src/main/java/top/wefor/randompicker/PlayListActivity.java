package top.wefor.randompicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created on 16/8/26.
 *
 * @author ice
 */
public class PlayListActivity extends AppCompatActivity {
    private static final String PLAY_LIST = "play_list";

    private ArrayList<Music> mMusics;

    public static void go(Context context, ArrayList<Music> historyList) {
        Intent intent = new Intent(context, PlayListActivity.class);
        intent.putParcelableArrayListExtra(PLAY_LIST, historyList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        if (getSupportActionBar() != null) {
            // 默认左上角按钮可以点击
            getSupportActionBar().setHomeButtonEnabled(true);
            // 默认显示左上角返回按钮
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMusics = getIntent().getParcelableArrayListExtra(PLAY_LIST);

        initList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = getLayoutInflater().inflate(R.layout.item_play_list, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                Music music = mMusics.get(position);
                myViewHolder.musicTv.setText(music.name);
                myViewHolder.weightTv.setText(music.weight + "");
                myViewHolder.pickedTimeTv.setText(music.pickedTime + "");
            }

            @Override
            public int getItemCount() {
                return mMusics.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder {

                private TextView musicTv, weightTv, pickedTimeTv;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    musicTv = (TextView) itemView.findViewById(R.id.music_tv);
                    weightTv = (TextView) itemView.findViewById(R.id.weight_tv);
                    pickedTimeTv = (TextView) itemView.findViewById(R.id.pickedTime_tv);
                }
            }

        });

    }

}

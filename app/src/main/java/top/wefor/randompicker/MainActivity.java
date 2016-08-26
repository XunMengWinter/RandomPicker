package top.wefor.randompicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String PLAY_LIST = "play_list";

    RandomPicker mRandomPicker = new RandomPicker();
    ArrayList<Music> mMusicList = new ArrayList<>();
    ArrayList<Music> mHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRandomPicker = new RandomPicker();
        mRandomPicker.setMultiplyNumber(2);
        mRandomPicker.setRepeatable(true);

        mMusicList.add(new Music("东风破"));
        mMusicList.add(new Music("Valder Fields"));
        mMusicList.add(new Music("Refrain"));
        mMusicList.add(new Music("花落暮夕"));
        mMusicList.add(new Music("Flower Dance"));
        mMusicList.add(new Music("Love Story"));
        mMusicList.add(new Music("Neptune Illusion"));
        mMusicList.add(new Music("My Soul"));
        mMusicList.add(new Music("Dark of Night"));
        mMusicList.add(new Music("Better Off"));
        mMusicList.add(new Music("告白气球"));
        mMusicList.add(new Music("Tassel"));
        mMusicList.add(new Music("Gotta Have You"));
        mMusicList.add(new Music("白金ディスコ"));

        mMusicList.get(0).weight = 3;
        mMusicList.get(mMusicList.size() - 1).weight = 5;

        mRandomPicker.initSize(mMusicList.size());
        mRandomPicker.changeOriginWeight(0, 3);
        mRandomPicker.changeOriginWeight(mMusicList.size() - 1, 5);
        mRandomPicker.setNextPick(3);

        findViewById(R.id.nextBtn_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPos = mRandomPicker.next();
                Music music = mMusicList.get(nextPos);
                setTitle(music.name);
                music.pickedTime++;
                mHistoryList.add(music);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_play_list:
                Intent intent = new Intent(MainActivity.this, PlayListActivity.class);
                intent.putParcelableArrayListExtra(PLAY_LIST, mMusicList);
                startActivity(intent);
                return true;
            case R.id.action_history_list:
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < mHistoryList.size(); i++) {
                    stringBuilder.append(mHistoryList.get(i).name).append("\n");
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Played List")
                        .setMessage(stringBuilder)
                        .create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

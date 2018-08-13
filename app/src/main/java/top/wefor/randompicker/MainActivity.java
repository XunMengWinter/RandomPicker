package top.wefor.randompicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import top.wefor.randompicker.music.MusicActivity;

public class MainActivity extends AppCompatActivity {

    RandomPicker mRandomPicker;
    ArrayList<Music> mMusicList = new ArrayList<>();
    ArrayList<Music> mHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMusicList.add(new Music("东风破"));
        mMusicList.add(new Music("Valder Fields"));
        mMusicList.add(new Music("Refrain"));
        mMusicList.add(new Music("花落暮夕"));
        mMusicList.add(new Music("Flower Dance"));
        mMusicList.add(new Music("Love Story"));
        mMusicList.add(new Music("Neptune Illusion"));
        mMusicList.add(new Music("My Soul"));
        mMusicList.add(new Music("Dark of Night"));
        mMusicList.add(new Music("猎户星座"));
        mMusicList.add(new Music("Better Off"));
        mMusicList.add(new Music("告白气球"));
        mMusicList.add(new Music("Tassel"));
        mMusicList.add(new Music("Gotta Have You"));
        mMusicList.add(new Music("白金ディスコ"));
        mMusicList.add(new Music("且听风吟"));

        mMusicList.get(0).weight = 3;
        mMusicList.get(mMusicList.size() - 1).weight = 5;


        mRandomPicker = new RandomPicker(mMusicList.size(), 1);
//        mRandomPicker.setRepeatable(true);
        mRandomPicker.setCalculator(new Calculator() {
            @Override
            public int calculateNextWeight(int position, int currentWeight, int originWeight) {
                return (currentWeight + 1) * originWeight;
            }
        });

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

        CheckBox checkBox = (CheckBox) findViewById(R.id.cutMode_checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SnackbarUtilKt.showTips(buttonView, (isChecked ? "Enter" : "Exit") + " Cut mode");

                if (isChecked)
                    mRandomPicker.enterCutMode();
                else
                    mRandomPicker.exitCutMode();
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
                PlayListActivity.go(MainActivity.this, mMusicList);
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

    public void openMusicAty(View view) {
        startActivity(new Intent(this, MusicActivity.class));
    }
}

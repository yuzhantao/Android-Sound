package cn.krund.soundtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.krund.soundlibrary.SoundCollector;
import cn.krund.soundlibrary.SoundPlayer;
import cn.krund.soundlibrary.cache.LocalSoundCache;
import cn.krund.soundlibrary.cache.NetworkClientSoundCache;
import cn.krund.soundlibrary.cache.NetworkServerSoundCache;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LocalSoundCache soundCache;
    private SoundCollector soundCollector;
    private SoundPlayer soundPlayer;

    private Button btn_rec;
    private Button btn_play;
    private Button btn_stopRec;
    private  Button btn_stopPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play=(Button)findViewById(R.id.btn_startRec);
        btn_play.setOnClickListener(this);

        btn_rec=(Button)findViewById(R.id.btn_startPlay);
        btn_rec.setOnClickListener(this);

        btn_stopRec=(Button)findViewById(R.id.btn_stopRec);
        btn_stopRec.setOnClickListener(this);

        btn_stopPlay=(Button)findViewById(R.id.btn_stopPlay);
        btn_stopPlay.setOnClickListener(this);

        soundCache=new LocalSoundCache(null);

    }

    NetworkServerSoundCache serverCache ;
    NetworkClientSoundCache clientCache;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startRec:
                clientCache=new NetworkClientSoundCache(null,"192.168.1.102",8888);
                soundCollector=new SoundCollector(clientCache);
                soundCollector.startCollect();
                break;
            case R.id.btn_startPlay:
                serverCache=new NetworkServerSoundCache(null,8888);
                soundPlayer=new SoundPlayer(serverCache);
                soundPlayer.play();
                break;
            case R.id.btn_stopRec:
                soundCollector.stopCollect();
                break;
            case R.id.btn_stopPlay:
                soundPlayer.stop();
                break;
        }
    }
}

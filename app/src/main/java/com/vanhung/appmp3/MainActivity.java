package com.vanhung.appmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chootdev.blurimg.BlurImage;
import com.gauravk.audiovisualizer.visualizer.CircleLineVisualizer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ImageView imgAnhNen,imgPlay,imgCD;
MediaPlayer player;
private boolean nhacDangChay;
private boolean daCoNhac;
private  int thoiGianNhacDung=0;
SeekBar sbNhac;
int timeMax;
TextView txvTimeNhacRun, txvTimeNhacAll,txvLoibaihat,txvtenBaiHat;
float gocQuay=0;
ArrayList<String> arrLoiBaiHat = new ArrayList<>();
int vitriloi=0;
//CircleLineVisualizer mVisualizer;
ArrayList<BaiNhac> arrBaiNhac = new ArrayList<>();
int indexBaiNhac=0;
BaiNhac baiNhac;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setUp();
        setClick();
    }
    private void init(){
        arrLoiBaiHat = new ArrayList<>();
        BaiNhac b1 = new BaiNhac();
        b1.ten="Mình Cưới Nhau Đi";
        b1.anh=R.drawable.bai_nhac_1;
        b1.baiHat=R.raw.bai_1;
        b1.arrBaiHat = new ArrayList<>();
        b1.arrBaiHat.add("Hay lad minh cuoi nhau di");
        b1.arrBaiHat.add("loi so 1: ");
        b1.arrBaiHat.add("loi so 2: ");
        b1.arrBaiHat.add("Minhf cuowis nhau di");
        b1.arrBaiHat.add("loi so 4: ");
        arrBaiNhac.add(b1);

        BaiNhac b2 = new BaiNhac();
        b2.ten="Way back home";
        b2.anh=R.drawable.bai_2;
        b2.baiHat= R.raw.bai_2;
        b2.arrBaiHat = new ArrayList<>();
        b2.arrBaiHat.add("Khong co loi");
        b2.arrBaiHat.add("loi so 1: ");
        b2.arrBaiHat.add("loi so 2: ");
        b2.arrBaiHat.add("loi so 3: ");
        b2.arrBaiHat.add("loi so 4: ");
        arrBaiNhac.add(b2);



    }
    private void anhXa(){
        imgAnhNen= findViewById(R.id.imgAnhNen);
        imgPlay= findViewById(R.id.imgPlay);
        sbNhac = findViewById(R.id.sbNhac);
        txvTimeNhacRun = findViewById(R.id.txvTimeNhacRun);
        txvTimeNhacAll = findViewById(R.id.txvTimeNhacAll);
        txvLoibaihat = findViewById(R.id.txvLoibaihat);
        imgCD = findViewById(R.id.imgCD);
        //mVisualizer=findViewById(R.id.blob);
        txvtenBaiHat=findViewById(R.id.txvtenBaiHat);





    }
    private  void setUp()
    {
        choiBaiNhac(0);
        dungNhacLai();
        setAnhNen(R.drawable.bai_nhac_1);
        //batDauChayNhac(R.raw.Bai_1);
        daCoNhac=false;
        nhacDangChay=false;
        new CountDownTimer(30000,50)
        {

            @Override
            public void onTick(long l) {
                upDate();
            }

            @Override
            public void onFinish() {
                start();
            }
        }.start();
        new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                upDate2();
                start();
            }
        }.start();
    }
    private void setClick(){
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daCoNhac == false)
                {
                    batDauChayNhac(R.raw.bai_1);
                }else {
                    if (nhacDangChay == true) {
                        dungNhacLai();
                    }else
                        {
                      chayTiepNhac();
                    }
                }
            }
        });
        sbNhac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(daCoNhac==false || nhacDangChay ==false)
                {
                    return;
                }
                thoiGianNhacDung = seekBar.getProgress();
                player.pause();
                player.seekTo(thoiGianNhacDung);
                player.start();
            }
        });
    }
    private void setAnhNen(int anh)
    {
        BlurImage.withContext(this)
                .setBlurRadius(15.5f)
                .setBitmapScale(0.1f)
                .blurFromResource(anh)
                .into(imgAnhNen);
    }

    private void batDauChayNhac(int bannhac)
    {
        if(player == null)
        {
            player=MediaPlayer.create(this,bannhac);
        }
        else
        {
            player.stop();
            player.release();
            player=MediaPlayer.create(this,bannhac);

        }
        player.start();
        imgPlay.setImageResource(R.drawable.pause);
        daCoNhac = true;
        nhacDangChay=true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sbNhac.setMin(0);
        }
        timeMax=player.getDuration();
        sbNhac.setMax(timeMax);
        txvTimeNhacAll.setText(misiToString(timeMax));
      //  mVisualizer.setAudioSessionId(player.getAudioSessionId());
        gocQuay=0;
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                chayXongNhac();
            }
        });
        vitriloi=0;
    }

    private void chayXongNhac()
    {
        nhacDangChay = false;
        daCoNhac=false;
        thoiGianNhacDung=0;
        gocQuay=0;
        imgPlay.setImageResource(R.drawable.play);
    }

    private void dungNhacLai()
    {
        nhacDangChay=false;
        imgPlay.setImageResource(R.drawable.play);
        player.pause();
        thoiGianNhacDung = player.getCurrentPosition();
    }

    private void chayTiepNhac()
    {
        nhacDangChay=true;
        imgPlay.setImageResource(R.drawable.pause);
        player.seekTo(thoiGianNhacDung);
        player.start();
    }

    private void upDate()
    {
        if(daCoNhac==false || nhacDangChay ==false)
        {
            return;
        }
        sbNhac.setProgress(player.getCurrentPosition());
        txvTimeNhacRun.setText(misiToString(player.getCurrentPosition()));
        gocQuay=gocQuay+0.5f;
        if(gocQuay == 360)
        {
            gocQuay=0;
        }
        imgCD.setRotation(gocQuay);
    }

    private String misiToString(int t)
    {
        t = t/1000;
        int p = t/60;
        int s = t%60;
        return checkNum10(p)+":"+checkNum10(s);
    }

    private String checkNum10(int i)
    {
        if(i < 10 )
        {
            return "0"+i;
        }
        return ""+i;
    }

    private void upDate2()
    {

        if(daCoNhac==false || nhacDangChay ==false)
        {
            return;
        }
        vitriloi++;
        if(arrLoiBaiHat.size()==vitriloi)
        {
            vitriloi=0;
        }
        txvLoibaihat.startAnimation(AnimationUtils.loadAnimation(this,R.anim.mat_di));
        new CountDownTimer(400, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                txvLoibaihat.setText(arrLoiBaiHat.get(vitriloi));
                txvLoibaihat.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.hien_len));

            }
        }.start();
    }
    private void choiBaiNhac(int i)
    {
        indexBaiNhac=indexBaiNhac+i;
        if(indexBaiNhac == arrBaiNhac.size())
        {
            indexBaiNhac = 0;
        }
        if(indexBaiNhac==-1)
        {
            indexBaiNhac = arrBaiNhac.size()-1;
        }
        baiNhac=arrBaiNhac.get(indexBaiNhac);
        txvtenBaiHat.setText(baiNhac.ten);
        imgCD.setImageResource(baiNhac.anh);
        arrLoiBaiHat.clear();
        arrLoiBaiHat.addAll(baiNhac.arrBaiHat);
        setAnhNen(baiNhac.anh);
        batDauChayNhac(baiNhac.baiHat);
    }
    public void choiBaiTruoc(View view )
    {
        choiBaiNhac(-1);
    }

    public void choiBaiSau(View view) {
        choiBaiNhac(1);
    }
}
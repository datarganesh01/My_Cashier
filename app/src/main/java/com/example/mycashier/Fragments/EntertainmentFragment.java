package com.example.mycashier.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycashier.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class EntertainmentFragment extends Fragment {

    TextView songname,artistname,starttime,totaltime;
    ImageView like,dislike,previous,playpause,next,shuffle,coverimage,backward,forward,back;
    SeekBar songprogress,volumeprogress;
    MediaPlayer mMediaplayer;
    AudioManager audioManager;
    private int currentIndex = 0;
    private static int sTime=0,tTime=0,oTime=0,bTime=5000,fTime=5000;
    Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_entertainment, container, false);

        Toast.makeText(getActivity(), "Entertainment Here", Toast.LENGTH_SHORT).show();

        songname = view.findViewById(R.id.tvSongName);
        artistname = view.findViewById(R.id.tvartistname);
        starttime = view.findViewById(R.id.tvstarttime);
        totaltime = view.findViewById(R.id.tvtotaltime);

        like=view.findViewById(R.id.ivlike);
        previous=view.findViewById(R.id.ivprevious);
        playpause = view.findViewById(R.id.ivplaypause);
        next=view.findViewById(R.id.ivnext);
        shuffle=view.findViewById(R.id.ivshuffle);
        coverimage = view.findViewById(R.id.ivcoverimage);
        back = view.findViewById(R.id.ivbackbutton);

        songprogress = view.findViewById(R.id.skbar);
        volumeprogress = view.findViewById(R.id.skvolumebar);

        ArrayList<Integer> songlist = new ArrayList<>();
        songlist.add(0,R.raw.bulleya);
        songlist.add(1,R.raw.ishq_di_bajiyaa);
        songlist.add(2,R.raw.o_rangrez);
        songlist.add(3,R.raw.tum_tak);
        songlist.add(4,R.raw.raanjhanaa_hua_mai_tera);

        mMediaplayer = MediaPlayer.create(getActivity(),songlist.get(currentIndex));

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaplayer != null && mMediaplayer.isPlaying())
                {
                    mMediaplayer.pause();
                    playpause.setImageResource(R.drawable.play2);
                }
                else
                {
                    mMediaplayer.start();
                    playpause.setImageResource(R.drawable.pause2);
                }

                tTime = mMediaplayer.getDuration();
                sTime = mMediaplayer.getCurrentPosition();

                if (oTime ==0)
                {
                    songprogress.setMax(tTime);
                    oTime =1;
                }

//                %d = decimal number
//                02 = always print 2 digits (pad with 0 if needed)


                totaltime.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                ));


                starttime.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                        ));

                handler.postDelayed(UpdateSongProgress,1000);
                songDetails();

            }
        });


        songprogress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser)
                {
                    mMediaplayer.seekTo(progress);
                    songprogress.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentIndex > 0)
                {
                    currentIndex--;
                }
                else
                {
                    currentIndex = songlist.size()-1;

                }

                if (mMediaplayer.isPlaying())
                {
                    mMediaplayer.stop();

                }

//                if (mMediaplayer!=null )
//                {
//                    playpause.setImageResource(R.drawable.play);
//                }

                playpause.setImageResource(R.drawable.pause2);
                mMediaplayer = MediaPlayer.create(getActivity(),songlist.get(currentIndex));
                mMediaplayer.start();

                tTime = mMediaplayer.getDuration();
                sTime = mMediaplayer.getCurrentPosition();

                if (oTime ==0)
                {
                    songprogress.setMax(tTime);
                    oTime =1;
                }

                totaltime.setText(String.format("%%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                ));


                starttime.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                ));

                handler.postDelayed(UpdateSongProgress,1000);
                songDetails();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (currentIndex < songlist.size() - 1)
                {
                    currentIndex++;
                }
                else
                {
                    currentIndex = 0;
                }

                if (mMediaplayer.isPlaying())
                {
                    mMediaplayer.stop();
                    mMediaplayer.release();

                }

//                if (mMediaplayer!=null )
//                {
//                    playpause.setImageResource(R.drawable.play);
//                }

                playpause.setImageResource(R.drawable.pause2);
                mMediaplayer = MediaPlayer.create(getActivity(),songlist.get(currentIndex));
                mMediaplayer.start();

                tTime = mMediaplayer.getDuration();
                sTime = mMediaplayer.getCurrentPosition();

                if (oTime ==0)
                {
                    songprogress.setMax(tTime);
                    oTime =1;
                }

                totaltime.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                ));


                starttime.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                ));

                handler.postDelayed(UpdateSongProgress,1000);
                songDetails();
            }
        });
        
        


        /*
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sTime - bTime > 0 && )
                {
                    sTime = sTime-bTime;
                    mMediaplayer.seekTo(sTime);
                }
                else
                {
                    Toast.makeText(getActivity(), "Can't Jump Backward", Toast.LENGTH_SHORT).show();

                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((sTime + fTime) < tTime)
                {
                    sTime = sTime+ fTime;
                    mMediaplayer.seekTo(sTime);
                }
                else
                {
                    Toast.makeText(getActivity(), "Can't Jump Forward", Toast.LENGTH_SHORT).show();
                }
            }
        });

        */

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomNavigationView bmv = getActivity().findViewById(R.id.homebottomnavview);
                bmv.setSelectedItemId(R.id.homehome);

            }
        });


        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeprogress.setMax(maxVolume);
        volumeprogress.setProgress(currentVolume);

        volumeprogress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                like.setImageResource(R.drawable.like_icon);
                Toast.makeText(getActivity(), "Song added to favourites 🩷", Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }

    private void songDetails() {

        if (currentIndex == 0)
        {
            songname.setText("Bulleya");
            artistname.setText("A.R.Rahman");
            coverimage.setImageResource(R.drawable.bulleya_ci);

        } else if (currentIndex == 1)
        {
            songname.setText("Ishq Di Bajiyaa");
            artistname.setText("Diljit Dosanjh");
            coverimage.setImageResource(R.drawable.ishq_di_bajiya_ci);

        } else if (currentIndex == 2)
        {
            songname.setText("O Rangrez");
            artistname.setText("Javed Bashir, Shreya Ghoshal");
            coverimage.setImageResource(R.drawable.o_rangrez_ci);

        } else if (currentIndex == 3)
        {
            songname.setText("Tum Tak");
            artistname.setText("Javed Ali, Keerthi Sagathia");
            coverimage.setImageResource(R.drawable.tum_tak_ci);

        } else if (currentIndex == 4)
        {
            songname.setText("Ranjhanaa hua mai tera");
            artistname.setText("Jaswinder Singh, A.R.Rahman");
            coverimage.setImageResource(R.drawable.ranjhana_ci);
        }

    }

    private Runnable UpdateSongProgress = new Runnable() {
        @Override
        public void run() {

            sTime = mMediaplayer.getCurrentPosition();

            starttime.setText(String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
            ));

            songprogress.setProgress(sTime);

            handler.postDelayed(this,1000);
        }
    };
}
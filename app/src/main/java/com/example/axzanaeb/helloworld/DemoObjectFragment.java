package com.example.axzanaeb.helloworld;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.os.SystemClock.sleep;
import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Created by axzanaeb on 11/29/15.
 */
public class DemoObjectFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    protected View view;
    private IncGame game;
    private int mPage;
    private boolean UIinit = false;
    private ArrayList<SeekBar> seekBarList = new ArrayList<>();
    private ArrayList<String> seekBarStringList = new ArrayList<>();

    public static DemoObjectFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DemoObjectFragment fragment = new DemoObjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        startGame(game = new IncGame());
    }

    private void startGame(final IncGame game) {

        new Thread() {

            int count=0;
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                        if(count>=game.getVillagers_breed_rate()){
                            count=0;
                            game.addVillager();
                        }else
                            count++;
                        if (UIinit)
                            updateUI();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_mines, container, false);

        switch (mPage) {
            case 1:
                view = inflater.inflate(R.layout.activity_mines, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.activity_village, container, false);
                updateVillageContent();
                break;
            case 3:
                view = inflater.inflate(R.layout.activity_science, container, false);
                break;
        }
        return view;
    }

    private void updateVillageContent() {
        fillLists();
        addListenersVillage(view);
        updateUI();
        UIinit=true;
    }

    private void updateUI() {

        runOnUiThread(new Runnable() {
            String str;
            TextView txv;
            @Override
            public void run() {
                str = view.getResources().getString(R.string.idle_villager, game.getVillagers());
                txv = (TextView) view.findViewById(R.id.idle_villagers_txv);
                txv.setText(str);

                str = view.getResources().getString(R.string.villager_breed_rate, game.getVillagers_breed_rate());
                txv = (TextView) view.findViewById(R.id.villager_breed_rate_txv);
                txv.setText(str);



            }
        });
    }

    private void fillLists() {
        seekBarList.add((SeekBar) view.findViewById(R.id.seekBar_farmers));
        seekBarList.add((SeekBar) view.findViewById(R.id.seekBar_lumberjacks));
        seekBarList.add((SeekBar) view.findViewById(R.id.seekBar_stone_miners));
        seekBarList.add((SeekBar) view.findViewById(R.id.seekBar_gold_miners));
    }


    public void addListenersVillage(final View view) {
        CheckBox chkBox;
        chkBox = (CheckBox) view.findViewById(R.id.checkbox_lumberjack);
        chkBox.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is it checked?
                View parent = (View) v.getParent();
                SeekBar skBar = (SeekBar) parent.findViewById(R.id.seekBar_farmers);

                if (((CheckBox) v).isChecked()) {
                    skBar.setEnabled(false);
                } else {
                    skBar.setEnabled(true);

                }

            }
        });

        for (SeekBar skBar : seekBarList) {
            skBar.setOnSeekBarChangeListener(new SeekBarListener(this));
        }
    }

    public void updateSeekBars(SeekBar seekBar) {
        while (totalSeekBarProgress() > 100)
            for (SeekBar sb : seekBarList) {
                SeekBar skBar = (SeekBar) view.findViewById(sb.getId());
                if (skBar.getId() != seekBar.getId() && skBar.getProgress() > 0) {
                    skBar.setProgress(skBar.getProgress() - 1);

                }
            game.setVillagersDistribution(
                    ((SeekBar) view.findViewById(R.id.seekBar_farmers)).getProgress(),
                    ((SeekBar) view.findViewById(R.id.seekBar_lumberjacks)).getProgress(),
                    ((SeekBar) view.findViewById(R.id.seekBar_stone_miners)).getProgress(),
                    ((SeekBar) view.findViewById(R.id.seekBar_gold_miners)).getProgress());
            }
        updateSeekBarStrings();
    }

    private void updateSeekBarStrings() {

        SeekBar sb = (SeekBar) view.findViewById(R.id.seekBar_farmers);
        String str = view.getResources().getString(R.string.farmer, sb.getProgress());
        TextView txv = (TextView) view.findViewById(R.id.farmers);
        txv.setText(str);

        sb = (SeekBar) view.findViewById(R.id.seekBar_lumberjacks);
        str = view.getResources().getString(R.string.lumberjack, sb.getProgress());
        txv = (TextView) view.findViewById(R.id.lumberjacks);
        txv.setText(str);

        sb = (SeekBar) view.findViewById(R.id.seekBar_stone_miners);
        str = view.getResources().getString(R.string.stone_miner, sb.getProgress());
        txv = (TextView) view.findViewById(R.id.stoneMiners);
        txv.setText(str);

        sb = (SeekBar) view.findViewById(R.id.seekBar_gold_miners);
        str = view.getResources().getString(R.string.gold_miner, sb.getProgress());
        txv = (TextView) view.findViewById(R.id.goldMiners);
        txv.setText(str);

    }

    private int totalSeekBarProgress() {
        int total = 0;
        for (SeekBar skBar : seekBarList) {
            total += skBar.getProgress();
        }
        return total;
    }
}

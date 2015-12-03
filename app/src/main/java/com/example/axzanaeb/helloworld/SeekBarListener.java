package com.example.axzanaeb.helloworld;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by axzanaeb on 11/30/15.
 */
public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
    DemoObjectFragment fragment;

    public SeekBarListener(DemoObjectFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            //TextView txV = (TextView) fragment.view.findViewById(R.id.farmers);
            //txV.setText(fragment.view.getResources().getString(R.string.lumberjack, progress));
            fragment.updateSeekBars(seekBar);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

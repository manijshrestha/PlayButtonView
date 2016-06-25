package com.manijshrestha.playbuttonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.manijshrestha.progressplaybutton.PlayButtonView;

public class MainActivity extends AppCompatActivity {

    private PlayButtonView mPlayButtonView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.progress);
        mPlayButtonView = (PlayButtonView) findViewById(R.id.play_button);
    }

    public void updateProgress(View view) {
        mPlayButtonView.setProgress(Float.valueOf(mEditText.getText().toString()), true);
    }

    public void toggleState(View view) {
        int buttonState = mPlayButtonView.getButtonState();
        switch (buttonState) {
            case PlayButtonView.STATE_PLAY:
                mPlayButtonView.setButtonState(PlayButtonView.STATE_PAUSE, true);
                break;
            case PlayButtonView.STATE_PAUSE:
                mPlayButtonView.setButtonState(PlayButtonView.STATE_PLAY, true);
                break;
        }
    }
}

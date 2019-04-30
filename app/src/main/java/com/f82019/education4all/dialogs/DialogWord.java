package com.f82019.education4all.dialogs;

import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.f82019.education4all.R;

import java.util.Locale;

public class DialogWord extends DialogFragment implements TextToSpeech.OnInitListener {

    private String word_txt, desc_txt;

    private TextToSpeech tts;

    public DialogWord(){}

    public static DialogWord newInstance(String word, String desc){
        DialogWord dialogWord = new DialogWord();

        Bundle args = new Bundle();
        args.putString("WORD", word);
        args.putString("DESC", desc);

        dialogWord.setArguments(args);

        return dialogWord;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            word_txt = getArguments().getString("WORD");
            desc_txt = getArguments().getString("DESC");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_word, container, false);

        tts = new TextToSpeech(getContext(), this);

        getDialog().setTitle("WORD DETAILS");

        Log.e("DIALOG", String.format(Locale.ENGLISH, "%s : %s", word_txt, desc_txt));

        ((TextView) view.findViewById(R.id.dialog_word_word))
                .setText(word_txt);

        ((TextView) view.findViewById(R.id.dialog_word_desc))
                .setText(desc_txt);

        view.findViewById(R.id.dialog_word_btn_speak)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.speak(
                                String.format(Locale.US, "%s. Meaning. %s", word_txt, desc_txt),
                                TextToSpeech.QUEUE_FLUSH,
                                null
                        );
                    }
                });



        return view;
    }



    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.98), (int) (size.y * 0.3)); // WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing

        super.onResume();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);
            // tts.setPitch(5); // set pitch level
            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }
}

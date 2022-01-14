package com.example.memom.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.memom.Model.MyCanvasView;
import com.example.memom.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CanvasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CanvasFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public CanvasFragment() {
        // Required empty public constructor
    }

    public static CanvasFragment newInstance(String param1, String param2) {
        CanvasFragment fragment = new CanvasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_canvas, container, false);

        Button btClear,btRed,btBlue,btGreen;
        MyCanvasView canvasView = view.findViewById(R.id.myCanvasView);
        btClear = view.findViewById(R.id.button_Clear);
        btGreen = view.findViewById(R.id.button_Green);
        btBlue = view.findViewById(R.id.button_Blue);
        btRed = view.findViewById(R.id.button_Red);

        btClear.setOnClickListener(v->{ canvasView.clear(); });
        btGreen.setOnClickListener(v -> {canvasView.setColor(Color.GREEN);});
        btBlue.setOnClickListener(v -> {canvasView.setColor(Color.BLUE);});
        btRed.setOnClickListener(v -> {canvasView.setColor(Color.RED);});
        // Inflate the layout for this fragment
        return view;
    }
}
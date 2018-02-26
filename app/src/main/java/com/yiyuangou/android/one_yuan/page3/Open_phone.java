package com.yiyuangou.android.one_yuan.page3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/3/17.
 */
public class Open_phone extends Fragment {

    View  view;
    qs_guess qs_guess;
    qs_pk10 qs_pk10;
    qs_hero qs_hero;
    private View guess_qs;
    private View pk10_qs;
    private View hero_qs;
    private TextView jingcai_text;
    private TextView pk10_text;
    private TextView hero_text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.more_qs_home, container, false);
        init();
        init_home();
        init_click();

        return view;
    }

    private void init_click() {
        guess_qs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess_qs.setBackgroundColor(getResources().getColor(R.color.touming));
                pk10_qs.setBackgroundColor(getResources().getColor(R.color.kjqs));
                hero_qs.setBackgroundColor(getResources().getColor(R.color.touming));
                jingcai_text.setTextColor(getResources().getColor(R.color.home_orange));
                pk10_text.setTextColor(getResources().getColor(R.color.white));
                hero_text.setTextColor(getResources().getColor(R.color.white));

                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
                beginTransaction2.replace(R.id.qs_layout, qs_guess);
                beginTransaction2.commit();
            }
        });
        pk10_qs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess_qs.setBackgroundColor(getResources().getColor(R.color.kjqs));
                pk10_qs.setBackgroundColor(getResources().getColor(R.color.touming));
                hero_qs.setBackgroundColor(getResources().getColor(R.color.touming));
                jingcai_text.setTextColor(getResources().getColor(R.color.white));
                pk10_text.setTextColor(getResources().getColor(R.color.home_orange));
                hero_text.setTextColor(getResources().getColor(R.color.white));
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
                beginTransaction2.replace(R.id.qs_layout, qs_pk10);
                beginTransaction2.commit();
            }
        });
        hero_qs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess_qs.setBackgroundColor(getResources().getColor(R.color.touming));
                pk10_qs.setBackgroundColor(getResources().getColor(R.color.touming));
                hero_qs.setBackgroundColor(getResources().getColor(R.color.kjqs));
                jingcai_text.setTextColor(getResources().getColor(R.color.white));
                pk10_text.setTextColor(getResources().getColor(R.color.white));
                hero_text.setTextColor(getResources().getColor(R.color.home_orange));
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
                beginTransaction2.replace(R.id.qs_layout, qs_hero);
                beginTransaction2.commit();
            }
        });
    }

    private void init_home() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.add(R.id.qs_layout,qs_guess);
        beginTransaction.commit();

    }

    private void init() {

        jingcai_text= (TextView) view.findViewById(R.id.jingcai_text);
        pk10_text= (TextView) view.findViewById(R.id.pk10_text);
        hero_text= (TextView) view.findViewById(R.id.hero_text);
        jingcai_text.setTextColor(getResources().getColor(R.color.home_orange));
        pk10_text.setTextColor(getResources().getColor(R.color.white));
        hero_text.setTextColor(getResources().getColor(R.color.white));

        guess_qs=view.findViewById(R.id.guess_qs);
        pk10_qs=view.findViewById(R.id.pk10_qs);
        hero_qs=view.findViewById(R.id.hero_qs);

        guess_qs.setBackgroundColor(getResources().getColor(R.color.touming));
        pk10_qs.setBackgroundColor(getResources().getColor(R.color.kjqs));
        hero_qs.setBackgroundColor(getResources().getColor(R.color.touming));
        qs_guess=new qs_guess();
        qs_pk10=new qs_pk10();
        qs_hero=new qs_hero();
    }

}

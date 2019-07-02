package com.example.module_main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_base.R2;
import com.example.common_base.annotation.BindEventBus;
import com.example.common_base.base.BaseActivity;
import com.example.module_main.R;
import com.example.module_main.fragment.HomeFragment;
import com.example.module_main.fragment.MineFragment;
import com.example.module_main.fragment.ProjectFragment;
import com.example.module_main.fragment.SystemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = "/main/main")
public class _MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton homeRadioButton;
    private RadioButton mineRadioButton;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private ProjectFragment projectFragment;
    private SystemFragment systemFragment;
    private int currentSelectedId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        radioGroup = findViewById(R.id.rg_radio_group);
        homeRadioButton = findViewById(R.id.rb_home);
        mineRadioButton = findViewById(R.id.rb_mine);
    }

    @Override
    protected void initData() {
        super.initData();
        fragmentManager = getSupportFragmentManager();
        createFragment();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == currentSelectedId) {
                    return;
                }
                currentSelectedId = checkedId;
                if (checkedId == R.id.rb_home) {
                    selectFragment(0);
                } else if (checkedId == R.id.rb_project) {
                    selectFragment(1);
//                    projectFragment.setStatusBarColor(Color.WHITE);
//                    StatusBarUtil.setLightMode(MainActivity.this);
                } else if (checkedId == R.id.rb_system) {
                    selectFragment(2);
                } else if (checkedId == R.id.rb_mine) {
                    selectFragment(3);

                }
            }
        });
        selectFragment(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void createFragment() {
        fragmentList = new ArrayList<>();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.fl_main_container, homeFragment);
        fragmentList.add(homeFragment);

        projectFragment = new ProjectFragment();
        ft.add(R.id.fl_main_container, projectFragment);
        fragmentList.add(projectFragment);

        systemFragment = new SystemFragment();
        ft.add(R.id.fl_main_container, systemFragment);
        fragmentList.add(systemFragment);

        MineFragment mineFragment = new MineFragment();
        ft.add(R.id.fl_main_container, mineFragment);
        fragmentList.add(mineFragment);

        // 提交事务
        ft.commit();
    }

    /**
     * 选中某个 Fragment
     *
     * @param index
     */
    private void selectFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == index) {
                ft.show(fragmentList.get(i));
            } else {
                ft.hide(fragmentList.get(i));
            }
        }
        ft.commit();
    }

}

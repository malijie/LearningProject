package com.base.lib.profile;

import android.content.Context;

/**
 * Created by malijie on 2017/5/5.
 */

public class Profile {
    public static Context sContext = null;

    public static final int DRIVER_SUBJECT_ONE = 1;//科目一
    public static final int DRIVER_SUBJECT_FOUR= 4;//科目四

    public static final String DRIVER_MODEL_ONE = "c1";//驾照类型

    public static final String TEST_TYPE_ORDER = "order";//顺序联系
    public static final String TEST_TYPE_RANDOM = "random";//随机练习

    public static final int SUBJECT1_ORDER_TOTAL_ITEM = 1229;//顺序练习总共题目
    public static final int SUBJECT4_ORDER_TOTAL_ITEM = 1094;//顺序练习总共题目
    public static final int RANDOM_TOTAL_ITEM = 100;//随机练习总共题目
    public static final int EXAM_TOTAL_ITEM = 100;//考试题目数目

    public static void init(Context context){
        sContext = context;
    }


}

package com.handsomexi.homework.Util;

import com.handsomexi.homework.Bean.HomeWorkBean;
import com.handsomexi.homework.Bean.HomeWorkBeanDao;
import com.handsomexi.homework.Myapp;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlUtil {
    private static HomeWorkBeanDao getDao(){
        return Myapp.getInstances().getDaoSession().getHomeWorkBeanDao();
    }
    public static void save(String ImagePath, String Subject, String SchoolYear, String Semester){
        HomeWorkBean bean = new HomeWorkBean( ImagePath,  Subject,  SchoolYear, Semester,  new Date().getTime());
        getDao().insert(bean);
    }
    public static void delete(String ImagePath){
        getDao().deleteByKey(ImagePath);
    }
    public static void delete(HomeWorkBean bean){
        getDao().delete(bean);
    }
    public static void delete(List<HomeWorkBean> beans){
        getDao().deleteInTx(beans);
    }
    public static void deleteAll(){
        getDao().deleteAll();
    }
    public static List<HomeWorkBean> query(String Subject,String SchoolYear,String Semester){
        QueryBuilder<HomeWorkBean> builder = getDao().queryBuilder();

        if(Subject.equals("全部")&&SchoolYear.equals("全部")&&Semester.equals("全部")){
            return getDao().loadAll();
        }
        if(Subject.equals("全部")&&SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Semester.eq(Semester)).list();
        }
        if(Subject.equals("全部")&&!SchoolYear.equals("全部")&&Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear)).list();
        }
        if(Subject.equals("全部")&&!SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear),HomeWorkBeanDao.Properties.Semester.eq(Semester)).list();
        }


        if(!Subject.equals("全部")&&SchoolYear.equals("全部")&&Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject)).list();
        }
        if(!Subject.equals("全部")&&SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject),HomeWorkBeanDao.Properties.Semester.eq(Semester)).list();
        }
        if(!Subject.equals("全部")&&!SchoolYear.equals("全部")&&Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject),HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear)).list();
        }
        if(!Subject.equals("全部")&&!SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject),HomeWorkBeanDao.Properties.Semester.eq(Semester),HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear)).list();
        }
        return new ArrayList<>();
    }
}

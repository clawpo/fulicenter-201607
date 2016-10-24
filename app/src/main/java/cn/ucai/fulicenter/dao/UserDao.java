package cn.ucai.fulicenter.dao;

import android.content.Context;

import cn.ucai.fulicenter.bean.User;


public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR_ID = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_user_avatar_lastupdate_time";

    public UserDao(Context context) {
        DBManager.getInstance().onInit(context);
    }

    public boolean saveUser(User user){
        return DBManager.getInstance().saveUser(user);
    }

    public User getUser(String username){
        return DBManager.getInstance().getUser(username);
    }

    public boolean updateUser(User user){
        return DBManager.getInstance().updateUser(user);
    }
}

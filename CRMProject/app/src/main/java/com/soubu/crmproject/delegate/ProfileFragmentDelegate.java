package com.soubu.crmproject.delegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.User;
import com.soubu.crmproject.utils.DrawableUtils;
import com.soubu.crmproject.utils.UserManager;

/**
 * Created by dingsigang on 16-8-9.
 */
public class ProfileFragmentDelegate extends BaseFragmentDelegate {

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_profile;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        User user = UserManager.getUser();
        TextView tvAvatar = get(R.id.tv_avatar);
        tvAvatar.setText(DrawableUtils.getMiniName(user.getNickname()));
        tvAvatar.setBackgroundResource(DrawableUtils.getAvatarColor(user.getNickname()));
        ((TextView)get(R.id.tv_name)).setText(user.getNickname());
        String department = user.getDepartment();
        String position = user.getPosition();
        if(TextUtils.isEmpty(department) && TextUtils.isEmpty(position)){
            get(R.id.ll_position).setVisibility(View.GONE);
        } else {
            if(TextUtils.isEmpty(department) || TextUtils.isEmpty(position)){
                get(R.id.v_mid_line).setVisibility(View.GONE);
            }
            ((TextView)get(R.id.tv_department)).setText(department);
            ((TextView)get(R.id.tv_position)).setText(position);
        }
    }

}

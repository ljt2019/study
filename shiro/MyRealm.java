package com.github.zhangkaitao.shiro.chapter2.realm;

import com.github.zhangkaitao.shiro.chapter2.constants.Constants;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Create by tiger on 2019/1/6
 * 自定义 Realm 实现
 */
public class MyRealm implements Realm {

    //返回一个唯一的Realm名字
    @Override
    public String getName() {
        return "myRealm";
    }

    //判断此Realm是否支持此Token
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持 UsernamePasswordToken 类型的Token
        return token instanceof UsernamePasswordToken;
    }

    //根据Token获取认证信息
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();  //得到用户名
        String password = new String((char[]) token.getCredentials()); //得到密码
        
        /********************** 认证逻辑 **** start ******************/
        if (!Constants.USERNAME_TIGER.equals(username)) {
            throw new UnknownAccountException();//如果用户名错误
        }
        if (!Constants.PASSWORD_TIGER.equals(password)) {
            throw new IncorrectCredentialsException();//如果密码错误
        }
        /********************** 认证逻辑 **** end ******************/

        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}

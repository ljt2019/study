package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Create by tiger on 2019/1/15
 * 密码加密
 */
public class EncryptPassword {

    /**
     * 对密码进行加密
     *
     * @param user
     * @param algorithmName  加密算法名称
     * @param hashIterations 哈希迭代法次数
     */
    public static void encryptPassword(User user, String algorithmName, final int hashIterations) {
        //盐使用随机数
        user.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
        //创建加密密码
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }
}

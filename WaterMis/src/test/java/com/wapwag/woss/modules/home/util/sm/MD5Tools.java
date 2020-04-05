package com.wapwag.woss.modules.home.util.sm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5技术加密解密
 */
public class MD5Tools {
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    // 测试主函数
    public static void main(String args[]) throws NoSuchAlgorithmException {
        String s = "ee6334f38ab10bdf5492c719c6bbda98"+"app_key10001request_md5C747491A764FD00CB2DD2D6017945263sign_methodhmactimestamp20161116143638"+"ee6334f38ab10bdf5492c719c6bbda98";
        System.out.println("原始：" + s);

        String result = md5(s);
        System.out.println("加密1后："+result);

        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(s));
        System.out.println("解密的：" + convertMD5(convertMD5(s)));

        //CF58222E6FA51E5170AA50C024FDF235

    }


    /**
     * md5加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String data) throws NoSuchAlgorithmException {
        //信息摘要器                                算法名称
        MessageDigest md = MessageDigest.getInstance("MD5");
        //把字符串转为字节数组
        byte[] b = data.getBytes();
        //使用指定的字节来更新我们的摘要
        md.update(b);
        //获取密文  （完成摘要计算）
        byte[] b2 = md.digest();
        //获取计算的长度
        int len = b2.length;
        //16进制字符串
        String str = "0123456789abcdef";
        //把字符串转为字符串数组
        char[] ch = str.toCharArray();

        //创建一个32位长度的字节数组
        char[] chs = new char[len*2];
        //循环16次
        for(int i=0,k=0;i<len;i++) {
            byte b3 = b2[i];//获取摘要计算后的字节数组中的每个字节
            // >>>:无符号右移
            // &:按位与
            //0xf:0-15的数字
            chs[k++] = ch[b3 >>> 4 & 0xf];
            chs[k++] = ch[b3 & 0xf];
        }

        //字符数组转为字符串
        return new String(chs);
    }

}


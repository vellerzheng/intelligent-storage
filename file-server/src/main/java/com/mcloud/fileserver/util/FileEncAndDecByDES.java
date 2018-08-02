package com.mcloud.fileserver.util;


import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class FileEncAndDecByDES {
    private Key key;

    FileEncAndDecByDES(){ }

    public FileEncAndDecByDES(String str) {
        getKey(str);//生成密匙
    }

    /**
     * 根据参数生成KEY
     */
    public void getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(strKey.getBytes());

            _generator.init(56,secureRandom);
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 文件file进行加密并保存目标文件destFile中
     *
     * @param file   要加密的文件 如c:/test/srcFile.txt
     *   加密后存放的文件名 如c:/加密后文件.txt
     */
    public String encrypt(File file)  {
        File tempFile = new File(file.getParent() + "/" + "encrypt");
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }

        String realFilePathName = file.getParent() + "/" + "encrypt"+ "/" + file.getName();
        try {
            Cipher cipher = Cipher.getInstance("DES");
            // cipher.init(Cipher.ENCRYPT_MODE, getKey());
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            InputStream  is = new FileInputStream(file);
            OutputStream out = new FileOutputStream(realFilePathName);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
            e.printStackTrace();
        }
        return realFilePathName;
    }


    public String encryptLimit4M(File file, String md5FileName)  {
        File tempFile = new File(file.getParent() + "/" + "encrypt");
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }

        String realFilePathName = file.getParent() + "/" + "encrypt"+ "/" + md5FileName;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            // cipher.init(Cipher.ENCRYPT_MODE, getKey());
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            InputStream  is = new FileInputStream(file);
            OutputStream out = new FileOutputStream(realFilePathName);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
            e.printStackTrace();
        }
        return realFilePathName;
    }
    /**
     * 文件采用DES算法解密文件
     *
     * @param file 已加密的文件 如c:/加密后文件.txt
     *         * @param destFile
     *         解密后存放的文件名 如c:/ test/解密后文件.txt
     */
    public String decrypt(File file) {
        File tempFile = new File(file.getParent() + "/decrypt");
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }

        String realFilePathName = file.getParent() +"/decrypt/" + file.getName();
        try{
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            InputStream is = new FileInputStream(file);
            OutputStream out = new FileOutputStream(realFilePathName);
            CipherOutputStream cos = new CipherOutputStream(out, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = is.read(buffer)) >= 0) {
                System.out.println();
                cos.write(buffer, 0, r);
            }
            cos.close();
            out.close();
            is.close();
        }catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException e){

        }

        return realFilePathName;
    }

    public static void main(String[] args){
        FileEncAndDecByDES  fs = new FileEncAndDecByDES("kraft");
/*        String src = "D:\\Test\\split\\分布式网络架构.pdf";
        String resFile = fs.encrypt(new File(src));*/

        String desPath = "D:\\Test\\split\\encrypt\\0371ac7ffe20d145685b00190fd6c73d.pdf";
        fs.decrypt(new File(desPath));
        System.out.println("finished!");
    }
}

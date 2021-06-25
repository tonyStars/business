package com.club.business.config;

import com.club.business.util.PropertiesUtil;

/**
 * 图片上传配置文件
 *
 * @author Tom
 * @date 2019-12-12
 */
public class ImageConfig {

    /**
     * 上传图片类型标识
     */
    public static String IMG_UPLOAD_USER = PropertiesUtil.getValue("config/image.properties","img_upload_user");
    

    /**
     * 上传图片的文件路径
     */
    public static String IMG_REAL_PATH_USER = PropertiesUtil.getValue("config/image.properties","img_real_path_user");

    /**
     * 上传图片文件的访问地址
     */
    public static String IMG_MAPPER_PATH_USER = PropertiesUtil.getValue("config/image.properties","img_mapper_path_user");

}

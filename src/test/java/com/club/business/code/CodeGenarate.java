package com.club.business.code;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * mybatis-plus数据库表自动生成类
 * @author Tom
 * @date 2019-11-19
 * 
 */
public class CodeGenarate {
	
	public static void main(String[] args) {
        /**业务模块相应的表*/
        String [] tables ={
                "business_super_lotto","business_bichromatic_sphere"
        };
        /**sys 关键字对应的是一个业务模块*/
        genTable("goodluck",tables);
    }

    public static void genTable(String modleName ,String[] tables){
        AutoGenerator mpg = new AutoGenerator();
        /**选择 freemarker引擎,默认 Veloctiy*/
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        /**全局配置*/
        GlobalConfig gc = new GlobalConfig();
        mpg.setGlobalConfig(gc);
        /**生成文件目录(默 D盘根目录)*/
        gc.setOutputDir("D:\\autocode");
        /**是否覆盖已有文件*/
        gc.setFileOverride(true);
        /**开启 ActiveRecord模式*/
        gc.setActiveRecord(false);
        /**是否在xml中添加二级缓存配置*/
        gc.setEnableCache(false);
        /**生成XML BaseResultMap*/
        gc.setBaseResultMap(true);
        /**生成 XML Base_Column_List*/
        gc.setBaseColumnList(true);
        /**设置java文件@author*/
        gc.setAuthor("");
        /**自定义文件命名，注意 %s会自动填充表实体属性！*/
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        /**是否打开输出目录*/
        gc.setOpen(false);
        /**数据源配置*/
        DataSourceConfig dsc = new DataSourceConfig();
        mpg.setDataSource(dsc);
        /**数据库类型*/
        dsc.setDbType(DbType.MYSQL);
        /**类型转换*/
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("business");
        dsc.setPassword("123456");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/business?characterEncoding=utf-8&allowMultiQueries=true&noAccessToProcedureBodies=true&serverTimezone=GMT%2B8");
        /**策略配置*/
        StrategyConfig strategy = new StrategyConfig();
        mpg.setStrategy(strategy);
        /**是否大写命名*/
        strategy.setCapitalMode(false);
        /**
         * 生成的文件名不带表前缀
         */
        strategy.setTablePrefix(new String[] {});
        /**表名生成策略(下划线转驼峰命名)*/
        strategy.setNaming(NamingStrategy.underline_to_camel);
        /**需要包含的表名(不设置生成所有的)*/
        strategy.setInclude(tables);
        /**controller 父类*/
        strategy.setSuperControllerClass("com.club.business.common.controller.BaseController");
        /**restful 风格*/
        strategy.setRestControllerStyle(false);
        /**包配置*/
        PackageConfig pc = new PackageConfig();
        mpg.setPackageInfo(pc);
        pc.setParent("com.club.business");
        pc.setModuleName(modleName);
        pc.setEntity("vo");
        pc.setService("service");
        pc.setServiceImpl("service");
        pc.setMapper("dao");
        pc.setController("controller");
        pc.setXml("dao");
        /**
         * 自定义模板配置(可无，默认读取资源文件下templates中的模板文件
         * 可以copy源码mybatis-plus-generate-*.jar下的templates目录中的文件进行修改)
         */
        TemplateConfig config = new TemplateConfig();
        config.setEntity(ConstVal.TEMPLATE_ENTITY_JAVA);
        config.setMapper(ConstVal.TEMPLATE_MAPPER);
        config.setXml(ConstVal.TEMPLATE_XML);
        config.setServiceImpl(ConstVal.TEMPLATE_SERVICE_IMPL);
        /**不生成controller和service的接口*/
        config.setController(null);
        /**config.setController(ConstVal.TEMPLATE_CONTROLLER);*/
        config.setService(null);
        mpg.setTemplate(config);
        /**执行生成*/
        mpg.execute();
    }
	
}

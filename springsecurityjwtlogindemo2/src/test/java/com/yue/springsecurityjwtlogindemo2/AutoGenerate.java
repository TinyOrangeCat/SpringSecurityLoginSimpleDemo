/*
package com.yue.springsecurityjwtlogindemo2;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

*/
/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 14:33
 *//*

public class AutoGenerate {

    @Test
    public void testCodeGenerator(){
        //1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();

        String projectJavaPath = System.getProperty("user.dir")+"\\src\\main\\java";
        System.out.println(projectJavaPath);
        globalConfig.setFileOverride(true)
                .setAuthor("YueLi/xiroiyuki@hotmail.com")
                .setActiveRecord(false)
                .setIdType(IdType.AUTO)
                .setServiceName("I%sService")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setSwagger2(true)
                .setOutputDir(projectJavaPath);
        //2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/test_database")
                .setUsername("xiroi")
                .setPassword("123456");

        //3.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("tab_")
                .setInclude("tab_manager")
                .setInclude("tab_manager_info")
                .setInclude("tab_role")
                .setInclude("tab_user")
                .setInclude("tab_user_info");

        //4.包名策略
        String basePackageName = "com.yue.springsecurityjwtlogindemo2";
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(basePackageName)
                .setMapper("mappers")
                .setService("services")
                .setServiceImpl("services.impls")
                .setController("controllers")
                .setEntity("beans")
                .setXml("mappers");

        //代码生成器
        //5.整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        autoGenerator.execute();

    }

}
*/

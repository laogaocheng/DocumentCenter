package com.hwagain.documentcenter;

import com.hwagain.framework.mybatisplus.generator.AutoGenerator;
import com.hwagain.framework.mybatisplus.generator.config.DataSourceConfig;
import com.hwagain.framework.mybatisplus.generator.config.GlobalConfig;
import com.hwagain.framework.mybatisplus.generator.config.PackageConfig;
import com.hwagain.framework.mybatisplus.generator.config.StrategyConfig;
import com.hwagain.framework.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.hwagain.framework.mybatisplus.generator.config.rules.DbColumnType;
import com.hwagain.framework.mybatisplus.generator.config.rules.DbType;
import com.hwagain.framework.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 
 * 自动生成映射工具类
 * 
 * @author hubin
 *
 */
public class AutoGeneratorHelper {

	/**
	 * 
	 * 测试 run 执行
	 * 
	 * <p>
	 * 配置方法查看 {@link ConfigGenerator}
	 * </p>
	 * 
	 */
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("E:\\Output");
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor("laogaocheng");

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		// gc.setMapperName("%sDao");
		// gc.setXmlName("%sDao");
		// gc.setServiceName("MP%sService");
		// gc.setServiceImplName("%sServiceDiy");
		// gc.setControllerName("%sAction");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("sit");
		dsc.setPassword("abc-123");
		dsc.setUrl("jdbc:mysql://192.168.68.204:3306/document_center?useSSL=true&createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setTablePrefix("bmd_");// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		// strategy.setInclude(new String[] { "user" }); // 需要生成的表
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 字段名生成策略
		strategy.setFieldNaming(NamingStrategy.underline_to_camel);
		// 自定义实体父类
		// strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuliderModel(true);

		//只生成指定的表

		strategy.setInclude(new String[] { 
				"app","permission"
				});

//		 strategy.setExclude(new String[] { 
//		 "info_user_group" });
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.hwagain");
		pc.setModuleName("documentcenter");
		mpg.setPackageInfo(pc);

		// // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		// InjectionConfig cfg = new InjectionConfig() {
		// @Override
		// public void initMap() {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("abc", this.getConfig().getGlobalConfig().getAuthor() +
		// "-mp");
		// this.setMap(map);
		// }
		// };
		// // 自定义 xxList.jsp 生成
		// List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		// focList.add(new FileOutConfig("/template/list.jsp.vm") {
		// @Override
		// public String outputFile(TableInfo tableInfo) {
		// // 自定义输入文件名称
		// return "D://my_" + tableInfo.getEntityName() + ".jsp";
		// }
		// });
		// cfg.setFileOutConfigList(focList);
		// mpg.setCfg(cfg);

		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
		// 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		// TemplateConfig tc = new TemplateConfig();
		// tc.setController("...");
		// tc.setEntity("...");
		// tc.setMapper("...");
		// tc.setXml("...");
		// tc.setService("...");
		// tc.setServiceImpl("...");
		// mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

		// 打印注入设置
		// System.err.println(mpg.getCfg().getMap().get("abc"));

	}

	
	
	public static void execute( String outputDir , String authors , String dbUser , String dbPassword 
			, String dbUrl , String parentPackage , String moduleName , String... tables){
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDir);
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor(authors);

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		// gc.setMapperName("%sDao");
		// gc.setXmlName("%sDao");
		// gc.setServiceName("MP%sService");
		// gc.setServiceImplName("%sServiceDiy");
		// gc.setControllerName("%sAction");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername(dbUser);
		dsc.setPassword(dbPassword);
		dsc.setUrl(dbUrl);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setTablePrefix("bmd_");// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		// strategy.setInclude(new String[] { "user" }); // 需要生成的表
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 字段名生成策略
		strategy.setFieldNaming(NamingStrategy.underline_to_camel);
		// 自定义实体父类
		// strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuliderModel(true);

		//只生成指定的表
		strategy.setInclude(tables);
		// strategy.setExclude(new String[] { "meeting_appraise",
		// "meeting_appraise_dispartake",
		// "meeting_appraise_partake", "meeting_appraise_report",
		// "meeting_invented", "meeting_main",
		// "meeting_main_attend_person", "meeting_main_coordinat",
		// "meeting_main_copy_person",
		// "meeting_main_excute", "meeting_main_feedback",
		// "meeting_main_other_reader", "meeting_main_post",
		// "meeting_main_property", "meeting_main_transmit_his",
		// "meeting_main_use_source",
		// "meeting_sum_other_reader", "meeting_summary",
		// "meeting_summary_copy", "meeting_summary_tran_his",
		// "meeting_template", "meeting_tmpl_attend_person",
		// "meeting_tmpl_copy_person",
		// "meeting_tmpl_other_reader", "meeting_tmpl_post",
		// "meeting_tmpl_use_source" });
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(parentPackage);
		pc.setModuleName(moduleName);
		mpg.setPackageInfo(pc);

		// // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		// InjectionConfig cfg = new InjectionConfig() {
		// @Override
		// public void initMap() {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("abc", this.getConfig().getGlobalConfig().getAuthor() +
		// "-mp");
		// this.setMap(map);
		// }
		// };
		// // 自定义 xxList.jsp 生成
		// List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		// focList.add(new FileOutConfig("/template/list.jsp.vm") {
		// @Override
		// public String outputFile(TableInfo tableInfo) {
		// // 自定义输入文件名称
		// return "D://my_" + tableInfo.getEntityName() + ".jsp";
		// }
		// });
		// cfg.setFileOutConfigList(focList);
		// mpg.setCfg(cfg);

		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
		// 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		// TemplateConfig tc = new TemplateConfig();
		// tc.setController("...");
		// tc.setEntity("...");
		// tc.setMapper("...");
		// tc.setXml("...");
		// tc.setService("...");
		// tc.setServiceImpl("...");
		// mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

		// 打印注入设置
		// System.err.println(mpg.getCfg().getMap().get("abc"));
	}
}

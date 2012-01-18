package com.utils.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CodeFactory {
	private static final String TEMPLATEPATH = "src/main/java/com/utils/generate/template";
	private static final String CODEPATH = "src/main/java/san/";
	private static final String TESTCODEPATH = "src/test/java/san/";
	private static final String SYSTEM_ENCODING = "UTF-8";
	private ICallBack callBack;

	/**
	 * 生成Java类.
	 */
	public enum CodeType {
		dao("Dao"), service("Service"), action("Action"), testDao("Dao"), testService(
				"Service"),controller("Controller"), ;
		private String type;

		CodeType(String type) {
			this.type = type;
		}

		public String getValue() {
			return type;
		}
	}

	public Configuration getConfiguration() throws IOException {
		Configuration cfg = new Configuration();
		String path = getTemplatePath();
		File templateDirFile = new File(path);
		cfg.setDirectoryForTemplateLoading(templateDirFile);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding(SYSTEM_ENCODING);
		return cfg;
	}

	/*
	 * 生成Java文件
	 * 
	 * @Params templateFileName是模板文件名
	 * 
	 * @Params type是生成类的类型，只能是现有枚举类中类型
	 * 
	 * @Params data是数据
	 */
	@SuppressWarnings("unchecked")
	public void generateFile(String templateFileName, String type, Map data) {
		try {
			String entityPackage = data.get("entityPackage").toString();
			String entityName = data.get("entityName").toString();
			String fileNamePath = getCodePath(type, entityPackage, entityName);
			String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
			Template template = getConfiguration()
					.getTemplate(templateFileName);
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + "/"));
			Writer out = new OutputStreamWriter(new FileOutputStream(
					fileNamePath), SYSTEM_ENCODING);
			template.process(data, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 得到项目代码的绝对路径，到src
	 */
	public String getProjectPath() {
		// String path = getClass().getProtectionDomain().getCodeSource()
		// .getLocation().getPath();
		// path = StringUtils.substringBefore(path, "target");
		// path = path.substring(1);
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

	/*
	 * 得到模板文件的绝对根目录
	 */
	public String getTemplatePath() {
		String path = getProjectPath() + TEMPLATEPATH;
		return path;
	}

	/*
	 * 得到生成代码文件的绝对路径
	 */
	public String getCodePath(String type, String entityPackage,
			String entityName) {
		String path = getProjectPath();
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotBlank(type)) {
			String codeType = Enum.valueOf(CodeType.class, type).getValue();
			str.append(path);
			if (StringUtils.startsWithIgnoreCase(type, "test")) {
				str.append(TESTCODEPATH);
				str.append(StringUtils.lowerCase(codeType));
				str.append("/");
				str.append(StringUtils.lowerCase(entityPackage));
				str.append("/Test");
				str.append(StringUtils.capitalize(entityName));
				str.append(codeType);
				str.append(".java");
			} else {
				str.append(CODEPATH);
				if ("Action".equalsIgnoreCase(codeType)||"Controller".equalsIgnoreCase(codeType)) {
					str.append(StringUtils.lowerCase("web"));
				} else {
					str.append(StringUtils.lowerCase(codeType));
				}
				str.append("/");
				str.append(StringUtils.lowerCase(entityPackage));
				str.append("/");
				str.append(StringUtils.capitalize(entityName));
				str.append(codeType);
				str.append(".java");
			}
		} else {
			throw new IllegalArgumentException("type is null");
		}
		return str.toString();
	}

	public void invoke(String templateFileName, String type) {
		Map<String, String> data = new HashMap<String, String>();
		data = callBack.execute();
		generateFile(templateFileName, type, data);
	}

	public ICallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}

}

package com.utils.generate;

import java.util.HashMap;
import java.util.Map;

import com.utils.NonceUtils;

/**
 * 自动生成Dao,Service,Action及Dao和Service测试类
 */
public class CodeGenerate implements ICallBack {

	/**
	 * entityPackage：Entity的上级包名 entityName：Entity的类名（注意第一个字母大写）
	 * serialVersionUID：随机生成的序列码
	 */
	public Map<String, String> execute() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("entityPackage", "demo");
		data.put("entityName", "Demo");
		long serialVersionUID = NonceUtils.randomLong()
				+ NonceUtils.currentMills();
		data.put("serialVersionUID", String.valueOf(serialVersionUID));
		return data;
	}

	public void generateToFile() {
		CodeFactory codeFactory = new CodeFactory();
		codeFactory.setCallBack(new CodeGenerate());
		codeFactory.invoke("daoTemplate.ftl", "dao");
		codeFactory.invoke("serviceTemplate.ftl", "service");
		codeFactory.invoke("controllerTemplate.ftl", "controller");
		codeFactory.invoke("testDaoTemplate.ftl", "testDao");
		codeFactory.invoke("testServiceTemplate.ftl", "testService");
	}

	/*
	 * 参数为Entity类名(注意第一个字符大写)
	 */
	public static void main(String[] args) {
		new CodeGenerate().generateToFile();
	}

}

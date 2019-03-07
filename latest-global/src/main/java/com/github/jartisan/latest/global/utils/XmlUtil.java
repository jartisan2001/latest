package com.github.jartisan.latest.global.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * xml 工具类
 * @author jalen
 *
 */
public class XmlUtil {
	
	private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
	/**
	 * @param message
	 * @return
	 */
	public static Map<String, String> xml2Map(String message) {
		Map<String, String> data = new LinkedHashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(message);
		} catch (DocumentException e) {
			logger.error("xml util parse : ",e);
			return data;
		}

		Element root = doc.getRootElement();
		String path = "/" + root.getName();
		element2Map(root, data, path);
		return data;
	}

	private static void element2Map(Element ele, Map<String, String> data, String path) {
		if (ele == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		List<Element> childrens = ele.elements();
		if (childrens != null && childrens.size() > 0) {
			Element pre = null;
			Element cur = null;
			Element next = null;
			int nodeIndex = 1;
			int length = childrens.size();
			for (int i = 0; i < length; i++) {
				cur = childrens.get(i);
				StringBuilder sb = new StringBuilder(path + "/" + cur.getName());
				if (pre == null) {
					next = childrens.get(i + 1);
					if (next.getName().equals(cur.getName())) {
						sb.append("[" + nodeIndex + "]");
						nodeIndex++;
					}
				} else {
					if (pre.getName().equals(cur.getName())) {
						sb.append("[" + nodeIndex + "]");
						nodeIndex++;
					} else {
						nodeIndex = 1;
					}
				}
				element2Map(cur, data, sb.toString());
				pre = cur;
			}
		} else {
			data.put(path, ele.getText());

		}

	}

	/**
	 * 把map转换成xml
	 * @param map
	 * @return
	 */
	public static String map2Xml(Map<String, String> map) {
		String xml = "";
		// 根据map用dom4j创建Document
		Document doc = DocumentHelper.createDocument();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();

			Element ele = DocumentHelper.makeElement(doc, key);
			ele.setText(value);
		}

		// 格式化
		OutputFormat format = OutputFormat.createPrettyPrint();
		// format.setNewLineAfterDeclaration(true);
		format.setExpandEmptyElements(false);
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter(out, format);
		try {
			writer.write(doc);
		} catch (IOException e) {
			logger.error("转换失败",e);
		}
		xml = out.toString();
		xml = xml.replaceAll("\\[\\d*\\]", "");
		return xml;
	}

	/****
	public static void main(String[] args) {
		File file = new File("E:/maven/apache-maven-3.6.0/conf/settings1.xml");
		try {
			String message = FileUtils.readFileToString(file, "utf-8");
			Map<String, String> map = xml2Map(message);
			logger.info(map.toString());
			logger.info(map2Xml(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*****/
}

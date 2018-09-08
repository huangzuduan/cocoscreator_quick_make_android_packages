package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author hongliang.dinghl Dom4j 生成XML文档与解析XML文档
 */
public final class XMLManger {
	/**
	 * 把XML配置转成HashMap
	 * 
	 * @param path
	 * @return HashMap
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static HashMap<String, String> readXml(String path)
			throws ParserConfigurationException, IOException, SAXException {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		StringReader read = new StringReader(getXML(path));
		InputSource is = new InputSource(read);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		NodeList list = doc.getElementsByTagName("option");
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			dataMap.put(node.getAttributes().getNamedItem("name")
					.getNodeValue(), node.getAttributes().getNamedItem("value")
					.getNodeValue());
		}
		return dataMap;
	}

	/**
	 * 获取xml文件内容
	 * 
	 * @author ruan 2012-5-12
	 * @param path
	 *            xml文件路径
	 * @return
	 * @throws IOException
	 */
	public static String getXML(String path) throws IOException {
		StringBuilder xml = new StringBuilder();
		BufferedReader input = new BufferedReader(new FileReader(path));
		String text = null;
		while ((text = input.readLine()) != null) {
			xml.append(text + "\n");
		}
		return xml.toString();
	}

	/**
	 * 修改xml文档
	 * 
	 * @author ruan 2012-5-12
	 * @param path
	 *            文件路径
	 * @param modifyContent
	 *            要修改的内容
	 * @param backup
	 *            是否需要备份旧文件
	 * @throws DocumentException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void editXML(String path,
			HashMap<String, String> modifyContent, boolean backup)
			throws DocumentException, IOException,
			ParserConfigurationException, SAXException, TransformerException {
		String xmlContent = getXML(path);
		StringReader read = new StringReader(xmlContent);
		InputSource is = new InputSource(read);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		NodeList list = doc.getElementsByTagName("option");

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			NamedNodeMap attrList = node.getAttributes();
			String key = attrList.getNamedItem("name").getNodeValue();
			if (modifyContent.containsKey(key)) {
				attrList.getNamedItem("value").setNodeValue(
						modifyContent.get(key));
			}
		}

		// 备份原来的xml文件
		if (backup) {
		//	FileSystem.write(path + ".bak", xmlContent, false);
		}

		// 保存新的xml文件
		doc2XmlFile(doc, path);
	}

	/**
	 * 修改xml文档
	 * 
	 * @author ruan 2012-5-14
	 * @param path
	 *            文件路径
	 * @param modifyContent
	 *            要修改的内容
	 * @throws DocumentException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void editXML(String path,
			HashMap<String, String> modifyContent) throws DocumentException,
			IOException, ParserConfigurationException, SAXException,
			TransformerException {
		editXML(path, modifyContent, false);
	}

	/**
	 * 把文档保存为xml文件形式
	 * 
	 * @author ruan 2012-5-12
	 * @param document
	 * @param filename
	 * @throws TransformerException
	 */
	private static void doc2XmlFile(Document document, String filename)
			throws TransformerException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(filename));
		transformer.transform(source, result);
	}
}
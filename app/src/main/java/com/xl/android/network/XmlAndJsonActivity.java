package com.xl.android.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xl.android.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 安卓解析xml,json格式数据
 */

public class XmlAndJsonActivity extends AppCompatActivity {

    private TextView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_json_layout);
        setTitle("xml/json数据解析");
        show = (TextView) findViewById(R.id.show_xml_json);
        init();
    }

    private void init() {
        //SAX
        findViewById(R.id.xml_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //获取文件资源建立输入流对象
                    InputStream is = getResources().getAssets().open("person1.xml");
                    String sax = readxmlForSAX(is);
                    show.setText(sax);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //DOM
        findViewById(R.id.xml_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ArrayList<String> json = readXMLByDom();
                    StringBuilder builder = new StringBuilder();
                    for (String str:json) {
                        builder.append(str);
                    }
                    show.setText(builder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //PULL
        findViewById(R.id.xml_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream is = getResources().getAssets().open("person1.xml");
                    String pull = readXMLByPull(is);
                    show.setText(pull);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //json
        findViewById(R.id.json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream is = getResources().getAssets().open("person.json");
                    ArrayList<String> json = readJson(is);
                    StringBuilder builder = new StringBuilder();
                    for (String str:json) {
                        builder.append(str);
                    }
                    show.setText(builder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Android中SAX解析XML
     * @return
     * @throws Exception
     */
    private String readxmlForSAX(InputStream is) throws Exception {

        MyDefaultHandler handler = new MyDefaultHandler();
        //得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //创建SAX解析器
        SAXParser parser = factory.newSAXParser();
        //将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
        parser.parse(is, handler);
        is.close();
        return handler.readXMLBySAX();
    }



    /**
     * Android中Json数据解析
     */

    public ArrayList<String> readJson(InputStream is) throws Exception {
        InputStreamReader isr = new InputStreamReader(is, "utf8");
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String string;
        while ((string = reader.readLine()) != null) {
            builder.append(string);
        }
        String str = builder.toString();
        JSONObject object = new JSONObject(str);
        JSONArray array = object.getJSONArray("json");
        ArrayList<String> mlist = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object1 = (JSONObject) array.get(i);
            mlist.add(object1.getString("id")+"-"+object1.getString("name")+"-"+object1.getString("age")+"\n");
        }
        is.close();
        isr.close();
        reader.close();
        return mlist;
    }


    /**
     * Android中DOM解析XML:安卓程序中不使用该方法,java中可以使用
     */

    public ArrayList<String> readXMLByDom() throws Exception {
        ArrayList<String> mlist = new ArrayList<>();
        //获取DocumentBuilderFactory工厂实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //获取DocumentBuilder实例
        DocumentBuilder builder = factory.newDocumentBuilder();
        //获取Document实例,参数为：xml文件路径
        Document document = builder.parse(getResources().getAssets().open("person2.xml"));
        //获取Elment对象
        Element element = document.getDocumentElement();
        //获取子节点集
        NodeList list = element.getElementsByTagName("person");
        //遍历子节点集中的所有子节点
        for (int i = 0; i < list.getLength(); i++) {
            Element person = (Element) list.item(i);
            mlist.add((person.getAttribute("id")));
            //获取子节点中的文本元素
            NodeList clist = person.getChildNodes();
            for (int j = 0; j < clist.getLength(); j++) {
                Node c = clist.item(j);
                if (c instanceof Element) {
                    System.out.println(c.getNodeName() + ":" + c.getTextContent());
                    mlist.add(c.getFirstChild().getNodeValue());
                }
            }
        }
        return mlist;
    }


    /**
     * Android中PULL解析XML,安卓系统自带
     */
    public String readXMLByPull(InputStream is) throws Exception {
        StringBuilder builder = new StringBuilder();
        // 创建一个xml解析的工厂
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // 获得xml解析类的引用
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(is, "UTF-8");
        // 获得事件的类型
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    builder.append("***");
                    break;
                case XmlPullParser.START_TAG:
                    if ("person".equals(parser.getName())) {
                       // 取出属性值
                        builder.append(parser.getAttributeValue(0));
                    } else if ("name".equals(parser.getName())) {
                        // 获取该节点的内容
                        builder.append(parser.nextText());
                    } else if ("age".equals(parser.getName())) {
                       // 获取该节点的内容
                        builder.append(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("person".equals(parser.getName())) {
                        builder.append("***\n");
                    }
                    break;
            }
            eventType = parser.next();//循环解析下一个元素
        }
        is.close();
        return builder.toString();
    }

}

/**
 * SAX解析XML工具类
 */
class MyDefaultHandler extends DefaultHandler {

    //当前解析的元素标签
    private String tagName = null;

    private StringBuilder builder;

    public String readXMLBySAX(){
        return  builder.toString();
    }


    /**
     * 当读取到文档开始标志是触发，通常在这里完成一些初始化操作
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
        Log.i("SAX", "读取到文档头,开始解析xml");
    }

    /**
     * 读到一个开始标签时调用,第二个参数为标签名,最后一个参数为属性数组
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("person")) {
            builder.append(attributes.getValue("id"));
            Log.i("SAX", "开始处理person元素~");
        }
        this.tagName = localName;
    }

    /**
     * 读到到内容,第一个参数为字符串内容,后面依次为起始位置与长度
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        //判断当前标签是否有效
        if (this.tagName != null) {
            String data = new String(ch, start, length);
            //读取标签中的内容
            if (this.tagName.equals("name")) {
                builder.append(data);
                Log.i("SAX", "处理name元素内容");
            } else if (this.tagName.equals("age")) {
                builder.append(data);
                Log.i("SAX", "处理age元素内容");
            }
        }
    }

    /**
     * 处理元素结束时触发,这里将对象添加到结合中
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("person")) {
            builder.append("\n");
            Log.i("SAX", "处理person元素结束~");
        }
        this.tagName = null;
    }

    /**
     * 读取到文档结尾时触发，
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.i("SAX", "读取到文档尾,xml解析结束");
    }
}

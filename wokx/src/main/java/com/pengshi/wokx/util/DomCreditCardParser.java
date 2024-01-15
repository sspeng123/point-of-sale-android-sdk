package com.pengshi.wokx.util;

import sdk.android.baidulibrary.com.googlelibrary.bean.GetCreditCardResponse;
import com.pengshi.android.sdk.abcpos.interfacePackage.CreditCardParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by karlp on 4/20/2017.
 */

public class DomCreditCardParser implements CreditCardParser {

    @Override
    public List<GetCreditCardResponse> parse(InputStream is) throws Exception {
        List<GetCreditCardResponse> list = new ArrayList<GetCreditCardResponse>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例
        DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例
        Document doc = builder.parse(is);   //解析输入流 得到Document实例
        Element rootElement = doc.getDocumentElement();
        NodeList items = rootElement.getElementsByTagName("response");
        for (int i = 0; i < items.getLength(); i++) {
            GetCreditCardResponse res = new GetCreditCardResponse();
            Node item = items.item(i);
            NodeList properties = item.getChildNodes();
            for (int j = 0; j < properties.getLength(); j++) {
                Node property = properties.item(j);
                String nodeName = property.getNodeName();
                if (nodeName.equals("RefId")) {
                    res.setRefId(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("RegisterId")) {
                    res.setRegisterId(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("InvNum")) {
                    res.setInvNum(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("ResultCode")) {
                    res.setResultCode(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("RespMSG")) {
                    res.setRespMSG(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("Message")) {
                    res.setMessage(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("AuthCode")) {
                    res.setAuthCode(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("PNRef")) {
                    res.setpNRef(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("PaymentType")) {
                    res.setPaymentType(property.getFirstChild().getNodeValue().toString());
                } else if (nodeName.equals("ExtData")) {
                    res.setExtData(property.getFirstChild().getNodeValue().toString());
                    String extData = res.getExtData();
                    Integer startCardType = extData.indexOf("CardType=");
                    Integer endCardType = extData.indexOf(",",startCardType + 9 );
                    String strCardType = extData.substring(startCardType+ 9,endCardType);
                    res.setCardType(strCardType);

                    Integer startAcntLast4 = extData.indexOf("AcntLast4=");
                    Integer endAcntLast4 = extData.indexOf(",",startAcntLast4+ 10);
                    String strAcntLast4 = extData.substring(startAcntLast4+ 10,endAcntLast4);
                    res.setAcntLast4(strAcntLast4);
                } else if (nodeName.equals("EMVData")) {
                    res.seteMVData(property.getFirstChild().getNodeValue().toString());
                }else if (nodeName.equals("Sign")) {
                    res.setSign(property.getFirstChild().getNodeValue().toString());
                }
            }
            list.add(res);
        }

        return list;
    }
}

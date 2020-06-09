package com.gxuwz.medical.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.domain.diseaseCard.DiseaseCard;
/**
 * 反射工具
 * @author 演示
 *
 */
public class ReflectUtil {

	
	/**
	 * 根据属性名获取属性值
	 * */
    private static  Object getFieldValueByName(String fieldName, Object o) {
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
             
            return null;  
        }  
    } 
    
    /**
     * 获取属性名数组
     * */
    public static String[] getFiledName(Object o){
    	Field[] fields=o.getClass().getDeclaredFields();
       	String[] fieldNames=new String[fields.length];
    	for(int i=0;i<fields.length;i++){
    		fieldNames[i]=fields[i].getName();
    	}
    	return fieldNames;
    }
    
    
    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的String组成的list
     * */
    private static List<String> getFiledsInfo(Object o){
    	Field[] fields=o.getClass().getDeclaredFields();
       	List<String> list = new ArrayList<String>();
    	for(int i=0;i<fields.length;i++){	
    		String type=fields[i].getType().toString();
    		String name= fields[i].getName();
    		Object value=getFieldValueByName(fields[i].getName(), o);
    		StringBuffer buffer=new StringBuffer();
    		String data=value==null?"0":value+"";
    		int length=data.length();
    		buffer.append("[");
    		buffer.append("type:"+type+",");
    		buffer.append("name:"+name+",");
    		buffer.append("value:"+value+",");
    		buffer.append("length:"+length+"]");
    		list.add(buffer.toString());
    	}
    	return list;
    }
    
    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public static Object[] getFiledValues(Object o){
    	String[] fieldNames=getFiledName(o);
    	Object[] value=new Object[fieldNames.length];
    	for(int i=0;i<fieldNames.length;i++){
    		value[i]=getFieldValueByName(fieldNames[i], o);
    	}
    	return value;
    }
    public static void f1(){

    	DiseaseCard entity = new DiseaseCard();
    	String[] fileds=ReflectUtil.getFiledName(entity);
    	int i=0;
    	for(String field:fileds){
    		String firstLetter=StringUtil.upperCase(field.substring(0,1));
    		String otherLetter=field.substring(1, field.length());
    		String Bigfield=StringUtil.upperCase(field);
    	    //System.out.println("model.set" + field.substring(0,1).toUpperCase() + field.substring(1) +"(rs.getString("+'"'+""+field+""+'"'+"));");
    	    //System.out.println("<td><%=model.get" + field.substring(0,1).toUpperCase() + field.substring(1) +"()%></td>");
    		//System.out.println("String " +field+" = req.getParameter("+'"'+""+field+""+'"'+");");
    		System.out.println(" pstmt.setString(index++, this." + field + ");");
    	    //System.out.print("" +field+", ");
    		//System.out.print("" + field + "=?, ");
    		//System.out.print("<%=model.get" + field.substring(0,1).toUpperCase() + field.substring(1) + "()%>  ");
    	    i++;
    	
    	}
    
    }

    public static void main(String[]args){
    	f1();
    
    }

}

package config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
  
public class JsonUtil{   
  
    /**
     * 从一个JSON 对象字符格式中得到一个java对象  
     * @param jsonString  
     * @param pojoCalss  
     * @return  
     */  
    @SuppressWarnings("rawtypes")
	public static Object getObject4JsonString(String jsonString,Class pojoCalss){   
        Object pojo;   
        JSONObject jsonObject = JSONObject.fromObject( jsonString );     
        pojo = JSONObject.toBean(jsonObject,pojoCalss);   
        return pojo;
    }
       
    /** 
     * 从json HASH表达式中获取一个map，改map支持嵌套功能  
     * @param jsonString  
     * @return  
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<String,Object> getMap4Json(String jsonString){   
        JSONObject jsonObject = JSONObject.fromObject( jsonString );     
        Iterator  keyIter = jsonObject.keys();   
        String key;   
        Object value;   
        HashMap<String,Object> valueMap = new HashMap();   
        while( keyIter.hasNext())   
        {   
            key = (String)keyIter.next();   
            value = jsonObject.get(key);   
            valueMap.put(key, value);   
        }   
        return valueMap;   
    }   
       
       
    /**
     * 从json数组中得到相应java数组  
     * @param jsonString  
     * @return  
     */  
    public static Object[] getObjectArray4Json(String jsonString){   
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        return jsonArray.toArray();   
    }   
       
       
    /**
     * 从json对象集合表达式中得到一个java对象列表  
     * @param jsonString  
     * @param pojoClass  
     * @return  
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getList4Json(String jsonString, Class pojoClass){   
           
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        JSONObject jsonObject;   
        Object pojoValue;   
           
        List list = new ArrayList();   
        for ( int i = 0 ; i<jsonArray.size(); i++){   
               
            jsonObject = jsonArray.getJSONObject(i);   
            pojoValue = JSONObject.toBean(jsonObject,pojoClass);   
            list.add(pojoValue);   
               
        }   
        return list;   
  
    }   
       
    /**
     * 从json数组中解析出java字符串数组  
     * @param jsonString  
     * @return  
     */  
    public static String[] getStringArray4Json(String jsonString){   
           
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        String[] stringArray = new String[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            stringArray[i] = jsonArray.getString(i);   
               
        }   
           
        return stringArray;   
    }   
       
    /**
     * 从json数组中解析出javaLong型对象数组  
     * @param jsonString  
     * @return  
     */  
    public static Long[] getLongArray4Json(String jsonString){   
           
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        Long[] longArray = new Long[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            longArray[i] = jsonArray.getLong(i);   
        }   
        return longArray;   
    }   
       
    /**
     * 从json数组中解析出java Integer型对象数组  
     * @param jsonString  
     * @return  
     */  
    public static Integer[] getIntegerArray4Json(String jsonString){   
           
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        Integer[] integerArray = new Integer[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            integerArray[i] = jsonArray.getInt(i);   
               
        }   
        return integerArray;   
    }   
       
    /**
     * 从json数组中解析出java Date 型对象数组，使用本方法必须保证  
     * @param jsonString  
     * @return  
     */  
    public static Date[] getDateArray4Json(String jsonString,String DataFormat){   
           
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        Date[] dateArray = new Date[jsonArray.size()];   
        String dateString;   
        Date date;   
           
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            dateString = jsonArray.getString(i);   
            date = DateTime.str2Date(dateString, DataFormat);   
            dateArray[i] = date;   
               
        }   
        return dateArray;   
    }   
    
       
    /**
     * 从json数组中解析出java Integer型对象数组  
     * @param jsonString  
     * @return  
     */  
    public static Double[] getDoubleArray4Json(String jsonString){   
           
        JSONArray jsonArray = JSONArray.fromObject(jsonString);   
        Double[] doubleArray = new Double[jsonArray.size()];   
        for( int i = 0 ; i<jsonArray.size() ; i++ ){   
            doubleArray[i] = jsonArray.getDouble(i);   
               
        }   
        return doubleArray;   
    }   
       
       
    /**
     * 将java对象转换成json字符串  
     * @param javaObj  
     * @return  
     */  
    public static String getJsonString4JavaPOJO(Object javaObj){   
        JSONObject json;   
        json = JSONObject.fromObject(javaObj);   
        return json.toString();   
    }
    
    /**
     * 将java对象转换成json字符串,并设定日期格式  
     * @param javaObj  
     * @param dataFormat  
     * @return  
     */
    public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat){   
        JSONObject json;   
        JsonConfig jsonConfig = configJson(dataFormat); 
        json = JSONObject.fromObject(javaObj,jsonConfig);
        return json.toString();
    }

    /**
     * @param args  
     */  
	public static void main(String[] args) { 
    	HashMap<String, Object> field = new HashMap<String, Object> ();
		field.put("uid", "1");
		 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
         for (int i = 0; i < 3; i++) {
//                 HashMap<String, Object> map = new HashMap<String, Object>();
//                 map.put("itemImage", i);
//                 map.put("itemText", i*10);
                 
                 Map<String, Object> map = new HashMap<String, Object>(); 
                 map.put("name", "Michael"); 
                 map.put("baby", new String[] { "Lucy", "Lily" }); 
                 map.put("age", 30); 
                 data.add((HashMap<String, Object>) map);
         }
         Map<String, Object> map = new HashMap<String, Object>(); 
         map.put("name", "Michael"); 
         map.put("baby", new String[] { "Lucy", "Lily" }); 
         map.put("age", 30); 
         
         //活动奖励
//         Map<String, Integer> awardObj = new HashMap<String, Integer>(); 
//         awardObj.put("1", 1);//道具ID，数量
//         awardObj.put("3", 2);
//         String jsonString = getJsonString4JavaPOJO(awardObj);
//         Map<String, Integer> newMap =getMap4Json(jsonString);
//
//         Map<String, String> misstionAwardObj = new HashMap<String, String>(); 
//         misstionAwardObj.put("EXP", "200");//道具ID，数量
//         misstionAwardObj.put("T", "10");
//         misstionAwardObj.put("P", "2");
//         misstionAwardObj.put("ITEM", "101001,101002");
//         String awardStr = getJsonString4JavaPOJO(misstionAwardObj);
//         System.out.println(awardStr);
         
//    	List list = new ArrayList();
//    	ItemClass jb1 = new ItemClass();
//    	jb1.setItemId(1);
//    	jb1.setItemName("青龙");
//    	ItemClass jb2 = new ItemClass();
//    	jb1.setItemId(2);
//    	jb1.setItemName("借用");
//    	list.add(jb1);
//    	list.add(jb2);
//    	JSONArray ja = JSONArray.fromObject(list);
//    	System.out.println(ja);
//      List abc =getList4Json(ja.toString(),ItemClass.class);
//      for(int i=0;i <list.size();i++){
//    	  Object personnelId=list.get(i);
//      } 
//      String ccc ="ccc";
//      System.out.println(abc);
    	
      
    }
       
    /** 
     * JSON 时间解析器具  
     * @param datePattern  
     * @return  
     */  
    public static JsonConfig configJson(String datePattern) {      
            JsonConfig jsonConfig = new JsonConfig();      
            jsonConfig.setExcludes(new String[]{""});      
            jsonConfig.setIgnoreDefaultExcludes(false);      
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);      
            jsonConfig.registerJsonValueProcessor(Date.class,      
                new DateJsonValueProcessor(datePattern));      
            return jsonConfig;      
    }
       
    /**
     * 
     * @param excludes
     * @param datePattern
     * @return  
     */
    public static JsonConfig configJson(String[] excludes,String datePattern) {      
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setExcludes(excludes);
            jsonConfig.setIgnoreDefaultExcludes(false);
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));      
            return jsonConfig;
    }
    
    

    
}  
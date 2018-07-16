package com.brian.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTest {
	
	public static void main01(String[] arags){
		
		//JsonTest js = new JsonTest();
		//js.testBeadToJSON();
	}
	//Array to JSON
	public void testArrayToJSON(){
        boolean[] boolArray = new boolean[]{true,false,true};  
        JSONArray jsonArray = JSONArray.fromObject( boolArray );  
        System.out.println( jsonArray );  
        // prints [true,false,true]  
    }
	//LIST to JSON
    public void testListToJSON(){
        List list = new ArrayList();  
        list.add( "first" );  
        list.add( "second" );  
        JSONArray jsonArray = JSONArray.fromObject( list );  
        System.out.println( jsonArray );  
        // prints ["first","second"]  
    }
    public void testJsonStrToJSON(){
        JSONArray jsonArray = JSONArray.fromObject( "['json','is','easy']" );  
        System.out.println( jsonArray );  
        // prints ["json","is","easy"]  
    }
    public void testMapToJSON(){
        Map map = new HashMap();  
        map.put( "name", "json" );  
        map.put( "bool", Boolean.TRUE );  
        map.put( "int", new Integer(1) );  
        map.put( "arr", new String[]{"a","b"} );  
        map.put( "func", "function(i){ return this.arr[i]; }" );  
          
        JSONObject jsonObject = JSONObject.fromObject( map );  
        System.out.println( jsonObject );  
    }
    
    public void testBeadToJSON(){
    	
        MyBean bean = new MyBean();
        bean.setId("001");
        bean.setName("银行卡");
        bean.setDate(new Date());
        
        List cardNum = new ArrayList();
        cardNum.add("农行");
        cardNum.add("工行");
        cardNum.add("建行");
        cardNum.add(new Person("test"));
        
        bean.setCardNum(cardNum);
        
        JSONObject jsonObject = JSONObject.fromObject(bean);
        System.out.println(jsonObject);
        
    }
    public void testJSONToObject() throws Exception{
    	/*
        String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";  
        JSONObject jsonObject = JSONObject.fromObject( json ); 
        System.out.println(jsonObject);
        Object bean = JSONObject.toBean( jsonObject ); 
        assertEquals( jsonObject.get( "name" ), PropertyUtils.getProperty( bean, "name" ) );  
        assertEquals( jsonObject.get( "bool" ), PropertyUtils.getProperty( bean, "bool" ) );  
        assertEquals( jsonObject.get( "int" ), PropertyUtils.getProperty( bean, "int" ) );  
        assertEquals( jsonObject.get( "double" ), PropertyUtils.getProperty( bean, "double" ) );  
        assertEquals( jsonObject.get( "func" ), PropertyUtils.getProperty( bean, "func" ) );  
        System.out.println(PropertyUtils.getProperty(bean, "name"));
        System.out.println(PropertyUtils.getProperty(bean, "bool"));
        System.out.println(PropertyUtils.getProperty(bean, "int"));
        System.out.println(PropertyUtils.getProperty(bean, "double"));
        System.out.println(PropertyUtils.getProperty(bean, "func"));
        System.out.println(PropertyUtils.getProperty(bean, "array"));
        
        List arrayList = (List)JSONArray.toCollection(jsonObject.getJSONArray("array"));
        for(Object object : arrayList){
            System.out.println(object);
        }
        */
        
    }
    public void testJSONToBeanHavaList(){
    	/*
        String json = "{list:[{name:'test1'},{name:'test2'}],map:{test1:{name:'test1'},test2:{name:'test2'}}}";
//        String json = "{list:[{name:'test1'},{name:'test2'}]}";
        Map classMap = new HashMap();
        classMap.put("list", Person.class);
        MyBeanWithPerson diyBean = (MyBeanWithPerson)JSONObject.toBean(JSONObject.fromObject(json),MyBeanWithPerson.class , classMap);
        System.out.println(diyBean);
        
        List list = diyBean.getList();
        for(Object o : list){
            if(o instanceof Person){
                Person p = (Person)o;
                System.out.println(p.getName());
            }
        }
        */
    }
    public void testJSONToBeanHavaMap(){
    	/*
        //把Map看成一个对象
        String json = "{list:[{name:'test1'},{name:'test2'}],map:{testOne:{name:'test1'},testTwo:{name:'test2'}}}";
        Map classMap = new HashMap();
        classMap.put("list", Person.class);
        classMap.put("map", Map.class);
        //使用暗示，直接将json解析为指定自定义对象，其中List完全解析,Map没有完全解析
        MyBeanWithPerson diyBean = (MyBeanWithPerson)JSONObject.toBean(JSONObject.fromObject(json),MyBeanWithPerson.class , classMap);
        System.out.println(diyBean);
        
        System.out.println("do the list release");
        List<Person> list = diyBean.getList();
        for(Person o : list){
            Person p = (Person)o;
            System.out.println(p.getName());
        }
        
        System.out.println("do the map release");
        
        //先往注册器中注册变换器，需要用到ezmorph包中的类
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        Morpher dynaMorpher = new BeanMorpher( Person.class,  morpherRegistry);  
        morpherRegistry.registerMorpher( dynaMorpher );  
        
        
        Map map = diyBean.getMap();
        //这里的map没进行类型暗示，故按默认的，里面存的为net.sf.ezmorph.bean.MorphDynaBean类型的对象
        System.out.println(map);
      
        
	//　　　{testOne=net.sf.ezmorph.bean.MorphDynaBean@f73c1[
	//　　　  {name=test1}
	//　　　], testTwo=net.sf.ezmorph.bean.MorphDynaBean@186c6b2[
	//　　　 {name=test2}
	//　　　]}
        List<Person> output = new ArrayList();  
        for(Iterator i = map.values().iterator(); i.hasNext(); ){  
            //使用注册器对指定DynaBean进行对象变换
           output.add( (Person)morpherRegistry.morph( Person.class, i.next() ) );  
        }  
        
        for(Person p : output){
            System.out.println(p.getName());
        }
          */
    }
    public class MyBean {

    	private String id = null;
    	private String name = null;
    	private Date date = null;
    	private List cardNum = null;
    	private String[] cardType = {"身份证", "银行卡" , "公车卡"};
    	public String getId() {
    		return id;
    	}
    	public void setId(String id) {
    		this.id = id;
    	}
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	public Date getDate() {
    		return date;
    	}
    	public void setDate(Date date) {
    		this.date = date;
    	}
    	public List getCardNum() {
    		return cardNum;
    	}
    	public void setCardNum(List cardNum) {
    		this.cardNum = cardNum;
    	}
    	public String[] getCardType() {
    		return cardType;
    	}
    	public void setCardType(String[] cardType) {
    		this.cardType = cardType;
    	}
    	
    	
    }
    public class Person {

    	private String name = null;
    	private String age;

    	public Person(){
    		
    	}
    	
    	public Person(String name){
    		this.name = name;
    	}
    	
    	public String getName() {
    		return name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}
    }
	public static void main(String[] arags){
//		String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";  
//        JSONObject jsonObject = JSONObject.fromObject( json ); 
//        System.out.println(jsonObject);
//        Object bean = JSONObject.toBean( jsonObject ); 
//        System.out.println(bean);
		
		String json = "{name:\"cloudy\",age:\"23\"}";
		Object person= JSONObject.toBean(JSONObject.fromObject(json));
		try {
			System.out.println(PropertyUtils.getProperty(person, "name"));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

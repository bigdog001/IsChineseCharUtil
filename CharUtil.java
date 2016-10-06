package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CharUtil {
	
	public static List<String> files = new ArrayList<String>();
	public static String dir_root = "C:\\Users\\jw362j\\a\\work\\code\\att\\TGuardSDKAndroidRoot";
	public static  int counter = 0;
	private static String postFix_disable[] = {"jar",".svn-base",".bin",".class",".png",".jar.txt",".db"};
	
	 public static void main(String[] args) {
		 scanning(new File(dir_root));
		 System.out.println("There are   ：" + files.size() +" files in the directory!");
		 
		 scaningText();
		 System.out.println("There are "+counter + " files which contain chinese character ");
	    }
	 
	 private static void scaningText(){
		 char c ;
         boolean re ;
		 for(String f_name : files){
			 f_name = f_name.trim();
			 if(  isNecessary(f_name)  ){
				 continue;
			 }
			 String t = txt2String (new File(f_name));
			 char[] ch = t.toCharArray();
			 
			 for (int i = 0; i < ch.length; i++) {
	                  c = ch[i];
	                  re = isChinese(c);
	                if(re){
	                	counter++;
	                	 System.out.println(c + " --> " +f_name);
	                }	               
	            }
		 }
	 }
	 private static boolean isNecessary(String fileFullName){
		 boolean result = false;
		 for(String s:postFix_disable){
			 if(fileFullName.endsWith(s)){
				 result = true;
				 break;
			 }
		 }
		 return result;
	 }
	 
	 public static String txt2String(File file){
	        StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file)); 
	            String s = null;
	            while((s = br.readLine())!=null){ 
	                result.append(System.lineSeparator()+s);
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return result.toString();
	    }
	 
	
	 public static void scanning(File f){		 		
		  
		 if(f!=null){
	            if(f.isDirectory()){
	                File[] fileArray=f.listFiles();
	                if(fileArray!=null){
	                    for (int i = 0; i < fileArray.length; i++) {
	                    	scanning(fileArray[i]);
	                    }
	                }
	            }else{
	            	files.add(f.getAbsolutePath());
	            }
		 }
	 }
	 
	    //  
	    private static boolean isChinese(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
	                ) {
	            return true;
	        }
	        return false;
	    }
	 
	    //  
	    public static boolean isChinese(String strName) {
	        char[] ch = strName.toCharArray();
	        for (int i = 0; i < ch.length; i++) {
	            char c = ch[i];
	            if (isChinese(c)) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
	    // 
	    public static boolean isChineseByREG(String str) {
	        if (str == null) {
	            return false;
	        }
	        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
	        return pattern.matcher(str.trim()).find();
	    }
	 
	    // 
	    public static boolean isChineseByName(String str) {
	        if (str == null) {
	            return false;
	        }
	        
	        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
	        Pattern pattern = Pattern.compile(reg);
	        return pattern.matcher(str.trim()).find();
	    }
}

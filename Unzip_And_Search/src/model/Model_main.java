package model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class Model_main {
    private String name;
    public ModelFile modelFile;
    public ModelDecompressed modelDecompressed;
    private String des_path;
    private String source_save_path = "\\source";
    private String result_compressed = "\\result_compressed.txt";
    private String result_illegal = "\\result_illegal.txt";
    private String result_log = "\\result_log.txt";
    //private String result_except_log = "\\except_file.txt";
    private String result_decompressed = "\\result_decompressed.txt";
    private ArrayList<String> item_list = new ArrayList<String>();
    private ArrayList<Boolean> status_list = new ArrayList<Boolean>();
    
    public Model_main(){
    	modelFile = new ModelFile();
    	modelDecompressed = new ModelDecompressed();
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    //toString()  overrite
    @Override
    public String toString() {
        return "Model{" + "name=" + name + '}';
    }
    
    public void execute(String path, String compressed_format, boolean auto_decompress, String illegal_format){
    	File file = new File(path);
    	File des_file = new File("results");
    	Date now = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
    	if(des_path == null) {
    		des_path = des_file.getAbsolutePath()+ "\\"+ file.getName()+ "_"+ formatter.format(now);
    	
	        source_save_path = des_path+ source_save_path;
	        result_compressed = des_path+ result_compressed;
	        result_illegal = des_path+ result_illegal;
	        result_log = des_path+ result_log;
	        //result_except_log = des_path+ result_except_log;
	        result_decompressed = des_path+ result_decompressed;
        }
    	System.out.println("source_save_path "+ source_save_path);
    	System.out.println("result_compressed "+ result_compressed);
    	System.out.println("result_log "+ result_log);
    	System.out.println("result_decompressed "+ result_decompressed);
    	
    	modelFile.FolderAndFileCheck(0, des_path);
    	modelFile.FolderAndFileCheck(0, source_save_path);
    	
    	String source_coped = source_save_path+ "\\"+ file.getName();
    	
    	try {
    		if(file.isDirectory()) {
    			FileUtils.copyDirectory(file, new File(source_save_path));
    		}else {
    			FileUtils.copyToDirectory(file, new File(source_save_path));
    			modelDecompressed.Decompressed(source_coped);
    		}
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	

		System.out.println("*** Scan compressed files. ***");
    	ArrayList<String> compressed_found = modelFile.scanFiles(source_save_path, compressed_format);
    	compressed_found.remove(source_coped);

		System.out.println("*** Scan illegal files. ***");
    	ArrayList<String> illegal_found = modelFile.scanFiles(source_save_path, illegal_format);
		
    	if(auto_decompress == true) {
    		System.out.println("*** Auto Decompress: ***");
    		String decompressed_string = "";
    		for(String temp: compressed_found) {
    			try {
    				modelDecompressed.Decompressed(temp);
    				decompressed_string += temp+ "\n";
    				modelFile.deleteFile(temp);
    		        System.out.println("File deleted: "+ temp);
    			}catch (Exception e){
    				decompressed_string += "\n";
    			}
    		}
    		modelFile.updateFile(result_decompressed, decompressed_string);
    	}
    	
		String result_string = "";
    	for(String temp: compressed_found) {
    		result_string += temp;
    	}
		modelFile.updateFile(result_compressed, result_string);
		item_list.add("Scan compressed files");
		if(result_string.equals("")) status_list.add(true);
		else status_list.add(false);
		
		result_string = "";
    	for(String temp: illegal_found) {
    		result_string += temp;
    	}
		modelFile.updateFile(result_illegal, result_string);
		item_list.add("Scan illegal files");
		if(result_string.equals("")) status_list.add(true);
		else status_list.add(false);
    }
    
    public void saveLog(String log) {
    	modelFile.updateFile(result_log, log);
    }
    
    public String getDestinationPath() {
    	return des_path;
    }

    public ArrayList<String> getReportItems() {
    	return item_list;
    }

    public ArrayList<Boolean> getReportItemsStatus() {
    	return status_list;
    }
}

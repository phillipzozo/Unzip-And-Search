package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelFile {
	public ModelFile(){
		
	}
	
	/**
     * Check folder or file exist, if not then create it.
     *
     * @param folder_file create a folder or file(folder:0, file:1)
     * @param path the folder or file path
     */
	public void FolderAndFileCheck(int folder_file, String path) {
		switch(folder_file) {
		case 0:
			if(! (new File(path).exists())) new File(path).mkdir();
			break;
		case 1:
			try {
				File file = new File(path);
				FolderAndFileCheck(0, file.getParent());
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
    }
    
	public String readFile(String path) {
		FolderAndFileCheck(1, path);
		String content = "";
    	try {
			FileReader file_reader = new FileReader(path);
			BufferedReader br = new BufferedReader(file_reader);
			String str;
			try {
				boolean isempty = true;
				while ((str = br.readLine()) != null) {
					content += str+ "\n";
					isempty = false;
				}
				
				if(!isempty) {
					content = content.substring(0, content.length()-1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	return content;
    }
    
	public void updateFile(String path, String content) {
    	try {
    		FolderAndFileCheck(1, path);
    		PrintStream fileStream = new PrintStream(new File(path));
    		fileStream.println(content);
    		fileStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
	public void deleteFile(String path) {
		File file = new File(path);
    	try {
    		if(file.exists()) {
    			file.delete();
    		}
		} catch (Exception e1) {
			//e1.printStackTrace();
		}
    }
	
	@SuppressWarnings("resource")
	public void copyFileUsingFileChannels(String source_path, String dest_path) throws IOException {
		File source = new File(source_path);
		File dest = new File(dest_path);
		if(source.isFile()) {
		    FileChannel sourceChannel = null;
		    FileChannel destChannel = null;
	        System.out.println(source_path+ " ---> "+ dest_path);
	        try {
	        	sourceChannel = new FileInputStream(source).getChannel();
	        	destChannel = new FileOutputStream(dest).getChannel();
	            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally{
	            sourceChannel.close();
	            destChannel.close();
	        }
        }else {
        	FolderAndFileCheck(0, dest.getAbsolutePath());
        }
	}
    
	public ArrayList<String> scanFiles(String path) {
		return scanFiles(path, "");
	}
    
	public ArrayList<String> scanFiles(String path, String condition) {
		ArrayList<String> conditions = new ArrayList<String>();
		String[] temp1 = condition.split("\n");
		String[] temp2;
		ArrayList<String> result = new ArrayList<String>();
		
		for(String t: temp1) {
			temp2 = t.split(",");
			for(String t2: temp2) {
				if(t2.equals("")) continue;
				conditions.add(t2);
				//System.out.println(t2);
			}
		}

		System.out.println(conditions);
		
    	try {
        	//File file = new File(path);
    		for(String reg: conditions) {
				reg = reg.replace(".", "\\.");
				reg = reg.replace("*", ".*");
				//System.out.println("reg: "+ reg);
	        	try (Stream<Path> paths = Files.walk(Paths.get(path))) {
        			String reg2 = reg;
	        		List<Path> paths_filter = paths.filter(p->{
	        			File file_p = p.toFile();
	        			//System.out.println(file_p.getName());
	        			return file_p.getName().matches(reg2);
	        		}).collect(Collectors.toList());
	        		
	        		for(Path item : paths_filter) {
	        			//System.out.println(item.toFile().getPath().toString());
	        			result.add(item.toFile().getPath().toString());
        			}
	            }
    		}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	
    	return result;
    }
}

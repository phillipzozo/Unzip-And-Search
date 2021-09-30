package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200Strategy;
import org.apache.commons.compress.utils.IOUtils;

/***
 * 
 * Reference: https://github.com/apache/commons-compress/tree/master/src/test/java/org/apache/commons/compress/compressors
 * 
 ***/
public class ModelDecompressed {
    public ModelFile modelFile = new ModelFile();
	
	public void Decompressed(String path) throws Exception {
		Decompressed(path, "");
	}
	
	public void Decompressed(String path, String des_path) throws Exception {
		File compress_file = new File(path);
		String format = compress_file.getName();
		//int size = format.length();
        //System.out.println(format.contains(".tar"));
		
        if(format.contains(".7z")) sevenz(compress_file);
        else if(format.contains(".lzma")) lzma(compress_file);
        else if(format.contains(".xz")) xz(compress_file);
        else if(format.contains(".Z")) z(compress_file);
        else if(format.contains(".z")) z(compress_file);
        else if(format.contains(".zst")) zstandard(compress_file);
        else if(format.contains(".tar")) tar(compress_file);
        else if(format.contains(".zip")) zip(compress_file);
        else if(format.contains(".bz2")) bzip2(compress_file);
        else if(format.contains(".gz")) gzip(compress_file);
        else if(format.contains(".pack200")) pack200(compress_file);
	}
	
	public void sevenz(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent());
		SevenZFile sevenZFile = new SevenZFile(compress_file);
		
		SevenZArchiveEntry entry = sevenZFile.getNextEntry();
	    while(entry != null){
	        System.out.println(entry.getName());
	        if (entry.isDirectory()) {
	        	String folder = dest_file.getAbsoluteFile()+ "/"+ entry.getName();
		        //System.out.println(folder);
	        	if(! (new File(folder).exists())) new File(folder).mkdir();
	        }else {
		        FileOutputStream out = new FileOutputStream(dest_file.getAbsoluteFile()+ "/"+ entry.getName());
		        byte[] content = new byte[(int) entry.getSize()];
		        sevenZFile.read(content, 0, content.length);
		        out.write(content);
		        out.close();
	        }
	        entry = sevenZFile.getNextEntry();
	    }
	    sevenZFile.close();
	}
	public void tar(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent());
		FileInputStream fi = new FileInputStream(compress_file);
		
		BufferedInputStream bi = new BufferedInputStream(fi);
		GzipCompressorInputStream gzi = new GzipCompressorInputStream(bi);
		TarArchiveInputStream ti; // = new TarArchiveInputStream(gzi);
		if(compress_file.getName().contains(".tar.gz")){
			ti = new TarArchiveInputStream(new GzipCompressorInputStream(gzi));
        }else{
        	ti = new TarArchiveInputStream(gzi);
        }

	    ArchiveEntry entry;
	    while ((entry = ti.getNextEntry()) != null) {
	        System.out.println(entry.getName());
            if (entry.isDirectory()) {
	        	String folder = dest_file.getAbsoluteFile()+ "/"+ entry.getName();
		        //System.out.println(folder);
	        	if(! (new File(folder).exists())) new File(folder).mkdir();
	        }else {
	        	Path newPath = zipSlipProtect(entry, Paths.get(dest_file.getAbsolutePath()));
	        	
	        	try {
	        		//System.out.println(newPath.toString());
	        		Files.copy(ti, newPath, StandardCopyOption.REPLACE_EXISTING);
	    		} catch (IOException e) {
	    			modelFile.FolderAndFileCheck(1, newPath.toString());
	    		}
            }

	    }
	    fi.close();
	    bi.close();
	    gzi.close();
	    ti.close();
	    
	}
	public void zip(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent());
		FileInputStream fi = new FileInputStream(compress_file);
		
		ArchiveInputStream ais = null;
		try {
			ais = new ArchiveStreamFactory().createArchiveInputStream("zip", fi);
		} catch (ArchiveException e1) {
			e1.printStackTrace();
		}
		 
        ZipArchiveEntry entry = null;
        while ((entry = (ZipArchiveEntry) ais.getNextEntry()) != null) {
        	System.out.println(entry.getName());
            if (entry.isDirectory()) {
	        	String folder = dest_file.getAbsoluteFile()+ "/"+ entry.getName();
		        //System.out.println(folder);
	        	if(! (new File(folder).exists())) new File(folder).mkdir();
	        }else {
	            Path newPath = zipSlipProtect(entry, Paths.get(dest_file.getAbsolutePath()));
	        	try {
	        		//System.out.println(newPath.toString());
	        		Files.copy(ais, newPath, StandardCopyOption.REPLACE_EXISTING);
	    		} catch (IOException e) {
	    			modelFile.FolderAndFileCheck(1, newPath.toString());
	    		}
	        }
        }
	    fi.close();
	    ais.close();
		
	}
	public void bzip2(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent());
		FileInputStream fi = new FileInputStream(compress_file);

		byte[] buffer = new byte[1024];
		BufferedInputStream bi = new BufferedInputStream(fi);
        CompressorInputStream in = new CompressorStreamFactory().createCompressorInputStream("bzip2", bi);
        FileOutputStream os = new FileOutputStream(dest_file);
        int bytes_read;
        while ((bytes_read = in.read(buffer)) > 0) {
	        System.out.println(bytes_read);
	        os.write(buffer, 0, bytes_read);
        }
        
        //IOUtils.copy(in, os);
        fi.close();
        in.close();
        os.close();
		
	}
	public void gzip(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent());
		FileInputStream fi = new FileInputStream(compress_file);
		
		byte[] buffer = new byte[1024];
        GZIPInputStream gZIPInputStream = new GZIPInputStream(fi);
        FileOutputStream fileOutputStream = new FileOutputStream(dest_file);
        int bytes_read;
        while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
	        System.out.println(bytes_read);
            fileOutputStream.write(buffer, 0, bytes_read);
        }
        fi.close();
        gZIPInputStream.close();
        fileOutputStream.close();
        
	}
	public void lzma(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent(), compress_file.getName().replace(".lzma", ""));
		FileInputStream fi = new FileInputStream(compress_file);
		
		byte[] buffer = new byte[1024];
		BufferedInputStream bi = new BufferedInputStream(fi);
        CompressorInputStream in = new CompressorStreamFactory().createCompressorInputStream(bi);
        FileOutputStream os = new FileOutputStream(dest_file);
        int bytes_read;
        while ((bytes_read = in.read(buffer)) > 0) {
	        System.out.println(bytes_read);
	        os.write(buffer, 0, bytes_read);
        }
        
        //IOUtils.copy(in, os);
        fi.close();
        bi.close();
        in.close();
        os.close();
		
	}
	public void pack200(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent());
		//FileInputStream fi = new FileInputStream(compress_file);
		
		InputStream is = new Pack200CompressorInputStream(compress_file, Pack200Strategy.TEMP_FILE);
		
        final ArchiveInputStream in = ArchiveStreamFactory.DEFAULT
                .createArchiveInputStream("jar", is);

        ArchiveEntry entry = in.getNextEntry();
        while (entry != null) {
	        System.out.println(entry.getName());
            final File archiveEntry = new File(dest_file, entry.getName());
            archiveEntry.getParentFile().mkdirs();
            if (entry.isDirectory()) {
                archiveEntry.mkdir();
                entry = in.getNextEntry();
                continue;
            }
            OutputStream out = Files.newOutputStream(archiveEntry.toPath());
            IOUtils.copy(in, out);
            out.close();
            entry = in.getNextEntry();
        }
        
        is.close();
        in.close();
        
	}
	public void xz(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent(), compress_file.getName().replace(".xz", ""));
		FileInputStream fi = new FileInputStream(compress_file);

		byte[] buffer = new byte[1024];
		BufferedInputStream bi = new BufferedInputStream(fi);
		CompressorInputStream in = new CompressorStreamFactory().createCompressorInputStream("xz", bi);
		FileOutputStream os = new FileOutputStream(dest_file);

        int bytes_read;
        while ((bytes_read = in.read(buffer)) > 0) {
	        System.out.println(bytes_read);
	        os.write(buffer, 0, bytes_read);
        }
        
        //IOUtils.copy(in, os);
        fi.close();
        bi.close();
        in.close();
        os.close();
	}
	public void z(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent(), compress_file.getName().replace(".Z", "").replace(".z", ""));
		FileInputStream fi = new FileInputStream(compress_file);

		byte[] buffer = new byte[1024];
		BufferedInputStream bi = new BufferedInputStream(fi);
		CompressorInputStream in = new CompressorStreamFactory().createCompressorInputStream(bi);
		FileOutputStream os = new FileOutputStream(dest_file);

        int bytes_read;
        while ((bytes_read = in.read(buffer)) > 0) {
	        System.out.println(bytes_read);
	        os.write(buffer, 0, bytes_read);
        }
        
        fi.close();
        bi.close();
        in.close();
        os.close();
        
	}
	public void zstandard(File compress_file) throws Exception {
		File dest_file = new File(compress_file.getParent(), compress_file.getName().replace(".zst", ""));
		FileInputStream fi = new FileInputStream(compress_file);

		byte[] buffer = new byte[1024];
		BufferedInputStream bi = new BufferedInputStream(fi);
		CompressorInputStream in = new CompressorStreamFactory().createCompressorInputStream(bi);
		FileOutputStream os = new FileOutputStream(dest_file);

        int bytes_read;
        while ((bytes_read = in.read(buffer)) > 0) {
	        System.out.println(bytes_read);
	        os.write(buffer, 0, bytes_read);
        }
        
        fi.close();
        bi.close();
        in.close();
        os.close();
	}
	
	private Path zipSlipProtect(ArchiveEntry entry, Path targetDir) throws IOException {
		
		String newName = entry.getName().replaceAll("[(\\:)(\\*)(\\\")(\\?)(\\<)(\\>)(\\|)]", "_");
        Path targetDirResolved = targetDir.resolve(newName);
        
        // make sure normalized file still has targetDir as its prefix,
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();

        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad entry: " + newName);
        }

        return normalizePath;
    }
}

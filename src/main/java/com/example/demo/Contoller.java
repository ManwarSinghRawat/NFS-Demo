package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class Contoller {

	@GetMapping("/NFS/")
	public List<String> helloWorld() {
		File folder = new File("/usr/bin");
		File[] listOfFiles = folder.listFiles();
		List<String> fileNames= new ArrayList<String>();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        System.out.println(file.getName());	       
		        fileNames.add("File Name: "+ file.getName() + " Path :"+file.getPath());
		        
		    }
		}	
		return fileNames;
	}
	
	
	@RequestMapping(path = "/download/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(@PathVariable String fileName) throws IOException {
		String dirPath = "/usr/bin/"; 
		File file = new File(dirPath+fileName);
		 file.getAbsolutePath();
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	    return ResponseEntity.ok()
	            .headers(null)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	
	@GetMapping("/NFS/All")
	public List<FileResponse> getAllFileDetails() {
		File folder = new File("/usr/bin");
		List<FileResponse>  filesReponses = new ArrayList<>();
		FileResponse fileResponse;
		File[] listOfFiles = folder.listFiles();
		String fileDownloadUri=null;

		for (File file : listOfFiles) {
			fileResponse = new FileResponse();
		    if (file.isFile()) {
		    	fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		                .path("/usr/bin/")
		                .path(file.getName())
		                .toUriString(); 
		        fileResponse.setFileName(file.getName());
		        fileResponse.setFileDownloadUri(fileDownloadUri);
		        fileResponse.setSize(file.getTotalSpace());
		        filesReponses.add(fileResponse);
		        
		    }
		}	
		return filesReponses;
	}
	
	
	
	
}

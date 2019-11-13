package com.oracle.sss.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.commons.ociobjectstorageutils.handler.ObjectStorageHandler;

/**
 * Sample Class for the operations around bucket and Objects in the bucket
 */
public class OCIReadWrite {

    public static void main(String[] args) throws Exception {
//        if(args.length<2){
//            System.out.println("Please pass the compartmentID and Running Time");
//            System.exit(0);
//        }
		/*
		Initialize the object storage client instance
		 */
        ObjectStorageHandler objectStorageHandler = ObjectStorageHandler.getInstance();
        objectStorageHandler.initializeObjectStorageEntities(objectStorageHandler.getObjectStorageClient(false));

		/*
		To Get the root tenancy
		 */
        String tenantID = objectStorageHandler.getTenantId();
		/*
		 Any compartment OCID where you want to do the operations
		 */
        
        String nameSpace = "paasdevsss"; //args[0];
        
        String compartmentOCID = "ocid1.compartment.oc1..aaaaaaaa44dewz4rqbodb5vmsd7iin45q72eniracmvbzqzgeinqopmcayrq"; //args[1];

		/*
		Bucket related operations creating TestBucketExample
		 */

		/*
		Setting the data
		 */
        String bucketName = "qa-assets"; //args[2];
        
        //paasdevsss ocid1.compartment.oc1..aaaaaaaa44dewz4rqbodb5vmsd7iin45q72eniracmvbzqzgeinqopmcayrq qa-assets
        
        String filePath = "/Users/marrajen/Documents/SSS";
        
        String fileName = "LongRun.txt";
        

//        Path path = Paths.get(System.getProperty("user.home") + filePath);
//        if (Files.exists(path)) {
//            Files.walk(path)
//                    .sorted(Comparator.reverseOrder())
//                    .map(Path::toFile)
//                    .forEach(File::delete);
//        }
//        String content = "Hello World !!";
//        File files = new File(System.getProperty("user.home") + "/upload/firstLevel/secondLevel");
//        if (!files.exists()) {
//            if (files.mkdirs()) {
//                System.out.println("Multiple directories are created!");
//            } else {
//                System.out.println("Failed to create multiple directories!");
//            }
//        }
//        Path path1 = Paths.get(System.getProperty("user.home") + "/upload");
//        Path path2 = Paths.get(System.getProperty("user.home") + "/upload//firstLevel");
//        Path path3 = Paths.get(System.getProperty("user.home") + "/upload/firstLevel/secondLevel");
        
        
/*        Path path1 = Paths.get(filePath);
        Path path1File = path1.resolve(fileName);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  

        String readContent = dtf.format(now);
        System.out.println("Data Written to File : " + readContent);
        Files.write(Paths.get(path1File.toString()), readContent.getBytes());
        
        String tempDir=System.getProperty("java.io.tmpdir");
        
        File tempFile = new File(tempDir+fileName);

        FileWriter fileWriter = new FileWriter(tempFile, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(readContent);
        bw.close();
        
 
        objectStorageHandler.getManageObjects().putObject(bucketName, tempFile.getAbsolutePath());
        
        System.out.println("***** File " + fileName + " is written to OCI Storage successfully *****");


        if (objectStorageHandler.getManageObjects().isObjectExists(bucketName, fileName))
        {
        	System.out.println("***** File Check : " + (objectStorageHandler.getManageObjects().isObjectExists(bucketName, fileName)) + " *****");
        	System.out.println("***** File " + fileName + " is written to OCI Storage successfully *****");
        	System.out.println(objectStorageHandler.getManageObjects().getObject(bucketName, (fileName)).getContentType());;
        }*/
        
//        PutObjectRequest putobjectRequest = PutObjectRequest.builder()
//                .namespaceName(nameSpace)
//                .bucketName(bucketName)
//                .objectName(fileName)
//        		  .putObjectBody(InputStream putObjectBody) 
//                .build();
//        
//        objectStorageHandler.getObjectStorageClient().putObject(putobjectRequest);
        
        
        
        GetObjectRequest getobjectRequest = GetObjectRequest.builder()
                .namespaceName(nameSpace)
                .bucketName(bucketName)
                .objectName(fileName)
                .build();
        
        GetObjectResponse objectResponse = objectStorageHandler.getObjectStorageClient().getObject(getobjectRequest);

        //String filepath="/Users/marrajen/Documents/SSS//"+fileName.toString();
        try(InputStream inputStream = objectResponse.getInputStream())
        {
        	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        	String line = null;
        	while((line = in.readLine()) != null) 
        	  System.out.println("****** " + line + " ******");
        	
        }
        catch (IOException e) 
        {
          System.out.println("An exception occurred while downloading {} to location: {}"+fileName+","+filePath+","+e);
        }
        
        
        File file = new File(filePath + fileName);
        try (InputStream inputStream = objectResponse.getInputStream()) 
        {
          file.createNewFile();
          Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
          System.out.println("***** Successfully downloaded the file " + fileName + "from OCI Storage to Local *****");
          //String writeContent = new String(Files.readAllBytes(Paths.get(path1File.toString())));
          
          //System.out.println("***** " + writeContent + " *****");
        } 
        catch (IOException e) 
        {
          System.out.println("An exception occurred while downloading {} to location: {}"+fileName+","+filePath+","+e);
        }
    }

}

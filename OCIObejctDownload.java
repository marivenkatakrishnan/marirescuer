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

public class OCIObejctDownload {
	public static void main(String[] args) throws Exception {

		ObjectStorageHandler objectStorageHandler = ObjectStorageHandler.getInstance();

		String nameSpace = "paasdevsss";
		String bucketName = "tpcds-sf1000";
		String fileName = "tpcds";

		String filePath = "/Users/marrajen/sss-workspace/TPC-DS";

		GetObjectRequest getobjectRequest = GetObjectRequest.builder()
				.namespaceName(nameSpace)
				.bucketName(bucketName)
				.objectName(fileName)
				.build();




		GetObjectResponse objectResponse = objectStorageHandler.getObjectStorageClient().getObject(getobjectRequest);



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

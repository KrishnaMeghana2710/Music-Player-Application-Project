package seneca.jammer.Service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class S3service implements FileService {

    @Autowired
    private AmazonS3Client awS3Client ;
    private static final String bucket_name = "jammer-bucket";

     @Override
    public String uploadFile(MultipartFile file){
        //upload file to aws

       //prepare a key
        
        String originalFileName = file.getOriginalFilename();
        

        //metadata
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        //putting the data into s3

         try{
                awS3Client.putObject(bucket_name, originalFileName, file.getInputStream(), metadata);
            } catch(IOException iException){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Exception occured while uploading the file"+iException);
            }

            //for access from frontend
            awS3Client.setObjectAcl(bucket_name, originalFileName, CannedAccessControlList.PublicRead);
            //getting the url of the file in s3
            String url = awS3Client.getResourceUrl(bucket_name, originalFileName);
                    return url;
                }



    @Override      
    public String deleteFile(String filename) {

        awS3Client.deleteObject(bucket_name,filename);
        return "File deleted";
    }
}
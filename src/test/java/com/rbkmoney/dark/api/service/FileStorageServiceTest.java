package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.converter.filestorage.FileStorageConverterImpl;
import com.rbkmoney.file.storage.FileData;
import com.rbkmoney.file.storage.FileNotFound;
import com.rbkmoney.file.storage.FileStorageSrv;
import com.rbkmoney.file.storage.NewFileResult;
import com.rbkmoney.file.storage.msgpack.Value;
import com.rbkmoney.swag.dark_api.model.FileDownload;
import com.rbkmoney.swag.dark_api.model.FileUploadData;
import com.rbkmoney.swag.dark_api.model.FileUploadRequest;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FileStorageConverterImpl.class, FileStorageService.class},
                properties = {
                    "filestorage.expiration.time.download.hours=1",
                    "filestorage.expiration.time.upload.hours=1"
                })
public class FileStorageServiceTest {

    @MockBean
    public FileStorageSrv.Iface fileStorageClient;

    @Autowired
    public FileStorageService service;

    private String fileId = "fileId";
    private String fileName = "fileName";
    private String fileIdUrl = "fileIdUrl";
    private Map<String, Value> metadata = new HashMap<>();

    @Test
    public void generateDownloadUrlSuccess() throws TException {
        when(fileStorageClient.generateDownloadUrl(any(), any())).thenReturn(fileIdUrl);

        FileDownload fileDownload = service.downloadFile(fileId);

        Assert.assertEquals(fileIdUrl, fileDownload.getUrl());
    }

    @Test(expected = FileNotFound.class)
    public void generateDownloadUrlFileNotFound() throws TException {
        when(fileStorageClient.generateDownloadUrl(any(), any())).thenThrow(FileNotFound.class);

        service.downloadFile(fileId);
    }

    @Test
    public void getFileInfoSuccessWithEmptyMetadata() throws TException {
        String created_at = Instant.now().toString();
        when(fileStorageClient.getFileData(any())).thenReturn(new FileData()
                .setCreatedAt(created_at)
                .setFileDataId(fileId)
                .setFileName(fileName)
                .setMetadata(metadata)
        );

        com.rbkmoney.swag.dark_api.model.FileData fileInfo = service.getFileInfo(fileId);
        Assert.assertEquals(fileId, fileInfo.getFileId());
        Assert.assertEquals(fileName, fileInfo.getFileName());
        Assert.assertEquals(metadata, fileInfo.getMetadata());
        Assert.assertEquals(created_at, fileInfo.getCreatedAt().toString());
    }

    @Test
    public void getFileInfoSuccessWithMetadata() throws TException {
        String created_at = Instant.now().toString();
        Map<String, Value> metadata = new HashMap<>() {{
            put("test", new Value());
        }};
        when(fileStorageClient.getFileData(any())).thenReturn(new FileData()
                .setCreatedAt(created_at)
                .setFileDataId(fileId)
                .setFileName(fileName)
                .setMetadata(metadata)
        );

        com.rbkmoney.swag.dark_api.model.FileData fileInfo = service.getFileInfo(fileId);
        Assert.assertEquals(fileId, fileInfo.getFileId());
        Assert.assertEquals(fileName, fileInfo.getFileName());
        Assert.assertEquals(metadata, fileInfo.getMetadata());
        Assert.assertEquals(created_at, fileInfo.getCreatedAt().toString());
    }

    @Test(expected = FileNotFound.class)
    public void getFileNotFound() throws TException {
        String created_at = Instant.now().toString();
        Map<String, Value> metadata = new HashMap<>() {{
            put("test", new Value());
        }};
        when(fileStorageClient.getFileData(any())).thenThrow(FileNotFound.class);

        service.getFileInfo(fileId);
    }

    @Test
    public void generateUploadUrlSuccess() throws TException {
        when(fileStorageClient.createNewFile(any(), any())).thenReturn(new NewFileResult(fileId, fileIdUrl));

        FileUploadData fileUploadData = service.uploadFile(new FileUploadRequest().metadata(metadata));

        Assert.assertEquals(fileIdUrl, fileUploadData.getUrl());
        Assert.assertEquals(fileId, fileUploadData.getFileId());
    }

}

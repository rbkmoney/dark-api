package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.converter.filestorage.FileStorageConverter;
import com.rbkmoney.file.storage.FileStorageSrv;
import com.rbkmoney.swag.dark_api.model.FileData;
import com.rbkmoney.swag.dark_api.model.FileDownload;
import com.rbkmoney.swag.dark_api.model.FileUploadData;
import com.rbkmoney.swag.dark_api.model.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${filestorage.expiration.time.download.hours}")
    private Integer downloadExpirationHours;
    @Value("${filestorage.expiration.time.upload.hours}")
    private Integer uploadExpirationHours;

    private final FileStorageSrv.Iface fileStorageClient;
    private final FileStorageConverter fileStorageConverter;


    public FileDownload downloadFile(String fileID) throws TException {
        return fileStorageConverter.convertFileDownload(
                fileStorageClient.generateDownloadUrl(
                        fileID,
                        Instant.now().plus(downloadExpirationHours, ChronoUnit.HOURS).toString()
                )
        );
    }

    public FileData getFileInfo(String fileID) throws TException {
        return fileStorageConverter.convertFileData(fileStorageClient.getFileData(fileID));
    }

    @SuppressWarnings("unchecked")
    public FileUploadData uploadFile(FileUploadRequest uploadFileRequest) throws TException {
        return fileStorageConverter.convertFileUploadData(
                fileStorageClient.createNewFile(
                        (Map<String, com.rbkmoney.file.storage.msgpack.Value>) uploadFileRequest.getMetadata(),
                        Instant.now().plus(uploadExpirationHours, ChronoUnit.HOURS).toString()
                )
        );
    }
}

package com.rbkmoney.dark.api.converter.filestorage;

import com.rbkmoney.file.storage.NewFileResult;
import com.rbkmoney.swag.dark_api.model.FileData;
import com.rbkmoney.swag.dark_api.model.FileDownload;
import com.rbkmoney.swag.dark_api.model.FileUploadData;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class FileStorageConverterImpl implements FileStorageConverter {

    @Override
    public FileData convertFileData(com.rbkmoney.file.storage.FileData fileData) {
        return new FileData()
                .fileId(fileData.getFileDataId())
                .fileName(fileData.getFileName())
                .createdAt(OffsetDateTime.parse(fileData.getCreatedAt()))
                .metadata(fileData.getMetadata());
    }

    @Override
    public FileUploadData convertFileUploadData(NewFileResult newFileResult) {
        return new FileUploadData()
                .url(newFileResult.getUploadUrl())
                .fileId(newFileResult.getFileDataId());
    }

    @Override
    public FileDownload convertFileDownload(String generateDownloadUrl) {
        return new FileDownload()
                .url(generateDownloadUrl);
    }
}

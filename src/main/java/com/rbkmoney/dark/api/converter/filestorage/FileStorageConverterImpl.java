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
    public FileData convertFileData(com.rbkmoney.file.storage.FileData tFileData) {
        return new FileData()
                .fileId(tFileData.getFileDataId())
                .fileName(tFileData.getFileName())
                .createdAt(OffsetDateTime.parse(tFileData.getCreatedAt()))
                .metadata(tFileData.getMetadata());
    }

    @Override
    public FileUploadData convertFileUploadData(NewFileResult tNewFileResult) {
        return new FileUploadData()
                .url(tNewFileResult.getUploadUrl())
                .fileId(tNewFileResult.getFileDataId());
    }

    @Override
    public FileDownload convertFileDownload(String generateDownloadUrl) {
        return new FileDownload()
                .url(generateDownloadUrl);
    }
}

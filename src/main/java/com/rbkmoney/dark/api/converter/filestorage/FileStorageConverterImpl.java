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
                .fileId(fileData.file_data_id)
                .fileName(fileData.file_name)
                .createdAt(OffsetDateTime.parse(fileData.created_at))
                .metadata(fileData.metadata);
    }

    @Override
    public FileUploadData convertFileUploadData(NewFileResult newFile) {
        return new FileUploadData()
                .url(newFile.upload_url)
                .fileId(newFile.file_data_id);
    }

    @Override
    public FileDownload convertFileDownload(String generateDownloadUrl) {
        return new FileDownload().url(generateDownloadUrl);
    }
}

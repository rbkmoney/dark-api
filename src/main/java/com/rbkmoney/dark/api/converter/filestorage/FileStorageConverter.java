package com.rbkmoney.dark.api.converter.filestorage;

import com.rbkmoney.file.storage.NewFileResult;
import com.rbkmoney.swag.dark_api.model.FileData;
import com.rbkmoney.swag.dark_api.model.FileDownload;
import com.rbkmoney.swag.dark_api.model.FileUploadData;

public interface FileStorageConverter {
    FileData convertFileData(com.rbkmoney.file.storage.FileData fileData);

    FileUploadData convertFileUploadData(NewFileResult newFile);

    FileDownload convertFileDownload(String generateDownloadUrl);
}

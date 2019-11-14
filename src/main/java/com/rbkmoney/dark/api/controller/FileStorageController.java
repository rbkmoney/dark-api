package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.FileStorageService;
import com.rbkmoney.swag.dark_api.api.FilesApi;
import com.rbkmoney.swag.dark_api.model.FileData;
import com.rbkmoney.swag.dark_api.model.FileDownload;
import com.rbkmoney.swag.dark_api.model.FileUploadData;
import com.rbkmoney.swag.dark_api.model.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@Slf4j
@RestController
@PreAuthorize("hasAuthority('invoices:read')") //todo what authority?
@RequiredArgsConstructor
public class FileStorageController implements FilesApi {

    private final FileStorageService fileStorageService;

    @Override
    public ResponseEntity<FileDownload> downloadFile(@RequestHeader(value = "X-Request-ID") String xRequestID,
                                                     @Size(min = 1, max = 40) String fileID) {
        try {
            return ResponseEntity.ok(fileStorageService.downloadFile(fileID));
        } catch (TException e) {
            // todo
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<FileData> getFileInfo(@RequestHeader(value = "X-Request-ID") String xRequestID,
                                                @Size(min = 1, max = 40) String fileID) {
        try {
            return ResponseEntity.ok(fileStorageService.getFileInfo(fileID));
        } catch (TException e) {
            // todo
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<FileUploadData> uploadFile(@RequestHeader(value = "X-Request-ID") String xRequestID,
                                                     @Valid FileUploadRequest uploadFileRequest) {
        try {
            return ResponseEntity.ok(fileStorageService.uploadFile(uploadFileRequest));
        } catch (TException e) {
            // todo
            e.printStackTrace();
            return null;
        }
    }
}

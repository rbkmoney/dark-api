package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.exceptions.client.NotFoundException;
import com.rbkmoney.dark.api.service.FileStorageService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.file.storage.FileNotFound;
import com.rbkmoney.swag.claim_management.model.GeneralError;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.rbkmoney.dark.api.util.ThriftClientUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileStorageController implements FilesApi {

    private final FileStorageService fileStorageService;
    private final PartyManagementService partyManagementService;

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<FileDownload> downloadFile(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                                     @NotNull @Size(min = 1, max = 40) String fileId) {
        try {
            log.info("Process 'downloadFile' get started, xRequestId={}, fileId={}", xRequestId, fileId);

            partyManagementService.checkStatus(xRequestId);

            FileDownload fileDownload = fileStorageService.downloadFile(fileId);

            log.info("Process 'downloadFile' finished, xRequestId={}, fileId={}", xRequestId, fileId);

            return ResponseEntity.ok(fileDownload);
        } catch (FileNotFound ex) {
            throw notFoundException(xRequestId, fileId, ex);
        } catch (TException ex) {
            throw darkApi5xxException("file-storage", "downloadFile", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<FileData> getFileInfo(@RequestHeader(value = "X-Request-ID") String xRequestId,
                                                @Size(min = 1, max = 40) String fileId) {
        try {
            log.info("Process 'getFileInfo' get started, xRequestId={}, fileId={}", xRequestId, fileId);

            partyManagementService.checkStatus(xRequestId);

            FileData fileInfo = fileStorageService.getFileInfo(fileId);

            log.info("Process 'getFileInfo' finished, xRequestId={}, fileId={}", xRequestId, fileId);

            return ResponseEntity.ok(fileInfo);
        } catch (FileNotFound ex) {
            throw notFoundException(xRequestId, fileId, ex);
        } catch (TException ex) {
            throw darkApi5xxException("file-storage", "getFileInfo", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<FileUploadData> uploadFile(@RequestHeader(value = "X-Request-ID") String xRequestId,
                                                     @Valid FileUploadRequest uploadFileRequest) {
        try {
            log.info("Process 'uploadFile' get started, xRequestId={}", xRequestId);

            partyManagementService.checkStatus(xRequestId);

            FileUploadData fileUploadData = fileStorageService.uploadFile(uploadFileRequest);

            log.info("Process 'uploadFile' finished, xRequestId={}", xRequestId);

            return ResponseEntity.ok(fileUploadData);
        } catch (TException ex) {
            throw darkApi5xxException("file-storage", "uploadFile", xRequestId, ex);
        }
    }

    private NotFoundException notFoundException(String xRequestId, String fileId, FileNotFound ex) {
        String msg = messageFileNotFound(fileId, xRequestId);
        GeneralError response = new GeneralError().message(msg);
        return new NotFoundException(msg, ex, response);
    }

    private String messageFileNotFound(String fileId, String xRequestId) {
        return String.format("File not found, fileId=%s, xRequestId=%s", fileId, xRequestId);
    }
}

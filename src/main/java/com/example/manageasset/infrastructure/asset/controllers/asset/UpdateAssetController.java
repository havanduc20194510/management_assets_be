package com.example.manageasset.infrastructure.asset.controllers.asset;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.services.asset.UpdateAssetService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/asset")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UpdateAssetController {
    private final UpdateAssetService updateAssetService;

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@RequestPart("assetDto") AssetDto assetDto, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException, NotFoundException {
        updateAssetService.update(assetDto, files);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Update asset success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}

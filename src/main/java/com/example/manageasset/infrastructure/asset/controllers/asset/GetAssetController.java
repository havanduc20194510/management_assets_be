package com.example.manageasset.infrastructure.asset.controllers.asset;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.services.asset.GetAssetService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetAssetController {
    private final GetAssetService getAssetService;

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) throws NotFoundException {
        AssetDto assetDto = getAssetService.get(id);
        PagingPayload.PagingPayloadBuilder<AssetDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(assetDto);
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}

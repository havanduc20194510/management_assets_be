package com.example.manageasset.infrastructure.asset.controllers.asset;

import com.example.manageasset.domain.asset.services.asset.UpdateStateAssetService;
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
public class UpdateStateAssetController {
    private final UpdateStateAssetService updateStateAssetService;
    @PutMapping("/update-state/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestParam("state") Integer state) throws NotFoundException {
        updateStateAssetService.update(id, state);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Update asset success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}

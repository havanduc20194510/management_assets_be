package com.example.manageasset.domain.asset.services.asset;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.Attachment;
import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.utility.Constants;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.infrastructure.shared.configs.FirebaseStorageConfig;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateAssetService {
    private final AssetRepository assetRepository;
    private final CategoryRepository categoryRepository;
    private final Bucket bucket;

    public void update(AssetDto assetDto, List<MultipartFile> multipartFiles) throws IOException, NotFoundException {
        Asset asset = assetRepository.getById(assetDto.getId());
        if(asset == null) {
            throw new NotFoundException(String.format("Asset[id=%d] not found", assetDto.getId()));
        }
        Category category = categoryRepository.getById(assetDto.getCategory().getId());
        if(category == null) {
            throw new NotFoundException(String.format("Category[id=%d] not found", assetDto.getCategory().getId()));
        }
        if(CollectionUtils.isEmpty(multipartFiles)){
            asset.update(assetDto.getName(), assetDto.getQuantity(), assetDto.getStatus(), assetDto.getValue(),
                    assetDto.getManagementUnit(), category, asset.getAttachments());
        }else{
            List<Attachment> attachments = new ArrayList<>();
            for(MultipartFile multipartFile: multipartFiles){
                if(multipartFile.getSize() > 0){
                    String nameFile = String.format("%s/%s", Constants.ASSET_FOLDER, new ULID().nextULID());
                    Blob blob = bucket.create(nameFile, multipartFile.getBytes(), multipartFile.getContentType());
                    String source = FirebaseStorageConfig.getURL(blob, nameFile).toString();
                    String mime = multipartFile.getContentType();
                    String name = multipartFile.getOriginalFilename();
                    Attachment attachment = new Attachment(source, mime, name);
                    attachments.add(attachment);
                }
            }
            asset.update(assetDto.getName(), assetDto.getQuantity(), assetDto.getStatus(), assetDto.getValue(),
                    assetDto.getManagementUnit(), category, attachments);
        }
        assetRepository.save(asset);
    }
}

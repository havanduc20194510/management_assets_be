package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.Attachment;
import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateAssetService {
    private final AssetRepository assetRepository;
    private final CategoryRepository categoryRepository;

    public void update(AssetDto assetDto, List<MultipartFile> multipartFiles) throws NotFoundException {
        Asset asset = assetRepository.getById(assetDto.getId());
        if(asset == null) {
            throw new NotFoundException("Asset not found");
        }
        Category category = categoryRepository.getById(assetDto.getCategory().getId());
        if(category == null) {
            throw new NotFoundException("Category not found");
        }
        if(CollectionUtils.isEmpty(multipartFiles)){
            asset.update(assetDto.getName(), assetDto.getQuantity(), assetDto.getStatus(), assetDto.getValue(),
                    assetDto.getManagementUnit(), category, asset.getAttachments());
        }else{
            List<Attachment> attachments = new ArrayList<>();
            for(MultipartFile multipartFile: multipartFiles){
                if(multipartFile.getSize() > 0){
                    String source = "abc.com";
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

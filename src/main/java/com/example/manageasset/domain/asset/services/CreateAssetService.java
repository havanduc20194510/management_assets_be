package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.Attachment;
import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAssetService {
    private final AssetRepository assetRepository;
    private final CategoryRepository categoryRepository;
//    private final UserRepository userRepository;

    public void create(AssetDto assetDto, List<MultipartFile> multipartFiles) throws NotFoundException{
        Category category = categoryRepository.getById(assetDto.getCategory().getId());
        if(category == null) {
            throw new NotFoundException("Category not found");
        }

        /*User manager = userRepository.getById(assetDto.getManager().getId());
        if(manager == null) {
            throw new NotFoundException("Manager not found");
        }*/

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
        Asset asset = Asset.create(assetDto.getName(), assetDto.getQuantity(), assetDto.getStatus(), assetDto.getValue(),
                assetDto.getManagementUnit(),/* manager,*/ category, attachments);
        assetRepository.save(asset);
    }
}

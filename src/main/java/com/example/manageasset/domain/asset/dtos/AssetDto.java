package com.example.manageasset.domain.asset.dtos;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDto {
    private Long id;
    private String name;
    private String status;
    private Double value;
    private String description;
    @JsonProperty("management_unit")
    private String managementUnit;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
    private UserDto manager;
    private CategoryDto category;
    private List<AttachmentDto> attachments;

    public static AssetDto fromModel(Asset asset) {
        return new AssetDto(asset.getId(), asset.getName(), asset.getStatus(), asset.getValue(),
                asset.getDescription(),
                asset.getManagementUnit(), asset.getCreatedAt().asLong(), asset.getUpdatedAt().asLong(),
                UserDto.fromModel(asset.getManager()),
                CategoryDto.fromModel(asset.getCategory()),
                CollectionUtils.isEmpty(asset.getAttachments()) ? null : asset.getAttachments().stream().map(AttachmentDto::fromModel).collect(Collectors.toList()));
    }
}

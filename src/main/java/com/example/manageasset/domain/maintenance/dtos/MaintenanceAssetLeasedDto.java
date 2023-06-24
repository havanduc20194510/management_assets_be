package com.example.manageasset.domain.maintenance.dtos;

import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenanceAssetLeasedDto {
    private String id;
    @JsonProperty("client")
    private UserDto clientDto;
    @JsonProperty("user")
    private UserDto userDto;
    private String reason;
    @JsonProperty("completed_at")
    private Long completedAt;
    @JsonProperty("started_at")
    private Long startedAt;
    private String note;
    @JsonProperty("asset_leased")
    private List<AssetLeasedDto> assetLeasedDtos;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;



    public static MaintenanceAssetLeasedDto fromModel(MaintenanceAssetLeased maintenanceAssetLeased) {
        return new MaintenanceAssetLeasedDto(
                maintenanceAssetLeased.getId(),
                UserDto.fromModel(maintenanceAssetLeased.getClient()),
                UserDto.fromModel(maintenanceAssetLeased.getUser()),
                maintenanceAssetLeased.getReason(),
                maintenanceAssetLeased.getCompletedAt().asLong(),
                maintenanceAssetLeased.getStartedAt().asLong(),
                maintenanceAssetLeased.getNote(),
                CollectionUtils.isEmpty(maintenanceAssetLeased.getAssetLeaseds()) ? null : maintenanceAssetLeased.getAssetLeaseds().stream().map(AssetLeasedDto::fromModel).collect(Collectors.toList()),
                maintenanceAssetLeased.getCreatedAt().asLong(),
                maintenanceAssetLeased.getUpdatedAt().asLong()
        );
    }
}

package com.example.manageasset.domain.asset.dtos;

import com.example.manageasset.domain.asset.models.Attachment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    private Long id;
    private String source;
    private String mime;
    private String name;

    public static AttachmentDto fromModel(Attachment attachment) {
        return new AttachmentDto(attachment.getId(), attachment.getSource(), attachment.getMime(), attachment.getName());
    }
}

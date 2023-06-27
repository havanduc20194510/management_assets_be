package com.example.manageasset.domain.asset.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private Long id;
    private String source;
    private String mime;
    private String name;

    public Attachment(String source, String mime, String name) {
        this.source = source;
        this.mime = mime;
        this.name = name;
    }
}

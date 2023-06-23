package com.example.manageasset.infrastructure.asset.repositories;

import com.example.manageasset.domain.asset.models.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "source", nullable = false, length = 1000)
    private String source;
    @Column(name = "mime", nullable = false)
    private String mime;
    @Column(name = "name", nullable = false)
    private String name;

    public static AttachmentEntity fromDomain(Attachment attachment) {
        return new AttachmentEntity(
                attachment.getId(),
                attachment.getSource(),
                attachment.getMime(),
                attachment.getName()
        );
    }

    public Attachment toDomain() {
        return new Attachment(id, source, mime, name);
    }
}

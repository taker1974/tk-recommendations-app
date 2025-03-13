package ru.spb.tksoft.advertising.entity.recommendation;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", nullable = false, length = 4096)
    @JsonProperty("text")
    private String description;

    @Override
    public String toString() {
        return String.format("%s: %s. %s", id, name, description);
    }

    public String toStringShort(int descriptionLength) {
        return String.format("%s: %s. %s", id, name,
                description.length() <= descriptionLength ? description
                        : description.substring(0, descriptionLength - 1));
    }
}

package ru.spb.tksoft.advertising.entity.recommendation;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class RecommendationsDto {

    @JsonProperty("user_id")
    UUID userId;

    List<Recommendation> recommendations;

    @Override
    public String toString() {
        var sb = new StringBuilder(userId + ":\n");
        for (Recommendation recommendation : recommendations) {
            sb.append(recommendation + "\n");
        }
        return sb.toString();
    }

    public String toStringShort(int descriptionLength) {
        var sb = new StringBuilder(userId + ":\n");
        for (Recommendation recommendation : recommendations) {
            sb.append(recommendation.toStringShort(descriptionLength) + "\n");
        }
        return sb.toString();
    }
}

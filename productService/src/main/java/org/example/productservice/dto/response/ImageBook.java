package org.example.productservice.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageBook {
    String mainImage;
    String otherImage;
    List<String> childImages;

    public List<String> convertChildImage() {
        if (otherImage == null || otherImage.isEmpty()) {
            this.childImages = new ArrayList<>();
        } else {
            this.childImages = new ArrayList<>(Arrays.asList(this.otherImage.split("///")));
        }

        return this.childImages;
    }

    public String convertChildImageToString() {
        if (childImages == null || childImages.isEmpty()) {
            this.otherImage = null;
        } else {
            StringJoiner joiner = new StringJoiner("///");
            this.childImages.forEach(joiner::add);

            this.otherImage = joiner.toString();
        }

        return this.otherImage;
    }
}

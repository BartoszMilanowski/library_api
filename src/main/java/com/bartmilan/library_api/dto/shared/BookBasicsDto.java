package com.bartmilan.library_api.dto.shared;

public class BookBasicsDto {

    private Long id;
    private String polishTitle;
    private String originalTitle;
    private String coverURl;

    public BookBasicsDto(Long id, String polishTitle, String originalTitle, String coverURl) {
        this.id = id;
        this.polishTitle = polishTitle;
        this.originalTitle = originalTitle;
        this.coverURl = coverURl;
    }

    public Long getId() {
        return id;
    }

    public String getPolishTitle() {
        return polishTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getCoverURl() {
        return coverURl;
    }
}

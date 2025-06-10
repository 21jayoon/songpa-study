package com.ohgiraffers.fileupload;

/*업로드된 파일과 관련된 정보를 모아서 관리하는 DTO 생성 11:08AM*/
public class FileDTO {
    private String originFileName;
    private String savedName;
    private String filePath;
    private String fileDesctiption;

    public FileDTO(){}

    public FileDTO(String originFileName, String savedName, String filePath, String fileDesctiption) {
        this.originFileName = originFileName;
        this.savedName = savedName;
        this.filePath = filePath;
        this.fileDesctiption = fileDesctiption;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDesctiption() {
        return fileDesctiption;
    }

    public void setFileDesctiption(String fileDesctiption) {
        this.fileDesctiption = fileDesctiption;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "originFileName='" + originFileName + '\'' +
                ", savedName='" + savedName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileDesctiption='" + fileDesctiption + '\'' +
                '}';
    }
}

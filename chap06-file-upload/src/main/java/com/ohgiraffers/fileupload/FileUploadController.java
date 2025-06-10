package com.ohgiraffers.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//250610 10:42 AM  FileUploadController class 생성
@Controller
public class FileUploadController {
    @PostMapping("/single-file")
    public String singleFileUpload(@RequestParam String singleFileDescription,
                                   @RequestParam MultipartFile singleFile,
                                   Model model){
        // 입력한 매개변수 잘 나오는지 확인
//        System.out.println(singleFileDescription);
//        System.out.println(singleFile);

        /*서버로 전달된 File을 서버에서 설정하는 경로에 저장한다*/
        String root = "src/main/resources/static";
        String filePath = root + "/uploadFiles";  // uploadFiles라는 폴더(directory) 만들어서 경로로 지정, 여기에 저장
        File dir = new File(filePath);
        System.out.println(dir.getAbsolutePath());

        //만약 경로로 지정된 폴더가 없다면?? 새로 폴더 만든다.
        if(!dir.exists()){
            dir.mkdirs();
        }

        /*파일명 변경 처리 : 동일한 파일명 사용 불가*/
        String originFileName = singleFile.getOriginalFilename();

        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        System.out.println("ext="+ext);

        String savedName = UUID.randomUUID() +ext;
        //.randomUUID는 영문자, 숫자 등을 무작위 조합하여 ID(혹은 이름)을 만들어주는 method다.
        System.out.println(savedName);

        /*File 저장*/
        try {
            singleFile.transferTo(new File(filePath+"/"+savedName));
            model.addAttribute("message", "파일 업로드 성공!");
        } catch (IOException e) {
            model.addAttribute("message", "파일 업로드 실패");
        }
        return "result";
    }


    @PostMapping("/multi-file")
    public String multiFileUpload(@RequestParam String multiFileDescription,
                                  @RequestParam List<MultipartFile> multiFile,
                                  Model model){
        System.out.println("multiFileDescription="+multiFileDescription);
        System.out.println("multiFile="+multiFile);

        //DTO class를 만들어 DTO에 파일을 넘겨 관리하도록 만들자. 11:07AM

        // 받아온 파일(사진) 저장할 저장소와 경로 명명
        String root = "src/main/resources/static";
        String filePath = root + "/uploadFiles";
        File dir = new File(filePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        List<FileDTO> files = new ArrayList<>();

        /*파일명 변경 처리 후 저장 : 다중 파일 반복문 처리*/
        try {
            for(MultipartFile file: multiFile) {
                String originFileName = file.getOriginalFilename();
                System.out.println(originFileName);

                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID() + ext;

                /*파일에 관한 정보 추출 후 보관*/
                files.add(new FileDTO(originFileName, savedName, filePath, multiFileDescription));

                /*파일 저장*/
                file.transferTo(new File(filePath + "/" + savedName));
            }
            model.addAttribute("message", "File Upload Complete!");
        } catch (IOException e) {
            /*파일저장 중간에 저장 실패 시 자원 낭비 막기 위해 이전에 저장된 파일 삭제
            * 왜 for문? 1개 이상 DTO에 저장되었던 거라 for문 통해 하나하나 체크해보며 저장되었던 파일 탐색*/
            for(FileDTO file : files){
                new File(filePath+ "/" +file.getSavedName()).delete();
            }
            model.addAttribute("message", "File Upload Failed...");
        }

        return "result";
    }
}

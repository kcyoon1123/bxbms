package com.bx.bxbms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bx.bxbms.monthrep.vo.MonthrepVO;
import com.bx.bxbms.weekrep.vo.WeekrepVO;

@Component("fileUtil")
public class FileUtil {
	// 파일 저장경로
	private static final String filePath = "D:\\eclipse\\workspace\\bxbms\\file\\";
	
	public WeekrepVO parseInsertFileInfo(String weekrepId, MultipartFile uploadFile) throws Exception {
		
		
		// 파일 생성할 위치
		File file = new File(filePath);
		
		if (file.exists() == false) {
			
			file.mkdirs();
		}
		// 파일 정보담을 vo
		WeekrepVO fileVO = new WeekrepVO();
		
			if (uploadFile.isEmpty() == false) {
				// 첨부파일 원본명
				String atchFileOrgnNm = null;
				atchFileOrgnNm = uploadFile.getOriginalFilename();
				
				// 첨부파일 확장자명
				String originalFileExtension = null;
				originalFileExtension = atchFileOrgnNm.substring(atchFileOrgnNm.lastIndexOf("."));
				
				// 첨부파일 실제저장명
				String atchFileStrgNm = null;
				atchFileStrgNm = getRandomString() + originalFileExtension;
				
				file = new File(filePath + atchFileStrgNm);
				// 주간보고 아이디
				fileVO.setWeekrepId(weekrepId);
				
				// 첨부파일 원본명
				fileVO.setAtchFileOrgnNm(atchFileOrgnNm);
				
				
				fileVO.setAtchFileStrgNm(atchFileStrgNm);
				
				// 첨부파일 용량
				fileVO.setAtchFileSize(uploadFile.getSize());
				
				// 첨부파일 저장경로
				fileVO.setAtchFileStrgPath(filePath);
				// 첨부파일 확장자명
				fileVO.setAtchFileExtNm(originalFileExtension);
				
				try {
					// 파일을 지정 경로로 업로드
					uploadFile.transferTo(file);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		return fileVO;
	}
	
	// filedownload
	public void filedownload(WeekrepVO vo, HttpServletResponse response, HttpServletRequest request) throws IOException {
		// 첨부파일 원본명
		String atchFileOrgnNm = vo.getAtchFileStrgNm();
		
		String atchFileStrgNm =  vo.getAtchFileStrgNm();
		System.out.println("atchFileStrgNmatchFileStrgNmatchFileStrgNm"+atchFileStrgNm);
		byte fileByte[] = FileUtils.readFileToByteArray(new File(filePath + atchFileStrgNm));
		File downloadfile = new File(filePath + atchFileStrgNm);
		if(downloadfile.exists() && downloadfile.isFile()) {
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentLength(fileByte.length);
			OutputStream outputstream = null;
            FileInputStream inputstream = null;
            try {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + atchFileOrgnNm + "\";");
                response.setHeader("Content-Transfer-Encoding", "binary");
                outputstream = response.getOutputStream();
                inputstream = new FileInputStream(downloadfile);
                 
                //일반 자바/JSP 파일다운로드
                byte[] buffer = new byte[1024]; 
                int length = 0; 
                while((length = inputstream.read(buffer)) > 0) {
                    outputstream.write(buffer,0,length); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputstream != null){
                        inputstream.close();
                    }
                    outputstream.flush();
                    outputstream.close();  
                } catch (Exception e2) {}
            }
        }else {
            System.out.println("파일존재하지 않음");
        }

			
		}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	private String check_browser(HttpServletRequest request) {
        String browser = "";
        String header = request.getHeader("User-Agent");
        //신규추가된 indexof : Trident(IE11) 일반 MSIE로는 체크 안됨
        if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1){
            browser = "ie";
        }
        //크롬일 경우
        else if (header.indexOf("Chrome") > -1){
            browser = "chrome";
        }
        //오페라일경우
        else if (header.indexOf("Opera") > -1){
            browser = "opera";
        }
        //사파리일 경우
        else if (header.indexOf("Apple") > -1){
            browser = "sarari";
        } else {
            browser = "firfox";
        }
        return browser;
    }
	
	private String getDisposition(String down_filename, String browser_check) throws UnsupportedEncodingException {
        String prefix = "attachment;filename=";
        String encodedfilename = null;
        System.out.println("browser_check:"+browser_check);
        if (browser_check.equals("ie")) {
            encodedfilename = URLEncoder.encode(down_filename, "UTF-8").replaceAll("\\+", "%20");
        }else if (browser_check.equals("chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < down_filename.length(); i++){
                char c = down_filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedfilename = sb.toString();
        }else {
            encodedfilename = "\"" + new String(down_filename.getBytes("UTF-8"), "8859_1") + "\"";
        }
        return prefix + encodedfilename;
    }


	// 다중 파일업로드
	public List<MonthrepVO> parseInsertFilesInfo(String monthrepId, List<MultipartFile> fileList) throws Exception {
		
		
		List<MonthrepVO> files = new ArrayList<MonthrepVO>();
		// 파일 정보담을 vo
		
		System.err.println("fileList.size()fileList.size()"+fileList.size());
		String atchFileOrgnNm = null;
			for(MultipartFile mf : fileList) {
				MonthrepVO fileVO = new MonthrepVO();
				// 파일 생성할 위치
				File file = new File(filePath);
				
				if (file.exists() == false) {
					
					file.mkdirs();
				}
				// 첨부파일 원본명
				atchFileOrgnNm = mf.getOriginalFilename();
				System.err.println("atchFileOrgnNm첨부파일atchFileOrgnNm"+atchFileOrgnNm);
				// 첨부파일 확장자명
				String originalFileExtension = null;
				originalFileExtension = atchFileOrgnNm.substring(atchFileOrgnNm.lastIndexOf("."));
				
				// 첨부파일 실제저장명
				String atchFileStrgNm = null;
				atchFileStrgNm = getRandomString() + originalFileExtension;
				
				file = new File(filePath + atchFileStrgNm);
				// 주간보고 아이디
				fileVO.setMonthrepId(monthrepId);
				
				// 첨부파일 원본명
				fileVO.setAtchFileOrgnNm(atchFileOrgnNm);
				
				
				fileVO.setAtchFileStrgNm(atchFileStrgNm);
				
				// 첨부파일 용량
				fileVO.setAtchFileSize(mf.getSize());
				
				// 첨부파일 저장경로
				fileVO.setAtchFileStrgPath(filePath);
				// 첨부파일 확장자명
				fileVO.setAtchFileExtNm(originalFileExtension);
				
				
				files.add(fileVO);

				try {
					// 파일을 지정 경로로 업로드
					mf.transferTo(file);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		return files;
	}
	
	

}

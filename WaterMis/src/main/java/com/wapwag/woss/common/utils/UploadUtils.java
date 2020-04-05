package com.wapwag.woss.common.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 文件上传工具类
 */
public class UploadUtils {
	
	/**上传的文件类型-images*/
	public static final String FILE_TYPE_IMAGES = "images";
	/**上传的文件类型-flashs*/
	public static final String FILE_TYPE_FLASHS = "flashs";
	/**上传的文件类型-medias*/
	public static final String FILE_TYPE_MEDIAS = "medias";
	/**上传的文件类型-files*/
	public static final String FILE_TYPE_FILES = "files";
	
	/**远程上传文件的服务器地址 */
	public static final String REMOTE_UPLOAD_URL = PropUtils.getPropertiesString("application.properties", "remote.upload.url");
	
	/**windows服务器 文件保存路径 */
	public static final String WIN_REMOTE_FILEPATH = "D:" + File.separator + "fileslocation" + File.separator;
	
	// 最大文件大小
	public static final long maxSize = 1024 * 1024 * 20;
	// 定义允许上传的文件扩展名
	public static Map<String, String> extMap = new HashMap<String, String>();
	// 文件保存目录相对路径
	public static String basePath = "upload";
	// 文件保存目录路径
	public static String savePath = "";
	// 文件保存目录url
	public static String saveUrl = "";
	// 文件最终的url包括文件名
	public static String fileUrl = "";

	static{
		extMap.put("images", "gif,jpg,jpeg,png,bmp");
		extMap.put("flashs", "swf,flv");
		extMap.put("medias", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("files", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	/**
	 * 单文件上传
	 * 上传到当前服务器
	 * @param request
	 * @param fileType 上传文件支持四种类型(FILE_TYPE_IMAGES,FILE_TYPE_FLASHS,FILE_TYPE_MEDIAS,FILE_TYPE_FILES)
	 * @return infos info[0] 验证文件域返回错误信息 info[1] 上传文件错误信息 info[2] savePath info[3] saveUrl info[4] fileUrl
	 */
	public static String[] uploadFile(HttpServletRequest request, String fileType) {
		String[] infos = new String[5];
		// 验证,并初始化保存路径
		infos[0] = validateFields(request, fileType);
		
		if(infos[0].equals("true")){
			infos[1] = transferFile(request, fileType);
			infos[2] = savePath;
			infos[3] = saveUrl;
			infos[4] = fileUrl;
		}
		return infos;
	}
	
	/**
	 * 多文件上传
	 * 上传到当前服务器
	 * @param request
	 * @return List<Map<String, String>>
	 * 			elementName 前端file所对应的Name                                      
	 * 			fileUrl  	文件URL
	 * 			savePath 	保存完整路径
	 * 			errorMsg 	错误信息
	 * 			success  	是否保存成功
	 */
	public static List<Map<String, String>> uploadFiles(HttpServletRequest request){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        //判断是否multipart流
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){
            	Map<String, String> map = new HashMap<String, String>();
            	String elementName = iter.next();
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(elementName);
                String errorMsg = "";
                String successFlag = "false";
                if(file != null){
                    //取得当前上传文件的文件名称  
                    String fileName = file.getOriginalFilename();
                    //获取文件后缀名
            		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            		
            		// 文件保存目录路径(目前存于项目路径)
            		savePath = request.getSession().getServletContext().getRealPath(File.separator) + basePath + File.separator;
            		// 文件保存目录URL
            		saveUrl = "/" + basePath + "/";
            		fileUrl = "";
            		
            		String fileType = "";
            		if (Arrays.<String> asList(extMap.get("images").split(",")).contains(fileExt)) {
            			fileType = "images";
            		}else if (Arrays.<String> asList(extMap.get("medias").split(",")).contains(fileExt)) {
            			fileType = "medias";
            		}else if (Arrays.<String> asList(extMap.get("flashs").split(",")).contains(fileExt)) {
            			fileType = "flashs";
            		}else if (Arrays.<String> asList(extMap.get("files").split(",")).contains(fileExt)) {
            			fileType = "files";
            		}
            		
            		//如果名称不为"",说明该文件存在，否则说明该文件不存在
            		if(fileName.trim() != ""){
	            		// 检查文件大小、扩展名
	            		if (file.getSize() > maxSize) { 
	            			errorMsg = "文件 " + fileName + " 的大小超过限制";
	            		} else if(fileType == ""){
	            			errorMsg = "不支持上传 "+ fileExt +" 后缀的文件";
	            		}else{
	            			
	            			savePath += fileType + File.separator;
	            			saveUrl += fileType + "/";
	            			File saveDirFile = new File(savePath);
	            			if (!saveDirFile.exists()) {
	            				saveDirFile.mkdirs();
	            			}
	            			// .../basePath/fileType/yyyyMMdd/
	            			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	            			String ymd = sdf.format(new Date());
	            			savePath += ymd + File.separator;
	            			saveUrl += ymd + "/";
	            			File dirFile = new File(savePath);
	            			if (!dirFile.exists()) {
	            				dirFile.mkdirs();
	            			}
	            			
            				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
	            			
	            			// .../basePath/fileType/yyyyMMdd/yyyyMMddHHmmss_xxx.xxx
	            			fileUrl = saveUrl + newFileName;
	            			
	            			File uploadedFile = new File(savePath, newFileName);
	            			//保存文件
	                        try {
								file.transferTo(uploadedFile);
							} catch (IllegalStateException e) {
								e.printStackTrace();
								System.out.println("上传失败");
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("上传失败");
							}
	                        successFlag = "true";
	            		}
            		}else{
            			errorMsg = "该文件不存在";
            		}
                }else{
                	errorMsg = "未取到上传文件信息";
                }
                System.out.println("fileUrl: " + fileUrl + " savePath: " + savePath + " errorMsg: " +
                		errorMsg + " success: " + successFlag +" elementName " + elementName);
                
                map.put("elementName", elementName);
                map.put("fileUrl", fileUrl);
                map.put("savePath", savePath);
                map.put("errorMsg", errorMsg);
                map.put("success", successFlag);
                list.add(map);
            }  
        }  
		return list;
	}
	
	/**
	 * 客户端文件上传到文件服务器 
	 * 需要上传时调用
	 * 客户端调用
	 * @param request
	 * @return JSON 数据类型,格式为:[{},{},...]或{}
	 * 具体参数：
	 * 			uniqueId    文件的唯一ID
	 * 			elementName 前端file所对应的Name                                      
	 * 			fileUrl  	文件URL
	 * 			errorMsg 	错误信息
	 * 			success  	是否保存成功
	 */
	public static String uploadFilesRemoteForClient(HttpServletRequest request){
		String errorJson = "{\"success\":\"false\",\"errorMsg\":\"文件保存出错\"}";
		//创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        //判断是否multipart流
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();
            //保存文件信息载体
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //设置编码为UTF-8
            builder.setCharset(Consts.UTF_8);
            while(iter.hasNext()){
            	String elementName = iter.next();
            	//取得上传文件  
                MultipartFile file = multiRequest.getFile(elementName);
                
            	System.out.println("文件的名称为：" + file.getOriginalFilename());
                
                if(file != null){
                	try {
						builder.addBinaryBody(elementName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
					} catch (IOException e) {
						return errorJson;
					}
                }
            }
            
            //初始化httpClient信息
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(REMOTE_UPLOAD_URL);
            HttpEntity reqEntity = builder.build();
            //配置信息
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setConnectTimeout(10 * 1000) //连接超时时间
                    .setConnectionRequestTimeout(10 * 1000) 
                    .setSocketTimeout(10 * 1000)
                    .build(); 
            
            try{
	            httpPost.setEntity(reqEntity);
	            httpPost.setConfig(requestConfig);
	            
	            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
	            //成功返回
	            if(closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	            	//获取返回的数据
	            	String strBack = EntityUtils.toString(closeableHttpResponse.getEntity(), Consts.UTF_8);
	            	
	            	System.out.println("成功返回!" + strBack);
	            	return strBack;
	            }
            }catch(Exception e){
            	e.printStackTrace();
            }finally{
            	if(httpPost != null){
            		httpPost.releaseConnection();
            	}
            	if(httpClient != null){
            		try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
        }
        return errorJson;
	}
	
	/**
	 * 文件服务器处理文件上传
	 * 支持单文件、多文件上传
	 * @param request
	 * @return JSON 数据类型,格式为:[{},{},...] 或 {}
	 * 具体参数：
	 * 			uniqueId    文件的唯一ID
	 * 			elementName 前端file所对应的Name                            
	 * 			fileUrl  	文件URL
	 * 			errorMsg 	错误信息
	 * 			success  	是否保存成功
	 */
	public static String uploadFilesRemoteForServer(HttpServletRequest request){
		String errorJson = "{\"success\":\"false\",\"errorMsg\":\"文件保存出错\"}";
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        //判断是否multipart流
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();
            int flag = 0;
            while(iter.hasNext()){
            	Map<String, String> map = new HashMap<String, String>();
            	String elementName = iter.next();
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(elementName);
                String uniqueId = "";
                String errorMsg = "";
                String successFlag = "false";
                if(file != null){
                    //取得当前上传文件的文件名称
                    String fileName = file.getOriginalFilename();
                    //获取文件后缀名
            		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            		
            		// 文件保存目录路径(目前存于项目路径)
            		savePath = WIN_REMOTE_FILEPATH + basePath + File.separator;
            		// 文件保存目录URL
            		saveUrl = "/" + basePath + "/";
            		fileUrl = "";
            		
            		String fileType = "";
            		if (Arrays.<String> asList(extMap.get("images").split(",")).contains(fileExt)) {
            			fileType = "images";
            		}else if (Arrays.<String> asList(extMap.get("medias").split(",")).contains(fileExt)) {
            			fileType = "medias";
            		}else if (Arrays.<String> asList(extMap.get("flashs").split(",")).contains(fileExt)) {
            			fileType = "flashs";
            		}else if (Arrays.<String> asList(extMap.get("files").split(",")).contains(fileExt)) {
            			fileType = "files";
            		}
            		
            		//如果名称不为"",说明该文件存在，否则说明该文件不存在
            		if(fileName.trim() != ""){
	            		// 检查文件大小、扩展名
	            		if (file.getSize() > maxSize) { 
	            			errorMsg = "文件 " + fileName + " 的大小超过限制";
	            		} else if(fileType == ""){
	            			errorMsg = "不支持上传 "+ fileExt +" 后缀的文件";
	            		}else{
	            			
	            			savePath += fileType + File.separator;
	            			saveUrl += fileType + "/";
	            			File saveDirFile = new File(savePath);
	            			if (!saveDirFile.exists()) {
	            				saveDirFile.mkdirs();
	            			}
	            			// .../basePath/fileType/yyyyMMdd/
	            			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	            			String ymd = sdf.format(new Date());
	            			savePath += ymd + File.separator;
	            			saveUrl += ymd + "/";
	            			File dirFile = new File(savePath);
	            			if (!dirFile.exists()) {
	            				dirFile.mkdirs();
	            			}
	            			
            				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            				String dateStr = df.format(new Date());
            				String newFileName = dateStr + new Random().nextInt(1000);
            				//组装唯一ID
	            			uniqueId =  fileType + "_" + newFileName + "_" + fileExt;
	            			// .../basePath/fileType/yyyyMMdd/yyyyMMddHHmmssxxx.xxx
	            			fileUrl = saveUrl + newFileName + "." + fileExt;
	            			
	            			File uploadedFile = new File(savePath, newFileName + "." + fileExt);
	            			//保存文件
	                        try {
								file.transferTo(uploadedFile);
							} catch (IllegalStateException e) {
								e.printStackTrace();
								System.out.println("上传失败");
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("上传失败");
							}
	                        successFlag = "true";
	            		}
            		}else{
            			errorMsg = "未收到文件传值";
            		}
                }else{
                	errorMsg = "未取到上传文件信息";
                }
                System.out.println("fileUrl: " + fileUrl + " savePath: " + savePath + " errorMsg: " +
                		errorMsg + " success: " + successFlag +" elementName " + elementName);
                
                map.put("uniqueId", uniqueId);
                map.put("elementName", elementName);
                map.put("fileUrl", fileUrl);
                map.put("errorMsg", errorMsg);
                map.put("success", successFlag);
                list.add(map);
                flag ++;
            }
            //转换为json
            ObjectMapper mapper = new ObjectMapper();
    		String strjson = "";
    		try{
	            if(flag == 1 && list != null && list.size() == 1){
	            	strjson = mapper.writeValueAsString((Map<String, String>)list.get(0));
	            }else{
	            	strjson = mapper.writeValueAsString(list);
	            }
            }catch(Exception e){
            	e.printStackTrace();
				System.out.println("list转json失败");
            }
    		return strjson;
        }
        return errorJson;
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "true");
		list.add(map);
		ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(list));
	}
	
	/**
	 * 上传验证,并初始化文件目录
	 * 
	 * @param request
	 * @param fileType 上传文件支持四种类型(FILE_TYPE_IMAGES,FILE_TYPE_FLASHS,FILE_TYPE_MEDIAS,FILE_TYPE_FILES)
	 */
	public static String validateFields(HttpServletRequest request, String fileType) {
		String errorInfo = "true";
		// boolean errorFlag = true;
		// 获取内容类型
		String contentType = request.getContentType();
		int contentLength = request.getContentLength();
		// 文件保存目录路径(目前存于项目路径)
		savePath = request.getSession().getServletContext().getRealPath(File.separator) + basePath + File.separator;
		// 文件保存目录URL
		saveUrl = "/" + basePath + "/";
		File uploadDir = new File(savePath);
		//不存在目录则创建
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		if (contentType == null || !contentType.startsWith("multipart")) {
			System.out.println("请求不包含multipart/form-data流");
			errorInfo = "请求不包含multipart/form-data流";
		} else if (maxSize < contentLength) {
			System.out.println("上传文件大小超出文件最大大小");
			errorInfo = "上传文件大小超出文件最大大小[" + maxSize + "]";
		} else if (!ServletFileUpload.isMultipartContent(request)) {
			errorInfo = "请选择文件";
		} else if (!uploadDir.isDirectory()) {// 检查目录
			errorInfo = "上传目录[" + savePath + "]不存在";
		} else if (!uploadDir.canWrite()) {
			errorInfo = "上传目录[" + savePath + "]没有写权限";
		} else if (!extMap.containsKey(fileType)) {
			errorInfo = "目录名不正确或不支持该文件类型";
		} else {
			// .../basePath/fileType/
			// 创建文件夹 不同类型的文件传到不同的文件夹中
			savePath += fileType + File.separator;
			saveUrl += fileType + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			// .../basePath/fileType/yyyyMMdd/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + File.separator;
			saveUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
		}

		return errorInfo;
	}
	
	/**
	 * 文件上传
	 * @param request 
	 * @param fileType 文件类型
	 * @return
	 */
	public static String transferFile(HttpServletRequest request, String fileType){
		String error = "true";
		//创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){
                    //取得当前上传文件的文件名称  
                    String fileName = file.getOriginalFilename();
            		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            		
            		//如果名称不为"",说明该文件存在，否则说明该文件不存在
            		if(fileName.trim() != ""){
	            		// 检查文件大小、扩展名
	            		if (file.getSize() > maxSize) { 
	            			error = "上传文件大小超过限制";
	            		} else if (!Arrays.<String> asList(extMap.get(fileType).split(",")).contains(fileExt)) {
	            			error = "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(fileType) + "格式。";
	            		} else {
	            			String newFileName = "";
	            			
            				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            				newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
	            			
	            			// .../basePath/fileType/yyyyMMdd/yyyyMMddHHmmss_xxx.xxx
	            			fileUrl = saveUrl + newFileName;
	            			
	            			File uploadedFile = new File(savePath, newFileName);
	            			//保存文件
	                        try {
								file.transferTo(uploadedFile);
							} catch (IllegalStateException e) {
								e.printStackTrace();
								System.out.println("上传失败");
							} catch (IOException e) {
								e.printStackTrace();
								System.out.println("上传失败");
							} 
	            		}
            		}
                }  
            }  
        }  
		return error;
	}
	
	/**
	 * 根据UniqueId返回文件的访问路径
	 * @param uniqueId
	 * @return {}
	 */
	public static String getFileUrlById(String uniqueId){
		if(uniqueId != null && uniqueId.length() > 0){
			String srr[] = uniqueId.split("_");
			if(srr.length == 3){
				return "/upload/" + srr[0] + "/" +srr[1].substring(0, 8) + "/" + srr[1] +"." + srr[2] ;
			}
		}
		return "";
	}
	
}

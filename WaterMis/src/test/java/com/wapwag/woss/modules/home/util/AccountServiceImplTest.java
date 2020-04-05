//package com.wapwag.woss.modules.home.util;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.wapwag.woss.modules.biz.entity.DeviceAccessoriesInfo;
//import com.wapwag.woss.modules.biz.entity.PumpHouseInfo;
//import com.wapwag.woss.modules.biz.service.DeviceAccessoriesInfoService;
//import com.wapwag.woss.modules.biz.service.ProjectService;
//import com.wapwag.woss.modules.biz.service.ServiceValuesCurrentService;
//import com.wapwag.woss.modules.biz.service.WaterCompanyService;
//import com.wapwag.woss.modules.home.service.TreeTypeService;
//import com.wapwag.woss.modules.monitor.service.MonitorService;
//import com.wapwag.woss.modules.monitor.service.PumpConfigurationService;
//
////让测试运行于Spring环境  
//@RunWith(SpringJUnit4ClassRunner.class)  
////引入Spring配置  
//@ContextConfiguration({"classpath*:/spring-context*.xml","classpath*:/spring-mvc.xml"})  
//public class AccountServiceImplTest {
//	
//	@Autowired
//	private DeviceAccessoriesInfoService deviceAccessoriesInfoService;
//	
//	@Autowired
//	private WaterCompanyService waterCompanyService;
//	
//	@Autowired
//	private MonitorService monitorService;
////	
////	@Autowired
////	private ProductComponentService productComponentService;
//	
//	@Autowired
//	private ProjectService projectService;
//	
//	@Autowired
//	private TreeTypeService treeTypeService;
//	
//	@Autowired
//	private PumpConfigurationService pumpConfigurationService;
//	
//	@Autowired
//	private ServiceValuesCurrentService serviceValuesCurrentService;
//	
//	 //进行测试  
//    @Test
//    public void testSelectFindAccountList() { 
//    	DeviceAccessoriesInfo info = deviceAccessoriesInfoService.get("04edc7524c1b423881f892d3936e5652");
//    	System.out.println(info);
//    }
//    
//    //@Test
//    public void testSave() {
//		//    	ProductComponent productComponent = new ProductComponent();
////    	productComponentService.save(productComponent);
////    	productComponent.setId("df59ac525e344496bac7be783538bd6d");
////    	productComponentService.save(productComponent);
////    	productComponentService.delete(productComponent);
////    	List<ProductComponent> list = productComponentService.selectListByKeyWord(null);
////    	System.out.println(list);
////    	WaterCompany waterCompany = new WaterCompany(); 
////    	waterCompany.setCompanyName("广西北控水司");
////    	waterCompany.setPinyin("GX");
////    	waterCompany.setMemo("备注Junit测试");
////    	waterCompany.setCreator("创建人");
////    	waterCompanyService.save(waterCompany);
////    	Project project = new Project();
////    	project.setUid("14030025110");
////    	project.setPinyin("xmpycs");
////    	project.setRegionType("0");
////    	project.setCompanyNode("f2742b9cb39f4b51b36a46ab864b04de");
////    	projectService.save(project);
////    	TreeTypeDto treeTypeDto = new TreeTypeDto();
////    	treeTypeDto.setUserId("1");
////    	treeTypeService.getTreeDataByType(treeTypeDto);
//    	
//		//    	Map<String, Object> queryDeviceStatistic2 = monitorService.queryDeviceStatistic2();
////    	PumpHouseInfo findPumpHouseInfoById = monitorService.findPumpHouseInfoById("16060103");
////    	System.out.println(findPumpHouseInfoById);
//    	String phid="976290237800034305";
//    	pumpConfigurationService.getServiceValues(phid);
//    }
//    @Test
//    public void test1() {
//    	serviceValuesCurrentService.saveCurrentValues();
//    }
//}

package com.wapwag.woss.modules.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.common.service.ServiceException;
import com.wapwag.woss.common.utils.DateUtils;
import com.wapwag.woss.modules.biz.entity.NoticeDto;
import com.wapwag.woss.modules.biz.service.NoticeService;
import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.sys.service.SystemService;
import com.wapwag.woss.modules.ticket.Entity.TicketComDto;
import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import com.wapwag.woss.modules.ticket.Entity.TicketLogDto;
import com.wapwag.woss.modules.ticket.Entity.TicketToDoDto;
import com.wapwag.woss.modules.ticket.dao.TicketDao;
import com.wapwag.woss.modules.ticket.utils.NodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TicketService  extends CrudService<TicketDao, TicketDto> {
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SystemService systemService;

    /**
     * 获取所有部门组织
     * @return
     */
    public List<TicketDto> getDeptList(){
        return ticketDao.getDeptList();
    }

    public List<String> getUserIdByDeptId(String deptId){
        return ticketDao.getUserIdByDeptId(deptId);
    }

    public void insertDetails(String ticketId,User u,String title,String userId){
        //每次插入新的之前都会把所有这个bizId的对应的流程都修改为一查看
        NoticeDto noticeDto1=new NoticeDto();
        noticeDto1.setBizId(ticketId);
        List<NoticeDto> details = noticeService.findDetails(noticeDto1);
        for(NoticeDto d:details){
            NoticeDto noticeDto2=new NoticeDto();
            noticeDto2.setId(d.getId());
            noticeDto2.setNoticeStatus("02");
            noticeService.updateNoticeDto(noticeDto2);
        }
        //插入新的流程消息
        TicketDto ticketDto=ticketDao.getTicketInfo(ticketId);
        //插入分发消息表
        NoticeDto noticeDto=new NoticeDto();
        noticeDto.setId(UUID.randomUUID().toString());
        noticeDto.setBizId(ticketId);
        noticeDto.setNoticeStatus("01");//通知状态：01待查看，02已查看，03忽略
        noticeDto.setNoticeTitle(title);
        String ticketType = ticketDto.getTicketType();
        if(!TextUtils.isEmpty(ticketType)){
            String comment="";
            if("1".equals(ticketType)){
                comment="告警工单";
            }else if("2".equals(ticketType)){
                comment="巡检工单";
            }else if("3".equals(ticketType)){
                comment="维保工单";
            }
            noticeDto.setNoticeType(comment);
        }
        noticeDto.setNoticeContent(ticketDto.getTicketReason());
        noticeDto.setNoticeGroupObj("01");
        noticeDto.setNoticeGroupRefId(userId);
        noticeDto.setCreateBy(u);
        noticeDto.setUpdateBy(u);
        noticeDto.setCreateDate(new Date());
        noticeDto.setIsNewRecord(true);
        noticeService.insertNoticeDetails(noticeDto);
    }

    /**
     * 待办列表中发起工单
     * @return
     */
    @Transactional(readOnly = false)
    public JSONObject startWorkOrder(String ticketId,String deptId,User user){
        JSONObject result=new JSONObject();
        //日志
        TicketLogDto log=new TicketLogDto();
        log.setId(UUID.randomUUID().toString());
        log.setTicketId(ticketId);
        log.setStatus("1");
        log.setCreateBy(user);
        log.setCreateDate(new Date());
        log.setUpdateBy(user);
        log.setUpdateDate(new Date());
        this.insertTicketLog(log);

        //1.先获取部门下有工单分发角色的所有人
        List<User> userList = this.getUserListByDeptId(deptId);
        //2.把人员插入代办表
        if(userList!=null && userList.size()>0){
            TicketToDoDto ticketToDoDto=null;
            for(User u:userList){
                //status :0待分发  1签收  (直接接受，不需要签收)
                ticketToDoDto =  new TicketToDoDto(UUID.randomUUID().toString(),ticketId,"","1",u.getId(),"1",new Date(),new Date(),user,user);
                this.insertTicketToDo(ticketToDoDto);
                result.put("code","201");
                result.put("status","success");
                result.put("message","工单创建成功!");
                //创建插入提示消息
                insertDetails(ticketId,user,"工单创建->待分发",u.getId());
            }
        }else{
            result.put("code","3001");
            result.put("message","该部门下没有工单分发角色的人员，请联系管理员!");
            throw new ServiceException(result.toJSONString());
        }
        TicketDto ticketDto =new TicketDto();
        ticketDto.setStatus("1");
        ticketDto.setTicketId(ticketId);

        //修改工单状态为新建（待部门负责人 分配）
        this.updateTicketInfo(ticketDto);
        return result;
    }

    /**
     * 创建工单
     * @param ticketDto
     * @return
     */
    @Transactional(readOnly = false)
    public JSONObject createWorkOrder(TicketDto ticketDto) throws Exception {
        JSONObject result=new JSONObject();
        //1,2,3
        /*String uCode = ticketDto.getCreateBy().getId().length()>6?ticketDto.getCreateBy().getId().substring(0,6):ticketDto.getCreateBy().getId();
        String p=ticketDto.getTicketType().equals("1")?"GJ":(ticketDto.getTicketType().equals("2")?"XJ":"WB");
        String ticketId= p+"-"+ DateUtils.formatDateTimeByFormat(new Date(),"yyyyMMddHHmmss")+"-"+uCode;
        ticketDto.setTicketId(ticketId);*/
        ticketDto.setValidFlag("1");
        this.insertTicket(ticketDto);
        //日志
        TicketLogDto log=new TicketLogDto();
        log.setId(UUID.randomUUID().toString());
        log.setTicketId(ticketDto.getTicketId());
        log.setStatus(ticketDto.getStatus());
        log.setCreateBy(ticketDto.getCreateBy());
        log.setCreateDate(new Date());
        log.setUpdateBy(ticketDto.getUpdateBy());
        log.setUpdateDate(new Date());
        this.insertTicketLog(log);
        //关联告警表（特殊处理）
        if("1".equals(ticketDto.getTicketType())){
            this.updateAlarmTicketByDeviceIdAndStartTime(ticketDto);
        }



        //是保存 还是创建
        if(StringUtils.isNotBlank(ticketDto.getSaveOrCreateFlag()) && ticketDto.getSaveOrCreateFlag().equals("save") ){
            TicketToDoDto ticketToDoDto =  new TicketToDoDto(UUID.randomUUID().toString(),ticketDto.getTicketId(),"","0",ticketDto.getCreateBy().getId(),"1",new Date(),new Date(),ticketDto.getUpdateBy(),ticketDto.getCreateBy());
                this.insertTicketToDo(ticketToDoDto);
            //1.先获取部门下有工单分发角色的所有人
            List<User> userList = this.getUserListByDeptId(ticketDto.getDeptId());
                if(userList!=null && userList.size()>0){
                }else{
                    result.put("code","3001");
                    result.put("message","该部门下没有工单分发角色的人员，请联系管理员!");
                    throw new ServiceException(result.toJSONString());
                }
        }else{
            //需求变更，发给部门负责人

            //获取角色下的 所有用户
            /*systemService.getUserIdByRoleId("");
            systemService.getAllRole();*/

            //根据部门ID获取负责人
            //List<String> userList= this.getUserIdByDeptId(ticketDto.getDeptId());
            //代办（部门下的人多能看到，状态为在签收）

            //1.先获取部门下有工单分发角色的所有人
             List<User> userList = this.getUserListByDeptId(ticketDto.getDeptId());
            //2.把人员插入代办表
            if(userList!=null && userList.size()>0){
                TicketToDoDto ticketToDoDto=null;
                for(User user:userList){
                    //status :0待分发  1签收  (直接接受，不需要签收)
                    ticketToDoDto =  new TicketToDoDto(UUID.randomUUID().toString(),ticketDto.getTicketId(),"","1",user.getId(),"1",new Date(),new Date(),ticketDto.getUpdateBy(),ticketDto.getCreateBy());
                    this.insertTicketToDo(ticketToDoDto);
                    result.put("code","201");
                    result.put("status","success");
                    result.put("message","工单创建成功!");
                    //创建插入提示消息
                    insertDetails(ticketDto.getTicketId(),ticketDto.getCreateBy(),"工单创建->待分发",user.getId());
                }
            }else{
                result.put("code","3001");
                result.put("message","该部门下没有工单分发角色的人员，请联系管理员!");
                throw new ServiceException(result.toJSONString());
            }

        }
        return result;
    }

    /**
     * 处理工单
     *
     *      * 01：分发业务到人修改为处理状态3、
     *      * 02回退业务分子修改为待分发状态2、
     *      * 03处理业务修改为待待审核状态4
     *      * 04审核不同意业务修改为处理状态3
     *      * 05审核同意业务修改为完成状态5
     *
     * @param ticketDto
     * @return
     */
    @Transactional(readOnly = false)
    public JSONObject handleWorkOrder(TicketDto ticketDto){
        JSONObject result=new JSONObject();
        try{
            TicketDto ticketInfo=ticketDao.getTicketInfo(ticketDto.getTicketId());
            //日志
            TicketLogDto log=new TicketLogDto();
            log.setId(UUID.randomUUID().toString());
            log.setTicketId(ticketDto.getTicketId());

            log.setApproveOpinion(ticketDto.getApproveOpinion());//处理结果 及其意见
            log.setApproveOperation(ticketDto.getApproveOperation());//同意不同意
            log.setCreateBy(ticketDto.getCreateBy());
            log.setCreateDate(new Date());
            log.setUpdateBy(ticketDto.getUpdateBy());
            log.setUpdateDate(new Date());
            String title="";
            String uId="";
            // 01：分发业务到人修改为 待接单状态2、
            if(StringUtils.isNotBlank(ticketDto.getHandleStatus()) && ticketDto.getHandleStatus().equals("01")){
                //工单为待分发 2  获取工单创建人的UserId
                String handleUserId = ticketDto.getHandleUserId();
                this.signIn(ticketDto.getTicketId(),handleUserId,"2","0",ticketDto.getCreateBy().getId());
                log.setStatus("2");
                log.setNodeId(NodeEnum.DISTRIBUTION.getValue());
                log.setNodeId(NodeEnum.DISTRIBUTION.getName());
                title="工单分发->待处理";
                uId=handleUserId;
            //退回（处理不了，退回给部门负责人）
            //02回退业务分子修改为待分发状态1、
            }else if(StringUtils.isNotBlank(ticketDto.getHandleStatus()) && ticketDto.getHandleStatus().equals("02")){
                //工单为待分发 1  获取工单部门负责人的UserId
                //List<String> mgUuer= this.getUserIdByDeptId(ticketDto.getDeptId());
                this.signIn(ticketDto.getTicketId(),ticketInfo.getTicketDistributeBy(),"1","1",null);
                log.setStatus("1");
                log.setNodeId(NodeEnum.SINGLE_BACK.getValue());
                log.setNodeId(NodeEnum.SINGLE_BACK.getName());
                title="工单回退->待分发";
                uId=ticketInfo.getTicketDistributeBy();
                //03处理业务修改为待审核状态4
            }else if(StringUtils.isNotBlank(ticketDto.getHandleStatus()) && ticketDto.getHandleStatus().equals("03")){
                //工单为待审核 4  获取工单部门负责人的UserId
                List<String> mgUuer= this.getUserIdByDeptId(ticketDto.getDeptId());
                this.signIn(ticketDto.getTicketId(),ticketInfo.getTicketDistributeBy(),"4","1",null);
                log.setStatus("4");
                log.setNodeId(NodeEnum.HANDLE.getValue());
                log.setNodeId(NodeEnum.HANDLE.getName());
                title="工单处理->待审核";
                uId=ticketInfo.getTicketDistributeBy();
            }else if(StringUtils.isNotBlank(ticketDto.getHandleStatus()) && ticketDto.getHandleStatus().equals("04")){
                //04审核不同意业务修改为处理状态3
                //获取工单最近处理人UserId
                String lastHandleUserId = this.getTicketLogLastUserIdByTicketId(ticketDto.getTicketId());
                this.signIn(ticketDto.getTicketId(),lastHandleUserId,"3","1",null);
                log.setStatus("3");
                log.setNodeId(NodeEnum.UN_AGREE.getValue());
                log.setNodeId(NodeEnum.UN_AGREE.getName());
                title="工单审核不通过->待重新处理";
                uId=lastHandleUserId;
            }else if(StringUtils.isNotBlank(ticketDto.getHandleStatus()) && ticketDto.getHandleStatus().equals("05")){
                //05审核同意业务修改为完成状态5
                ticketDto.setStatus("5");//完成
                this.updateTicketInfo(ticketDto);
                //清空历史待处理人
                boolean a=this.delTicketToDoByTicketId(ticketDto.getTicketId());
                log.setStatus("5");
                log.setNodeId(NodeEnum.AGREE.getValue());
                log.setNodeId(NodeEnum.AGREE.getName());
                title="工单审核同意";
                uId="";
                TicketDto obj=new TicketDto();
                obj.setTicketId(ticketDto.getTicketId());
                obj.setAlarmStatus("0");
                obj.setConfirmStatus("1");
                this.updateAlarmData(obj);
            }
            //创建插入提示消息
            insertDetails(ticketDto.getTicketId(),ticketDto.getCreateBy(),title,uId);

            this.insertTicketLog(log);

            ticketDto.setStatus(log.getStatus());
            this.updateTicketInfo(ticketDto);
            result.put("code","201");
            result.put("status","success");
            result.put("message","工单处理成功!");
        }catch (Exception e){
            result.put("code","500");
            result.put("message","工单处理失败，请核实!");
            throw new ServiceException(result.toJSONString());
        }
        return result;

    }

    /**
     * 单用户签收
     * @param ticketId
     * @param userId
     * @return
     */
    @Transactional(readOnly = false)
    public boolean signIn(String ticketId,String userId,String status,String signInStatus,String ticketDistributeBy){
        //必须是待签收状态 0,才能签收（否者已被他人签收）
        // to  do  ...
        //清空历史待处理人
        boolean a=this.delTicketToDoByTicketId(ticketId);
        User u=new User();
        u.setId(userId);
        //待插入当前  处理人
        TicketToDoDto ticketToDoDto =  new TicketToDoDto(UUID.randomUUID().toString(),ticketId,"",signInStatus,userId,"1",new Date(),new Date(),u,u);
        boolean b= this.insertTicketToDo(ticketToDoDto);
        //修改工单状态
        TicketDto ticketDto=new TicketDto();
        ticketDto.setTicketId(ticketId);
        ticketDto.setStatus(status);//处理中
        ticketDto.setTicketDistributeBy(ticketDistributeBy);//工单分配的操作人
        boolean c=this.updateTicketInfo(ticketDto);
        return a && b && c;
    }

    @Transactional(readOnly = false)
    public List<User> getUserListByDeptId(String deptId){
        return ticketDao.getUserListByDeptId(deptId);
    }
    /**
     * 新增工单
     * @param ticket
     * @return
     */
    @Transactional(readOnly = false)
    public boolean insertTicket(TicketDto ticket){
        int i=ticketDao.insertTicket(ticket);
        return i>0;
    }

    /**
     * 新增工单日子
     * @param ticketLogDto
     * @return
     */
    @Transactional(readOnly = false)
    public boolean insertTicketLog(TicketLogDto ticketLogDto){
        int i=ticketDao.insertTicketLog(ticketLogDto);
        return i>0;
    }

    /**
     * 修改告警业务表的工单ID（建立联系）
     * @param ticket
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updateAlarmTicketByDeviceIdAndStartTime(TicketDto ticket){
        int i=ticketDao.updateAlarmTicketByDeviceIdAndStartTime(ticket);
        return i>0;
    }
    @Transactional(readOnly = false)
    public boolean updateAlarmData(TicketDto ticket){
        int i=ticketDao.updateAlarmData(ticket);
        return i>0;
    }


    /**
     * 修改工单信息
     * @param ticket
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updateTicketInfo(TicketDto ticket){
        int i=ticketDao.updateTicketInfo(ticket);
        return i>0;
    }

    @Transactional(readOnly = false)
    public boolean delTicketInfo(TicketDto ticket){
        int i=ticketDao.updateTicketInfo(ticket);
        //删除告警工单时，需要清空告警表中 关联的工单ID
        TicketDto obj=new TicketDto();
        obj.setTicketId(ticket.getTicketId());
        obj.setClearTicketId(ticket.getTicketId());
        this.updateAlarmData(obj);
        return i>0;
    }


    /**
     * 编辑工单
     * @param ticketDto
     * @return
     */
    @Transactional(readOnly = false)
    public JSONObject updateWorkOrder(TicketDto ticketDto) throws Exception {
        JSONObject result=new JSONObject();
        try {
            ticketDto.setValidFlag("1");
            this.updateTicketInfo(ticketDto);
            result.put("code","201");
            result.put("status","success");
            result.put("message","工单编辑成功!");
        }catch (Exception e){
            result.put("code","3001");
            result.put("message","该部门下没有配置负责人员，请联系管理员!");
            throw new ServiceException(result.toJSONString());
        }
        return result;
    }


    /**
     * 新增代办
     * @param ticketToDoDto
     * @return
     */
    @Transactional(readOnly = false)
    public boolean insertTicketToDo(TicketToDoDto ticketToDoDto){
        int i=ticketDao.insertTicketToDo(ticketToDoDto);
        return i>0;
    }

    /**
     * 修改代办
     * @param ticketToDoDto
     * @return
     */
    @Transactional(readOnly = false)
    public boolean updateTicketToDoById(TicketToDoDto ticketToDoDto){
        int i=ticketDao.updateTicketToDoById(ticketToDoDto);
        return i>0;
    }

    /**
     * 删除工单对应的所有代办
     * @param ticketId
     * @return
     */
    @Transactional(readOnly = false)
   public boolean delTicketToDoByTicketId(String ticketId){
        int i=ticketDao.delTicketToDoByTicketId(ticketId);
        return i>0;
    }

    /**
     * 获取工单列表信息
     * @param ticket
     * @return
     */
    public List<TicketDto> getTicketList(TicketDto ticket){
        return ticketDao.getTicketList(ticket);
    }

    /**
     *
     * @param page 分页对象
     * @param ticketDto
     * @return
     */
    public Page<TicketDto> findPage(Page<TicketDto> page, TicketDto ticketDto) {
        return super.findPage(page, ticketDto);
    }

    /**
     *
     * @param page 分页对象
     * @param ticketDto
     * @return
     */
    public Page<TicketDto> findAllListPage(Page<TicketDto> page, TicketDto ticketDto) {
        return super.findListAllPage(page, ticketDto);
    }



    /**
     *获取对应工单的最后处理人
     * @param ticketId
     * @return
     */
    public  String getTicketLogLastUserIdByTicketId(String ticketId){
       return ticketDao.getTicketLogLastUserIdByTicketId(ticketId);
    }
    /**
     *获取对应工单的创建人
     * @param ticketId
     * @return
     */
    public  String getTicketCreateUserIdByTicketId(String ticketId){
        return ticketDao.getTicketCreateUserIdByTicketId(ticketId);
    }

    /**
     *获取对应工单
     * @param ticketId
     * @return
     */
    public  TicketDto getTicketInfo(String ticketId){
        return ticketDao.getTicketInfo(ticketId);
    }

    /**
     *获取对应工单的 处理日志
     * @param ticketId
     * @return
     */
    public  List<TicketLogDto> getTicketLogList(String ticketId){
        return ticketDao.getTicketLogList(ticketId);
    }

    public List<TicketComDto> getPumpList( String projectId){
        return ticketDao.getPumpList(projectId);
    }

    public  List<TicketComDto> getDeviceList(String id){
       return ticketDao.getDeviceList(id);
   }

    public String getTicketId(String mark){
        String res="";
        TicketDto ticketDto=new TicketDto();
        ticketDto.setTicketType(mark);
        TicketDto ticketInfo = ticketDao.getTicketInfoForObj(ticketDto);
        int uCode=0;
        if(ticketInfo!=null){
            String ticketId = ticketInfo.getTicketId();
            if(!TextUtils.isEmpty(ticketId)){
                try {
                    uCode = Integer.parseInt(ticketId.substring(10));
                }catch (Exception e){
                    uCode=0;
                }
            }
        }
        uCode++;
        String code = String.format("%03d", uCode);
        if("1".equals(mark)){//告警
            res= "GJ"+ DateUtils.formatDateTimeByFormat(new Date(),"yyyyMMdd")+code;
        }else if("2".equals(mark)){//巡检
            res= "XJ"+ DateUtils.formatDateTimeByFormat(new Date(),"yyyyMMdd")+code;
        }else if("3".equals(mark)){//维保
            res= "WB"+ DateUtils.formatDateTimeByFormat(new Date(),"yyyyMMdd")+code;
        }
        return res;
    }

}

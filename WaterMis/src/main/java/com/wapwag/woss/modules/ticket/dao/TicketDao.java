package com.wapwag.woss.modules.ticket.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.User;
import com.wapwag.woss.modules.ticket.Entity.TicketComDto;
import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import com.wapwag.woss.modules.ticket.Entity.TicketLogDto;
import com.wapwag.woss.modules.ticket.Entity.TicketToDoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao("com.wapwag.woss.modules.ticket.dao.TicketDao")
public interface TicketDao  extends CrudDao<TicketDto> {
    /**
     * 获取所有部门组织
    * @return
    */
    List<TicketDto> getDeptList();

    /**
     * 新增工单
     * @param ticket
     * @return
     */
    int insertTicket(TicketDto ticket);

    /**
     * 新增工单日子
     * @param ticketLogDto
     * @return
     */
    int insertTicketLog(TicketLogDto ticketLogDto);

    /**
     * 新增代办
     * @param ticketToDoDto
     * @return
     */
    int insertTicketToDo(TicketToDoDto ticketToDoDto);

    /**
     * 修改代办
     * @param ticketToDoDto
     * @return
     */
    int updateTicketToDoById(TicketToDoDto ticketToDoDto);

    /**
     * 删除工单对应的所有代办
     * @param ticketId
     * @return
     */
    int delTicketToDoByTicketId(String ticketId);

    /**
     * 获取部门下的所有人员
     * @param deptId
     * @return
     */
    List<User> getUserListByDeptId(String deptId);
    /**
     * 修改告警业务表的工单ID（建立联系）
     * @param ticket
     * @return
     */
    int updateAlarmTicketByDeviceIdAndStartTime(TicketDto ticket);

    /**
     * 修改工单信息
     * @param ticket
     * @return
     */
    int updateTicketInfo(TicketDto ticket);

    /**
     * 获取工单列表信息
     * @param ticket
     * @return
     */
    List<TicketDto> getTicketList(TicketDto ticket);

    /**
     * 获取对应工单的最后处理人
     * @param ticketId
     * @return
     */
    String getTicketLogLastUserIdByTicketId(String ticketId);

    /**
     * 获取对应工单的创建人
     * @param ticketId
     * @return
     */
    String getTicketCreateUserIdByTicketId(String ticketId);

    TicketDto getTicketInfo(@Param("ticketId") String ticketId);

    List<TicketLogDto> getTicketLogList(@Param("ticketId") String ticketId);

    List<TicketComDto> getPumpList();

    List<TicketComDto> getDeviceList(@Param("id") String id);


}

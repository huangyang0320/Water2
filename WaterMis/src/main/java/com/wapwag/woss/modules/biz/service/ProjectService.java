package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.Project;
import com.wapwag.woss.modules.biz.dao.ProjectDao;

/**
 * 项目Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class ProjectService extends CrudService<ProjectDao, Project> {

	public Project get(String id) {
		return super.get(id);
	}
	
	public List<Project> findList(Project project) {
		return super.findList(project);
	}
	
	public Page<Project> findPage(Page<Project> page, Project project) {
		return super.findPage(page, project);
	}
	
	@Transactional(readOnly = false)
	public void save(Project project) {
		super.save(project);
	}
	
	@Transactional(readOnly = false)
	public void delete(Project project) {
		super.delete(project);
	}
	
}
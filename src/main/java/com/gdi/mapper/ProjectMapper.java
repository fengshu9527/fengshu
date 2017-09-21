package com.gdi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdi.bean.pojo.Project;

public interface ProjectMapper {
	int insert(Project project);
	Project findById(@Param("id") int id);
	List<Project> findProjectList();
}

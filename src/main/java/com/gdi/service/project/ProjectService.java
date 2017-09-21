package com.gdi.service.project;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.gdi.bean.pojo.Project;
import com.gdi.bean.returns.Return;

public interface ProjectService {
	/**
	 * 根据页码获取项目列表
	 * @param pageIndex
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	Return getProjectList(int pageIndex) throws InterruptedException, ExecutionException;
	/**
	 * 根据代币名称获取项目详情
	 * @param tokenName
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	Return getProjectByTokenName(String tokenName) throws InterruptedException, ExecutionException;
	/**
	 * 添加项目
	 * @param project
	 * @return
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	Map<String,Object> addProject(Project project) throws ParseException, InterruptedException, ExecutionException, IOException;
}

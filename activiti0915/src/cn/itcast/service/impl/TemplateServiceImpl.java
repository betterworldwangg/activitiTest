package cn.itcast.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.ITemplateDao;
import cn.itcast.domain.Template;
import cn.itcast.service.ITemplateService;
/**
 * 模板管理service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class TemplateServiceImpl implements ITemplateService {
	@Autowired
	private ITemplateDao templateDao;

	/**
	 * 查询模板列表
	 */
	public List<Template> findList() {
		return templateDao.findList();
	}

	/**
	 * 根据id删除模板数据，同时删除对应的doc文件
	 */
	public void deleteById(Integer id) {
		//根据id查询一个模板对象
		Template template = templateDao.findById(id);
		
		//获得此模板对象对应的doc文件的存储路径
		String filePath = template.getFilePath();//d:\test.docx
		
		//删除模板对象
		templateDao.delete(template);
		
		//构造一个File对象
		File file = new File(filePath);
		
		//如果当前文件存在，就删除
		if(file.exists()){
			file.delete();
		}
	}

	public void save(Template model) {
		templateDao.save(model);
	}

	/**
	 * 根据模板的id获得对应的doc文件的输入流
	 */
	public InputStream findDocStreamById(Integer id) {
		Template template = templateDao.findById(id);
		String filePath = template.getFilePath();
		InputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}

	public String findNameById(Integer id) {
		return templateDao.findNameById(id);
	}

	public Template findById(Integer id) {
		return templateDao.findById(id);
	}

	public void update(Template template) {
		templateDao.update(template);
	}
}

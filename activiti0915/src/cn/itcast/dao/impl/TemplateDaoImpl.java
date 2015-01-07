package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.ITemplateDao;
import cn.itcast.domain.Template;

/**
 * 模板管理Dao
 * @author zhaoqx
 *
 */
@Repository
public class TemplateDaoImpl implements ITemplateDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 查询模板列表
	 */
	public List<Template> findList() {
		String hql = "FROM Template t ORDER BY t.id DESC";
		return hibernateTemplate.find(hql);
	}

	public Template findById(Integer id) {
		return hibernateTemplate.get(Template.class, id);
	}

	public void delete(Template template) {
		hibernateTemplate.delete(template);
	}

	public void save(Template model) {
		hibernateTemplate.save(model);
	}

	public String findNameById(Integer id) {
		String hql = "SELECT t.name FROM Template t WHERE t.id = ?";
		List<String> list = hibernateTemplate.find(hql,id);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public void update(Template template) {
		hibernateTemplate.update(template);
	}
}

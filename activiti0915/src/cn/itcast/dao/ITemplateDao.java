package cn.itcast.dao;

import java.util.List;

import cn.itcast.domain.Template;

public interface ITemplateDao {

	public List<Template> findList();

	public Template findById(Integer id);

	public void delete(Template template);

	public void save(Template model);

	public String findNameById(Integer id);

	public void update(Template template);

}

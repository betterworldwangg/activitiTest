package cn.itcast.service;

import java.io.InputStream;
import java.util.List;

import cn.itcast.domain.Template;

public interface ITemplateService {

	public List<Template> findList();

	public void deleteById(Integer id);

	public void save(Template model);

	public InputStream findDocStreamById(Integer id);

	public String findNameById(Integer id);

	public Template findById(Integer id);

	public void update(Template template);

}

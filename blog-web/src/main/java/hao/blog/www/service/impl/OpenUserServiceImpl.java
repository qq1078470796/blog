package hao.blog.www.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hao.blog.www.dao.OpenUserMapper;
import hao.blog.www.entity.OpenUser;
import hao.blog.www.service.OpenUserService;

import java.util.List;


@Service
public class OpenUserServiceImpl implements OpenUserService {
	@Autowired
	private OpenUserMapper openUserMapper;

	public OpenUser findByOpenId(String openId) {
		OpenUser openUser = new OpenUser();
		openUser.setOpenId( openId );
		return openUserMapper.selectOne( openUser );
	}

	public OpenUser findById(Long id) {
		return openUserMapper.selectByPrimaryKey( id );
	}

	public OpenUser findByUidAndType(Long uid, String type) {
		OpenUser openUser = new OpenUser();
		openUser.setuId( uid );
		openUser.setOpenType( type );
		return openUserMapper.selectOne( openUser );
	}

	public List<OpenUser> findByUid(Long uid) {
		OpenUser openUser = new OpenUser();
		openUser.setuId( uid );
		return openUserMapper.select( openUser );
	}

	public void add(OpenUser openUser) {
		openUserMapper.insert( openUser );
	}

	public void update(OpenUser openUser) {
		openUserMapper.updateByPrimaryKeySelective( openUser );

	}

	public void deleteByOpenId(String openId) {
		OpenUser openUser = new OpenUser();
		openUser.setOpenId( openId );
		openUserMapper.delete( openUser );

	}

	public void deleteById(Long openId) {
		openUserMapper.deleteByPrimaryKey( openId );
	}

	@Override
	public void deleteByUidAndType(Long uid, String type) {
		OpenUser openUser = new OpenUser();
		openUser.setuId(uid);
		openUser.setOpenType(type);
		openUserMapper.delete(openUser);
	}
}

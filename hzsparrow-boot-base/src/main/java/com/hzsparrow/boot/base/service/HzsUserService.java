package com.hzsparrow.boot.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzsparrow.boot.base.contant.DeleteFlagEnum;
import com.hzsparrow.boot.base.entity.HzsUser;
import com.hzsparrow.boot.base.mapper.HzsUserMapper;
import com.hzsparrow.boot.base.vo.LoginVO;
import com.hzsparrow.framework.model.page.PageQO;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HzsUserService {

    @Autowired
    private HzsUserMapper hzsUserMapper;

    @Autowired
    private HzsFileService hzsFileService;

    /**
     * 新增
     *
     * @param entity
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> save(HzsUser entity, LoginVO user) {
        entity.setHsuId(UUID.randomUUID().toString());
        entity.setHsuPassword(getEntryPassword(entity.getHsuAccount(), entity.getHsuPassword()));
        entity.setDeleteFlag(DeleteFlagEnum.NORMAL.getValue());
        entity.setCrtId(user.getUserId());
        entity.setCrtName(user.getUserName());
        entity.setCrtTime(new Date());
        entity.setMdfId(user.getUserId());
        entity.setMdfName(user.getUserName());
        entity.setMdfTime(new Date());
        hzsUserMapper.insert(entity);
        if (StringUtils.isNotBlank(entity.getImgId())) {
            hzsFileService.updateRelation(entity.getHsuId(), "hzs_user", entity.getImgId());
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 修改
     *
     * @param entity
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> edit(HzsUser entity, LoginVO user) {
        HzsUser oldEntity = getById(entity.getHsuId()).getData();
        if (StringUtils.isNotBlank(entity.getHsuPassword())) {
            entity.setHsuPassword(getEntryPassword(oldEntity.getHsuAccount(), entity.getHsuPassword()));
        } else {
            entity.setHsuPassword(oldEntity.getHsuPassword());
        }
        entity.setMdfId(user.getUserId());
        entity.setMdfName(user.getUserName());
        entity.setMdfTime(new Date());
        if (!StringUtils.equals(entity.getImgId(), oldEntity.getImgId())) {
            hzsFileService.updateRelation(entity.getHsuId(), "hzs_user", entity.getImgId());
        }
        hzsUserMapper.updateByPrimaryKey(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 删除
     *
     * @param hsuId
     * @return
     */
    @Transactional
    public ResultDTO<Object> remove(String[] hsuId) {
        for (String id : hsuId) {
            hzsUserMapper.remove(DeleteFlagEnum.DESTROY.getValue(), id);
            hzsFileService.removeByRelationId(id);
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 修改密码
     *
     * @param hsuId       用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Transactional
    public ResultDTO<Object> changePassword(String hsuId, String oldPassword, String newPassword) {
        HzsUser userEntity = getById(hsuId).getData();
        if (userEntity == null) {
            return ResultDTO.getOptionFaild(200, "用户不存在!");
        }
        String entryPassword = getEntryPassword(userEntity.getHsuAccount(), oldPassword);
        if (!StringUtils.equals(userEntity.getHsuPassword(), entryPassword)) {
            return ResultDTO.getOptionFaild(200, "原密码输入错误！");
        }
        String newEntityPassword = getEntryPassword(userEntity.getHsuAccount(), newPassword);
        hzsUserMapper.changePassword(hsuId, newEntityPassword);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 分页查询
     *
     * @param hsuName
     * @param mobile
     * @param pageQO
     * @return
     */
    public ResultDTO<PageResultDTO<HzsUser>> pageFindAll(String hsuName, String mobile, PageQO pageQO) {
        PageHelper.startPage(pageQO.getPage(), pageQO.getSize());
        List<HzsUser> list = hzsUserMapper.selectAll(hsuName, mobile);
        return ResultDTO.getDataSuccess(new PageResultDTO<>((Page<HzsUser>) list));
    }

    /**
     * 通过账号获取用户信息
     *
     * @param hsuAccount
     * @return
     */
    public ResultDTO<HzsUser> getByAccount(String hsuAccount) {
        HzsUser entity = hzsUserMapper.getByAccount(hsuAccount);
        return ResultDTO.getDataSuccess(entity);
    }

    /**
     * 获取用户信息
     *
     * @param hsuId
     * @return
     */
    public ResultDTO<HzsUser> getById(String hsuId) {
        HzsUser entity = hzsUserMapper.selectByPrimaryKey(hsuId);
        return ResultDTO.getDataSuccess(entity);
    }

    /**
     * 通过账号和密码计算获得加密密码
     *
     * @param account
     * @param password
     * @return
     */
    public String getEntryPassword(String account, String password) {
        return new Md5Hash(account + password).toHex();
    }
}

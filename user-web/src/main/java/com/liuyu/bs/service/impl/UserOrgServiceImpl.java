package com.liuyu.bs.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.liuyu.bs.business.*;
import com.liuyu.bs.dao.CityDao;
import com.liuyu.bs.dao.CountyDao;
import com.liuyu.bs.dao.ProvinceDao;
import com.liuyu.bs.dao.SchoolDao;
import com.liuyu.bs.service.UserOrgService;
import com.liuyu.user.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName: UserOrgServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-11 上午11:29 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class UserOrgServiceImpl implements UserOrgService {
    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private CountyDao countyDao;
    @Autowired
    private CityDao cityDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Override
    public Org getOrg(User user) {
        return null;
    }

    @Override
    public List<Org> queryUserOrg(User user, String searchName) {
        return null;
    }

    @Override
    public Province getProvince(User user) {
        Province province = null;
        if (user.isSuperAdmin()) {
            province = getProvince(null, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isProvince(org)) {
                province = OrgHelper.converTo(org, Province.class);
            } else if (OrgHelper.isCounty(org)) {
                province = cityDao.getCityOwnerProvince(org.getCode());
            } else if (OrgHelper.isCounty(org)) {
                province = countyDao.getCountyOwnerProvince(org.getCode());
            } else if (OrgHelper.isSchool(org)) {
                province = schoolDao.getSchoolOwnerProvince(org.getCode());
            } else {
                province = getProvince(org.getCode(), org.getDeep());
            }
        }
        return province;
    }

    private Province getProvince(String code, int deep) {
        Province province = null;
        PageHelper.startPage(0, 1);
        List<Province> provinces = provinceDao.queryProvinces(code, null, deep);
        if (!provinces.isEmpty()) {
            province = provinces.get(0);
        }
        return province;
    }

    @Override
    public List<Province> queryUserProvince(User user, String searchName) {
        if (StringUtils.isEmpty(searchName)) {
            searchName = null;
        } else {
            searchName = "%" + searchName + "%";
        }
        List<Province> provinces = null;
        if (user.isSuperAdmin()) {
            provinces = provinceDao.queryProvinces(null, searchName, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isProvince(org)) {
                Province province = OrgHelper.converTo(org, Province.class);
                provinces = Lists.newArrayList(province);
            } else if (OrgHelper.isCounty(org)) {
                Province province = cityDao.getCityOwnerProvince(org.getCode());
                provinces = Lists.newArrayList(province);
            } else if (OrgHelper.isCounty(org)) {
                Province province = countyDao.getCountyOwnerProvince(org.getCode());
                provinces = Lists.newArrayList(province);
            } else if (OrgHelper.isSchool(org)) {
                Province province = schoolDao.getSchoolOwnerProvince(org.getCode());
                provinces = Lists.newArrayList(province);
            } else {
                provinces = provinceDao.queryProvinces(org.getCode(), searchName, org.getDeep());
            }
        }
        return provinces;
    }

    @Override
    public City getCity(User user) {
        City city = null;
        if (user.isSuperAdmin()) {
            city = getCity(null, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isCity(org)) {
                city = OrgHelper.converTo(org, City.class);
            } else if (OrgHelper.isCounty(org)) {
                city = countyDao.getCountyOwnerCity(org.getCode());
            } else if (OrgHelper.isSchool(org)) {
                city = schoolDao.getSchoolOwnerCity(org.getCode());
            } else {
                city = getCity(org.getCode(), org.getDeep());
            }
        }
        return city;
    }

    private City getCity(String code, int deep) {
        City city = null;
        PageHelper.startPage(0, 1);
        List<City> cities = cityDao.queryCitys(code, null, deep);
        if (!cities.isEmpty()) {
            city = cities.get(0);
        }
        return city;
    }


    @Override
    public List<City> queryUserCity(User user, String searchName) {
        if (StringUtils.isEmpty(searchName)) {
            searchName = null;
        } else {
            searchName = "%" + searchName + "%";
        }
        List<City> cities = null;
        if (user.isSuperAdmin()) {
            cities = cityDao.queryCitys(null, searchName, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isCity(org)) {
                City city = OrgHelper.converTo(org, City.class);
                cities = Lists.newArrayList(city);
            } else if (OrgHelper.isCounty(org)) {
                City city = countyDao.getCountyOwnerCity(org.getCode());
                cities = Lists.newArrayList(city);
            } else if (OrgHelper.isSchool(org)) {
                City city = schoolDao.getSchoolOwnerCity(org.getCode());
                cities = Lists.newArrayList(city);
            } else {
                cities = cityDao.queryCitys(org.getCode(), searchName, org.getDeep());
            }
        }
        return cities;
    }

    @Override
    public County getCounty(User user) {
        County county = null;
        if (user.isSuperAdmin()) {
            county = getCounty(null, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isSchool(org)) {
                county = schoolDao.getSchoolOwnerCounty(org.getCode());
            } else if (OrgHelper.isCounty(org)) {
                county = OrgHelper.converTo(org, County.class);
            } else {
                county = getCounty(org.getCode(), org.getDeep());
            }
        }
        return county;
    }

    private County getCounty(String code, int deep) {
        County county = null;
        PageHelper.startPage(0, 1);
        List<County> counties = countyDao.queryCountys(code, null, deep);
        if (!counties.isEmpty()) {
            county = counties.get(0);
        }
        return county;
    }

    @Override
    public List<County> queryUserCounty(User user, String searchName) {
        if (StringUtils.isEmpty(searchName)) {
            searchName = null;
        } else {
            searchName = "%" + searchName + "%";
        }
        List<County> counties = null;
        if (user.isSuperAdmin()) {
            counties = countyDao.queryCountys(null, searchName, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isSchool(org)) {
                County county = schoolDao.getSchoolOwnerCounty(org.getCode());
                counties = Lists.newArrayList(county);
            } else if (OrgHelper.isCounty(org)) {
                County county = OrgHelper.converTo(org, County.class);
                counties = Lists.newArrayList(county);
            } else {
                counties = countyDao.queryCountys(org.getCode(), searchName, org.getDeep());
            }
        }
        return counties;
    }

    @Override
    public School getSchool(User user) {
        School school = null;
        if (user.isSuperAdmin()) {
            school = getSchool(null, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isSchool(org)) {
                school = OrgHelper.converTo(org, School.class);
            } else {
                school = getSchool(org.getCode(), org.getDeep());
            }
        }
        return school;
    }

    private School getSchool(String code, int deep) {
        School school = null;
        PageHelper.startPage(0, 1);
        List<School> schools = schoolDao.querySchools(code, null, deep);
        if (!schools.isEmpty()) {
            school = schools.get(0);
        }
        return school;
    }


    @Override
    public List<School> queryUserSchools(User user, String searchName) {
        if (StringUtils.isEmpty(searchName)) {
            searchName = null;
        } else {
            searchName = "%" + searchName + "%";
        }
        List<School> schools = null;
        if (user.isSuperAdmin()) {
            schools = schoolDao.querySchools(null, searchName, -1);
        } else {
            Org org = user.queryOrg();
            if (OrgHelper.isSchool(org)) {
                School school = OrgHelper.converTo(org, School.class);
                schools = Lists.newArrayList(school);
            } else {
                schools = schoolDao.querySchools(org.getCode(), searchName, org.getDeep());
            }
        }
        return schools;
    }

    @Override
    public List<Teacher> getTeacher(User user) {
        return null;
    }
}

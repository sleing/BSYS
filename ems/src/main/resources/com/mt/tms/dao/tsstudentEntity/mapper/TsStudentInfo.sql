
/* find(newMenuId)  */ select max(`menu_id`)+1  as value from  `sys_menu`;
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="团学会学生信息管理";
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = 0 ;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, 0, '团学会学生信息管理', 'MenuOutlined', '','', 0,0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="团学会学生信息管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  ="团学会学生信息管理管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '团学会学生信息管理管理', 'MenuOutlined', '/tms/tsstudentEntity/tsStudentInfo',
'/tms/tsstudentEntity/tsStudentInfo',0, 0,${sort_number}, 'tms:tsstudentEntity:TsStudentInfo:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="团学会学生信息管理管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "查询团学会学生信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '查询团学会学生信息管理', '', '',
'',1,0, ${sort_number}, 'tms:tsstudentEntity:TsStudentInfo:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "添加团学会学生信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '添加团学会学生信息管理', '', '', '', 1, 0,${sort_number}, 'tms:tsstudentEntity:TsStudentInfo:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "修改团学会学生信息管理";
/* find(newMenuId)  */ select max(`menu_id`) +1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '修改团学会学生信息管理', '', '',
'', 1,0, ${sort_number}, 'tms:tsstudentEntity:TsStudentInfo:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "删除团学会学生信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  =  ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '删除团学会学生信息管理', '', '',
'', 1, 0,${sort_number}, 'tms:tsstudentEntity:TsStudentInfo:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});




                                                
/* find(dict_id)  */ select `dict_id` from  `sys_dict` where `dict_name`  ="政治面貌";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict  t ;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id,dict_name,sort_number)
    VALUES (${new_dict_id}, "政治面貌", ${sort_number});
/* find(dict_id)  */ select `dict_id` from  `sys_dict` where `dict_name`  ="政治面貌";

        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="中共党员";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "中共党员", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="预备党员";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "预备党员", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="共青团员";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "共青团员", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="群众";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "群众", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="其他";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "其他", ${sort_number});
        
                    
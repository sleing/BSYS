
/* find(newMenuId)  */ select max(`menu_id`)+1  as value from  `sys_menu`;
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="部门信息管理";
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = 0 ;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, 0, '部门信息管理', 'MenuOutlined', '','', 0,0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="部门信息管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  ="部门信息管理管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '部门信息管理管理', 'MenuOutlined', '/tms/tsdepartmentEntity/tsDepartment',
'/tms/tsdepartmentEntity/tsDepartment',0, 0,${sort_number}, 'tms:tsdepartmentEntity:TsDepartment:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="部门信息管理管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "查询部门信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '查询部门信息管理', '', '',
'',1,0, ${sort_number}, 'tms:tsdepartmentEntity:TsDepartment:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "添加部门信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '添加部门信息管理', '', '', '', 1, 0,${sort_number}, 'tms:tsdepartmentEntity:TsDepartment:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "修改部门信息管理";
/* find(newMenuId)  */ select max(`menu_id`) +1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '修改部门信息管理', '', '',
'', 1,0, ${sort_number}, 'tms:tsdepartmentEntity:TsDepartment:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "删除部门信息管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  =  ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '删除部门信息管理', '', '',
'', 1, 0,${sort_number}, 'tms:tsdepartmentEntity:TsDepartment:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});




                
/* find(dict_id)  */ select `dict_id` from  `sys_dict` where `dict_name`  ="部门级别";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict  t ;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id,dict_name,sort_number)
    VALUES (${new_dict_id}, "部门级别", ${sort_number});
/* find(dict_id)  */ select `dict_id` from  `sys_dict` where `dict_name`  ="部门级别";

        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="院级";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "院级", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="校级";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "校级", ${sort_number});
        
                                                            
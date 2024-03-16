
/* find(newMenuId)  */ select max(`menu_id`)+1  as value from  `sys_menu`;
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="学生管理";
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = 0 ;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, 0, '学生管理', 'MenuOutlined', '','', 0,0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="学生管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  ="学生管理";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '学生管理', 'MenuOutlined', '/ems/student/student',
'/ems/student/student',0, 0,${sort_number}, 'ems:student:Student:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id` from  `sys_menu` where `title`  ="学生管理";

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "查询学生";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value  from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '查询学生', '', '',
'',1,0, ${sort_number}, 'ems:student:Student:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "添加学生";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '添加学生', '', '', '', 1, 0,${sort_number}, 'ems:student:Student:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "修改学生";
/* find(newMenuId)  */ select max(`menu_id`) +1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_menu  t where parent_id  = ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '修改学生', '', '',
'', 1,0, ${sort_number}, 'ems:student:Student:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id` from  `sys_menu` where `title`  = "删除学生";
/* find(newMenuId)  */ select max(`menu_id`)+1 as value from  `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1 as value from sys_menu  t where parent_id  =  ${moduleId} ;
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`title`,`icon`,`path`,`component`,`menu_type`,`open_type`,`sort_number`,`authority`) values
(${newMenuId}, ${moduleId}, '删除学生', '', '',
'', 1, 0,${sort_number}, 'ems:student:Student:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});




                                        
/* find(dict_id)  */ select `dict_id` from  `sys_dict` where `dict_name`  ="Gender";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict  t ;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id,dict_name,sort_number)
    VALUES (${new_dict_id}, "Gender", ${sort_number});
/* find(dict_id)  */ select `dict_id` from  `sys_dict` where `dict_name`  ="Gender";

        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="男";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "男", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="女";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "女", ${sort_number});
        
/* find(dict_parent_id)  */ select `dict_id` from  `sys_dict` where parent_id = ${dict_id} and `dict_name`  ="未知";
/* find(new_dict_id)  */ select max(`dict_id`) +1 as value from  `sys_dict` ;
/* find(sort_number)  */ select IFNULL(max(t.sort_number),0)+1  as value from sys_dict t  where t.parent_id = ${dict_id} ;
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id,parent_id,dict_name,sort_number)
        VALUES (${new_dict_id},${dict_id}, "未知", ${sort_number});
        
                    
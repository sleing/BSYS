/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学生处评优评先管理";
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = 0;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                                 `menu_type`, `open_type`, `sort_number`, `authority`)
                          values (${newMenuId}, 0, '学生处评优评先管理', 'MenuOutlined', '', '', 0, 0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                          values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学生处评优评先管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "评优评先登记管理";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '评优评先登记管理', 'MenuOutlined',
                                '/ams/appraisingEntity/appraising',
                                '/ams/appraisingEntity/appraising', 0, 0, ${sort_number},
                                'ams:appraisingEntity:Appraising:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "评优评先登记管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "查询评优评先登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '查询评优评先登记', '', '',
                                '', 1, 0, ${sort_number}, 'ams:appraisingEntity:Appraising:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "添加评优评先登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '添加评优评先登记', '', '', '', 1, 0, ${sort_number},
                                'ams:appraisingEntity:Appraising:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "修改评优评先登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '修改评优评先登记', '', '',
                                '', 1, 0, ${sort_number}, 'ams:appraisingEntity:Appraising:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "删除评优评先登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '删除评优评先登记', '', '',
                                '', 1, 0, ${sort_number}, 'ams:appraisingEntity:Appraising:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "性别";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "性别", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "性别";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "男";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "男", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "女";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "女", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "有无";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "有无", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "有无";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "有";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "有", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "无";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "无", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "有无";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "有无", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "有无";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "有";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "有", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "无";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "无", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "是否";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "是否", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "是否";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "是";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "是", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "否";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "否", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "审核状态";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "审核状态", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "审核状态";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "未审核";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "未审核", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "审核通过";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "审核通过", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "审核未通过";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "审核未通过", ${sort_number});
        
                                
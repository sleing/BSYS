/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学生获奖登记管理";
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = 0;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                                 `menu_type`, `open_type`, `sort_number`, `authority`)
                          values (${newMenuId}, 0, '学生获奖登记管理', 'MenuOutlined', '', '', 0, 0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                          values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学生获奖登记管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "获奖登记管理";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '获奖登记管理', 'MenuOutlined', '/ams/awardEntity/award',
                                '/ams/awardEntity/award', 0, 0, ${sort_number}, 'ams:awardEntity:Award:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "获奖登记管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "查询获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '查询获奖登记', '', '',
                                '', 1, 0, ${sort_number}, 'ams:awardEntity:Award:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "添加获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '添加获奖登记', '', '', '', 1, 0, ${sort_number},
                                'ams:awardEntity:Award:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "修改获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '修改获奖登记', '', '',
                                '', 1, 0, ${sort_number}, 'ams:awardEntity:Award:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "删除获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '删除获奖登记', '', '',
                                '', 1, 0, ${sort_number}, 'ams:awardEntity:Award:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "获奖级别";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "获奖级别", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "获奖级别";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "国家级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "国家级", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "省市级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "省市级", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "省级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "省级", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "市级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "市级", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "校级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "校级", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "院级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "院级", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "获奖等级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "获奖等级", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "获奖等级";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "特等";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "特等", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "一等";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "一等", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "二等";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "二等", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "三等";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "三等", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "金奖";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "金奖", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "银奖";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "银奖", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "铜奖";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "铜奖", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "参与奖";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "参与奖", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "优胜奖";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "优胜奖", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "其他";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "其他", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "竞赛类别";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "竞赛类别", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "竞赛类别";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "政府类";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "政府类", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "学会类";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "学会类", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "未定类";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "未定类", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "举办单位等级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "举办单位等级", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "举办单位等级";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "I";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "I", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "II";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "II", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "未定级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "未定级", ${sort_number});


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
        
                            